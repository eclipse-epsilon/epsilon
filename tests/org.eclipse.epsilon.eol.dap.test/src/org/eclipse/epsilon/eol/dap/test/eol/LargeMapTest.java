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
import org.eclipse.epsilon.eol.dap.variables.SuspendedState;
import org.eclipse.lsp4j.debug.ContinueArguments;
import org.eclipse.lsp4j.debug.StoppedEventArgumentsReason;
import org.eclipse.lsp4j.debug.Variable;
import org.eclipse.lsp4j.debug.VariablesResponse;
import org.junit.Test;

public class LargeMapTest extends AbstractEpsilonDebugAdapterTest {

	private static final File SCRIPT_FILE = new File(BASE_RESOURCE_FOLDER, "26-largeMap.eol");

	@Override
	protected void setupModule() throws Exception {
		this.module = new EolModule();
		module.parse(SCRIPT_FILE);
	}

	@Test
	public void canShowSlices() throws Exception {
		adapter.setBreakpoints(createBreakpoints(createBreakpoint(3))).get();
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		final int nExpected = 250;

		Map<String, Variable> topVariables = getVariablesFromTopStackFrame();
		Variable mapVariable = topVariables.get("m");
		assertNotNull("The top scope should have an 'm' variable", mapVariable);
		assertEquals("Map", mapVariable.getType());
		assertNotEquals("The 'm' variable should have its own reference",
			0, mapVariable.getVariablesReference());

		VariablesResponse numbersVars = getVariables(mapVariable.getVariablesReference());
		Map<String, Variable> numbersVarsByName = getVariablesByName(numbersVars);
		assertEquals("The 'm' variable should list one element per slice",
			(int) Math.ceil(nExpected / (double) SuspendedState.SLICE_SIZE),
			numbersVarsByName.size());

		{
			final String firstSliceName = String.format("m[0..%d]", SuspendedState.SLICE_SIZE - 1);
			Variable firstVariable = numbersVarsByName.get(firstSliceName);
			assertEquals(firstSliceName, firstVariable.getName());
			assertEquals(String.format("<slice with %d elements>", SuspendedState.SLICE_SIZE), firstVariable.getValue());

			VariablesResponse firstSliceElements = getVariables(firstVariable.getVariablesReference());
			Map<String, Variable> firstSliceByName = getVariablesByName(firstSliceElements);
			assertEquals("The first slice should be full", SuspendedState.SLICE_SIZE, firstSliceByName.size());

			Variable firstSliceElementVariable = firstSliceByName.get("m[0]");
			assertNotNull("The first slice should start from zero", firstSliceElementVariable);
			assertEquals("m[0]", firstSliceElementVariable.getName());
			assertNotEquals("The first slice element should point to a map entry",
				0, firstSliceElementVariable.getVariablesReference());
		}

		adapter.continue_(new ContinueArguments());
		assertProgramCompletedSuccessfully();
	}
}
