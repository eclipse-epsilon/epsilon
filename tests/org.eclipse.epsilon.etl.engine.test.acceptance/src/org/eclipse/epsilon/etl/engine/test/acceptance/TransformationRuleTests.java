/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.etl.engine.test.acceptance;

import static org.junit.Assert.*;
import java.io.File;
import java.util.function.Supplier;
import org.eclipse.epsilon.etl.IEtlModule;
import org.eclipse.epsilon.etl.dom.TransformationRule;
import org.eclipse.epsilon.etl.execute.context.IEtlContext;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class TransformationRuleTests extends EtlTest {

	@Parameter
	public Supplier<? extends IEtlModule> moduleGetter;
	
	private IEtlModule module;

	@Parameters(name = "{0}")
	public static Iterable<Supplier<? extends IEtlModule>> modules() {
		return EtlAcceptanceTestUtil.modules();
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		registerMetamodel("models/Tree.ecore", TransformationRuleTests.class);
		registerMetamodel("models/Graph.ecore", TransformationRuleTests.class);
	}
	
	@Before
	public void setup() throws Exception {
		module = moduleGetter.get();
		
		module.getContext().getModelRepository().addModels(
			loadModel("Tree", "models/tree.model", "Tree", true, false),
			loadModel("Graph", "models/graph.model", "Graph", false, false)
		);
	}

	@After
	public void tearDown() throws Exception {
		module.getContext().dispose();
	}

	@Test
	public void testLazyRuleIdentification() throws Exception {
		module.parse(new File(getFullPath("TransformationRuleTests.etl")));
		assertTrue(module.getParseProblems().isEmpty());
		
		module.execute();
		IEtlContext context = module.getContext();
		
		for (TransformationRule rule : module.getTransformationRules()) {
			assertEquals(
				rule.getName() + " should be lazy",
				rule.getBooleanAnnotationValue("isLazy", null),
				rule.isLazy(context)
			);
		}
	}

}
