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
import java.util.Map;

import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.dap.test.AbstractEpsilonDebugAdapterTest;
import org.eclipse.lsp4j.debug.ContinueArguments;
import org.eclipse.lsp4j.debug.StoppedEventArgumentsReason;
import org.eclipse.lsp4j.debug.Variable;
import org.eclipse.lsp4j.debug.VariablesResponse;
import org.junit.Test;

public class TupleWithCycleTest extends AbstractEpsilonDebugAdapterTest {

	private static final File SCRIPT_FILE = new File(BASE_RESOURCE_FOLDER, "28-tuple-cycle.eol");

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
		Variable tupleVariable = topVariables.get("t");
		assertNotNull("The top scope should have a 't' variable", tupleVariable);
		assertEquals("Tuple", tupleVariable.getType());
		assertNotEquals("The 't' variable should have its own reference",
			0, tupleVariable.getVariablesReference());

		/*
		 * We should handle the case where trying to get the string version of a value could fail
		 * (e.g. because of a StackOverflowException due to a cycle in the thing we're trying to inspect).
		 */
		assertTrue(tupleVariable.getValue().contains("failed to get value"));

		VariablesResponse tupleVars = getVariables(tupleVariable.getVariablesReference());
		Map<String, Variable> tupleVarsByName = getVariablesByName(tupleVars);
		assertEquals("The 't' variable should list one element", 1, tupleVarsByName.size());

		// For the 'myself' variable, we will get a different variablesReference as the name changes
		Variable myselfVariable = tupleVarsByName.get("myself");
		assertEquals("myself", myselfVariable.getName());
		assertNotEquals(tupleVariable.getVariablesReference(), myselfVariable.getVariablesReference());

		/*
		 * If we do t.myself.myself, then we will get the same variablesReference as before
		 * (meaning that an IDE could potentially notice such a cycle).
		 */
		VariablesResponse myselfVars = getVariables(myselfVariable.getVariablesReference());
		Map<String, Variable> myselfVarsByName = getVariablesByName(myselfVars);
		assertEquals("The 'myself' variable should list one element", 1, myselfVarsByName.size());

		Variable myselfAgainVariable = myselfVarsByName.get("myself");
		assertEquals("myself", myselfAgainVariable.getName());
		assertEquals(myselfVariable.getVariablesReference(), myselfAgainVariable.getVariablesReference());

		adapter.continue_(new ContinueArguments()).get();
		assertProgramCompletedSuccessfully();
	}
}
