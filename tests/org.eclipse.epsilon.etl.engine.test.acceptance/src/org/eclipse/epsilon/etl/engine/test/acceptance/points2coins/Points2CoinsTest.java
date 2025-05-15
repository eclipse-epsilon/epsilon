/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.etl.engine.test.acceptance.points2coins;

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
public class Points2CoinsTest extends EtlTest {

	@Parameter
	public Supplier<? extends IEtlModule> moduleGetter;

	@Parameters(name = "{0}")
	public static Iterable<Supplier<? extends IEtlModule>> modules() {
		return EtlAcceptanceTestUtil.modules();
	}

	static Resource coinsModelOracle, coinsModelClean;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		registerMetamodel("models/Coins.ecore", Points2CoinsTest.class);
		registerMetamodel("models/Points.ecore", Points2CoinsTest.class);
		coinsModelClean = getResource("models/coins_clean.model", Points2CoinsTest.class);
		coinsModelOracle = getResource("models/coins.model", Points2CoinsTest.class);
	}
	
	@Test
	public void testTree2GraphTransformation() throws Exception {
		IEtlModule module = moduleGetter.get();
		module.parse(getFile("Points2Coins.etl"));

		EmfModel coinsModel = loadModel("Coins", "models/coins.model", "coins", false, false);
		
		module.getContext().getModelRepository().addModels(
			loadModel("Points", "models/points.model", "points", true, false),
			coinsModel
		);
		
		module.execute();
		testForEquivalence(coinsModelOracle, coinsModel, coinsModelClean);
		module.getContext().dispose();		
	}

}
