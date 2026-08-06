/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.etl.engine.test.acceptance.oo2db;

import java.util.function.Supplier;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.etl.IEtlModule;
import org.eclipse.epsilon.etl.engine.test.acceptance.EtlAcceptanceTestUtil;
import org.eclipse.epsilon.etl.engine.test.acceptance.EtlTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class OO2DBTest extends EtlTest {
	
	@Parameter
	public Supplier<? extends IEtlModule> moduleGetter;

	@Parameters(name = "{0}")
	public static Iterable<Supplier<? extends IEtlModule>> modules() {
		return EtlAcceptanceTestUtil.modules();
	}
	
	static Resource dbModelOracle, dbModelClean;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		registerMetamodel("models/OO.ecore", OO2DBTest.class);
		registerMetamodel("models/DB.ecore", OO2DBTest.class);
		registerMetamodel("models/TM.ecore", OO2DBTest.class);
		registerMetamodel("models/SimpleTrace.ecore", OO2DBTest.class);
		dbModelClean = getResource("models/DBInstance_clean.model", OO2DBTest.class);
		dbModelOracle = getResource("models/DBInstance.model", OO2DBTest.class);
	}
	
	@Test
	public void testOO2DBTransformation() throws Exception {	
		IEtlModule module = moduleGetter.get();
		getFile("OO2DB.eol"); // Make sure this imported file is also copied next to the ETL transformation
		module.parse(getFile("OO2DB.etl"));

		EmfModel dbModel = loadModel("DB", "models/DBInstance.model", "DB", false, false);
		
		module.getContext().getModelRepository().addModels(
			loadModel("OO", "models/OOInstance.model", "OO", true, false),
			dbModel,
			loadModel("OO2DB", "models/OO2DB.model", "TM", true, false),
			loadModel("Trace", "models/Trace.model", "SimpleTrace", false, false)
		);
		
		module.execute();
		testForEquivalence(dbModelOracle, dbModel, dbModelClean);
		module.getContext().dispose();
	}
		
}
