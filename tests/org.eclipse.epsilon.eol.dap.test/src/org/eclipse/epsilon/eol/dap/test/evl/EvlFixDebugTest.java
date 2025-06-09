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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Future;

import org.eclipse.epsilon.eol.dap.test.AbstractEpsilonDebugAdapterTest;
import org.eclipse.epsilon.eol.dap.test.metamodel.Person;
import org.eclipse.epsilon.eol.models.java.JavaModel;
import org.eclipse.epsilon.evl.EvlModule;
import org.eclipse.epsilon.evl.execute.UnsatisfiedConstraint;
import org.eclipse.lsp4j.debug.ContinueArguments;
import org.eclipse.lsp4j.debug.ScopesResponse;
import org.eclipse.lsp4j.debug.SetBreakpointsResponse;
import org.eclipse.lsp4j.debug.StackTraceResponse;
import org.eclipse.lsp4j.debug.StoppedEventArgumentsReason;
import org.eclipse.lsp4j.debug.TerminateArguments;
import org.eclipse.lsp4j.debug.Variable;
import org.eclipse.lsp4j.debug.VariablesResponse;
import org.junit.Test;

public class EvlFixDebugTest extends AbstractEpsilonDebugAdapterTest {

	private static final File SCRIPT_FILE = new File(BASE_RESOURCE_FOLDER, "11-validation.evl");

	@Override
	protected void setupModule() throws Exception {
		this.module = new EvlModule();
		module.parse(SCRIPT_FILE);

		final ArrayList<Object> objects = new ArrayList<Object>();
		objects.add(new Person("John", null));
		final JavaModel model = new JavaModel("M", objects, new ArrayList<>(Arrays.asList(Person.class)));
		module.getContext().getModelRepository().addModel(model);
	}

	@Test
	public void canStopInsideFixTitleExpression() throws Exception {
		SetBreakpointsResponse breakpoints = adapter.setBreakpoints(
			createBreakpoints(createBreakpoint(13))).get();
		assertTrue("The breakpoint on the file should be recognised",
			breakpoints.getBreakpoints()[0].isVerified());
		assertEquals("The breakpoint should be verified on the fix expression",
			(Integer) 13, breakpoints.getBreakpoints()[0].getLine());
		attach();

		// Wait for the EVL script to run
		runModuleResult.get();

		// Try to get the title of the first fix
		Future<String> futureTitle = executor.submit(() -> {
			Collection<UnsatisfiedConstraint> allUnsatisfied = 
				((EvlModule) module).getContext().getUnsatisfiedConstraints();
			UnsatisfiedConstraint unsatisfied = allUnsatisfied.iterator().next();
			return unsatisfied.getFixes().get(0).getTitle();
		});

		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);
		assertFalse("Computing the title should not be done yet", futureTitle.isDone());
		StackTraceResponse stackTrace = getStackTrace();
		assertEquals("Shold be stopped at the fix title expression line",
			13, stackTrace.getStackFrames()[0].getLine());

		ScopesResponse scopes = getScopes(stackTrace.getStackFrames()[1]);
		VariablesResponse variables = getVariables(scopes.getScopes()[0]);
		Map<String, Variable> varsByName = getVariablesByName(variables);
		Variable personVariable = varsByName.get("self");
		assertNotNull("The second stack frame should have a 'self' variable", personVariable);

		adapter.continue_(new ContinueArguments()).get();
		assertNotNull(futureTitle.get());

		// We need to explicitly terminate as there is a lingering fix that could be applied
		adapter.terminate(new TerminateArguments()).get();

		// Terminated programs exit with a non-zero status
		assertProgramFailed();
	}
}
