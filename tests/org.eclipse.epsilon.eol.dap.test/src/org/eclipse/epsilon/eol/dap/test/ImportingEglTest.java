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
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Map;

import org.eclipse.epsilon.egl.EglModule;
import org.eclipse.lsp4j.debug.ContinueArguments;
import org.eclipse.lsp4j.debug.SetBreakpointsResponse;
import org.eclipse.lsp4j.debug.StoppedEventArgumentsReason;
import org.eclipse.lsp4j.debug.Variable;
import org.junit.Test;

public class ImportingEglTest extends AbstractEpsilonDebugAdapterTest {

	private static final File SCRIPT_FILE = new File(BASE_RESOURCE_FOLDER, "09-imports.egl");

	@Override
	protected void setupModule() throws Exception {
		this.module = new EglModule();
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
		assertEquals("John Doe", variablesByName.get("fullName").getValue());

		// Remove breakpoints from the file and continue
		adapter.setBreakpoints(createBreakpoints(importedFile.getCanonicalPath()));
		adapter.continue_(new ContinueArguments());
		assertProgramCompletedSuccessfully();
	}

}
