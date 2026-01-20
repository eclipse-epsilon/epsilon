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
import java.util.Map;

import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.dap.test.AbstractEpsilonDebugAdapterTest;
import org.eclipse.lsp4j.debug.ContinueArguments;
import org.eclipse.lsp4j.debug.StoppedEventArgumentsReason;
import org.eclipse.lsp4j.debug.Variable;
import org.eclipse.lsp4j.debug.VariablesResponse;
import org.junit.Test;

public class SmallCollectionTest extends AbstractEpsilonDebugAdapterTest {

	private static final File SCRIPT_FILE = new File(BASE_RESOURCE_FOLDER, "05-smallCollection.eol");

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
		Variable numbersVariable = topVariables.get("numbers");
		assertNotNull("The top scope should have a 'numbers' variable", numbersVariable);
		assertEquals("Sequence", numbersVariable.getType());
		assertNotEquals("The 'numbers' variable should have its own reference",
			0, numbersVariable.getVariablesReference());

		VariablesResponse numbersVars = getVariables(numbersVariable.getVariablesReference());
		Map<String, Variable> numbersVarsByName = getVariablesByName(numbersVars);
		assertEquals("The 'numbers' variable should list ten elements", 10, numbersVarsByName.size());

		Variable firstVariable = numbersVarsByName.get("numbers[0]");
		assertEquals("numbers[0]", firstVariable.getName());
		assertEquals("1", firstVariable.getValue());
		assertEquals("Integer", firstVariable.getType());

		Variable lastVariable = numbersVarsByName.get("numbers[9]");
		assertEquals("numbers[9]", lastVariable.getName());
		assertEquals("10", lastVariable.getValue());

		adapter.continue_(new ContinueArguments());
		assertProgramCompletedSuccessfully();
	}
}
