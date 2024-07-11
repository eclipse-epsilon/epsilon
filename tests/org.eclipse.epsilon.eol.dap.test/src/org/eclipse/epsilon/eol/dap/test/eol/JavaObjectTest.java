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
import org.eclipse.epsilon.eol.types.EolClasspathNativeTypeDelegate;
import org.eclipse.lsp4j.debug.ContinueArguments;
import org.eclipse.lsp4j.debug.StoppedEventArgumentsReason;
import org.eclipse.lsp4j.debug.Variable;
import org.eclipse.lsp4j.debug.VariablesResponse;
import org.junit.Test;

public class JavaObjectTest extends AbstractEpsilonDebugAdapterTest {

	private static final File SCRIPT_FILE = new File(BASE_RESOURCE_FOLDER, "27-javaObject.eol");

	public static class Task {
		private int effortDays;
		private String responsible;

		public int getEffortDays() {
			return effortDays;
		}

		public void setEffortDays(int effortDays) {
			this.effortDays = effortDays;
		}

		public String getResponsible() {
			return responsible;
		}

		public void setResponsible(String responsible) {
			this.responsible = responsible;
		}

		@Override
		public String toString() {
			return "Task [effortDays=" + effortDays + ", responsible=" + responsible + "]";
		}
	}

	@Override
	protected void setupModule() throws Exception {
		this.module = new EolModule();

		module.parse(SCRIPT_FILE);
		module.getContext().getNativeTypeDelegates().add(
			new EolClasspathNativeTypeDelegate(Task.class.getClassLoader()));
	}

	@Test
	public void canShowElements() throws Exception {
		adapter.setBreakpoints(createBreakpoints(createBreakpoint(7))).get();
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		Map<String, Variable> topVariables = getVariablesFromTopStackFrame();
		Variable personVariable = topVariables.get("p");
		assertNotNull("The top scope should have a 'p' variable", personVariable);
		assertEquals("Native('org.eclipse.epsilon.eol.dap.test.eol.JavaObjectTest$Task')", personVariable.getType());
		assertNotEquals("The 'p' variable should have its own reference", 0, personVariable.getVariablesReference());

		VariablesResponse personVars = getVariables(personVariable.getVariablesReference());
		Map<String, Variable> personVarsByName = getVariablesByName(personVars);
		assertEquals("The 'p' variable should list two elements", 3, personVarsByName.size());

		assertEquals("John Doe", personVarsByName.get("responsible").getValue());
		assertEquals("5", personVarsByName.get("effortDays").getValue());
		assertNotNull(personVarsByName.get("class").getValue());

		adapter.continue_(new ContinueArguments());
		assertProgramCompletedSuccessfully();
	}
}
