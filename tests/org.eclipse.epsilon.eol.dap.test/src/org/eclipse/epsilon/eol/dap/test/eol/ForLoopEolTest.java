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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.dap.test.AbstractEpsilonDebugAdapterTest;
import org.eclipse.lsp4j.debug.BreakpointEventArguments;
import org.eclipse.lsp4j.debug.BreakpointEventArgumentsReason;
import org.eclipse.lsp4j.debug.ContinueArguments;
import org.eclipse.lsp4j.debug.EvaluateResponse;
import org.eclipse.lsp4j.debug.SetBreakpointsResponse;
import org.eclipse.lsp4j.debug.SourceBreakpoint;
import org.eclipse.lsp4j.debug.StackFrame;
import org.eclipse.lsp4j.debug.StackTraceResponse;
import org.eclipse.lsp4j.debug.StoppedEventArgumentsReason;
import org.junit.Test;

public class ForLoopEolTest extends AbstractEpsilonDebugAdapterTest {

	@Override
	protected void setupModule() throws Exception {
		this.module = new EolModule();
		module.parse(new File(BASE_RESOURCE_FOLDER, "20-for.eol"));
	}

	@Test
	public void breakInsideForLoop() throws Exception {
		SetBreakpointsResponse breakResult = adapter.setBreakpoints(createBreakpoints(createBreakpoint(2))).get();
		assertEquals(1, breakResult.getBreakpoints().length);
		assertTrue("The breakpoint should have been verified", breakResult.getBreakpoints()[0].isVerified());
		assertEquals(2, (int) breakResult.getBreakpoints()[0].getLine());

		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);
		final StackTraceResponse stackTrace = getStackTrace();
		StackFrame stackFrame = stackTrace.getStackFrames()[0];
		assertEquals("The stack frame should be on line 2", 2, stackFrame.getLine());
		assertEvaluateEquals("i", "1", stackFrame);

		// Continue execution (should stop again)
		adapter.continue_(new ContinueArguments()).get();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);
		assertEvaluateEquals("i", "2", getStackTrace().getStackFrames()[0]);

		// After continuing a second time, execution should complete successfully
		adapter.continue_(new ContinueArguments()).get();
		assertProgramCompletedSuccessfully();
	}

	@Test
	public void expressionEvaluationErrorIsReported() throws Exception {
		SetBreakpointsResponse breakResult = adapter.setBreakpoints(createBreakpoints(createBreakpoint(2))).get();
		assertEquals(1, breakResult.getBreakpoints().length);
		assertTrue("The breakpoint should have been verified", breakResult.getBreakpoints()[0].isVerified());
		assertEquals(2, (int) breakResult.getBreakpoints()[0].getLine());

		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);
		EvaluateResponse evalResult = evaluate("j", getStackTrace().getStackFrames()[0]);
		assertTrue(evalResult.getResult().startsWith("(failed to evaluate"));
		assertTrue(evalResult.getResult().contains("UndefinedVariable"));

		// Remove breakpoints and finish
		adapter.setBreakpoints(createBreakpoints());
		adapter.continue_(new ContinueArguments()).get();
		assertProgramCompletedSuccessfully();
	}

	@Test
	public void unparsableExpressionErrorIsReported() throws Exception {
		SetBreakpointsResponse breakResult = adapter.setBreakpoints(createBreakpoints(createBreakpoint(2))).get();
		assertEquals(1, breakResult.getBreakpoints().length);
		assertTrue("The breakpoint should have been verified", breakResult.getBreakpoints()[0].isVerified());
		assertEquals(2, (int) breakResult.getBreakpoints()[0].getLine());

		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);
		EvaluateResponse evalResult = evaluate("i %?% i", getStackTrace().getStackFrames()[0]);
		assertTrue(evalResult.getResult().contains("failed to parse"));

		// Remove breakpoints and finish
		adapter.setBreakpoints(createBreakpoints());
		adapter.continue_(new ContinueArguments()).get();
		assertProgramCompletedSuccessfully();
	}

	@Test
	public void parsingExceptionIsReported() throws Exception {
		SetBreakpointsResponse breakResult = adapter.setBreakpoints(createBreakpoints(createBreakpoint(2))).get();
		assertEquals(1, breakResult.getBreakpoints().length);
		assertTrue("The breakpoint should have been verified", breakResult.getBreakpoints()[0].isVerified());
		assertEquals(2, (int) breakResult.getBreakpoints()[0].getLine());

		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);
		EvaluateResponse evalResult = evaluate("23482398429384298749238428492423423423", getStackTrace().getStackFrames()[0]);
		assertTrue(evalResult.getResult().contains("failed to evaluate"));

		// Remove breakpoints and finish
		adapter.setBreakpoints(createBreakpoints());
		adapter.continue_(new ContinueArguments()).get();
		assertProgramCompletedSuccessfully();
	}

	@Test
	public void nonExistingFrameIsReported() throws Exception {
		SetBreakpointsResponse breakResult = adapter.setBreakpoints(createBreakpoints(createBreakpoint(2))).get();
		assertEquals(1, breakResult.getBreakpoints().length);
		assertTrue("The breakpoint should have been verified", breakResult.getBreakpoints()[0].isVerified());
		assertEquals(2, (int) breakResult.getBreakpoints()[0].getLine());

		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);
		StackFrame sf = new StackFrame();
		sf.setId(1234);
		EvaluateResponse evalResult = evaluate("i", sf);
		assertTrue(evalResult.getResult().contains("cannot find frame"));

		// Remove breakpoints and finish
		adapter.setBreakpoints(createBreakpoints());
		adapter.continue_(new ContinueArguments()).get();
		assertProgramCompletedSuccessfully();
	}

	@Test
	public void breakAtForStatement() throws Exception {
		SetBreakpointsResponse breakResult = adapter.setBreakpoints(createBreakpoints(createBreakpoint(1))).get();
		assertEquals(1, breakResult.getBreakpoints().length);
		assertTrue("The breakpoint should have been verified", breakResult.getBreakpoints()[0].isVerified());
		assertEquals(1, (int) breakResult.getBreakpoints()[0].getLine());

		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		// Continue execution (should run to completion - should only stop at the for statement once)
		adapter.continue_(new ContinueArguments()).get();
		assertProgramCompletedSuccessfully();
	}

	@Test
	public void conditionalBreakInsideForLoop() throws Exception {
		SourceBreakpoint breakpoint = createBreakpoint(2);
		breakpoint.setCondition("i > 1");
		SetBreakpointsResponse breakResult = adapter.setBreakpoints(createBreakpoints(breakpoint)).get();
		assertTrue("The breakpoint should have been verified", breakResult.getBreakpoints()[0].isVerified());

		// Execution should only stop once (when i == 2)
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);
		final StackTraceResponse stackTrace = getStackTrace();
		assertEquals("The stack frame should be on line 2", 2, stackTrace.getStackFrames()[0].getLine());

		adapter.continue_(new ContinueArguments()).get();
		assertProgramCompletedSuccessfully();
	}

	@Test
	public void badConditionsUnverifyBreakpoint() throws Exception {
		SourceBreakpoint breakpoint = createBreakpoint(2);
		breakpoint.setCondition("not valid EOL code");
		SetBreakpointsResponse breakResult = adapter.setBreakpoints(createBreakpoints(breakpoint)).get();
		assertTrue("The breakpoint should have been verified", breakResult.getBreakpoints()[0].isVerified());

		// Execution should only stop once (when i == 2)
		attach();
		assertProgramCompletedSuccessfully();

		assertEquals(1, client.getBreakpointEvents().size());
		BreakpointEventArguments ev = client.getBreakpointEvents().get(0);
		assertEquals("The breakpoint should have changed",
			BreakpointEventArgumentsReason.CHANGED, ev.getReason());
		assertEquals("The event should mention the line",
			2, ev.getBreakpoint().getLine().intValue());
		assertEquals("The event should mention the column",
			2, ev.getBreakpoint().getColumn().intValue());
		assertTrue("The event should mention the path",
			ev.getBreakpoint().getSource().getPath().endsWith("20-for.eol"));
		assertFalse("The breakpoint should no longer be verified",
			ev.getBreakpoint().isVerified());
	}

	@Test
	public void userDefinedOperationsCanBeCalled() throws Exception {
		SourceBreakpoint breakpoint = createBreakpoint(2);
		breakpoint.setCondition("i.multiply(3) > 3");
		SetBreakpointsResponse breakResult = adapter.setBreakpoints(createBreakpoints(breakpoint)).get();
		assertTrue("The breakpoint should have been verified", breakResult.getBreakpoints()[0].isVerified());

		// Execution should only stop once (when i == 2)
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);
		final StackTraceResponse stackTrace = getStackTrace();
		assertEquals("The stack frame should be on line 2", 2, stackTrace.getStackFrames()[0].getLine());

		adapter.continue_(new ContinueArguments()).get();
		assertProgramCompletedSuccessfully();
	}

}
