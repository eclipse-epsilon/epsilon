/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eunit.examples.etl.vsjunit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.net.URISyntaxException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.diff.DefaultDiffEngine;
import org.eclipse.emf.compare.diff.DiffBuilder;
import org.eclipse.emf.compare.match.IMatchEngine;
import org.eclipse.emf.compare.match.impl.MatchEngineFactoryImpl;
import org.eclipse.emf.compare.match.impl.MatchEngineFactoryRegistryImpl;
import org.eclipse.emf.compare.scope.IComparisonScope;
import org.eclipse.emf.compare.utils.UseIdentifiers;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.models.ModelRepository;
import org.eclipse.epsilon.etl.EtlModule;
import org.eclipse.epsilon.etl.IEtlModule;
import org.eclipse.epsilon.eunit.cmp.emf.EMFModelComparator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Shows what would be required to test a model-to-model transformation using
 * plain JUnit.
 * 
 * @author Antonio Garcia-Dominguez
 */
public class TransformationTest {

	private static final String RESOURCE_PATH_PREFIX = "/";
	private static final String GRAPH_EXPECTED_MODEL_PATH = "models/graph-expected.model";
	private static final String TREE_MODEL_PATH = "models/tree.model";
	private static final String GRAPH_METAMODEL_PATH = "metamodels/graph.ecore";
	private static final String TREE_METAMODEL_PATH = "metamodels/tree.ecore";

	private EmfModel originalTree;
	private EmfModel expectedGraph;
	private EmfModel resultGraph;
	private IEtlModule etlModule;

	@Before
	public void setup() throws Exception {
		loadModels();
		loadEtl();
	}

	@After
	public void disposeModels() {
		originalTree.dispose();
		expectedGraph.dispose();
		resultGraph.dispose();
	}

	@Test
	public void etlProducesExpectedResult() throws Exception {
		etlModule.execute();

		// Using EMF Compare directly
		// From http://wiki.eclipse.org/EMF_Compare/Developer_Guide#Putting_it_all_together
		final IMatchEngine.Factory matchEngineFactory = new MatchEngineFactoryImpl(UseIdentifiers.NEVER);
        final IMatchEngine.Factory.Registry matchEngineRegistry = new MatchEngineFactoryRegistryImpl();
        matchEngineRegistry.add(matchEngineFactory);
		final EMFCompare emfCompare = EMFCompare.builder()
				.setMatchEngineFactoryRegistry(matchEngineRegistry)
				.setDiffEngine(new DefaultDiffEngine(new DiffBuilder()))
				.build();

		final IComparisonScope scope = EMFCompare.createDefaultScope(expectedGraph.getResource(), resultGraph.getResource());
		final Comparison cmp = emfCompare.compare(scope);
		assertTrue(cmp.getDifferences().isEmpty());

		// Using generic comparison through EMC
		assertEquals(null, new EMFModelComparator().compare(expectedGraph, resultGraph));
	}

	private void loadEtl() throws Exception {
		etlModule = new EtlModule();
		etlModule.parse(getResourceURI("etl/Tree2Graph.etl"));

		// Make the source and destination models available to the transformation
		final ModelRepository etlModelRepo = etlModule.getContext().getModelRepository();
		etlModelRepo.addModel(originalTree);
		etlModelRepo.addModel(resultGraph);
	}

	private void loadModels() throws EolModelLoadingException {
		originalTree = loadModel("Tree", TREE_METAMODEL_PATH, TREE_MODEL_PATH);
		expectedGraph = loadModel("GraphExpected", GRAPH_METAMODEL_PATH, GRAPH_EXPECTED_MODEL_PATH);
		resultGraph = loadModel("Graph", GRAPH_METAMODEL_PATH, GRAPH_EXPECTED_MODEL_PATH, false);
	}

	private EmfModel loadModel(String modelName, String metamodelPath, String modelPath)
			throws EolModelLoadingException {
		return loadModel(modelName, metamodelPath, modelPath, true);
	}

	private EmfModel loadModel(String modelName, String metamodelPath,
			String modelPath, boolean readOnLoad) throws EolModelLoadingException {
		EmfModel model = new EmfModel();
		model.setName(modelName);
		model.setMetamodelFileUri(getResourceEmfURI(metamodelPath));
		model.setModelFileUri(getResourceEmfURI(modelPath));
		model.setReadOnLoad(readOnLoad);
		model.setStoredOnDisposal(false);
		model.load();
		return model;
	}

	private java.net.URI getResourceURI(String resourcePath)
			throws URISyntaxException {
		return getClass().getResource(RESOURCE_PATH_PREFIX + resourcePath).toURI();
	}

	private URI getResourceEmfURI(String resourcePath) {
		return URI.createURI(getClass().getResource(
				RESOURCE_PATH_PREFIX + resourcePath).toExternalForm());
	}

}
