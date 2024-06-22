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

	@Rule
	public Timeout globalTimeout = Timeout.seconds(10);

	protected class TestClient implements IDebugProtocolClient {
		Semaphore isStopped = new Semaphore(0);
		StoppedEventArguments stoppedArgs;

		Semaphore isExited = new Semaphore(0);
		ExitedEventArguments exitedArgs;

		List<OutputEventArguments> outputs = new ArrayList<>();

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
	}

	protected static final File BASE_RESOURCE_FOLDER = new File("../org.eclipse.epsilon.eol.dap.test/epsilon/");

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

	protected void assertStoppedBecauseOf(final String reason) throws InterruptedException {
		client.isStopped.acquire();//.tryAcquire(5, TimeUnit.SECONDS);
		assertNotNull("The script should have stopped within 5s", client.stoppedArgs);
		assertEquals("The debugger should say it stopped because of " + reason, reason, client.stoppedArgs.getReason());
		client.stoppedArgs = null;
	}

	protected void assertProgramCompletedSuccessfully() throws InterruptedException {
		client.isExited.tryAcquire(5, TimeUnit.SECONDS);
		assertNotNull("The script should have exited within 5s", client.exitedArgs);
		assertEquals("The script should have completed its execution successfully", 0, client.exitedArgs.getExitCode());
		client.exitedArgs = null;
	}

	protected void assertProgramFailed() throws InterruptedException {
		client.isExited.tryAcquire(5, TimeUnit.SECONDS);
		assertNotNull("The script should have exited within 5s", client.exitedArgs);
		assertEquals("The script should have completed its execution with an error", 1, client.exitedArgs.getExitCode());
		client.exitedArgs = null;
	}

	protected StackTraceResponse getStackTrace() throws Exception {
		ThreadsResponse threads = adapter.threads().get(5, TimeUnit.SECONDS);
		assertEquals("The debugger should report one thread", 1, threads.getThreads().length);

		final int threadId = threads.getThreads()[0].getId();
		return getStackTrace(threadId);
	}

	protected StackTraceResponse getStackTrace(final int threadId) throws Exception {
		final StackTraceArguments stackTraceArgs = createStackTraceArgs(threadId);
		StackTraceResponse stackTrace = adapter.stackTrace(stackTraceArgs).get(5, TimeUnit.SECONDS);
		return stackTrace;
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
		String allOutput = client.outputs.stream()
			.filter(o -> OutputEventArgumentsCategory.STDOUT.equals(o.getCategory()))
			.map(o -> o.getOutput())
			.reduce("", (a, b) -> a + b);
		return allOutput;
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

}
