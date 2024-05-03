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
import java.util.Collections;
import java.util.Map;

import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.lsp4j.debug.ContinueArguments;
import org.eclipse.lsp4j.debug.ScopesResponse;
import org.eclipse.lsp4j.debug.SetBreakpointsResponse;
import org.eclipse.lsp4j.debug.StackTraceResponse;
import org.eclipse.lsp4j.debug.StoppedEventArgumentsReason;
import org.eclipse.lsp4j.debug.Variable;
import org.eclipse.lsp4j.debug.VariablesResponse;
import org.junit.Test;

public class ImportingEolTest extends AbstractEpsilonDebugAdapterTest {

	@Override
	protected void setupModule() throws Exception {
		this.module = new EolModule();
		module.parse(new File(BASE_RESOURCE_FOLDER, "02-imports-main.eol"));
	}

	@Test
	public void breakOnImported() throws Exception {
		final File importedFile = new File(BASE_RESOURCE_FOLDER, "02-imports-imported.eol");
		SetBreakpointsResponse breakpoints = adapter.setBreakpoints(
			createBreakpoints(importedFile.getCanonicalPath(), createBreakpoint(3))).get();
		assertTrue("The breakpoint on the imported file should be recognised",
			breakpoints.getBreakpoints()[0].isVerified());

		adapter.attach(Collections.emptyMap()).get();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);
		Map<String, Variable> variablesByName = getVariablesFromTopStackFrame();
		assertEquals("Bob Someone", variablesByName.get("fullName").getValue());

		// Remove breakpoints from the file and continue
		adapter.setBreakpoints(createBreakpoints(importedFile.getCanonicalPath()));
		adapter.continue_(new ContinueArguments());
		assertProgramCompletedSuccessfully();
	}

	protected Map<String, Variable> getVariablesFromTopStackFrame() throws Exception {
		StackTraceResponse stackTrace = getStackTrace();
		ScopesResponse scopes = getScopes(stackTrace.getStackFrames()[0]);
		VariablesResponse variables = getVariables(scopes);
		Map<String, Variable> variablesByName = getVariablesByName(variables);
		return variablesByName;
	}

}
