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
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.Map;

import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.lsp4j.debug.ContinueArguments;
import org.eclipse.lsp4j.debug.StoppedEventArgumentsReason;
import org.eclipse.lsp4j.debug.Variable;
import org.eclipse.lsp4j.debug.VariablesResponse;
import org.junit.Test;

public class TupleTest extends AbstractEpsilonDebugAdapterTest {

	private static final File SCRIPT_FILE = new File(BASE_RESOURCE_FOLDER, "07-tuples.eol");

	@Override
	protected void setupModule() throws Exception {
		this.module = new EolModule();
		module.parse(SCRIPT_FILE);
	}

	@Test
	public void canShowElements() throws Exception {
		adapter.setBreakpoints(createBreakpoints(createBreakpoint(5))).get();
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		Map<String, Variable> topVariables = getVariablesFromTopStackFrame();
		Variable tupleVariable = topVariables.get("t");
		assertNotNull("The top scope should have a 't' variable", tupleVariable);
		assertEquals("Tuple", tupleVariable.getType());
		assertNotEquals("The 't' variable should have its own reference",
			0, tupleVariable.getVariablesReference());

		VariablesResponse tupleVars = getVariables(tupleVariable.getVariablesReference());
		Map<String, Variable> tupleVarsByName = getVariablesByName(tupleVars);
		assertEquals("The 't' variable should list two elements", 2, tupleVarsByName.size());

		Variable nameVariable = tupleVarsByName.get("name");
		assertEquals("name", nameVariable.getName());
		assertEquals("X", nameVariable.getValue());
		assertEquals("String", nameVariable.getType());

		Variable valueVariable = tupleVarsByName.get("value");
		assertEquals("value", valueVariable.getName());
		assertEquals("1234", valueVariable.getValue());
		assertEquals("Integer", valueVariable.getType());

		adapter.continue_(new ContinueArguments());
		assertProgramCompletedSuccessfully();
	}
}
