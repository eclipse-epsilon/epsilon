/*********************************************************************
 * Copyright (c) 2019 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.evl.engine.test.acceptance.reuse;

import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.models.ModelRepository;
import org.eclipse.epsilon.evl.*;
import org.eclipse.epsilon.evl.engine.test.acceptance.EvlTests;
import org.eclipse.epsilon.evl.execute.context.EvlContext;
import org.eclipse.epsilon.evl.execute.context.IEvlContext;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * Tests that the same module can be re-executed multiple times without re-parsing.
 * 
 * @author Sina Madani
 * @since 1.6
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EvlModuleReuseTests {

	static IEvlModule module = new EvlModule();
	
	static IModel addAndLoadNewModel() throws Exception {
		IModel model = EvlTests.newTestModel();
		module.getContext().getModelRepository().addModel(model);
		model.load();
		return model;
	}
	
	static IModel removeModel(String modelName) throws Exception {
		ModelRepository modelRepo = module.getContext().getModelRepository();
		IModel model = modelRepo.getModelByName(modelName);
		modelRepo.removeModel(model);
		return model;
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		module.parse(EvlTests.getTestScript(module));
	}
	
	@After
	public void testExecution() throws Exception {
		EvlTests.getTestScript(module);		// Setup variables
		module.execute();
		EvlTests.testUnsatisfiedConstraintsForTestScriptAndModel(module.getContext());
	}
	
	@Test
	public void test0_NormalExecution() throws Exception {
		addAndLoadNewModel();
	}
	
	@Test
	public void test1_ReuseWithChangedContextSameModel() throws Exception {
		module.setContext(new EvlContext());
		addAndLoadNewModel();
	}
	
	/**
	 * This should work because UnsatisfiedConstraints is a Set
	 * 
	 * @throws EolModelNotFoundException
	 */
	@Test
	public void test2_ReuseWithChangedModelRepositorySameModel() throws Exception {
		ModelRepository modelRepo = new ModelRepository();
		modelRepo.addModel(module.getContext().getModelRepository().getModelByName("test"));
		module.getContext().setModelRepository(modelRepo);
	}
	
	@Test
	public void test3_ReuseWithChangedContextDifferentModel() throws Exception {
		module.setContext(new EvlContext());
		addAndLoadNewModel();
	}
	
	@Test
	public void test4_ReuseWithChangedContextSameModelRepositorySameModel() {
		IEvlContext context = new EvlContext();
		context.setModelRepository(module.getContext().getModelRepository());
		module.setContext(context);
	}
}
