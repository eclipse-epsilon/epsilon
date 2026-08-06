/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.eol.dap.test.pinset;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.eclipse.epsilon.eol.dap.test.AbstractEpsilonDebugAdapterTest;
import org.eclipse.epsilon.eol.dap.test.metamodel.Person;
import org.eclipse.epsilon.eol.models.java.JavaModel;
import org.eclipse.epsilon.pinset.PinsetModule;
import org.eclipse.lsp4j.debug.ContinueArguments;
import org.eclipse.lsp4j.debug.ScopesResponse;
import org.eclipse.lsp4j.debug.SetBreakpointsResponse;
import org.eclipse.lsp4j.debug.StackTraceResponse;
import org.eclipse.lsp4j.debug.StoppedEventArgumentsReason;
import org.eclipse.lsp4j.debug.Variable;
import org.eclipse.lsp4j.debug.VariablesResponse;
import org.junit.Test;

public class PinsetDebugTest extends AbstractEpsilonDebugAdapterTest {

	private static final File SCRIPT_FILE = new File(BASE_RESOURCE_FOLDER, "17-dataset.pinset");

	@Override
	protected void setupModule() throws Exception {
		this.module = new PinsetModule();
		module.parse(SCRIPT_FILE);

		final ArrayList<Object> objects = new ArrayList<Object>();
		objects.add(new Person("John", "Doe"));
		final JavaModel model = new JavaModel("M", objects, new ArrayList<>(Arrays.asList(Person.class)));
		module.getContext().getModelRepository().addModel(model);
	}

	@Test
	public void canStopOnFirstColumn() throws Exception {
		assertCanStopOnColumnAtLine(2);
	}

	@Test
	public void canStopOnSecondColumn() throws Exception {
		assertCanStopOnColumnAtLine(3);
	}

	@Test
	public void canStopInsideColumnBlock() throws Exception {
		SetBreakpointsResponse result = adapter.setBreakpoints(createBreakpoints(createBreakpoint(7))).get();
		assertTrue("Breakpoint should have been verified", result.getBreakpoints()[0].isVerified());
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		// Top frame should have the 'n' variable
		Map<String, Variable> varsByName = getVariablesFromTopStackFrame();
		assertNotNull("The top stack frame should have an 'n' variable", varsByName.get("n"));

		adapter.continue_(new ContinueArguments()).get();
		assertProgramCompletedSuccessfully();
	}

	protected void assertCanStopOnColumnAtLine(final int line)
			throws InterruptedException, ExecutionException, IOException, Exception {
		SetBreakpointsResponse result = adapter.setBreakpoints(createBreakpoints(createBreakpoint(line))).get();
		assertTrue("Breakpoint should have been verified", result.getBreakpoints()[0].isVerified());
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		// Top frame doesn't have any variables here
		assertTrue(getVariablesFromTopStackFrame().isEmpty());

		// The variables of interest are on the second frame
		StackTraceResponse stackTrace = getStackTrace();
		ScopesResponse scopes = getScopes(stackTrace.getStackFrames()[1]);
		VariablesResponse variables = getVariables(scopes.getScopes()[0]);
		Map<String, Variable> varsByName = getVariablesByName(variables);
		assertNotNull("The scope of the second stack frame should have a 'p' variable", varsByName.get("p"));

		adapter.continue_(new ContinueArguments()).get();
		assertProgramCompletedSuccessfully();
	}
	
}
