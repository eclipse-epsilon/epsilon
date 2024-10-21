/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.eol.dap.test.egl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Collections;
import java.util.Map;

import org.eclipse.epsilon.egl.EglModule;
import org.eclipse.epsilon.eol.dap.EpsilonDebugAdapter;
import org.eclipse.epsilon.eol.dap.test.AbstractEpsilonDebugAdapterTest;
import org.eclipse.lsp4j.debug.ContinueArguments;
import org.eclipse.lsp4j.debug.SetBreakpointsResponse;
import org.eclipse.lsp4j.debug.SourceBreakpoint;
import org.eclipse.lsp4j.debug.StackTraceResponse;
import org.eclipse.lsp4j.debug.StoppedEventArgumentsReason;
import org.eclipse.lsp4j.debug.Variable;
import org.junit.Test;

public class EglDebugTest extends AbstractEpsilonDebugAdapterTest {

	private static final File SCRIPT_FILE = new File(BASE_RESOURCE_FOLDER, "08-hello.egl");

	@Override
	protected void setupModule() throws Exception {
		this.module = new EglModule();
		module.parse(SCRIPT_FILE);
	}

	@Test
	public void canStopOnFirstLine() throws Exception {
		SetBreakpointsResponse stopResult = adapter.setBreakpoints(
			createBreakpoints(createBreakpoint(1))).get();
		assertTrue("The breakpoint at line 2 was verified",
			stopResult.getBreakpoints()[0].isVerified());
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		adapter.continue_(new ContinueArguments()).get();
		assertProgramCompletedSuccessfully();
	}

	@Test
	public void canStopToSeeVariable() throws Exception {
		SetBreakpointsResponse stopResult = adapter.setBreakpoints(
			createBreakpoints(createBreakpoint(2))).get();
		assertTrue("The breakpoint at line 2 was verified",
			stopResult.getBreakpoints()[0].isVerified());
		attach();

		// Break on the first static segment of line 2
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		StackTraceResponse stackTrace = getStackTrace();
		assertEquals("Should be stopped at the second line", 2, getCurrentLine(stackTrace));
		assertEquals("Should be stopped from the first column", 1, getCurrentStartColumn(stackTrace));
		assertEquals("Should be stopped until column 7", 7, getCurrentEndColumn(stackTrace));

		Map<String, Variable> topVariables = getVariablesFromTopStackFrame();
		Variable numbersVariable = topVariables.get("name");
		assertNotNull("The top scope should have a 'name' variable", numbersVariable);
		assertEquals("String", numbersVariable.getType());
		assertEquals("Joe", numbersVariable.getValue());

		// Continue: it should finish running - if we don't use an inline breakpoint,
		// we will only break at the first location in the line.
		adapter.continue_(new ContinueArguments()).get();
		assertProgramCompletedSuccessfully();
	}

	@Test
	public void oneInlineBreakpoint() throws Exception {
		// Set an inline breakpoint inside the [%=name%] dynamic region
		SourceBreakpoint bp = createBreakpoint(2);
		bp.setColumn(10);
		SetBreakpointsResponse stopResult = adapter.setBreakpoints(
				createBreakpoints(bp)).get();
		assertTrue("The breakpoint at line 2 was verified",
			stopResult.getBreakpoints()[0].isVerified());
		attach();

		// It should only stop in the dynamic region
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);
		StackTraceResponse stackTrace = getStackTrace();
		assertEquals("Should be stopped at the second line", 2, getCurrentLine(stackTrace));
		assertEquals("Should be stopped from column 10", 10, getCurrentStartColumn(stackTrace));

		// Continue: it should complete
		adapter.continue_(new ContinueArguments()).get();
		assertProgramCompletedSuccessfully();
	}

	@Test
	public void defaultAndInlineBreakpoints() throws Exception {
		// Set an inline breakpoint inside the [%=name%] dynamic region
		SourceBreakpoint inlineBP = createBreakpoint(2);
		inlineBP.setColumn(10);
		SetBreakpointsResponse stopResult = adapter.setBreakpoints(
			createBreakpoints(createBreakpoint(2), inlineBP)).get();
		assertTrue("The start-of-line breakpoint at line 2 was verified",
			stopResult.getBreakpoints()[0].isVerified());
		assertTrue("The inline breakpoint at line 2 was verified",
				stopResult.getBreakpoints()[1].isVerified());
		attach();

		// Break on the first static region of line 2
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);
		StackTraceResponse stackTrace = getStackTrace();
		assertEquals("Should be stopped at the second line", 2, getCurrentLine(stackTrace));
		assertEquals("Should be stopped from the first column", 1, getCurrentStartColumn(stackTrace));
		assertEquals("Should be stopped until column 7", 7, getCurrentEndColumn(stackTrace));

		// It should then stop in the dynamic region
		adapter.continue_(new ContinueArguments()).get();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);
		stackTrace = getStackTrace();
		assertEquals("Should be stopped at the second line", 2, getCurrentLine(stackTrace));
		assertEquals("Should be stopped from column 10", 10, getCurrentStartColumn(stackTrace));

		// Continue: it should complete
		adapter.continue_(new ContinueArguments()).get();
		assertProgramCompletedSuccessfully();
	}

	@Test
	public void stopAtAllStatements() throws Exception {
		SetBreakpointsResponse stopResult = adapter.setBreakpoints(createBreakpoints(createBreakpoint(2))).get();
		assertTrue("The breakpoint at line 2 was verified", stopResult.getBreakpoints()[0].isVerified());

		// Attach with the "stop at every statement" option turned on
		attach(Collections.singletonMap(EpsilonDebugAdapter.STOP_AT_EVERY_STATEMENT, "true"));

		// Break on the first static region of line 2
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);
		StackTraceResponse stackTrace = getStackTrace();
		assertEquals("Should be stopped at the second line", 2, getCurrentLine(stackTrace));
		assertEquals("Should be stopped from the first column", 1, getCurrentStartColumn(stackTrace));

		// Continue to the dynamic region
		adapter.continue_(new ContinueArguments()).get();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);
		stackTrace = getStackTrace();
		assertEquals("Should be stopped at the dynamic region", 10, getCurrentStartColumn(stackTrace));

		// Continue to the second static region
		adapter.continue_(new ContinueArguments()).get();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);
		stackTrace = getStackTrace();
		assertEquals("Should be stopped at the dynamic region", 16, getCurrentStartColumn(stackTrace));

		// Continue to the newline
		adapter.continue_(new ContinueArguments()).get();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);
		stackTrace = getStackTrace();
		assertEquals("Should be stopped at the dynamic region", 17, getCurrentStartColumn(stackTrace));

		// Continue: it should complete
		adapter.continue_(new ContinueArguments()).get();
		assertProgramCompletedSuccessfully();
	}
}
