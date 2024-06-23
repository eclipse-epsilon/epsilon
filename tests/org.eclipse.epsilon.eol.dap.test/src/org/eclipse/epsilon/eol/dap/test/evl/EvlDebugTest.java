/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.eol.dap.test.evl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.eclipse.epsilon.eol.dap.test.AbstractEpsilonDebugAdapterTest;
import org.eclipse.epsilon.eol.dap.test.metamodel.Person;
import org.eclipse.epsilon.eol.models.java.JavaModel;
import org.eclipse.epsilon.evl.EvlModule;
import org.eclipse.lsp4j.debug.ContinueArguments;
import org.eclipse.lsp4j.debug.SetBreakpointsResponse;
import org.eclipse.lsp4j.debug.StoppedEventArgumentsReason;
import org.eclipse.lsp4j.debug.Variable;
import org.junit.Test;

public class EvlDebugTest extends AbstractEpsilonDebugAdapterTest {

	private static final File SCRIPT_FILE = new File(BASE_RESOURCE_FOLDER, "11-validation.evl");

	@Override
	protected void setupModule() throws Exception {
		this.module = new EvlModule();
		module.parse(SCRIPT_FILE);

		final ArrayList<Object> objects = new ArrayList<Object>();
		objects.add(new Person("John", "Doe"));
		final JavaModel model = new JavaModel("M", objects, new ArrayList<>(Arrays.asList(Person.class)));
		module.getContext().getModelRepository().addModel(model);
	}

	@Test
	public void canStopOnCheckExpression() throws Exception {
		SetBreakpointsResponse result = adapter.setBreakpoints(createBreakpoints(createBreakpoint(3))).get();
		assertTrue("The breakpoint on the check expression should be verified", result.getBreakpoints()[0].isVerified());
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		Map<String, Variable> topVariables = getVariablesFromTopStackFrame();
		Variable personVariable = topVariables.get("self");
		assertNotNull("The top scope should have a 'self' variable", personVariable);

		adapter.setBreakpoints(createBreakpoints()).get();
		adapter.continue_(new ContinueArguments()).get();
		assertProgramCompletedSuccessfully();
	}

	@Test
	public void canStopOnGuardExpression() throws Exception {
		adapter.setBreakpoints(createBreakpoints(createBreakpoint(7))).get();
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		Map<String, Variable> topVariables = getVariablesFromTopStackFrame();
		Variable personVariable = topVariables.get("self");
		assertNotNull("The top scope should have a 'self' variable", personVariable);

		adapter.continue_(new ContinueArguments()).get();
		assertProgramCompletedSuccessfully();
	}

	@Test
	public void canStopInsideCheckBlock() throws Exception {
		adapter.setBreakpoints(createBreakpoints(createBreakpoint(10))).get();
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		Map<String, Variable> topVariables = getVariablesFromTopStackFrame();
		Variable personVariable = topVariables.get("l");
		assertNotNull("The top scope should have an 'l' variable", personVariable);

		adapter.continue_(new ContinueArguments()).get();
		assertProgramCompletedSuccessfully();
	}
	
}
