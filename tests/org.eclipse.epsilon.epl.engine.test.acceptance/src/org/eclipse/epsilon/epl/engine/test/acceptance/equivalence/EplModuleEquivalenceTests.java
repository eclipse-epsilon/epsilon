/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.epl.engine.test.acceptance.equivalence;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Supplier;

import org.eclipse.epsilon.common.util.Multimap;
import org.eclipse.epsilon.eol.engine.test.acceptance.util.EolAcceptanceTestUtil;
import org.eclipse.epsilon.eol.engine.test.acceptance.util.AbstractEolEquivalenceTests;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.epl.EplModule;
import org.eclipse.epsilon.epl.engine.test.acceptance.EplAcceptanceTestUtil;
import org.eclipse.epsilon.epl.launch.EplRunConfiguration;
import org.junit.runners.Parameterized.Parameters;

public class EplModuleEquivalenceTests extends AbstractEolEquivalenceTests<EplRunConfiguration> {

	public EplModuleEquivalenceTests(EquivalenceTestParameters<EplRunConfiguration> params) {
		super(params);
	}

	@Parameters
	public static Collection<EquivalenceTestParameters<EplRunConfiguration>> configurations() throws Exception {
		Multimap<String, Supplier<EplRunConfiguration>> oracleSuppliers = 
			EplAcceptanceTestUtil.getScenarioSuppliers(EplAcceptanceTestUtil.allInputs, true, Collections.singleton(EplModule::new));

		Multimap<String, Supplier<EplRunConfiguration>> testedSuppliers =
			EplAcceptanceTestUtil.getScenarioSuppliers(EplAcceptanceTestUtil.allInputs, false, EplAcceptanceTestUtil.modules(false));

		return EolAcceptanceTestUtil.getPairedConfigurations(oracleSuppliers, testedSuppliers);
	}

	@Override
	protected void assertEquivalentConfigurations(EplRunConfiguration oracleConfig, EplRunConfiguration testedConfig) throws Exception {
		super.assertEquivalentConfigurations(oracleConfig, testedConfig);

		IModel expectedModel = oracleConfig.getResult();
		IModel actualModel = testedConfig.getResult();

		// TODO: implement

		assertEquals(expectedModel.allContents().size(), actualModel.allContents().size());
	}

}
