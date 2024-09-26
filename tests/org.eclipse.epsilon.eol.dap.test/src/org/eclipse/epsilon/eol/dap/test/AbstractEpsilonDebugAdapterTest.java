/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.eol.dap.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.dap.EpsilonDebugAdapter;
import org.eclipse.lsp4j.debug.DisconnectArguments;
import org.eclipse.lsp4j.debug.ExitedEventArguments;
import org.eclipse.lsp4j.debug.InitializeRequestArguments;
import org.eclipse.lsp4j.debug.OutputEventArguments;
import org.eclipse.lsp4j.debug.OutputEventArgumentsCategory;
import org.eclipse.lsp4j.debug.Scope;
import org.eclipse.lsp4j.debug.ScopesArguments;
import org.eclipse.lsp4j.debug.ScopesResponse;
import org.eclipse.lsp4j.debug.SetBreakpointsArguments;
import org.eclipse.lsp4j.debug.Source;
import org.eclipse.lsp4j.debug.SourceBreakpoint;
import org.eclipse.lsp4j.debug.StackFrame;
import org.eclipse.lsp4j.debug.StackTraceArguments;
import org.eclipse.lsp4j.debug.StackTraceResponse;
import org.eclipse.lsp4j.debug.StoppedEventArguments;
import org.eclipse.lsp4j.debug.ThreadEventArguments;
import org.eclipse.lsp4j.debug.ThreadEventArgumentsReason;
import org.eclipse.lsp4j.debug.ThreadsResponse;
import org.eclipse.lsp4j.debug.Variable;
import org.eclipse.lsp4j.debug.VariablesArguments;
import org.eclipse.lsp4j.debug.VariablesResponse;
import org.eclipse.lsp4j.debug.services.IDebugProtocolClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.Timeout;

public abstract class AbstractEpsilonDebugAdapterTest {

	/** Timeout used for various assertions in this base class. */
	private static final int TIMEOUT_SECONDS = 5;

	@Rule
	public Timeout globalTimeout = Timeout.seconds(TIMEOUT_SECONDS * 2);

	protected class TestClient implements IDebugProtocolClient {
		private Semaphore isStopped = new Semaphore(0);
		private StoppedEventArguments stoppedArgs;

		private Semaphore isExited = new Semaphore(0);
		private ExitedEventArguments exitedArgs;

		private List<OutputEventArguments> outputs = new ArrayList<>();
		private List<ThreadEventArguments> threadEvents = new ArrayList<>();

		@Override
		public void stopped(StoppedEventArguments args) {
			this.stoppedArgs = args;
			isStopped.release();
		}

		@Override
		public void exited(ExitedEventArguments args) {
			this.exitedArgs = args;
			isExited.release();
		}

		@Override
		public void output(OutputEventArguments args) {
			this.outputs.add(args);
		}

		@Override
		public void thread(ThreadEventArguments args) {
			this.threadEvents.add(args);
		}

		public StoppedEventArguments getStoppedArgs() {
			return stoppedArgs;
		}

		public ExitedEventArguments getExitedArgs() {
			return exitedArgs;
		}

		public List<OutputEventArguments> getOutputs() {
			return outputs;
		}

		public List<ThreadEventArguments> getThreadEvents() {
			return threadEvents;
		}
	}

	protected static final File BASE_RESOURCE_FOLDER = new File("../org.eclipse.epsilon.eol.dap.test/epsilon/");
	protected static final File BASE_MODELS_FOLDER = new File("../org.eclipse.epsilon.eol.dap.test/models/");

	protected IEolModule module;
	protected EpsilonDebugAdapter adapter;
	protected TestClient client;

	protected abstract void setupModule() throws Exception;

	protected void setupAdapter() throws Exception {
		// do nothing by default
	}
	protected void setupInitializeArguments(InitializeRequestArguments args) {
		args.setSupportsVariableType(true);
	}

	@Before
	public void setup() throws Exception {
		setupModule();

		this.adapter = new EpsilonDebugAdapter();
		adapter.setModule(module);
		adapter.setOnAttach(this::onAttach);
		setupAdapter();

		this.client = new TestClient();

		final InitializeRequestArguments initArgs = new InitializeRequestArguments();
		initArgs.setClientID("test-client");
		initArgs.setAdapterID("epsilon-adapter");
		setupInitializeArguments(initArgs);
		adapter.initialize(initArgs).get();

		adapter.connect(client);
	}

	@After
	public void teardown() {
		adapter.disconnect(new DisconnectArguments());
	}

	protected void assertStoppedBecauseOf(final String expectedReason) throws InterruptedException {
		boolean acquired = client.isStopped.tryAcquire(TIMEOUT_SECONDS, TimeUnit.SECONDS);
		assertTrue("The script should have stopped within " + TIMEOUT_SECONDS + " seconds", acquired);
		assertNotNull("The script should have provided stopping arguments", client.stoppedArgs);
		assertEquals("The script should stop for the expected reason" , expectedReason, client.stoppedArgs.getReason());
	}

	protected void assertProgramCompletedSuccessfully() throws InterruptedException {
		boolean acquired = client.isExited.tryAcquire(TIMEOUT_SECONDS, TimeUnit.SECONDS);
		assertTrue("The script should have exited within " + TIMEOUT_SECONDS + " seconds", acquired);
		assertNotNull("The script should have provided exiting arguments", client.exitedArgs);
		assertEquals("The script should have completed its execution successfully", 0, client.exitedArgs.getExitCode());
	}

	protected void assertProgramFailed() throws InterruptedException {
		client.isExited.tryAcquire(TIMEOUT_SECONDS, TimeUnit.SECONDS);
		assertNotNull("The script should have exited within " + TIMEOUT_SECONDS + " seconds", client.exitedArgs);
		assertEquals("The script should have completed its execution with an error", 1, client.exitedArgs.getExitCode());
	}

	protected StackTraceResponse getStackTrace() throws Exception {
		ThreadsResponse threads = adapter.threads().get(TIMEOUT_SECONDS, TimeUnit.SECONDS);
		assertEquals("The debugger should report one thread within " + TIMEOUT_SECONDS + " seconds", 1, threads.getThreads().length);

		final int threadId = threads.getThreads()[0].getId();
		return getStackTrace(threadId);
	}

	protected StackTraceResponse getStackTrace(final int threadId) throws Exception {
		final StackTraceArguments stackTraceArgs = createStackTraceArgs(threadId);
		StackTraceResponse stackTrace = adapter.stackTrace(stackTraceArgs).get(TIMEOUT_SECONDS, TimeUnit.SECONDS);
		return stackTrace;
	}

	protected int getCurrentLine(StackTraceResponse resp) {
		return resp.getStackFrames()[0].getLine();
	}

	protected int getCurrentStartColumn(StackTraceResponse resp) {
		return resp.getStackFrames()[0].getColumn();
	}

	protected int getCurrentEndColumn(StackTraceResponse resp) {
		return resp.getStackFrames()[0].getEndColumn();
	}

	protected Map<String, Variable> getVariablesByName(VariablesResponse variables) {
		Map<String, Variable> variablesByName = new HashMap<>();
		for (Variable v : variables.getVariables()) {
			variablesByName.put(v.getName(), v);
		}
		return variablesByName;
	}

	protected String getModulePath() throws IOException {
		return module.getFile().getCanonicalPath();
	}

	protected Source createSource(final String path) {
		Source source = new Source();
		source.setPath(path);
		return source;
	}

	protected StackTraceArguments createStackTraceArgs(final int threadId) {
		final StackTraceArguments stackTraceArgs = new StackTraceArguments();
		stackTraceArgs.setThreadId(threadId);
		return stackTraceArgs;
	}

	protected SetBreakpointsArguments createBreakpoints(String path, SourceBreakpoint... breakpoints) throws IOException {
		SetBreakpointsArguments setBreaks = new SetBreakpointsArguments();
		setBreaks.setSource(createSource(path));
		setBreaks.setBreakpoints(breakpoints);
		return setBreaks;
	}

	protected SetBreakpointsArguments createBreakpoints(SourceBreakpoint... breakpoints) throws IOException {
		return createBreakpoints(getModulePath(), breakpoints);
	}

	protected SourceBreakpoint createBreakpoint(final int line) {
		SourceBreakpoint sbp = new SourceBreakpoint();
		sbp.setLine(line);
		return sbp;
	}

	protected void onAttach() {
		Thread epsilonThread = new Thread(this::runModule);
		epsilonThread.setName("EpsilonDebuggee");
		epsilonThread.start();
	}

	protected void runModule() {
		try {
			module.execute();
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			module.getContext().getModelRepository().dispose();
			module.getContext().dispose();
			module = null;
		}
	}

	protected VariablesResponse getVariables(Scope scope) throws Exception {
		return getVariables(scope.getVariablesReference());
	}

	protected VariablesResponse getVariables(final int variablesReference)
			throws InterruptedException, ExecutionException {
		VariablesArguments variablesArgs = new VariablesArguments();
		variablesArgs.setVariablesReference(variablesReference);
		VariablesResponse variables = adapter.variables(variablesArgs).get();
		return variables;
	}

	protected ScopesResponse getScopes(StackFrame stackFrame) throws Exception {
		ScopesArguments scopesArgs = new ScopesArguments();
		scopesArgs.setFrameId(stackFrame.getId());
		ScopesResponse scopes = adapter.scopes(scopesArgs).get();
		return scopes;
	}

	protected String getStdout() {
		return getOutputByCategory(OutputEventArgumentsCategory.STDOUT);
	}

	protected String getOutputByCategory(final String category) {
		String allOutput = client.outputs.stream()
			.filter(o -> category.equals(o.getCategory()))
			.map(o -> o.getOutput())
			.reduce("", (a, b) -> a + b);
		return allOutput;
	}

	protected String getStderr() {
		return getOutputByCategory(OutputEventArgumentsCategory.STDERR);
	}

	protected void attach() throws InterruptedException, ExecutionException {
		adapter.attach(Collections.emptyMap()).get();
	}

	protected Map<String, Variable> getVariablesFromTopStackFrame() throws Exception {
		StackTraceResponse stackTrace = getStackTrace();
		ScopesResponse scopes = getScopes(stackTrace.getStackFrames()[0]);
		VariablesResponse variables = getVariables(scopes.getScopes()[0]);
		Map<String, Variable> variablesByName = getVariablesByName(variables);
		return variablesByName;
	}

	protected void assertThreadStarted(int threadId) {
		assertThreadEventWithReasonExists(threadId, ThreadEventArgumentsReason.STARTED);
	}

	protected void assertThreadExited(int threadId) {
		assertThreadEventWithReasonExists(threadId, ThreadEventArgumentsReason.EXITED);
	}

	protected void assertThreadEventWithReasonExists(int threadId, final String reason) {
		for (ThreadEventArguments ev : client.getThreadEvents()) {
			if (ev.getThreadId() == threadId && reason.equals(ev.getReason())) {
				return;
			}
		}

		fail(String.format("Expected thread %d to have a %s event", threadId, reason));
	}

}
