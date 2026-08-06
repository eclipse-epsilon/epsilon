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
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.dap.test.AbstractEpsilonDebugAdapterTest;
import org.eclipse.epsilon.eol.dap.test.metamodel.Person;
import org.eclipse.epsilon.eol.models.java.JavaModel;
import org.eclipse.lsp4j.debug.ContinueArguments;
import org.eclipse.lsp4j.debug.StoppedEventArgumentsReason;
import org.eclipse.lsp4j.debug.Variable;
import org.eclipse.lsp4j.debug.VariablesResponse;
import org.junit.Test;

public class ModelElementPropertiesTest extends AbstractEpsilonDebugAdapterTest {

	private static final File SCRIPT_FILE = new File(BASE_RESOURCE_FOLDER, "04-fields.eol");

	@Override
	protected void setupModule() throws Exception {
		this.module = new EolModule();
		module.parse(SCRIPT_FILE);

		final JavaModel model = new JavaModel("M", new ArrayList<Object>(), new ArrayList<>(Arrays.asList(Person.class)));
		module.getContext().getModelRepository().addModel(model);
	}

	@Test
	public void canShowFields() throws Exception {
		adapter.setBreakpoints(createBreakpoints(createBreakpoint(5))).get();
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		Map<String, Variable> topVariables = getVariablesFromTopStackFrame();
		Variable personVariable = topVariables.get("p");
		assertNotNull("The top scope should have a 'p' variable", personVariable);
		assertEquals("The 'p' variable should use the same type as in EOL",
			Person.class.getCanonicalName(), personVariable.getType());
		assertNotEquals("The 'p' variable should have its own reference",
			0, personVariable.getVariablesReference());

		VariablesResponse pVars = getVariables(personVariable.getVariablesReference());
		Map<String, Variable> pVarsByName = getVariablesByName(pVars);
		assertEquals("The 'p' variable should list two properties", 2, pVarsByName.size());

		Variable nameVariable = pVarsByName.get("name");
		assertEquals("name", nameVariable.getName());
		assertEquals("John", nameVariable.getValue());

		Variable lastNameVariable = pVarsByName.get("lastName");
		assertEquals("lastName", lastNameVariable.getName());
		assertEquals("Doe", lastNameVariable.getValue());

		adapter.continue_(new ContinueArguments());
		assertProgramCompletedSuccessfully();
	}
}
