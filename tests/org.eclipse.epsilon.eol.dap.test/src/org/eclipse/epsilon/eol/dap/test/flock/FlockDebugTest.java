/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.eol.dap.test.flock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Map;

import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.dap.test.AbstractEpsilonDebugAdapterTest;
import org.eclipse.epsilon.flock.FlockModule;
import org.eclipse.lsp4j.debug.ContinueArguments;
import org.eclipse.lsp4j.debug.EvaluateArguments;
import org.eclipse.lsp4j.debug.EvaluateResponse;
import org.eclipse.lsp4j.debug.SetBreakpointsResponse;
import org.eclipse.lsp4j.debug.StoppedEventArgumentsReason;
import org.eclipse.lsp4j.debug.Variable;
import org.junit.Test;

public class FlockDebugTest extends AbstractEpsilonDebugAdapterTest {

	private static final File SCRIPT_FILE = new File(BASE_RESOURCE_FOLDER, "18-migrate.mig");
	private static final File OLD_MM_FILE = new File(BASE_MODELS_FOLDER, "Graph.ecore");
	private static final File OLD_MODEL_FILE = new File(BASE_MODELS_FOLDER, "leftGraph.model");
	private static final File NEW_MM_FILE = new File(BASE_MODELS_FOLDER, "Graph_v2.ecore");

	@Override
	protected void setupModule() throws Exception {
		final FlockModule flockModule = new FlockModule();
		this.module = flockModule;
		module.parse(SCRIPT_FILE);

		final EmfModel originalModel = new EmfModel();
		originalModel.setModelFile(OLD_MODEL_FILE.getCanonicalPath());
		originalModel.setMetamodelFile(OLD_MM_FILE.getCanonicalPath());
		originalModel.setReadOnLoad(true);
		originalModel.setStoredOnDisposal(false);
		originalModel.setName("Original");
		originalModel.load();
		flockModule.getContext().setOriginalModel(originalModel);
		
		final EmfModel migratedModel = new EmfModel();
		migratedModel.setModelFile(new File("migrated.model").getCanonicalPath());
		migratedModel.setMetamodelFile(NEW_MM_FILE.getCanonicalPath());
		migratedModel.setReadOnLoad(false);
		migratedModel.setStoredOnDisposal(false);
		migratedModel.setName("Migrated");
		migratedModel.load();
		flockModule.getContext().setMigratedModel(migratedModel);
	}

	@Test
	public void canStopInsideMigrateRule() throws Exception {
		SetBreakpointsResponse result = adapter.setBreakpoints(createBreakpoints(createBreakpoint(7))).get();
		assertTrue("The breakpoint on the check expression should be verified", result.getBreakpoints()[0].isVerified());
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		Map<String, Variable> topVariables = getVariablesFromTopStackFrame();
		assertNotNull("The top scope should have a 'migrated' variable", topVariables.get("migrated"));
		assertNotNull("The top scope should have an 'original' variable", topVariables.get("original"));

		// Test evaluation from within a Flock 'migrate' block
		EvaluateResponse evalResult = evaluate("original.eResource().uri", getStackTrace().getStackFrames()[0]);
		assertEquals(OLD_MODEL_FILE.getCanonicalFile().toURI().toString(), evalResult.getResult());

		// We will stop once more for the second Node object
		adapter.continue_(new ContinueArguments()).get();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		adapter.continue_(new ContinueArguments()).get();
		assertProgramCompletedSuccessfully();
	}

	@Test
	public void canStopInsidePreBlock() throws Exception {
		SetBreakpointsResponse result = adapter.setBreakpoints(createBreakpoints(createBreakpoint(3))).get();
		assertTrue("The breakpoint on the check expression should be verified", result.getBreakpoints()[0].isVerified());
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		EvaluateResponse evalResult = evaluate("y.x", getStackTrace().getStackFrames()[0]);
		assertTrue(evalResult.getResult().contains("failed to evaluate"));

		// Test evaluation from within a Flock 'migrate' block
		evalResult = evaluate("x", getStackTrace().getStackFrames()[0]);
		assertEquals("23", evalResult.getResult());

		adapter.continue_(new ContinueArguments()).get();
		assertProgramCompletedSuccessfully();
	}

	@Test
	public void canStopInsidePostBlock() throws Exception {
		SetBreakpointsResponse result = adapter.setBreakpoints(createBreakpoints(createBreakpoint(12))).get();
		assertTrue("The breakpoint on the check expression should be verified", result.getBreakpoints()[0].isVerified());
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		adapter.continue_(new ContinueArguments()).get();
		assertProgramCompletedSuccessfully();
	}

	@Test
	public void allEvaluateRequestsArePendingWhenNotStopped() throws Exception {
		// If you're not stopped at a breakpoint, we won't evaluate any expressions
		// (as it may interfere with the regular execution of the program).
		EvaluateResponse evalResult = adapter.evaluate(new EvaluateArguments()).get();
		assertEquals("(pending)", evalResult.getResult());
	}

	@Test
	public void canStepOverAfterEvaluating() throws Exception {
		adapter.setBreakpoints(createBreakpoints(createBreakpoint(7))).get();
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);
		evaluate("x", getStackTrace().getStackFrames()[0]);

		stepOver();
		assertEquals(8, getStackTrace().getStackFrames()[0].getLine());

		// Remove all breakpoints and let the program finish
		adapter.setBreakpoints(createBreakpoints()).get();
		adapter.continue_(new ContinueArguments()).get();
		assertProgramCompletedSuccessfully();
	}
}
