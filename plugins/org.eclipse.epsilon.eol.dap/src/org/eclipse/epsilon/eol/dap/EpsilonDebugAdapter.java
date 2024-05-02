/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.eol.dap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.Position;
import org.eclipse.epsilon.common.parse.Region;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.debug.EolDebugger;
import org.eclipse.epsilon.eol.debug.IEpsilonDebugTarget;
import org.eclipse.epsilon.eol.debug.SuspendReason;
import org.eclipse.epsilon.eol.dom.Import;
import org.eclipse.epsilon.eol.dom.Operation;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.Frame;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.SingleFrame;
import org.eclipse.epsilon.eol.execute.control.IExecutionListener;
import org.eclipse.lsp4j.debug.Breakpoint;
import org.eclipse.lsp4j.debug.Capabilities;
import org.eclipse.lsp4j.debug.ContinueArguments;
import org.eclipse.lsp4j.debug.ContinueResponse;
import org.eclipse.lsp4j.debug.DisconnectArguments;
import org.eclipse.lsp4j.debug.ExitedEventArguments;
import org.eclipse.lsp4j.debug.InitializeRequestArguments;
import org.eclipse.lsp4j.debug.NextArguments;
import org.eclipse.lsp4j.debug.OutputEventArguments;
import org.eclipse.lsp4j.debug.OutputEventArgumentsCategory;
import org.eclipse.lsp4j.debug.Scope;
import org.eclipse.lsp4j.debug.ScopesArguments;
import org.eclipse.lsp4j.debug.ScopesResponse;
import org.eclipse.lsp4j.debug.SetBreakpointsArguments;
import org.eclipse.lsp4j.debug.SetBreakpointsResponse;
import org.eclipse.lsp4j.debug.SetExceptionBreakpointsArguments;
import org.eclipse.lsp4j.debug.SetExceptionBreakpointsResponse;
import org.eclipse.lsp4j.debug.Source;
import org.eclipse.lsp4j.debug.SourceBreakpoint;
import org.eclipse.lsp4j.debug.StackFrame;
import org.eclipse.lsp4j.debug.StackTraceArguments;
import org.eclipse.lsp4j.debug.StackTraceResponse;
import org.eclipse.lsp4j.debug.StepInArguments;
import org.eclipse.lsp4j.debug.StepOutArguments;
import org.eclipse.lsp4j.debug.StoppedEventArguments;
import org.eclipse.lsp4j.debug.StoppedEventArgumentsReason;
import org.eclipse.lsp4j.debug.TerminateArguments;
import org.eclipse.lsp4j.debug.TerminatedEventArguments;
import org.eclipse.lsp4j.debug.ThreadsResponse;
import org.eclipse.lsp4j.debug.Variable;
import org.eclipse.lsp4j.debug.VariablesArguments;
import org.eclipse.lsp4j.debug.VariablesResponse;
import org.eclipse.lsp4j.debug.services.IDebugProtocolClient;
import org.eclipse.lsp4j.debug.services.IDebugProtocolServer;

/**
 * <p>
 * Implements the Debug Adapter Protocol for Epsilon scripts. Only supports
 * attaching to existing modules.
 * </p>
 *
 * <p>
 * For details about DAP, see
 * <a href="https://microsoft.github.io/debug-adapter-protocol/overview">Microsoft's website</a>.
 * </p>
 */
public class EpsilonDebugAdapter implements IDebugProtocolServer, IEpsilonDebugTarget {

	private static final int EPSILON_THREAD_ID = 1;

	protected class ModuleCompletionListener implements IExecutionListener {
		@Override
		public void aboutToExecute(ModuleElement ast, IEolContext context) {
			// nothing for now
		}

		@Override
		public void finishedExecuting(ModuleElement ast, Object result, IEolContext context) {
			if (ast == module) {
				module.getContext().getOutputStream().flush();
				module.getContext().getErrorStream().flush();
				sendTerminated();
				sendExited(0);
			}
		}

		@Override
		public void finishedExecutingWithException(ModuleElement ast, EolRuntimeException exception, IEolContext context) {
			if (ast == module) {
				sendTerminated();
				sendExited(1);
			}
		}
	}

	protected PrintStream createStream(String category) {
		return new PrintStream(new DAPTeeByteArrayOutputStream(category), true);
	}

	protected class DAPTeeByteArrayOutputStream extends ByteArrayOutputStream {
		private String category;

		public DAPTeeByteArrayOutputStream(String category) {
			this.category = category;
		}
		
		@Override
		public void flush() throws IOException {
			String output = new String(toByteArray(), StandardCharsets.UTF_8);
			if (output.length() > 0) {
				reset();
				sendOutput(category, output);
			}
		}
	}

	private static final Logger LOGGER = Logger.getLogger(EpsilonDebugAdapter.class.getCanonicalName());

	/** Runnable to be executed upon attachment. */
	private Runnable onAttach;

	/** Client that we're talking back to. */
	private IDebugProtocolClient client;

	/** Epsilon module being debugged. */
	private IEolModule module;

	/** Debugger created from the module. */
	private EolDebugger debugger;

	private volatile boolean isTerminated = false;

	/** Converts a remote line to a local one (Epsilon uses 1-based lines). */
	private LocationConverter lineConverter;

	/** Converts a remote column to a local one (Epsilon uses 1-based columns). */
	private LocationConverter columnConverter;

	/** Breakpoints for each source file. */
	private Map<String, Set<Integer>> lineBreakpointsByFile = new ConcurrentHashMap<>();

	/**
	 * Semaphore used for suspending. Should only be used through {@link #suspend()}
	 * and {@link #resume()}.
	 */
	private Semaphore suspendSemaphore = new Semaphore(0);

	private SuspendedState suspendedState = new SuspendedState();

	public void connect(IDebugProtocolClient client) {
		if (module == null) {
			throw new IllegalStateException("connect(): the module has not been set up yet");
		}
		this.debugger = module.createDebugger();
		this.debugger.setTarget(this);

		this.client = client;
		client.initialized();
	}

	@Override
	public CompletableFuture<Capabilities> initialize(InitializeRequestArguments args) {
		return CompletableFuture.supplyAsync(() -> {
			/*
			 * Note: according to the DAP 1.65 spec, the defaults are that lines and columns
			 * are 1-based.
			 */
			this.lineConverter = args.getLinesStartAt1() == null || args.getLinesStartAt1()
				? new RemoteIsOneBasedConverter() : new RemoteIsZeroBasedConverter();
			this.columnConverter = args.getColumnsStartAt1() == null || args.getColumnsStartAt1()
				? new RemoteIsOneBasedConverter() : new RemoteIsZeroBasedConverter();

			Capabilities caps = new Capabilities();
			caps.setSupportsTerminateRequest(true);
			return caps;
		});
	}

	/*
	 * TODO: support launch via same launch properties as used by Eclipse launch
	 * configurations?
	 */

	@Override
	public CompletableFuture<Void> attach(Map<String, Object> args) {
		return CompletableFuture.runAsync(() -> {
			// Attach to the module's output and error streams
			module.getContext().setOutputStream(createStream(OutputEventArgumentsCategory.STDOUT));
			module.getContext().setErrorStream(createStream(OutputEventArgumentsCategory.STDERR));

			// Listen to module completion, and allow for termination
			module.getContext().getExecutorFactory().addExecutionListener(new ModuleCompletionListener());
			module.getContext().getExecutorFactory().setExecutionController(debugger);

			// Run the onAttach hook (e.g. for starting the configured module)
			if (this.onAttach != null) {
				this.onAttach.run();
			}
		});
	}

	@Override
	public CompletableFuture<ThreadsResponse> threads() {
		return CompletableFuture.supplyAsync(() -> {
			ThreadsResponse r = new ThreadsResponse();

			org.eclipse.lsp4j.debug.Thread epsilonThread
				= new org.eclipse.lsp4j.debug.Thread();
			epsilonThread.setId(EPSILON_THREAD_ID);
			epsilonThread.setName("Epsilon");
			r.setThreads(new org.eclipse.lsp4j.debug.Thread[] {
				epsilonThread
			});

			return r;
		});
	}

	@Override
	public CompletableFuture<StackTraceResponse> stackTrace(StackTraceArguments args) {
		return CompletableFuture.supplyAsync(() -> {
			StackTraceResponse resp = new StackTraceResponse();
			if (args.getThreadId() != EPSILON_THREAD_ID) {
				return resp;
			}

			List<StackFrame> responseFrames = new ArrayList<>();
			int i = 0;
			for (SingleFrame moduleFrame : module.getContext().getFrameStack().getFrames()) {
				StackFrame responseFrame = new StackFrame();
				responseFrames.add(responseFrame);

				// ID is just 0-based index
				responseFrame.setId(i++);
				responseFrame.setName(getStackFrameName(i, moduleFrame));

				ModuleElement frameStatement = moduleFrame.getCurrentStatement();
				if (frameStatement != null) {
					setStackFrameLocation(responseFrame, frameStatement);
				} else {
					responseFrame.setLine(0);
					responseFrame.setColumn(0);
				}
			}

			resp.setStackFrames(responseFrames.toArray(new StackFrame[responseFrames.size()]));
			return resp;
		});
	}

	public CompletableFuture<ScopesResponse> scopes(ScopesArguments args) {
		return CompletableFuture.supplyAsync(() -> {
			FrameStack moduleStack = module.getContext().getFrameStack();
			SingleFrame localFrame = moduleStack.getFrames().get(args.getFrameId());

			Scope sc = new Scope();
			sc.setExpensive(false);
			sc.setNamedVariables(localFrame.getAll().size());
			sc.setVariablesReference(suspendedState.getReference(localFrame));

			switch (localFrame.getType()) {
			case PROTECTED:
				sc.setName("Protected");
				break;
			case UNPROTECTED:
				sc.setName("Unprotected");
				break;
			}

			ScopesResponse resp = new ScopesResponse();
			resp.setScopes(new Scope[] { sc });
			return resp;
		});
	}

	@Override
	public CompletableFuture<VariablesResponse> variables(VariablesArguments args) {
		return CompletableFuture.supplyAsync(() -> {
			VariablesResponse resp = new VariablesResponse();

			SingleFrame sf = suspendedState.getFrame(args.getVariablesReference());
			if (sf != null) {
				// reference points to a frame, list all variables
				List<Variable> respVariables = new ArrayList<>();
				for (org.eclipse.epsilon.eol.execute.context.Variable localV : sf.getAll().values()) {
					Variable respVariable = createVariable(localV);
					respVariables.add(respVariable);
				}

				resp.setVariables(respVariables.toArray(new Variable[respVariables.size()]));
			}

			// TODO: support structured variables (e.g. fields, collections)

			return resp;
		});
	}

	protected Variable createVariable(org.eclipse.epsilon.eol.execute.context.Variable localV) {
		Variable respVariable = new Variable();
		respVariable.setName(localV.getName());
		respVariable.setValue(localV.getValue() + "");
		// TODO: support variable types

		// TODO: support structured variables (e.g. fields, collections)
		respVariable.setVariablesReference(0);
		return respVariable;
	}

	protected void setStackFrameLocation(StackFrame responseFrame, ModuleElement frameEntrypoint) {
		Position frameStart = frameEntrypoint.getRegion().getStart();
		responseFrame.setLine(lineConverter.fromLocalToRemote(frameStart.getLine()));
		responseFrame.setColumn(columnConverter.fromLocalToRemote(frameStart.getColumn()));
		responseFrame.setSource(createSource(frameEntrypoint));
	}

	@Override
	public CompletableFuture<Void> terminate(TerminateArguments args) {
		return CompletableFuture.runAsync(() -> {
			this.isTerminated = true;
			resume();
		});
	}

	@Override
	public CompletableFuture<Void> disconnect(DisconnectArguments args) {
		return CompletableFuture.runAsync(() -> {
			this.isTerminated = true;
			this.client = null;
			resume();
		});
	}

	@Override
	public CompletableFuture<SetExceptionBreakpointsResponse> setExceptionBreakpoints(SetExceptionBreakpointsArguments args) {
		/*
		 * We don't support exception breakpoints in Epsilon, but this method is called
		 * by VS Code 1.88.1 regardless of our caps not having exception breakpoint
		 * filters.
		 */
		return CompletableFuture.completedFuture(new SetExceptionBreakpointsResponse());
	}

	@Override
	public CompletableFuture<SetBreakpointsResponse> setBreakpoints(SetBreakpointsArguments args) {
		return CompletableFuture.supplyAsync(() -> {
			SetBreakpointsResponse response = new SetBreakpointsResponse();

			try {
				/*
				 * Step 1. Map from path-based source to IEolModule
				 *
				 * TODO: deal with more complicated mappings (e.g. scripts loaded through platform:/ URIs).
				 * Could use something approximate where we map by basename if it's unique?
				 */
				Map<String, IModule> pathToModule = new HashMap<>();
				addAllModules(pathToModule, module);
				Set<Integer> localBreakpoints = null;
				IModule resolvedModule = null;
				if (args.getSource().getPath() != null) {
					resolvedModule = pathToModule.get(args.getSource().getPath());
					if (resolvedModule != null) {
						localBreakpoints = new HashSet<>();
						this.lineBreakpointsByFile.put(resolvedModule.getFile().getPath(), localBreakpoints);
					}
				}

				// Step 2. Build response while we update internal breakpoint list
				List<Breakpoint> responseBreakpoints = new ArrayList<>(args.getBreakpoints().length);
				for (SourceBreakpoint sbp : args.getBreakpoints()) {
					Breakpoint bp = new Breakpoint();
					responseBreakpoints.add(bp);

					bp.setVerified(false);
					if (resolvedModule != null) {
						final int localSBPLine = lineConverter.fromRemoteToLocal(sbp.getLine());
						final int localBPLine = findFirstLineGreaterThanOrEqualTo(resolvedModule, localSBPLine);

						if (localBPLine >= 1) {
							bp.setLine(lineConverter.fromLocalToRemote(localBPLine));
							localBreakpoints.add(localBPLine);
							bp.setSource(createSource(resolvedModule));
							bp.setVerified(true);
						}
					}
				}

				response.setBreakpoints(responseBreakpoints.toArray(new Breakpoint[responseBreakpoints.size()]));
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, "Failed to obtain canonical path for module file", e);
			}

			return response;
		});
	}

	private void addAllModules(Map<String, IModule> pathToModule, IModule module) throws IOException {
		String modulePath = module.getFile().getCanonicalPath();
		if (!pathToModule.containsKey(modulePath)) {
			pathToModule.put(modulePath, module);
			if (module instanceof IEolModule) {
				for (Import imp : ((IEolModule) module).getImports()) {
					addAllModules(pathToModule, imp.getModule());
				}
			}
		}
	}

	@Override
	public CompletableFuture<ContinueResponse> continue_(ContinueArguments args) {
		return CompletableFuture.supplyAsync(() -> {
			resume();

			ContinueResponse resp = new ContinueResponse();
			resp.setAllThreadsContinued(true);
			return resp;
		});
	}

	@Override
	public CompletableFuture<Void> stepIn(StepInArguments args) {
		return CompletableFuture.runAsync(() -> {
			debugger.step();
			resume();
		});
	}

	@Override
	public CompletableFuture<Void> next(NextArguments args) {
		return CompletableFuture.runAsync(() -> {
			debugger.stepOver();
			resume();
		});
	}

	@Override
	public CompletableFuture<Void> stepOut(StepOutArguments args) {
		return CompletableFuture.runAsync(() -> {
			debugger.stepReturn();
			resume();
		});
	}

	protected Source createSource(ModuleElement resolvedModule) {
		Source bpSource = new Source();
		bpSource.setName(resolvedModule.getFile().getName());
		try {
			bpSource.setPath(resolvedModule.getFile().getCanonicalPath());
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Cannot produce canonical path: falling back to regular path", e);
			bpSource.setPath(resolvedModule.getFile().getPath());
		}
		return bpSource;
	}

	/**
	 * Finds the first line greater than or equal to the given line that actually
	 * has a module element in it. This handles cases where we set a breakpoint
	 * on an empty line: it will instead break on the first non-empty line after it.
	 */
	protected int findFirstLineGreaterThanOrEqualTo(ModuleElement root, int localLine) {
		// This is a leaf node: either we find the line or we don't
		if (root.getChildren().isEmpty()) {
			final int ownLine = root.getRegion().getStart().getLine();
			return ownLine >= localLine ? ownLine : -1; 
		}

		/*
		 * Not a leaf node: find earliest child whose region spans the line or something
		 * after the line. We scan over all elements, because the children of a module
		 * element do not have to be in the same order as they appear on the file.
		 * 
		 * This is the case, for instance, for the operations that appear below a bit of
		 * top-level code, which will appear before that top-level code in the list of
		 * children.
		 */
		ModuleElement earliest = null;
		int earliestLine = Integer.MAX_VALUE;
		for (ModuleElement child : root.getChildren()) {
			final Region childRegion = child.getRegion();
			final int startLine = childRegion.getStart().getLine();
			final int endLine = childRegion.getEnd().getLine();
			if (startLine < earliestLine && endLine >= localLine) {
				earliest = child;
				earliestLine = startLine;
			}
		}

		if (earliest != null) {
			int earliestStart = earliest.getRegion().getStart().getLine();
			if (earliestStart >= localLine) {
				// We've already hit a line that is greater than or equal to localLine
				return earliestStart;
			} else {
				/*
				 * We haven't crossed the target line yet, but we know the module element spans
				 * over this line, so we go over its children.
				 */
				return findFirstLineGreaterThanOrEqualTo(earliest, localLine);
			}
		}

		return -1;
	}

	protected void sendExited(int exitCode) {
		if (client != null) {
			ExitedEventArguments exitedArgs = new ExitedEventArguments();
			exitedArgs.setExitCode(exitCode);
			client.exited(exitedArgs);
		}
	}

	protected void sendTerminated() {
		if (client != null) {
			client.terminated(new TerminatedEventArguments());
		}
	}

	protected void sendOutput(String category, String output) {
		if (client != null) {
			OutputEventArguments outputArguments = new OutputEventArguments();
			outputArguments.setCategory(category);
			outputArguments.setOutput(output);

			ModuleElement currentStatement = getCurrentStatement();
			Region region = currentStatement.getRegion();
			if (region != null) {
				Position regionStart = region.getStart();
				if (regionStart != null) {
					outputArguments.setLine(lineConverter.fromLocalToRemote(regionStart.getLine()));
					outputArguments.setColumn(columnConverter.fromLocalToRemote(regionStart.getColumn()));
				}
			}

			if (currentStatement.getFile() != null) {
				outputArguments.setSource(createSource(currentStatement));
			}

			client.output(outputArguments);
			LOGGER.fine(() -> "Sent output: " + outputArguments);
		}
	}

	protected ModuleElement getCurrentStatement() {
		return module.getContext().getFrameStack().getTopFrame().getCurrentStatement();
	}

	protected void sendStopped(String reason) {
		if (client != null) {
			StoppedEventArguments stoppedArgs = new StoppedEventArguments();
			stoppedArgs.setReason(reason);
			stoppedArgs.setThreadId(EPSILON_THREAD_ID);
			client.stopped(stoppedArgs);
		}
	}

	@Override
	public boolean isTerminated() {
		return isTerminated;
	}

	@Override
	public IEolModule getModule() {
		return module;
	}

	public void setModule(IEolModule module) {
		this.module = module;
	}

	public void setOnAttach(Runnable onAttach) {
		this.onAttach = onAttach;
	}

	private String getStackFrameName(int position, Frame frame) {
		final ModuleElement entryPoint = frame.getEntryPoint();
		if (entryPoint != null) {
			StringBuilder builder = new StringBuilder();
			if (entryPoint instanceof Operation) {
				builder.append(entryPoint.getChildren().get(0).toString());
			} else {
				builder.append(entryPoint.toString());
			}
			builder.append(" at ");
			builder.append(entryPoint.getUri().toString());
			return builder.toString();
		}
		else {
			return "globals";
		}
	}

	protected void resume() {
		if (suspendSemaphore.availablePermits() == 0) {
			suspendSemaphore.release();
		}
	}

	@Override
	public boolean hasBreakpointItself(ModuleElement ast) {
		Set<Integer> lineBreakpoints = lineBreakpointsByFile.get(ast.getFile().getPath());
		return lineBreakpoints != null && lineBreakpoints.contains(ast.getRegion().getStart().getLine());
	}

	@Override
	public void suspend(ModuleElement ast, SuspendReason reason) throws InterruptedException {
		switch (reason) {
		case STEP:
			sendStopped(StoppedEventArgumentsReason.STEP);
			break;
		case BREAKPOINT:
			sendStopped(StoppedEventArgumentsReason.BREAKPOINT);
			break;
		default:
			throw new IllegalArgumentException("Unknown suspend reason");
		}

		suspendedState.suspended();
		suspendSemaphore.acquire();
	}
	
}
