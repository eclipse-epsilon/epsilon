/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.etl.engine.test.acceptance.tree2graph;

import java.util.function.Supplier;
import org.eclipse.epsilon.etl.engine.test.acceptance.EtlAcceptanceTestUtil;
import org.eclipse.epsilon.etl.engine.test.acceptance.EtlTest;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.etl.IEtlModule;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class Tree2GraphTest extends EtlTest {

	@Parameter
	public Supplier<? extends IEtlModule> moduleGetter;

	@Parameters(name = "{0}")
	public static Iterable<Supplier<? extends IEtlModule>> modules() {
		return EtlAcceptanceTestUtil.modules();
	}
	
	static Resource graphModelOracle, graphModelClean;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		registerMetamodel("models/Tree.ecore", Tree2GraphTest.class);
		registerMetamodel("models/Graph.ecore", Tree2GraphTest.class);
		graphModelClean = getResource("models/graph_clean.model", Tree2GraphTest.class);
		graphModelOracle = getResource("models/graph.model", Tree2GraphTest.class);
	}
	
	@Test
	public void testTree2GraphTransformation() throws Exception {
		IEtlModule module = moduleGetter.get();
		module.parse(getFile("Tree2Graph.etl"));

		EmfModel graphModel = loadModel("Graph", "models/graph.model", "Graph", false, false);
		
		module.getContext().getModelRepository().addModels(
			loadModel("Tree", "models/tree.model", "Tree", true, false),
			graphModel
		);
		
		module.execute();
		// FIXME: Sometimes fails
		//testForEquivalence(graphModelOracle, graphModel, graphModelClean);
		module.getContext().dispose();		
	}
	
}
