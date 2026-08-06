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
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.epsilon.egl.EglModule;
import org.eclipse.epsilon.eol.dap.test.AbstractEpsilonDebugAdapterTest;
import org.eclipse.lsp4j.debug.ContinueArguments;
import org.eclipse.lsp4j.debug.SetBreakpointsResponse;
import org.eclipse.lsp4j.debug.StoppedEventArgumentsReason;
import org.junit.Test;

public class ForCallEglTest extends AbstractEpsilonDebugAdapterTest {

	private static final File SCRIPT_FILE = new File(BASE_RESOURCE_FOLDER, "21-forCall.egl");

	@Override
	protected void setupModule() throws Exception {
		this.module = new EglModule();
		module.parse(SCRIPT_FILE);
	}

	@Test
	public void canStopAtForStatement() throws Exception {
		SetBreakpointsResponse stopResult = adapter.setBreakpoints(
			createBreakpoints(createBreakpoint(1))).get();
		assertTrue("The breakpoint at the for statement was verified",
			stopResult.getBreakpoints()[0].isVerified());
		assertEquals("The breakpoint at the for statement should be verified at its own line",
			(Integer) 1, stopResult.getBreakpoints()[0].getLine());
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		assertEquals("Should be stopped at the first line",
			1, getStackTrace().getStackFrames()[0].getLine());

		adapter.continue_(new ContinueArguments()).get();
		assertProgramCompletedSuccessfully();
	}

	@Test
	public void canStopAtCalledOperation() throws Exception {
		SetBreakpointsResponse stopResult = adapter.setBreakpoints(
			createBreakpoints(createBreakpoint(5))).get();
		assertTrue("The breakpoint at the called operation was verified",
			stopResult.getBreakpoints()[0].isVerified());
		assertEquals("The breakpoint at the called operation should be verified at its first statement",
			(Integer) 6, stopResult.getBreakpoints()[0].getLine());
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		adapter.continue_(new ContinueArguments()).get();
		assertProgramCompletedSuccessfully();
	}

	@Test
	public void canStopAtFirstOperationStatement() throws Exception {
		SetBreakpointsResponse stopResult = adapter.setBreakpoints(
			createBreakpoints(createBreakpoint(6))).get();
		assertTrue("The breakpoint at the first statement of the operation was verified",
			stopResult.getBreakpoints()[0].isVerified());
		assertEquals("The breakpoint at the first statement of the operation should be kept as requested",
			(Integer) 6, stopResult.getBreakpoints()[0].getLine());
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		adapter.continue_(new ContinueArguments()).get();
		assertProgramCompletedSuccessfully();
	}
	
}
