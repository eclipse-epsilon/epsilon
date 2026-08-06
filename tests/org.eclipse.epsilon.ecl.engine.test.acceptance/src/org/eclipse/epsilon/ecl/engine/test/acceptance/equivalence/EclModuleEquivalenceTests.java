/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.ecl.engine.test.acceptance.equivalence;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import org.eclipse.epsilon.ecl.EclModule;
import org.eclipse.epsilon.ecl.engine.test.acceptance.EclAcceptanceTestUtil;
import org.eclipse.epsilon.ecl.launch.EclRunConfiguration;
import org.eclipse.epsilon.eol.engine.test.acceptance.util.AbstractEolEquivalenceTests;
import org.junit.runners.Parameterized.Parameters;

public class EclModuleEquivalenceTests extends AbstractEolEquivalenceTests<EclRunConfiguration> {

	public EclModuleEquivalenceTests(EquivalenceTestParameters<EclRunConfiguration> params) {
		super(params);
	}

	@Override
	protected void assertEquivalentConfigurations(EclRunConfiguration oracleConfig, EclRunConfiguration testedConfig) throws Exception {
		super.assertEquivalentConfigurations(oracleConfig, testedConfig);

		assertEquals("Match traces should be equal",
			oracleConfig.getResult(),
			testedConfig.getResult());
	}

	@Parameters
	public static Collection<EquivalenceTestParameters<EclRunConfiguration>> configurations() throws Exception {
		Collection<Supplier<EclRunConfiguration>> oracleSuppliers = EclAcceptanceTestUtil.getScenarioSuppliers(Collections.singleton(EclModule::new));
		Collection<Supplier<EclRunConfiguration>> testSuppliers = EclAcceptanceTestUtil.getScenarioSuppliers(EclAcceptanceTestUtil.modules(false));

		int i = 1;
		List<EquivalenceTestParameters<EclRunConfiguration>> configs = new ArrayList<>();
		for (Supplier<EclRunConfiguration> oracleSupplier : oracleSuppliers) {
			for (Supplier<EclRunConfiguration> testSupplier : testSuppliers) {
				configs.add(new EquivalenceTestParameters<>(
					"test" + i++,
					oracleSupplier,
					testSupplier
				));
			}
		}
		return configs;
	}
}
