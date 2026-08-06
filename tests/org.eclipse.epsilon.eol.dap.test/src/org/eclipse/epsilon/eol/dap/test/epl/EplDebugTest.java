/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.eol.dap.test.epl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.eclipse.epsilon.eol.dap.test.AbstractEpsilonDebugAdapterTest;
import org.eclipse.epsilon.eol.dap.test.metamodel.Person;
import org.eclipse.epsilon.eol.models.java.JavaModel;
import org.eclipse.epsilon.epl.EplModule;
import org.eclipse.lsp4j.debug.ContinueArguments;
import org.eclipse.lsp4j.debug.ScopesResponse;
import org.eclipse.lsp4j.debug.SetBreakpointsResponse;
import org.eclipse.lsp4j.debug.StackTraceResponse;
import org.eclipse.lsp4j.debug.StoppedEventArgumentsReason;
import org.eclipse.lsp4j.debug.Variable;
import org.eclipse.lsp4j.debug.VariablesResponse;
import org.junit.Test;

public class EplDebugTest extends AbstractEpsilonDebugAdapterTest {

	private static final File SCRIPT_FILE = new File(BASE_RESOURCE_FOLDER, "15-match.epl");

	@Override
	protected void setupModule() throws Exception {
		this.module = new EplModule();
		module.parse(SCRIPT_FILE);

		final ArrayList<Object> objects = new ArrayList<Object>();
		objects.add(new Person("John", "Doe"));
		final JavaModel model = new JavaModel("M", objects, new ArrayList<>(Arrays.asList(Person.class)));
		module.getContext().getModelRepository().addModel(model);
	}

	@Test
	public void canStopOnMatchExpression() throws Exception {
		SetBreakpointsResponse result = adapter.setBreakpoints(createBreakpoints(createBreakpoint(3))).get();
		assertTrue("The breakpoint on the match expression should be verified", result.getBreakpoints()[0].isVerified());
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		// The stack trace for EPL includes a first unprotected frame with no variables - skip it
		StackTraceResponse stackTrace = getStackTrace();
		ScopesResponse scopes = getScopes(stackTrace.getStackFrames()[1]);
		VariablesResponse variables = getVariables(scopes.getScopes()[0]);
		Map<String, Variable> varsByName = getVariablesByName(variables);
		Variable personVariable = varsByName.get("p");
		assertNotNull("The scope for the second stack frame should have a 'p' variable", personVariable);

		adapter.setBreakpoints(createBreakpoints()).get();
		adapter.continue_(new ContinueArguments()).get();
		assertProgramCompletedSuccessfully();
	}
	
}
