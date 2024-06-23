/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.eol.dap.test.etl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.eclipse.epsilon.eol.dap.test.AbstractEpsilonDebugAdapterTest;
import org.eclipse.epsilon.eol.dap.test.metamodel.Person;
import org.eclipse.epsilon.eol.models.java.JavaModel;
import org.eclipse.epsilon.etl.EtlModule;
import org.eclipse.lsp4j.debug.ContinueArguments;
import org.eclipse.lsp4j.debug.SetBreakpointsResponse;
import org.eclipse.lsp4j.debug.StoppedEventArgumentsReason;
import org.eclipse.lsp4j.debug.Variable;
import org.junit.Test;

public class EtlDebugTest extends AbstractEpsilonDebugAdapterTest {

	private static final File SCRIPT_FILE = new File(BASE_RESOURCE_FOLDER, "12-swapFirstLastNames.etl");

	@Override
	protected void setupModule() throws Exception {
		this.module = new EtlModule();
		module.parse(SCRIPT_FILE);

		final ArrayList<Object> objects = new ArrayList<Object>();
		objects.add(new Person("John", "Doe"));
		final JavaModel sourceModel = new JavaModel("Source", objects, new ArrayList<>(Arrays.asList(Person.class)));
		module.getContext().getModelRepository().addModel(sourceModel);
		final JavaModel targetModel = new JavaModel("Target", new ArrayList<>(), new ArrayList<>(Arrays.asList(Person.class)));
		module.getContext().getModelRepository().addModel(targetModel);
	}

	@Test
	public void canStopOnRule() throws Exception {
		SetBreakpointsResponse result = adapter.setBreakpoints(createBreakpoints(createBreakpoint(1))).get();
		assertTrue("The breakpoint on the rule should be verified", result.getBreakpoints()[0].isVerified());
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		Map<String, Variable> topVariables = getVariablesFromTopStackFrame();
		Variable sourceVariable = topVariables.get("firstLast");
		assertNotNull("The top scope should have a 'firstLast' variable", sourceVariable);
		Variable targetVariable = topVariables.get("lastFirst");
		assertNotNull("The top scope should have a 'lastFirst' variable", targetVariable);

		adapter.continue_(new ContinueArguments()).get();
		assertProgramCompletedSuccessfully();
	}

	@Test
	public void canStopInsideRule() throws Exception {
		SetBreakpointsResponse result = adapter.setBreakpoints(createBreakpoints(createBreakpoint(5))).get();
		assertTrue("The breakpoint inside the rule body should be verified", result.getBreakpoints()[0].isVerified());
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		Map<String, Variable> topVariables = getVariablesFromTopStackFrame();
		Variable sourceVariable = topVariables.get("firstLast");
		assertNotNull("The top scope should have a 'firstLast' variable", sourceVariable);
		Variable targetVariable = topVariables.get("lastFirst");
		assertNotNull("The top scope should have a 'lastFirst' variable", targetVariable);

		adapter.continue_(new ContinueArguments()).get();
		assertProgramCompletedSuccessfully();
	}
	
}
