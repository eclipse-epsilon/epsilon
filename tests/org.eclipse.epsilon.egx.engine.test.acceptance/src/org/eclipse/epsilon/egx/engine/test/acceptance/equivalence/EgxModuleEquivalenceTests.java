/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.egx.engine.test.acceptance.equivalence;

import static org.eclipse.epsilon.egx.engine.test.acceptance.EgxAcceptanceTestUtil.allInputs;
import static org.eclipse.epsilon.egx.engine.test.acceptance.EgxAcceptanceTestUtil.deleteOutputDirectories;
import static org.eclipse.epsilon.egx.engine.test.acceptance.EgxAcceptanceTestUtil.getResult;
import static org.eclipse.epsilon.egx.engine.test.acceptance.EgxAcceptanceTestUtil.modules;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Supplier;

import org.eclipse.epsilon.common.util.Multimap;
import org.eclipse.epsilon.egl.EgxModule;
import org.eclipse.epsilon.egl.launch.EgxRunConfiguration;
import org.eclipse.epsilon.egx.engine.test.acceptance.EgxAcceptanceTestUtil;
import org.eclipse.epsilon.eol.engine.test.acceptance.util.AbstractEolEquivalenceTests;
import org.eclipse.epsilon.eol.engine.test.acceptance.util.EolAcceptanceTestUtil;
import org.junit.AfterClass;
import org.junit.runners.Parameterized.Parameters;

public class EgxModuleEquivalenceTests extends AbstractEolEquivalenceTests<EgxRunConfiguration> {

	public EgxModuleEquivalenceTests(EquivalenceTestParameters<EgxRunConfiguration> params) {
		super(params);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		deleteOutputDirectories();
	}

	@Parameters
	public static Collection<EquivalenceTestParameters<EgxRunConfiguration>> configurations() throws Exception {
		Multimap<String, Supplier<EgxRunConfiguration>> expectedSuppliers = 
			EgxAcceptanceTestUtil.getScenarioSuppliers(allInputs, Collections.singleton(EgxModule::new));

		Multimap<String, Supplier<EgxRunConfiguration>> testedSuppliers =
			EgxAcceptanceTestUtil.getScenarioSuppliers(allInputs, modules(false));

		return EolAcceptanceTestUtil.getPairedConfigurations(expectedSuppliers, testedSuppliers);
	}

	@Override
	protected void assertEquivalentConfigurations(EgxRunConfiguration oracleConfig, EgxRunConfiguration testedConfig) throws Exception {
		super.assertEquivalentConfigurations(oracleConfig, testedConfig);

		Map<Path, byte[]> expectedOutput = getResult(oracleConfig), actualOutput = getResult(testedConfig);
		assertEquals(expectedOutput.size(), actualOutput.size());
		assertEquals(expectedOutput.keySet(), actualOutput.keySet());

		Collection<byte[]> expectedOutputs = expectedOutput.values(), actualOutputs = actualOutput.values();

		Iterator<byte[]> actualIter = actualOutputs.iterator();
		for (byte[] expectedBytes : expectedOutputs) {
			assert actualIter.hasNext();
			byte[] actualBytes = actualIter.next();
			assertArrayEquals(expectedBytes, actualBytes);
		}
	}
	
}
