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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Map;

import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.dap.test.AbstractEpsilonDebugAdapterTest;
import org.eclipse.epsilon.flock.FlockModule;
import org.eclipse.lsp4j.debug.ContinueArguments;
import org.eclipse.lsp4j.debug.SetBreakpointsResponse;
import org.eclipse.lsp4j.debug.StoppedEventArgumentsReason;
import org.eclipse.lsp4j.debug.Variable;
import org.junit.Test;

public class FlockDebugTest extends AbstractEpsilonDebugAdapterTest {

	private static final File SCRIPT_FILE = new File(BASE_RESOURCE_FOLDER, "18-migrate.mig");
	private static final File OLD_MM_FILE = new File(BASE_MODELS_FOLDER, "Graph.ecore");
	private static final File NEW_MM_FILE = new File(BASE_MODELS_FOLDER, "Graph_v2.ecore");

	@Override
	protected void setupModule() throws Exception {
		final FlockModule flockModule = new FlockModule();
		this.module = flockModule;
		module.parse(SCRIPT_FILE);

		final EmfModel originalModel = new EmfModel();
		originalModel.setModelFile(new File(BASE_MODELS_FOLDER, "leftGraph.model").getCanonicalPath());
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
		SetBreakpointsResponse result = adapter.setBreakpoints(createBreakpoints(createBreakpoint(2))).get();
		assertTrue("The breakpoint on the check expression should be verified", result.getBreakpoints()[0].isVerified());
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		Map<String, Variable> topVariables = getVariablesFromTopStackFrame();
		assertNotNull("The top scope should have a 'migrated' variable", topVariables.get("migrated"));
		assertNotNull("The top scope should have an 'original' variable", topVariables.get("original"));

		// We will stop once more for the second Node object
		adapter.continue_(new ContinueArguments()).get();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		adapter.continue_(new ContinueArguments()).get();
		assertProgramCompletedSuccessfully();
	}
	
}
