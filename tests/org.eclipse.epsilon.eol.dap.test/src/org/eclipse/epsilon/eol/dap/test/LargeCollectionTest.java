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
import org.eclipse.epsilon.eol.dap.variables.SuspendedState;
import org.eclipse.lsp4j.debug.ContinueArguments;
import org.eclipse.lsp4j.debug.StoppedEventArgumentsReason;
import org.eclipse.lsp4j.debug.Variable;
import org.eclipse.lsp4j.debug.VariablesResponse;
import org.junit.Test;

public class LargeCollectionTest extends AbstractEpsilonDebugAdapterTest {

	private static final File SCRIPT_FILE = new File(BASE_RESOURCE_FOLDER, "06-largeCollection.eol");

	@Override
	protected void setupModule() throws Exception {
		this.module = new EolModule();
		module.parse(SCRIPT_FILE);
	}

	@Test
	public void canShowSlicesForList() throws Exception {
		adapter.setBreakpoints(createBreakpoints(createBreakpoint(2))).get();
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		final int nExpected = 950;

		Map<String, Variable> topVariables = getVariablesFromTopStackFrame();
		Variable numbersVariable = topVariables.get("numbers");
		assertNotNull("The top scope should have a 'numbers' variable", numbersVariable);
		assertNotEquals("The 'numbers' variable should have its own reference", 0,
				numbersVariable.getVariablesReference());
		assertEquals(String.format("<sliced EolSequence of %d elements>", nExpected), numbersVariable.getValue());

		VariablesResponse numbersVars = getVariables(numbersVariable.getVariablesReference());
		Map<String, Variable> numbersVarsByName = getVariablesByName(numbersVars);
		assertEquals("The 'numbers' variable should list one subvariable per slice",
			(int) Math.ceil(nExpected / (double) SuspendedState.SLICE_SIZE),
			numbersVarsByName.size());

		{
			final String firstSliceName = String.format("numbers[0..%d]", SuspendedState.SLICE_SIZE - 1);
			Variable firstVariable = numbersVarsByName.get(firstSliceName);
			assertEquals(firstSliceName, firstVariable.getName());
			assertEquals(String.format("<slice with %d elements>", SuspendedState.SLICE_SIZE), firstVariable.getValue());

			VariablesResponse firstSliceElements = getVariables(firstVariable.getVariablesReference());
			Map<String, Variable> firstSliceByName = getVariablesByName(firstSliceElements);
			assertEquals("The first slice should be full", SuspendedState.SLICE_SIZE, firstSliceByName.size());

			Variable firstSliceElementVariable = firstSliceByName.get("numbers[0]");
			assertEquals("numbers[0]", firstSliceElementVariable.getName());
			assertEquals("1", firstSliceElementVariable.getValue());
		}

		{
			final String lastSliceName = String.format("numbers[%d..%d]", nExpected - (nExpected % SuspendedState.SLICE_SIZE), nExpected - 1);
			Variable lastVariable = numbersVarsByName.get(lastSliceName);
			assertEquals(lastSliceName, lastVariable.getName());

			VariablesResponse lastSliceElements = getVariables(lastVariable.getVariablesReference());
			final Map<String, Variable> lastSliceElementsByName = getVariablesByName(lastSliceElements);
			assertEquals("The last slice may not be full",
					nExpected % SuspendedState.SLICE_SIZE,
					lastSliceElementsByName.size());

			final String lastElementName = String.format("numbers[%d]", nExpected - 1);
			Variable lastElementVariable = lastSliceElementsByName.get(lastElementName);
			assertEquals(lastElementName, lastElementVariable.getName());
			assertEquals(nExpected + "", lastElementVariable.getValue());
		}

		adapter.continue_(new ContinueArguments());
		assertProgramCompletedSuccessfully();
	}

	@Test
	public void canShowSlicesForNonList() throws Exception {
		adapter.setBreakpoints(createBreakpoints(createBreakpoint(5))).get();
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		final int nExpected = 950;

		Map<String, Variable> topVariables = getVariablesFromTopStackFrame();
		Variable numberSetVariable = topVariables.get("numberSet");
		assertNotNull("The top scope should have a 'numberSet' variable", numberSetVariable);
		assertNotEquals("The 'numberSet' variable should have its own reference", 0,
				numberSetVariable.getVariablesReference());
		assertEquals(String.format("<sliced EolSet of %d elements>", nExpected), numberSetVariable.getValue());

		VariablesResponse numberSetVars = getVariables(numberSetVariable.getVariablesReference());
		Map<String, Variable> numbersVarsByName = getVariablesByName(numberSetVars);
		assertEquals("The 'numberSet' variable should list one subvariable per slice",
			(int) Math.ceil(nExpected / (double) SuspendedState.SLICE_SIZE),
			numbersVarsByName.size());

		{
			final String firstSliceName = String.format("numberSet[0..%d]", SuspendedState.SLICE_SIZE - 1);
			Variable firstVariable = numbersVarsByName.get(firstSliceName);
			assertEquals(firstSliceName, firstVariable.getName());
			assertEquals(String.format("<slice with %d elements>", SuspendedState.SLICE_SIZE), firstVariable.getValue());

			VariablesResponse firstSliceElements = getVariables(firstVariable.getVariablesReference());
			Map<String, Variable> firstSliceByName = getVariablesByName(firstSliceElements);
			assertEquals("The first slice should be full", SuspendedState.SLICE_SIZE, firstSliceByName.size());

			Variable firstSliceElementVariable = firstSliceByName.get("numberSet[0]");
			assertEquals("numberSet[0]", firstSliceElementVariable.getName());
			assertNotNull(firstSliceElementVariable.getValue());
		}
		
		adapter.continue_(new ContinueArguments());
		assertProgramCompletedSuccessfully();
	}
}
