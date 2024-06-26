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
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.dap.test.AbstractEpsilonDebugAdapterTest;
import org.eclipse.lsp4j.debug.ContinueArguments;
import org.eclipse.lsp4j.debug.SetBreakpointsResponse;
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
		
		// Continue execution (should stop again)
		adapter.continue_(new ContinueArguments()).get();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		// After continuing a second time, execution should complete successfully
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

}
