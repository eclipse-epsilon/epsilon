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
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.dap.test.AbstractEpsilonDebugAdapterTest;
import org.eclipse.epsilon.eol.dap.variables.maps.MapEntryReference;
import org.eclipse.lsp4j.debug.ContinueArguments;
import org.eclipse.lsp4j.debug.StoppedEventArgumentsReason;
import org.eclipse.lsp4j.debug.Variable;
import org.eclipse.lsp4j.debug.VariablesResponse;
import org.junit.Test;

public class SmallMapTest extends AbstractEpsilonDebugAdapterTest {

	private static final File SCRIPT_FILE = new File(BASE_RESOURCE_FOLDER, "25-smallMap.eol");

	@Override
	protected void setupModule() throws Exception {
		this.module = new EolModule();
		module.parse(SCRIPT_FILE);
	}

	@Test
	public void canShowElements() throws Exception {
		adapter.setBreakpoints(createBreakpoints(createBreakpoint(3))).get();
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		Map<String, Variable> topVariables = getVariablesFromTopStackFrame();
		Variable mapVariable = topVariables.get("m");
		assertNotNull("The top scope should have an 'm' variable", mapVariable);
		assertEquals("Map", mapVariable.getType());
		assertNotEquals("The 'm' variable should have its own reference",
			0, mapVariable.getVariablesReference());

		VariablesResponse numbersVars = getVariables(mapVariable.getVariablesReference());
		Map<String, Variable> numbersVarsByName = getVariablesByName(numbersVars);
		assertEquals("The 'm' variable should list two elements", 2, numbersVarsByName.size());

		/*
		 * Using an indexed list allows us to separately display key and value (in case
		 * the key is a complex object of its own).
		 */
		assertTrue("The 'm' variable should have an indexed list",
			numbersVarsByName.containsKey("m[0]"));
		final Variable firstElement = numbersVarsByName.get("m[0]");
		final int firstElementVR = firstElement.getVariablesReference();
		assertNotEquals("Each element should have its own reference", 0, firstElementVR);
		assertTrue(firstElement.getValue().startsWith("Map entry for key: "));

		VariablesResponse entryVars = getVariables(firstElementVR);
		Map<String, Variable> entryVarsByName = getVariablesByName(entryVars);
		final HashSet<String> expectedEntryVariableNames = new HashSet<>(Arrays.asList(
			MapEntryReference.KEY_VARIABLE_NAME, MapEntryReference.VALUE_VARIABLE_NAME));
		assertEquals("Each entry should have variables for its key and its name",
			expectedEntryVariableNames,
			entryVarsByName.keySet());

		adapter.continue_(new ContinueArguments());
		assertProgramCompletedSuccessfully();
	}
}
