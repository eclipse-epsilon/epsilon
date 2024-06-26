/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.eol.dap.test.eol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Map;

import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.dap.EpsilonDebugAdapter;
import org.eclipse.epsilon.eol.dap.test.AbstractEpsilonDebugAdapterTest;
import org.eclipse.lsp4j.debug.ContinueArguments;
import org.eclipse.lsp4j.debug.NextArguments;
import org.eclipse.lsp4j.debug.ScopesResponse;
import org.eclipse.lsp4j.debug.SetBreakpointsResponse;
import org.eclipse.lsp4j.debug.SetExceptionBreakpointsArguments;
import org.eclipse.lsp4j.debug.StackTraceResponse;
import org.eclipse.lsp4j.debug.StepInArguments;
import org.eclipse.lsp4j.debug.StepOutArguments;
import org.eclipse.lsp4j.debug.StoppedEventArgumentsReason;
import org.eclipse.lsp4j.debug.TerminateArguments;
import org.eclipse.lsp4j.debug.Variable;
import org.eclipse.lsp4j.debug.VariablesResponse;
import org.junit.Test;

/**
 * Test class for an EOL script with no imports. It uses direct calls to the
 * {@link EpsilonDebugAdapter}, bypassing the use of streams and JSON-RPC.
 */
public class StandaloneEolTest extends AbstractEpsilonDebugAdapterTest {

	@Override
	protected void setupModule() throws Exception {
		this.module = new EolModule();
		module.parse(new File(BASE_RESOURCE_FOLDER, "01-hello.eol"));
	}

	@Test
	public void breakThenContinue() throws Exception {
		SetBreakpointsResponse breakResult = adapter.setBreakpoints(createBreakpoints(createBreakpoint(1))).get();
		assertEquals(1, breakResult.getBreakpoints().length);
		assertTrue("The breakpoint should have been verified", breakResult.getBreakpoints()[0].isVerified());
		assertEquals(1, (int) breakResult.getBreakpoints()[0].getLine());

		// Note: this is just to check that the operation does not throw an OperationUnsupportedException
		adapter.setExceptionBreakpoints(new SetExceptionBreakpointsArguments()).get();

		// We're done with set up - we can let the module start execution now
		attach();

		// Wait for the client to be stopped at the breakpoint
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);
		assertEquals("The debugger should mention the stopped thread",
			(Integer) EpsilonDebugAdapter.FIRST_THREAD_ID, client.getStoppedArgs().getThreadId());

		// Stack trace
		final StackTraceResponse stackTrace = getStackTrace();
		assertEquals("The debugger should only report one frame", 1, (int) stackTrace.getStackFrames().length);
		assertEquals("The stack frame should be on line 1", 1, stackTrace.getStackFrames()[0].getLine());
		assertEquals("The stack frame should mention end line as well", (Integer) 1, stackTrace.getStackFrames()[0].getEndLine());
		assertEquals("The stack frame should mention column as well", 1, stackTrace.getStackFrames()[0].getColumn());
		assertEquals("The stack frame should mention end column as well", (Integer) 45, stackTrace.getStackFrames()[0].getEndColumn());

		final String stackFramePath = stackTrace.getStackFrames()[0].getSource().getPath();
		final String modulePath = module.getFile().getCanonicalPath();
		assertTrue("The stack frame should be on the EOL file", modulePath.equals(stackFramePath));

		// Scopes
		ScopesResponse scopes = getScopes(stackTrace.getStackFrames()[0]);
		assertEquals("The debugger should report one scope", 1, scopes.getScopes().length);
		assertEquals("The debugger should report 2 named variables in the global scope",
			2, (int) scopes.getScopes()[0].getNamedVariables());
		assertTrue("The debugger should report a non-zero variable reference in the global scope",
			scopes.getScopes()[0].getVariablesReference() > 0);

		// Variables
		VariablesResponse variables = getVariables(scopes.getScopes()[0]);
		assertEquals("The debugger should list 2 variables", 2, variables.getVariables().length);

		Map<String, Variable> variablesByName = getVariablesByName(variables);
		assertTrue("The debugger should list the System variable", variablesByName.containsKey("System"));
		assertNotNull("The System variable should have a value", variablesByName.get("System").getValue());
		assertTrue("The debugger should list the null variable", variablesByName.containsKey("null"));
		assertNotNull("The null variable should have a value", variablesByName.get("null").getValue());
		
		// Continue execution
		adapter.continue_(new ContinueArguments()).get();

		// Execution should complete successfully
		assertProgramCompletedSuccessfully();

		final String expected = "Hello Bob Someone"
			+ System.lineSeparator()
			+ "Hello Alice Important"
			+ System.lineSeparator();

		assertEquals(expected, getStdout());
	}

	@Test
	public void breakThenNext() throws Exception {
		// Break at the first call of the operation 
		adapter.setBreakpoints(createBreakpoints(createBreakpoint(1))).get();
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		// Step over should stop the program at line 2
		final NextArguments args = new NextArguments();
		args.setThreadId(adapter.threads().get().getThreads()[0].getId());
		adapter.next(args);
		assertStoppedBecauseOf(StoppedEventArgumentsReason.STEP);

		StackTraceResponse stackTrace = getStackTrace();
		assertEquals("After the next() call, the program should be stopped at line 2",
			2, stackTrace.getStackFrames()[0].getLine());

		adapter.continue_(new ContinueArguments());
		assertProgramCompletedSuccessfully();
	}

	@Test
	public void breakThenStepIn() throws Exception {
		// Break at the second call of the operation 
		adapter.setBreakpoints(createBreakpoints(createBreakpoint(2))).get();
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		// Step over should stop the program at the first line of the operation
		final StepInArguments args = new StepInArguments();
		args.setThreadId(adapter.threads().get().getThreads()[0].getId());
		adapter.stepIn(args);

		assertStoppedBecauseOf(StoppedEventArgumentsReason.STEP);
		StackTraceResponse stackTrace = getStackTrace();
		assertEquals("After the next() call, the program should be stopped at the first line of the operation",
			5, stackTrace.getStackFrames()[0].getLine());

		adapter.continue_(new ContinueArguments());
		assertProgramCompletedSuccessfully();
	}

	@Test
	public void breakThenTerminate() throws Exception {
		adapter.setBreakpoints(createBreakpoints(createBreakpoint(1))).get();
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		adapter.terminate(new TerminateArguments());
		assertProgramFailed();
	}

	@Test
	public void breakThenStepOut() throws Exception {
		// Break at the first line of the operation 
		adapter.setBreakpoints(createBreakpoints(createBreakpoint(5))).get();
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		// First step out should stop the program at the line after the operation
		final StepOutArguments args = new StepOutArguments();
		args.setThreadId(adapter.threads().get().getThreads()[0].getId());
		adapter.stepOut(args);
		
		assertStoppedBecauseOf(StoppedEventArgumentsReason.STEP);
		StackTraceResponse stackTrace = getStackTrace();
		assertEquals("After the stepOut() call, the program should be stopped at the line after the operation call",
			2, stackTrace.getStackFrames()[0].getLine());

		// If we continue, we'll stop again at the breakpoint
		adapter.continue_(new ContinueArguments());
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		// Second step out should just have the problem complete successfully
		adapter.stepOut(args);
		assertProgramCompletedSuccessfully();
	}

	@Test
	public void breakAtEmptyLine() throws Exception {
		// Break at empty line in the middle of the operation should be mapped to its final line 
		SetBreakpointsResponse breakpoints = adapter.setBreakpoints(createBreakpoints(createBreakpoint(6))).get();

		// Let the program finish while we do the assertions
		adapter.setBreakpoints(createBreakpoints()).get();
		attach();
		assertProgramCompletedSuccessfully();

		assertEquals("The breakpoint on the empty line should have been remapped to the first non-empty line after it",
			7, (int) breakpoints.getBreakpoints()[0].getLine());
	}
}
