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
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.Position;
import org.eclipse.epsilon.common.parse.Region;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.dap.variables.IVariableReference;
import org.eclipse.epsilon.eol.dap.variables.SingleFrameReference;
import org.eclipse.epsilon.eol.dap.variables.SuspendedState;
import org.eclipse.epsilon.eol.debug.BreakpointRequest;
import org.eclipse.epsilon.eol.debug.BreakpointResult;
import org.eclipse.epsilon.eol.debug.BreakpointState;
import org.eclipse.epsilon.eol.debug.IEolDebugger;
import org.eclipse.epsilon.eol.debug.IEpsilonDebugTarget;
import org.eclipse.epsilon.eol.debug.SuspendReason;
import org.eclipse.epsilon.eol.dom.Operation;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.ExecutorFactory;
import org.eclipse.epsilon.eol.execute.Return;
import org.eclipse.epsilon.eol.execute.context.Frame;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.SingleFrame;
import org.eclipse.epsilon.eol.execute.control.ExecutionController;
import org.eclipse.epsilon.eol.execute.control.IExecutionListener;
import org.eclipse.lsp4j.debug.Breakpoint;
import org.eclipse.lsp4j.debug.BreakpointEventArguments;
import org.eclipse.lsp4j.debug.BreakpointEventArgumentsReason;
import org.eclipse.lsp4j.debug.BreakpointNotVerifiedReason;
import org.eclipse.lsp4j.debug.Capabilities;
import org.eclipse.lsp4j.debug.ContinueArguments;
import org.eclipse.lsp4j.debug.ContinueResponse;
import org.eclipse.lsp4j.debug.DisconnectArguments;
import org.eclipse.lsp4j.debug.EvaluateArguments;
import org.eclipse.lsp4j.debug.EvaluateResponse;
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
import org.eclipse.lsp4j.debug.Thread;
import org.eclipse.lsp4j.debug.ThreadEventArguments;
import org.eclipse.lsp4j.debug.ThreadEventArgumentsReason;
import org.eclipse.lsp4j.debug.ThreadsResponse;
import org.eclipse.lsp4j.debug.Variable;
import org.eclipse.lsp4j.debug.VariablesArguments;
import org.eclipse.lsp4j.debug.VariablesResponse;
import org.eclipse.lsp4j.debug.services.IDebugProtocolClient;
import org.eclipse.lsp4j.debug.services.IDebugProtocolServer;
import org.eclipse.lsp4j.jsonrpc.messages.Tuple;
import org.eclipse.lsp4j.jsonrpc.messages.Tuple.Two;

import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;

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
public class EpsilonDebugAdapter implements IDebugProtocolServer {

	/**
	 * Keeps track of the modules that start and complete execution. Note that there should
	 * only be exactly one instance of this listener per execution: the same listener should
	 * be propagated across all scripts being launched (e.g. from EGX to EGL scripts).
	 */
	protected class ModuleCompletionListener implements IExecutionListener {
		private IEolModule topModule;
		private final Set<ModuleElement> runningRoots = new HashSet<>();

		@Override
		public void aboutToExecute(ModuleElement ast, IEolContext context) {
			if (ast.getParent() == null || runningRoots.isEmpty()) {
				runningRoots.add(ast);

				if (topModule == null && ast.getModule() instanceof IEolModule) {
					/*
					 * The very first module we will run is assumed to be the "main" module of our
					 * program. When this is considered terminated, the whole program will be considered
					 * terminated.
					 */
					topModule = (IEolModule) ast.getModule();
				}

				/*
				 * In some cases, we don't get to run the main module that we originally
				 * attached to, but instead run a module generated by the main module (e.g. the
				 * external EGL module and its own internal EGL module). We detect those cases
				 * here, and remove the thread associated with the main module.
				 */
				synchronized (threads) {
					for (Iterator<ThreadState> it = threads.values().iterator(); it.hasNext(); ) {
						ThreadState thread = it.next();
						if (!runningRoots.contains(thread.getModule())) {
							it.remove();
						}
					}
				}

				if (ast.getModule() instanceof IEolModule) {
					ThreadState threadState = attachTo((IEolModule) ast.getModule());
					sendThreadEvent(threadState.getThreadId(), ThreadEventArgumentsReason.STARTED);
				}
			}
		}

		@Override
		public void finishedExecuting(ModuleElement ast, Object result, IEolContext context) {
			if (runningRoots.remove(ast)) {
				if (ast.getModule() instanceof IEolModule) {
					final IEolModule eolModule = (IEolModule) ast.getModule();
					eolModule.getContext().getOutputStream().flush();
					eolModule.getContext().getErrorStream().flush();
					removeThreadFor(eolModule);

					if (topModule != null && ast.getModule() == topModule) {
						ExecutionController execController = topModule.getContext().getExecutorFactory().getExecutionController();
						if (execController instanceof IEolDebugger && ((IEolDebugger) execController).isDoneAfterModuleElement(ast)) {
							sendTerminated();
							sendExited(0);
							topModule = null;
						}
					}
				}
			}
		}

		@Override
		public void finishedExecutingWithException(ModuleElement ast, EolRuntimeException exception, IEolContext context) {
			if (ast.getParent() == null && ast instanceof IEolModule) {
				final IEolModule eolModule = (IEolModule) ast;

				eolModule.getContext().getOutputStream().flush();
				eolModule.getContext().getErrorStream().flush();
				removeThreadFor(eolModule);

				if (topModule != null && ast == topModule) {
					// Report the exception that was propagated to the top module
					topModule.getContext().getErrorStream().println(exception.toString());

					ExecutionController execController = topModule.getContext().getExecutorFactory().getExecutionController();
					if (execController instanceof IEolDebugger && ((IEolDebugger) execController).isDoneAfterModuleElement(ast)) {
						sendTerminated();
						sendExited(1);
						topModule = null;
					}
				}
			}
		}

		protected void removeThreadFor(IEolModule module) {
			synchronized (threads) {
				for (Iterator<ThreadState> itThread = threads.values().iterator(); itThread.hasNext();) {
					final ThreadState thread = itThread.next();
					if (thread.module == module) {
						sendThreadEvent(thread.getThreadId(), ThreadEventArgumentsReason.EXITED);
						itThread.remove();
					}
				}
			}
		}
	}

	protected PrintStream createStream(IEolContext context, String category) {
		return new PrintStream(new DAPTeeByteArrayOutputStream(context, category), true);
	}

	protected class DAPTeeByteArrayOutputStream extends ByteArrayOutputStream {
		private final IEolContext context;
		private final String category;

		public DAPTeeByteArrayOutputStream(IEolContext context, String category) {
			this.context = context;
			this.category = category;
		}
		
		@Override
		public void flush() throws IOException {
			String output = new String(toByteArray(), StandardCharsets.UTF_8);
			if (output.length() > 0) {
				reset();
				sendOutput(context, category, output);
			}
		}
	}

	/**
	 * If this key is set to "true" upon attaching, line breakpoints (without an
	 * explicit column) will stop at each statement instead of just the first one.
	 */
	public static final String STOP_AT_EVERY_STATEMENT = "stop-at-every-statement";
	private boolean stopAtEveryStatement;

	private static final Logger LOGGER = Logger.getLogger(EpsilonDebugAdapter.class.getCanonicalName());

	/** Runnable to be executed upon attachment. */
	private Runnable onAttach;

	/** Client that we're talking back to. */
	private IDebugProtocolClient client;

	/** Main Epsilon module being debugged. */
	private IEolModule mainModule;

	/**
	 * <p>
	 * Represents the debugger and other state needed to debug an execution thread.
	 * Some Epsilon scripts invoke other Epsilon scripts (specifically, EGX runs EGL
	 * scripts), so we treat the invoked script as its own execution thread.
	 * </p>
	 *
	 * <p>
	 * The script may resolve breakpoints somewhat differently than the original
	 * one, so it has its own set of breakpoints.
	 * </p>
	 */
	protected class ThreadState implements IEpsilonDebugTarget {
		protected final int threadId;
		protected final IEolModule module;
		protected final IEolDebugger debugger;

		/** Breakpoints by URI: these are the ones resolved to specific module URIs. */
		private Map<URI, Multimap<Integer, BreakpointInfo>> lineBreakpointsByURI = new ConcurrentHashMap<>();

		public ThreadState(int threadId, IEolModule module) {
			this.threadId = threadId;
			this.module = module;
			this.debugger = module.createDebugger();
			this.debugger.setTarget(this);

			// When creating a new ThreadState, populate breakpoints from path-based map
			for (Entry<String, Multimap<Integer, BreakpointInfo>> e : lineBreakpointsByPath.entrySet()) {
				for (Entry<Integer, BreakpointInfo> localLine : e.getValue().entries()) {
					setBreakpoint(e.getKey(), localLine.getKey(), localLine.getValue());
				}
			}
		}

		public int getThreadId() {
			return threadId;
		}

		@Override
		public boolean isTerminated() {
			return isTerminated;
		}

		@Override
		public boolean hasBreakpointItself(ModuleElement ast) {
			if (ast.getUri() == null) {
				// An AST may not have a URI (e.g. a breakpoint condition)
				return false;
			}

			Multimap<Integer, BreakpointInfo> lineBreakpoints = lineBreakpointsByURI.get(ast.getUri());
			final int startLine = ast.getRegion().getStart().getLine();
			if (lineBreakpoints != null && lineBreakpoints.containsKey(startLine)) {
				Collection<BreakpointInfo> bpInfos = lineBreakpoints.get(startLine);
				for (BreakpointInfo bpInfo : bpInfos) {
					if (!stopAtEveryStatement && (
							ast.getRegion().getStart().getColumn() > bpInfo.column
							|| ast.getRegion().getEnd().getColumn() < bpInfo.column
						)) {
						continue;
					}

					if (bpInfo.condition != null && !evaluateBreakpointCondition(ast, startLine, bpInfo)) {
						continue;
					}

					return true;
				}
			}
			return false;
		}

		protected boolean evaluateBreakpointCondition(ModuleElement breakpointAst, final int startLine, BreakpointInfo bpInfo) {
			/*
			 * We parse a bit of EOL, but we don't run the new module as such: instead, we
			 * run the expression from inside the debugged module so we have access to user
			 * defined operations, imports, and so on.
			 */
			final EolModule miniEol = new EolModule();
			final String eolCondition = bpInfo.condition;
			final String eol = "return (" + eolCondition + ").asBoolean();";
			try {
				miniEol.parse(eol);
			} catch (Exception e) {
				LOGGER.log(Level.WARNING,
						String.format("Exception while parsing condition '%s': disabling breakpoint", eolCondition), e);
				unverifyBreakpoint(breakpointAst, startLine, bpInfo);
				return false;
			}

			if (!miniEol.getParseProblems().isEmpty()) {
				LOGGER.log(Level.WARNING, String.format(
					"Condition '%s' produced parse errors: disabling breakpoint\n%s",
					eolCondition,
					String.join("\n",
						miniEol.getParseProblems()
							.stream().map(e -> e.toString())
							.collect(Collectors.toList()))));
				unverifyBreakpoint(breakpointAst, startLine, bpInfo);
				return false;
			}

			try {
				miniEol.setContext(new EvaluatorContext(module.getContext()));
				ExecutorFactory moduleExecutor = miniEol.getContext().getExecutorFactory();
				Return returned = (Return) moduleExecutor.execute(miniEol.getMain(), miniEol.getContext());

				if (returned != null && returned.getValue() instanceof Boolean) {
					return (Boolean) returned.getValue();
				} else {
					LOGGER.log(Level.WARNING, String
							.format("Condition '%s' did not produce a boolean: disabling breakpoint", eolCondition));
					unverifyBreakpoint(breakpointAst, startLine, bpInfo);
				}
			} catch (Exception e) {
				LOGGER.log(Level.WARNING,
					String.format("Exception while evaluating condition '%s': disabling breakpoint", eolCondition),
					e);
				unverifyBreakpoint(breakpointAst, startLine, bpInfo);
			}

			return false;
		}

		public EvaluateResponse evaluateExpression(String expression) {
			EvaluateResponse r = new EvaluateResponse();

			EolModule miniEol = new EolModule();
			try {
				miniEol.parse(String.format("var returned = (%s);", expression));

				if (!miniEol.getParseProblems().isEmpty()) {
					LOGGER.log(Level.FINE, String.format(
						"Expression '%s' produced parse errors\n%s",
						expression,
						String.join("\n",
							miniEol.getParseProblems()
								.stream().map(e -> e.toString())
								.collect(Collectors.toList()))));

					r.setResult("(failed to parse)");
				} else {
					miniEol.setContext(new EvaluatorContext(module.getContext()));

					FrameStack frameStack = miniEol.getContext().getFrameStack();
					SingleFrame sf = (SingleFrame) frameStack.enterLocal(FrameType.UNPROTECTED, miniEol.getMain());
					ExecutorFactory moduleExecutor = miniEol.getContext().getExecutorFactory();
					moduleExecutor.execute(miniEol.getMain(), miniEol.getContext());

					IVariableReference frameReference = suspendedState.getReference(miniEol.getContext(), sf);
					Optional<IVariableReference> oReturnedRef = frameReference.getVariables(suspendedState).stream()
							.filter(v -> "returned".equals(v.getName())).findFirst();

					if (oReturnedRef.isPresent()) {
						IVariableReference returnedRef = oReturnedRef.get();
						r.setResult(returnedRef.getValue());

						List<IVariableReference> subvariables = returnedRef.getVariables(suspendedState);
						if (subvariables.size() > 1) {
							r.setNamedVariables(subvariables.size());
							r.setVariablesReference(returnedRef.getId());
						}
						if (initializeArguments.getSupportsVariableType()) {
							// The IDE supports variable types: provide it as well
							r.setType(returnedRef.getTypeName());
						}
					} else {
						r.setResult("(failed to evaluate)");
					}
				}
			} catch (Exception ex) {
				LOGGER.log(Level.FINE,
					String.format("Failed to evaluate expression '%s'", expression), ex);
				r.setResult(String.format("(failed to evaluate with exception: %s)",
					ex.getClass().getCanonicalName()));
			}

			return r;
		}

		protected void unverifyBreakpoint(ModuleElement breakpointAst, int startLine, BreakpointInfo bpInfo) {
			Multimap<Integer, BreakpointInfo> lineBreakpoints = lineBreakpointsByURI.get(breakpointAst.getUri());
			lineBreakpoints.remove(startLine, bpInfo);

			BreakpointEventArguments args = new BreakpointEventArguments();
			Breakpoint bp = new Breakpoint();
			bp.setSource(createSource(breakpointAst));
			bp.setLine(startLine);
			bp.setColumn(bpInfo.column);
			bp.setVerified(false);
			args.setBreakpoint(bp);
			args.setReason(BreakpointEventArgumentsReason.CHANGED);
			client.breakpoint(args);
		}

		@Override
		public void suspend(ModuleElement ast, SuspendReason reason) throws InterruptedException {
			// The suspend latch is the same across all threads, so this is done at the adapter level
			EpsilonDebugAdapter.this.suspend(threadId, ast, reason);
		}

		@Override
		public IEolModule getModule() {
			return module;
		}

		protected BreakpointResult setBreakpoint(final String sourcePath, Integer localLine, BreakpointInfo info) {
			BreakpointRequest request = new BreakpointRequest(uriToPathMappings, sourcePath, localLine, info.column, info.condition);
			BreakpointResult result = debugger.verifyBreakpoint(request);

			if (result.getState() != BreakpointState.FAILED) {
				info.column = result.getColumn();

				Multimap<Integer, BreakpointInfo> uriBreakpoints = lineBreakpointsByURI.get(result.getModuleURI());
				if (uriBreakpoints == null) {
					uriBreakpoints = TreeMultimap.create();
					lineBreakpointsByURI.put(result.getModuleURI(), uriBreakpoints);
				}
				uriBreakpoints.put(result.getLine(), info);
			}

			return result;
		}
	}

	protected ThreadState attachTo(IEolModule module) {
		ThreadState thread;
		synchronized (threads) {
			for (ThreadState t : threads.values()) {
				if (t.module == module) {
					// We already have attached to this module
					return t;
				}
			}

			// Add a thread for this module
			final int threadId = nextThread.getAndIncrement();
			thread = new ThreadState(threadId, module);
			threads.put(threadId, thread);
			module.getContext().getExecutorFactory().setExecutionController(thread.debugger);
		}

		// Attach to the module's output and error streams
		final PrintStream outStream = createStream(module.getContext(), OutputEventArgumentsCategory.STDOUT);
		module.getContext().setOutputStream(outStream);
		final PrintStream errStream = createStream(module.getContext(), OutputEventArgumentsCategory.STDERR);
		module.getContext().setErrorStream(errStream);

		return thread;
	}

	/** Additional options that we keep for a breakpoint. */
	protected class BreakpointInfo implements Comparable<BreakpointInfo> {
		public String condition;
		public int column;

		@Override
		public int compareTo(BreakpointInfo o) {
			return Integer.compare(column, o.column);
		}
	}

	public static final int FIRST_THREAD_ID = 1;
	private final AtomicInteger nextThread = new AtomicInteger(FIRST_THREAD_ID);
	private final Map<Integer, ThreadState> threads = new HashMap<>();

	private volatile boolean isTerminated = false;

	/** Converts a remote line to a local one (Epsilon uses 1-based lines). */
	private LocationConverter lineConverter;

	/** Converts a remote column to a local one (Epsilon uses 0-based columns). */
	private LocationConverter columnConverter;

	/**
	 * Breakpoints by path: these are prior to resolution (needed to resolve
	 * breakpoints on dynamically loaded modules over classpath resources).
	 */
	private Map<String, Multimap<Integer, BreakpointInfo>> lineBreakpointsByPath = new ConcurrentHashMap<>();

	/**
	 * Mappings from module URIs to filesystem paths. Useful when debugging
	 * code that is loaded from a non-file URI.
	 */
	private final Map<URI, Path> uriToPathMappings = new HashMap<>();

	/**
	 * Latch used for suspending all threads. Should only be used through {@link #suspend()}
	 * and {@link #resumeAllThreads()}.
	 */
	private final AtomicBoolean suspendedLatch = new AtomicBoolean(false);

	/**
	 * Abstraction over the state of the program while stopped. Used to keep track
	 * of DAP variable references. May need to interact with the module at times.
	 */
	private SuspendedState suspendedState;

	/**
	 * Arguments passed by client for the adapter to initialise itself.
	 * Mostly useful as a set of flags with the client's capabilities.
	 */
	private InitializeRequestArguments initializeArguments;

	/**
	 * The only instance for this adapter of the listener used to detect
	 * module startup and completion. No other instances should be created.
	 */
	private final ModuleCompletionListener completionListener = new ModuleCompletionListener();

	public void connect(IDebugProtocolClient client) {
		if (mainModule == null) {
			throw new IllegalStateException("connect(): the module has not been set up yet");
		}
		attachTo(mainModule);
		mainModule.getContext().getExecutorFactory().addExecutionListener(completionListener);

		this.client = client;
		client.initialized();
	}

	@Override
	public CompletableFuture<Capabilities> initialize(InitializeRequestArguments args) {
		return CompletableFuture.supplyAsync(() -> {
			/*
			 * Note: according to the DAP 1.65 spec, the defaults are that lines and columns
			 * are 1-based. Epsilon itself has 1-based lines, and 0-based columns (matching
			 * the Eclipse IDE).
			 */
			this.lineConverter = args.getLinesStartAt1() == null || args.getLinesStartAt1()
				? new DeltaLocationConverter(0) : new DeltaLocationConverter(-1);
			this.columnConverter = args.getColumnsStartAt1() == null || args.getColumnsStartAt1()
				? new DeltaLocationConverter(1) : new DeltaLocationConverter(0);

			this.initializeArguments = args;

			Capabilities caps = new Capabilities();
			caps.setSupportsTerminateRequest(true);
			caps.setSupportsConditionalBreakpoints(true);
			return caps;
		});
	}

	@Override
	public CompletableFuture<Void> attach(Map<String, Object> args) {
		return CompletableFuture.runAsync(() -> {
			synchronized(this) {
				// If this is the first time we're attaching to the module...
				if (this.suspendedState == null) {
					// We accept both the "true" string and a Boolean true value
					Object argStop = args.get(STOP_AT_EVERY_STATEMENT);
					this.stopAtEveryStatement = "true".equals(argStop) || Boolean.TRUE.equals(argStop);

					// Prepare the suspended state
					suspendedState = new SuspendedState();

					// Run the onAttach hook exactly once (e.g. for starting the configured module)
					if (this.onAttach != null) {
						this.onAttach.run();
					}
				}
			}
		});
	}

	@Override
	public CompletableFuture<ThreadsResponse> threads() {
		return CompletableFuture.supplyAsync(() -> {
			List<Thread> threads = new ArrayList<>();

			synchronized (this.threads) {
				for (Entry<Integer, ThreadState> entry : this.threads.entrySet()) {
					IEolModule module = entry.getValue().module;

					/*
					 * Only show threads for which we have a file: for example, the
					 * ExecutionQueueModule does not have a file associated with it.
					 */
					if (module.getUri() != null) {
						Thread lspThread = new Thread();
						lspThread.setId(entry.getKey());

						lspThread.setName(String.format("%s#%d",
							module.getClass().getSimpleName(), entry.getKey()));
						threads.add(lspThread);
					}
				}
			}

			ThreadsResponse r = new ThreadsResponse();
			r.setThreads(threads.toArray(new Thread[threads.size()]));
			return r;
		});
	}

	@Override
	public CompletableFuture<EvaluateResponse> evaluate(EvaluateArguments args) {
		return CompletableFuture.supplyAsync(() -> {
			synchronized (suspendedLatch) {
				if (!suspendedLatch.get()) {
					// Not stopped at a breakpoint yet - can't evaluate until then
					EvaluateResponse r = new EvaluateResponse();
					r.setResult("(pending)");
					return r;
				}

				// Try to find the frame mentioned in the evaluate() request
				IVariableReference ref = suspendedState.getReference(args.getFrameId());
				SingleFrameReference sfRef = null;
				if (ref instanceof SingleFrameReference) {
					sfRef = (SingleFrameReference) ref;
				} else {
					EvaluateResponse r = new EvaluateResponse();
					r.setResult("(failed to evaluate: cannot find frame #" + args.getFrameId());
					return r;
				}

				// Frame is found: try to evaluate the expression
				IEolModule module = (IEolModule) sfRef.getContext().getModule();
				ThreadState threadState = attachTo(module);
				return threadState.evaluateExpression(args.getExpression());
			}
		});
	}

	@Override
	public CompletableFuture<StackTraceResponse> stackTrace(StackTraceArguments args) {
		return CompletableFuture.supplyAsync(() -> {
			StackTraceResponse resp = new StackTraceResponse();

			ThreadState thread;
			synchronized (threads) {
				thread = threads.get(args.getThreadId());
				if (thread == null) {
					return resp;
				}
			}

			List<StackFrame> responseFrames = new ArrayList<>();
			int i = 0;

			final IEolContext moduleContext = thread.module.getContext();
			for (SingleFrame moduleFrame : moduleContext.getFrameStack().getFrames()) {
				StackFrame responseFrame = new StackFrame();
				responseFrames.add(responseFrame);

				final IVariableReference mfReference = suspendedState.getReference(moduleContext, moduleFrame);
				responseFrame.setId(mfReference.getId());
				responseFrame.setName(getStackFrameName(i, moduleFrame));

				final Two<ModuleElement, Source> location = resolveStackFrameLocation(thread.module, moduleFrame);
				final Position frameStart = location.getFirst().getRegion().getStart();
				final Position frameEnd = location.getFirst().getRegion().getEnd();
			
				responseFrame.setLine(lineConverter.fromLocalToRemote(frameStart.getLine()));
				responseFrame.setEndLine(lineConverter.fromLocalToRemote(frameEnd.getLine()));
				responseFrame.setColumn(columnConverter.fromLocalToRemote(frameStart.getColumn()));
				responseFrame.setEndColumn(columnConverter.fromLocalToRemote(frameEnd.getColumn()));
				responseFrame.setSource(location.getSecond());
			}

			resp.setStackFrames(responseFrames.toArray(new StackFrame[responseFrames.size()]));
			return resp;
		});
	}

	protected Tuple.Two<ModuleElement, Source> resolveStackFrameLocation(IModule module, Frame sf) {
		if (sf.getCurrentStatement() != null) {
			// Always try with the current statement first
			Source source = createSource(sf.getCurrentStatement());
			if (source != null && source.getPath() != null) {
				return Tuple.two(sf.getCurrentStatement(), source);
			}
		}
		if (sf.getEntryPoint() != null) {
			// If that doesn't work, fall back on the entrypoint
			Source source = createSource(sf.getEntryPoint());
			if (source != null && source.getPath() != null) {
				return Tuple.two(sf.getEntryPoint(), source);
			}
		}

		// If we can't find a current statement or an entrypoint, fall back on the module
		return Tuple.two(module, createSource(module));
	}

	public CompletableFuture<ScopesResponse> scopes(ScopesArguments args) {
		return CompletableFuture.supplyAsync(() -> {
			IVariableReference ref = suspendedState.getReference(args.getFrameId());
			SingleFrameReference sfRef = null;
			if (ref instanceof SingleFrameReference) {
				sfRef = (SingleFrameReference) ref;
			} else {
				return new ScopesResponse();
			}

			SingleFrame localFrame = sfRef.getTarget();
			Scope sc = new Scope();
			sc.setExpensive(false);
			sc.setVariablesReference(ref.getId());
			sc.setNamedVariables(localFrame.getAll().size());
			sc.setName(ref.getName());

			ScopesResponse resp = new ScopesResponse();
			resp.setScopes(new Scope[] { sc });
			return resp;
		});
	}

	@Override
	public CompletableFuture<VariablesResponse> variables(VariablesArguments args) {
		return CompletableFuture.supplyAsync(() -> {
			VariablesResponse resp = new VariablesResponse();

			IVariableReference ref = suspendedState.getReference(args.getVariablesReference());
			if (ref != null) {
				List<Variable> respVariables = new ArrayList<>();

				for (IVariableReference vRef : ref.getVariables(suspendedState)) {
					Variable respVariable = new Variable();
					respVariable.setName(vRef.getName());
					try {
						respVariable.setValue(vRef.getValue());
					} catch (Throwable t) {
						respVariable.setValue(String.format("(failed to get value: %s)", t));
					}
					if (initializeArguments.getSupportsVariableType()) {
						respVariable.setType(vRef.getTypeName());
					}
					respVariable.setVariablesReference(vRef.getId());
					respVariables.add(respVariable);
				}

				resp.setVariables(respVariables.toArray(new Variable[respVariables.size()]));
			}

			return resp;
		});
	}

	@Override
	public CompletableFuture<Void> terminate(TerminateArguments args) {
		return CompletableFuture.runAsync(() -> {
			this.isTerminated = true;
			resumeAllThreads();
			sendTerminated();
			sendExited(2);
		});
	}

	@Override
	public CompletableFuture<Void> disconnect(DisconnectArguments args) {
		return CompletableFuture.runAsync(() -> {
			this.isTerminated = true;
			this.client = null;
			resumeAllThreads();
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
			List<Breakpoint> responseBreakpoints = new ArrayList<>(args.getBreakpoints().length);

			synchronized (this.threads) {
				final String sourcePath = args.getSource().getPath();
				final TreeMultimap<Integer, BreakpointInfo> localLines = TreeMultimap.create();
				for (SourceBreakpoint sbp : args.getBreakpoints()) {
					BreakpointInfo bpInfo = new BreakpointInfo();
					bpInfo.condition = sbp.getCondition();
					if (sbp.getColumn() != null) {
						bpInfo.column = columnConverter.fromRemoteToLocal(sbp.getColumn());
					}
					localLines.put(lineConverter.fromRemoteToLocal(sbp.getLine()), bpInfo);
				}

				// First, clear all existing breakpoints
				removeAllBreakpoints(sourcePath);
				if (!localLines.isEmpty()) {
					lineBreakpointsByPath.put(sourcePath, localLines);

					for (Entry<Integer, BreakpointInfo> localLine : localLines.entries()) {
						Breakpoint bp = new Breakpoint();
						responseBreakpoints.add(bp);
						bp.setVerified(false);
						bp.setReason(BreakpointNotVerifiedReason.FAILED);

						for (ThreadState thread : this.threads.values()) {
							BreakpointResult result = thread.setBreakpoint(sourcePath, localLine.getKey(), localLine.getValue());
							updateResponseBreakpointFromResult(sourcePath, bp, result);
						}
					}
				}
			}

			response.setBreakpoints(responseBreakpoints.toArray(new Breakpoint[responseBreakpoints.size()]));
			return response;
		});
	}

	protected void updateResponseBreakpointFromResult(final String sourcePath, Breakpoint bp, BreakpointResult result) {
		switch (result.getState()) {
		case VERIFIED:
			bp.setVerified(true);
			bp.setReason(null);
			break;
		case PENDING:
			if (!bp.isVerified()) {
				bp.setVerified(false);
				bp.setReason(BreakpointNotVerifiedReason.PENDING);
			}
			break;
		case FAILED:
			// nothing to do
			break;
		}

		if (result.getState() != BreakpointState.FAILED) {
			bp.setLine(lineConverter.fromLocalToRemote(result.getLine()));
			bp.setSource(createSource(result, sourcePath));
		}
	}

	protected void removeAllBreakpoints(final String sourcePath) {
		lineBreakpointsByPath.remove(sourcePath);

		/*
		 * This is just a request to clear all breakpoints: ask the debuggers
		 * for a module URI, using line 1 as a placeholder.
		 */
		BreakpointRequest request = new BreakpointRequest(uriToPathMappings, sourcePath, 1, 0, null);

		// We propagate requests across all debuggers
		synchronized (this.threads) {
			for (ThreadState e : this.threads.values()) {
				BreakpointResult result = e.debugger.verifyBreakpoint(request);
				if (result.getModuleURI() != null) {
					e.lineBreakpointsByURI.remove(result.getModuleURI());
				}
			}
		}
	}

	@Override
	public CompletableFuture<ContinueResponse> continue_(ContinueArguments args) {
		return CompletableFuture.supplyAsync(() -> {
			resumeAllThreads();

			ContinueResponse resp = new ContinueResponse();
			resp.setAllThreadsContinued(true);
			return resp;
		});
	}

	@Override
	public CompletableFuture<Void> stepIn(StepInArguments args) {
		return CompletableFuture.runAsync(() -> {
			synchronized (threads) {
				ThreadState thread = this.threads.get(args.getThreadId());
				if (thread != null) {
					thread.debugger.step();
					resumeAllThreads();
				} else {
					throw new IllegalArgumentException("Could not find thread with ID " + args.getThreadId());
				}
			}
		});
	}

	@Override
	public CompletableFuture<Void> next(NextArguments args) {
		return CompletableFuture.runAsync(() -> {
			synchronized (this.threads) {
				ThreadState thread = this.threads.get(args.getThreadId());
				if (thread != null) {
					thread.debugger.stepOver();
					resumeAllThreads();
				} else {
					throw new IllegalArgumentException("Could not find thread with ID " + args.getThreadId());
				}
			}
		});
	}

	@Override
	public CompletableFuture<Void> stepOut(StepOutArguments args) {
		return CompletableFuture.runAsync(() -> {
			synchronized (threads) {
				ThreadState thread = this.threads.get(args.getThreadId());
				if (thread != null) {
					thread.debugger.stepReturn();
					resumeAllThreads();
				} else {
					throw new IllegalArgumentException("Could not find thread with ID " + args.getThreadId());
				}
			}
		});
	}

	protected Source createSource(BreakpointResult result, final String sourcePath) {
		Source src = new Source();
		if (result.getModule() != null) {
			return createSource(result.getModule());
		} else {
			populateSourceFromMappings(src, result.getModuleURI(), Paths.get(sourcePath));
			return src;
		}
	}

	/**
	 * Creates a DAP Source object from a module element. It will prefer using
	 * files if available (as they will probably be the same as those in the IDE),
	 * but it will fall back to URIs if there is no file to refer back to.
	 */
	protected Source createSource(ModuleElement resolvedModule) {
		final Source bpSource = new Source();

		// First, try to use the URI-to-path mappings
		final URI moduleURI = resolvedModule.getUri();
		if (moduleURI != null && moduleURI.getPath() != null) {
			Path path;
			
			// URI#getPath() does not correctly convert `file:/` URIs to valid paths on Windows, so use Paths#get(URI) instead
			if ("file".equals(moduleURI.getScheme())) {
				path = Paths.get(moduleURI);
			} else {
				path = Paths.get(moduleURI.getPath());
			}

			populateSourceFromMappings(bpSource, moduleURI, path);
		}

		// If that didn't produce a path, fall back to the module file if it's available
		if (bpSource.getPath() == null && resolvedModule.getFile() != null) {
			final File moduleFile = resolvedModule.getFile();
			bpSource.setName(moduleFile.getName());
			try {
				bpSource.setPath(moduleFile.getCanonicalPath());
			} catch (IOException e) {
				final String rawPath = moduleFile.getPath();

				LOGGER.log(Level.WARNING, String.format(
					"Cannot produce canonical path for '%s': " + "falling back to regular path", rawPath), e);
				bpSource.setPath(rawPath);
			}
		}

		return bpSource;
	}

	protected void populateSourceFromMappings(final Source bpSource, final URI moduleURI, Path path) {
		String basename = path.getFileName().toString();
		bpSource.setName(basename);
		mapUriToSourcePath(moduleURI.toString(), bpSource);
	}

	protected void mapUriToSourcePath(String uri, Source bpSource) {
		for (Entry<URI, Path> mapping : this.uriToPathMappings.entrySet()) {
			File mappingFile = mapping.getValue().toFile();
			if (mappingFile.isFile()) {
				if (uri.equals(mapping.getKey().toString())) {
					setSourceFile(bpSource, mappingFile);
					break;
				}
			} else if (mappingFile.isDirectory()) {
				String uriPrefix = mapping.getKey().toString();
				if (!uriPrefix.endsWith("/")) {
					uriPrefix = uriPrefix + "/";
				}
				final Path pathPrefix = mapping.getValue();

				if (uri.startsWith(uriPrefix)) {
					String uriSuffix = uri.substring(uriPrefix.length());
					File resolvedFile = pathPrefix.resolve(uriSuffix).toFile();
					if (resolvedFile.exists()) {
						setSourceFile(bpSource, resolvedFile);
						break;
					}
				}
			}
		}
	}

	protected void setSourceFile(Source bpSource, final File mappedFile) {
		try {
			bpSource.setPath(mappedFile.getCanonicalPath());
		} catch (IOException e) {
			bpSource.setPath(mappedFile.getPath());
			LOGGER.log(Level.WARNING,
				String.format(
					"Cannot produce canonical path for '%s':"
					+ " falling back to regular path",
					mappedFile.getPath()),
				e);
		}
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

	protected void sendOutput(IEolContext context, String category, String output) {
		if (client != null) {
			OutputEventArguments outputArguments = new OutputEventArguments();
			outputArguments.setCategory(category);
			outputArguments.setOutput(output);

			Two<ModuleElement, Source> location = resolveStackFrameLocation(
				context.getModule(), context.getFrameStack().getTopFrame());

			ModuleElement currentStatement = location.getFirst();
			Region region = currentStatement.getRegion();
			if (region != null) {
				Position regionStart = region.getStart();
				if (regionStart != null) {
					outputArguments.setLine(lineConverter.fromLocalToRemote(regionStart.getLine()));
					outputArguments.setColumn(columnConverter.fromLocalToRemote(regionStart.getColumn()));
				}
			}
			outputArguments.setSource(location.getSecond());

			client.output(outputArguments);
			LOGGER.fine(() -> "Sent output: " + outputArguments);
		}
	}

	protected void sendStopped(int threadId, String reason) {
		if (client != null) {
			StoppedEventArguments stoppedArgs = new StoppedEventArguments();
			stoppedArgs.setReason(reason);
			stoppedArgs.setThreadId(threadId);
			stoppedArgs.setAllThreadsStopped(true);
			client.stopped(stoppedArgs);
		}
	}

	protected void sendThreadEvent(int threadId, String reason) {
		if (client != null) {
			ThreadEventArguments args = new ThreadEventArguments();
			args.setReason(reason);
			args.setThreadId(threadId);
			client.thread(args);
		}
	}


	public IEolModule getModule() {
		return mainModule;
	}

	public void setModule(IEolModule module) {
		this.mainModule = module;
	}

	public void setOnAttach(Runnable onAttach) {
		this.onAttach = onAttach;
	}

	/**
	 * Mappings from module URIs to filesystem paths. Useful when debugging
	 * code that is loaded from a non-file URI.
	 */
	public Map<URI, Path> getUriToPathMappings() {
		return uriToPathMappings;
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
			if (entryPoint.getUri() != null) {
				builder.append(" at ");
				builder.append(entryPoint.getUri().toString());
			}
			return builder.toString();
		}
		else {
			return "globals";
		}
	}

	protected void suspend(int threadId, ModuleElement ast, SuspendReason reason) throws InterruptedException {
		synchronized (suspendedLatch) {
			/*
			 * Note: the synchronized region must cover the sending of stopped messages,
			 * as otherwise there is the risk that someone reacting to the "stopped" message
			 * may try to release the suspendedLatch before it has actually been set.
			 */
			switch (reason) {
			case STEP:
				sendStopped(threadId, StoppedEventArgumentsReason.STEP);
				break;
			case BREAKPOINT:
				sendStopped(threadId, StoppedEventArgumentsReason.BREAKPOINT);
				break;
			default:
				throw new IllegalArgumentException("Unknown suspend reason");
			}

			suspendedLatch.set(true);
			do {
				final int timeoutMillis = 500;
				suspendedLatch.wait(timeoutMillis);
			} while (suspendedLatch.get());
		}
	}

	protected void resumeAllThreads() {
		synchronized (suspendedLatch) {
			suspendedLatch.set(false);
			suspendedLatch.notifyAll();
		}
	}
	
}
