/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.eol.dap.test.eml;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Map;

import org.eclipse.epsilon.ecl.EclModule;
import org.eclipse.epsilon.ecl.trace.MatchTrace;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eml.EmlModule;
import org.eclipse.epsilon.eol.dap.test.AbstractEpsilonDebugAdapterTest;
import org.eclipse.lsp4j.debug.ContinueArguments;
import org.eclipse.lsp4j.debug.SetBreakpointsResponse;
import org.eclipse.lsp4j.debug.StoppedEventArgumentsReason;
import org.eclipse.lsp4j.debug.Variable;
import org.junit.BeforeClass;
import org.junit.Test;

public class EmlDebugTest extends AbstractEpsilonDebugAdapterTest {

	private static final File COMPARE_FILE = new File(BASE_RESOURCE_FOLDER, "13-compareGraphs.ecl");
	private static final File EML_FILE = new File(BASE_RESOURCE_FOLDER, "14-mergeGraphs.eml");
	private static final File ETL_FILE = new File(BASE_RESOURCE_FOLDER, "14-mergeGraphs.etl");
	private static final File METAMODEL_FILE = new File(BASE_MODELS_FOLDER, "Graph.ecore");

	private static EmfModel leftModel;
	private static EmfModel rightModel;
	private static MatchTrace reducedMatchTrace;

	@BeforeClass
	public static void compareGraphs() throws Exception {
		EclModule eclModule = new EclModule();
		eclModule.parse(COMPARE_FILE);

		leftModel = new EmfModel();
		leftModel.setModelFile(new File(BASE_MODELS_FOLDER, "leftGraph.model").getAbsolutePath());
		leftModel.setMetamodelFile(METAMODEL_FILE.getCanonicalPath());
		leftModel.setName("Left");
		leftModel.setReadOnLoad(true);
		leftModel.setStoredOnDisposal(false);
		leftModel.load();
		eclModule.getContext().getModelRepository().addModel(leftModel);

		rightModel = new EmfModel();
		rightModel.setModelFile(new File(BASE_MODELS_FOLDER, "rightGraph.model").getAbsolutePath());
		rightModel.setMetamodelFile(METAMODEL_FILE.getCanonicalPath());
		rightModel.setName("Right");
		rightModel.setReadOnLoad(true);
		rightModel.setStoredOnDisposal(false);
		rightModel.load();
		eclModule.getContext().getModelRepository().addModel(rightModel);

		eclModule.execute();
		reducedMatchTrace = eclModule.getContext().getMatchTrace().getReduced();
	}

	@Override
	protected void setupModule() throws Exception {
		final EmlModule emlModule = new EmlModule();
		this.module = emlModule;
		module.parse(EML_FILE);

		leftModel.getAliases().clear();
		leftModel.getAliases().add("Source");
		module.getContext().getModelRepository().addModel(leftModel);

		rightModel.getAliases().clear();
		rightModel.getAliases().add("Source");
		module.getContext().getModelRepository().addModel(rightModel);

		EmfModel targetModel = new EmfModel();
		targetModel.setModelFile("dummy.xmi");
		targetModel.setMetamodelFile(METAMODEL_FILE.getCanonicalPath());
		targetModel.setReadOnLoad(false);
		targetModel.setStoredOnDisposal(false);
		targetModel.setName("Target");
		targetModel.load();
		module.getContext().getModelRepository().addModel(targetModel);

		emlModule.getContext().setMatchTrace(reducedMatchTrace);
	}

	@Test
	public void canStopOnMergeRule() throws Exception {
		SetBreakpointsResponse result = adapter.setBreakpoints(createBreakpoints(createBreakpoint(3))).get();
		assertTrue("The breakpoint on the merge rule should be verified", result.getBreakpoints()[0].isVerified());
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		Map<String, Variable> topVariables = getVariablesFromTopStackFrame();
		assertNotNull("The top scope should have an 'l' variable", topVariables.get("l"));
		assertNotNull("The top scope should have an 'r' variable", topVariables.get("r"));
		assertNotNull("The top scope should have an 't' variable", topVariables.get("t"));

		adapter.continue_(new ContinueArguments()).get();
		assertProgramCompletedSuccessfully();
	}

	@Test
	public void canStopInsideTransformNodeRule() throws Exception {
		SetBreakpointsResponse result = adapter.setBreakpoints(createBreakpoints(ETL_FILE.getCanonicalPath(), createBreakpoint(12))).get();
		assertTrue("The breakpoint inside the transformation rule should be verified", result.getBreakpoints()[0].isVerified());
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		Map<String, Variable> topVariables = getVariablesFromTopStackFrame();
		assertNotNull("The top scope should have an 's' variable", topVariables.get("s"));
		assertNotNull("The top scope should have an 't' variable", topVariables.get("t"));

		adapter.setBreakpoints(createBreakpoints(ETL_FILE.getCanonicalPath())).get();
		adapter.continue_(new ContinueArguments()).get();
		assertProgramCompletedSuccessfully();
	}
	
}
