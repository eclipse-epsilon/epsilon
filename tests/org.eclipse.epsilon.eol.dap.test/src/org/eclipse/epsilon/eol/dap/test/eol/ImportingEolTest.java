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
import java.util.Map;

import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.dap.test.AbstractEpsilonDebugAdapterTest;
import org.eclipse.lsp4j.debug.ContinueArguments;
import org.eclipse.lsp4j.debug.SetBreakpointsResponse;
import org.eclipse.lsp4j.debug.StoppedEventArgumentsReason;
import org.eclipse.lsp4j.debug.Variable;
import org.junit.Test;

public class ImportingEolTest extends AbstractEpsilonDebugAdapterTest {

	private static final File SCRIPT_FILE = new File(BASE_RESOURCE_FOLDER, "02-imports-main.eol");

	@Override
	protected void setupModule() throws Exception {
		this.module = new EolModule();
		module.parse(SCRIPT_FILE);
	}

	@Test
	public void breakOnImported() throws Exception {
		final File importedFile = new File(BASE_RESOURCE_FOLDER, "02-imports-imported.eol");
		SetBreakpointsResponse breakpoints = adapter.setBreakpoints(
			createBreakpoints(importedFile.getCanonicalPath(), createBreakpoint(3))).get();
		assertTrue("The breakpoint on the imported file should be recognised",
			breakpoints.getBreakpoints()[0].isVerified());

		attach();

		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);
		Map<String, Variable> variablesByName = getVariablesFromTopStackFrame();
		assertEquals("Bob Someone", variablesByName.get("fullName").getValue());

		// Remove breakpoints from the file and continue
		adapter.setBreakpoints(createBreakpoints(importedFile.getCanonicalPath()));
		adapter.continue_(new ContinueArguments());
		assertProgramCompletedSuccessfully();
	}

	@Test
	public void breakOnImportMovesToFirstStatement() throws Exception {
		SetBreakpointsResponse breakpoints = adapter.setBreakpoints(
			createBreakpoints(createBreakpoint(1))).get();
		assertTrue("The breakpoint on the imported file should be recognised",
			breakpoints.getBreakpoints()[0].isVerified());
		assertEquals("The breakpoint on the imported file should be moved to the line of the first statement",
				(Integer) 3, breakpoints.getBreakpoints()[0].getLine());

		attach();

		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);
		assertEquals(3, getStackTrace().getStackFrames()[0].getLine());

		// Remove breakpoints from the file and continue
		adapter.setBreakpoints(createBreakpoints());
		adapter.continue_(new ContinueArguments());
		assertProgramCompletedSuccessfully();
	}
	
}
