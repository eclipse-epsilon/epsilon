/*********************************************************************
 * Copyright (c) 2022 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.epl.engine.test.acceptance.equivalence;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Supplier;

import org.eclipse.epsilon.common.util.Multimap;
import org.eclipse.epsilon.eol.engine.test.acceptance.util.EolAcceptanceTestUtil;
import org.eclipse.epsilon.eol.exceptions.EolInternalException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.epl.EplModule;
import org.eclipse.epsilon.epl.concurrent.EplModuleParallelPatterns;
import org.eclipse.epsilon.epl.engine.test.acceptance.EplAcceptanceTestUtil;
import org.eclipse.epsilon.epl.execute.context.concurrent.EplContextParallel;
import org.eclipse.epsilon.epl.launch.EplRunConfiguration;
import org.junit.Test;

public class EplModuleStackTraceEquivalenceTests {

	private static final String EXPECTED_START = "Type 'Person1' not found";
	private static final String EXPECTED_CONTAINS = "project.epl@4:10-4:17)";

	@Test
	public void testSequential() throws Exception {
		Multimap<String, Supplier<EplRunConfiguration>> sequentialSuppliers = EolAcceptanceTestUtil.getScenarioSuppliers(
			EplRunConfiguration.class, EplAcceptanceTestUtil.projectInputs, Collections.singleton(EplModule::new), null, EplAcceptanceTestUtil.class);

		EplRunConfiguration config = null;
		try {
			config = getFirstSupplier(sequentialSuppliers).get();
			config.call();
			fail("Expected EolInternalException");
		}
		catch (EolInternalException ex) {
			String exStr = ex.toString();
			assertTrue(exStr.startsWith(EXPECTED_START));
			assertTrue(exStr.contains(EXPECTED_CONTAINS));
			assertTrue(ex.getAst() instanceof EplModule);
		}
		finally {
			if (config != null) {
				config.dispose();
			}
		}
	}

	@Test
	public void testParallelPatterns() throws Exception {
		Multimap<String, Supplier<EplRunConfiguration>> parallelSuppliers = EolAcceptanceTestUtil.getScenarioSuppliers(
				EplRunConfiguration.class, EplAcceptanceTestUtil.projectInputs,
				Collections.singleton(() -> new EplModuleParallelPatterns(new EplContextParallel(4))),
				null, EplAcceptanceTestUtil.class);

		EplRunConfiguration config = null;
		try {
			config = getFirstSupplier(parallelSuppliers).get();
			config.call();
			fail("Expected EolInternalException");
		}
		catch (EolRuntimeException ex) {
			assertTrue(ex.getAst() instanceof EplModuleParallelPatterns);
			Throwable nested = ex.getCause().getCause();
			assertTrue(nested instanceof RuntimeException);
			String exceptionStr = nested.toString();
			assertTrue(exceptionStr.startsWith("java.lang.RuntimeException: "+EXPECTED_START));
			assertTrue(exceptionStr.contains(EXPECTED_CONTAINS));
		}
	}

	private Supplier<EplRunConfiguration> getFirstSupplier(
			Multimap<String, Supplier<EplRunConfiguration>> sequentialSuppliers) {
		Collection<Supplier<EplRunConfiguration>> firstKeySuppliers = sequentialSuppliers.values().iterator().next();
		Supplier<EplRunConfiguration> configSupplier = firstKeySuppliers.iterator().next();
		return configSupplier;
	}
}
