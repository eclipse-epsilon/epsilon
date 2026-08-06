/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.eol.dap.test.ecl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Map;

import org.eclipse.epsilon.ecl.EclModule;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.dap.test.AbstractEpsilonDebugAdapterTest;
import org.eclipse.lsp4j.debug.ContinueArguments;
import org.eclipse.lsp4j.debug.SetBreakpointsResponse;
import org.eclipse.lsp4j.debug.StoppedEventArgumentsReason;
import org.eclipse.lsp4j.debug.Variable;
import org.junit.Test;

public class EclDebugTest extends AbstractEpsilonDebugAdapterTest {

	private static final File SCRIPT_FILE = new File(BASE_RESOURCE_FOLDER, "13-compareGraphs.ecl");

	@Override
	protected void setupModule() throws Exception {
		this.module = new EclModule();
		module.parse(SCRIPT_FILE);

		final String metamodelPath = new File(BASE_MODELS_FOLDER, "Graph.ecore").getAbsolutePath();
		EmfModel leftModel = new EmfModel();
		leftModel.setModelFile(new File(BASE_MODELS_FOLDER, "leftGraph.model").getAbsolutePath());
		leftModel.setMetamodelFile(metamodelPath);
		leftModel.setName("Left");
		leftModel.setReadOnLoad(true);
		leftModel.setStoredOnDisposal(false);
		leftModel.load();
		module.getContext().getModelRepository().addModel(leftModel);

		EmfModel rightModel = new EmfModel();
		rightModel.setModelFile(new File(BASE_MODELS_FOLDER, "rightGraph.model").getAbsolutePath());
		rightModel.setMetamodelFile(metamodelPath);
		rightModel.setName("Right");
		rightModel.setReadOnLoad(true);
		rightModel.setStoredOnDisposal(false);
		rightModel.load();
		module.getContext().getModelRepository().addModel(rightModel);
	}

	@Test
	public void canStopOnRule() throws Exception {
		SetBreakpointsResponse result = adapter.setBreakpoints(createBreakpoints(createBreakpoint(1))).get();
		assertTrue("The breakpoint on the comparison rule should be verified", result.getBreakpoints()[0].isVerified());
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		Map<String, Variable> topVariables = getVariablesFromTopStackFrame();
		assertNotNull("The top scope should have an 'l' variable", topVariables.get("l"));
		assertNotNull("The top scope should have an 'r' variable", topVariables.get("r"));

		adapter.setBreakpoints(createBreakpoints()).get();
		adapter.continue_(new ContinueArguments()).get();
		assertProgramCompletedSuccessfully();
	}

	@Test
	public void canStopOnCompareExpression() throws Exception {
		SetBreakpointsResponse result = adapter.setBreakpoints(createBreakpoints(createBreakpoint(5))).get();
		assertTrue("The breakpoint on the comparison rule should be verified", result.getBreakpoints()[0].isVerified());
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		Map<String, Variable> topVariables = getVariablesFromTopStackFrame();
		assertNotNull("The top scope should have an 'l' variable", topVariables.get("l"));
		assertNotNull("The top scope should have an 'r' variable", topVariables.get("r"));

		adapter.setBreakpoints(createBreakpoints()).get();
		adapter.continue_(new ContinueArguments()).get();
		assertProgramCompletedSuccessfully();
	}
	
}
