/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.evl.engine.test.acceptance.equivalence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.epsilon.common.util.Multimap;
import org.eclipse.epsilon.eol.engine.test.acceptance.util.EolAcceptanceTestUtil;
import org.eclipse.epsilon.eol.engine.test.acceptance.util.AbstractEolEquivalenceTests;
import org.eclipse.epsilon.evl.EvlModule;
import org.eclipse.epsilon.evl.dom.Constraint;
import org.eclipse.epsilon.evl.engine.test.acceptance.EvlAcceptanceTestUtil;
import org.eclipse.epsilon.evl.execute.UnsatisfiedConstraint;
import org.eclipse.epsilon.evl.launch.EvlRunConfiguration;
import org.junit.runners.Parameterized.Parameters;

public class EvlModuleEquivalenceTests extends AbstractEolEquivalenceTests<EvlRunConfiguration> {

	public EvlModuleEquivalenceTests(EquivalenceTestParameters<EvlRunConfiguration> params) {
		super(params);
	}

	@Override
	protected void assertEquivalentConfigurations(EvlRunConfiguration oracleConfig, EvlRunConfiguration testedConfig) throws Exception {
		super.assertEquivalentConfigurations(oracleConfig, testedConfig);

		// Unsatisfied constraints
		Collection<UnsatisfiedConstraint> oracleUnsatisfied = oracleConfig.getModule().getContext().getUnsatisfiedConstraints();
		Collection<UnsatisfiedConstraint> testedUnsatisfied = testedConfig.getModule().getContext().getUnsatisfiedConstraints();
		assertEqualElementsIgnoringOrder(String.format("%s - equals unsatisfied constraints", parameters.name),
			oracleUnsatisfied, testedUnsatisfied);

		// Constraint traces
		if (oracleConfig.getModule().getContext().isOptimizeConstraintTrace() == testedConfig.getModule().getContext().isOptimizeConstraintTrace()) {
			// Uses Set instead of List for performance reasons when calling containsAll.
			Function<EvlRunConfiguration, Collection<?>> ctContents = cfg ->
				cfg.getModule().getContext().getConstraintTrace().getItems();

			assertEquals(String.format("%s - equals constraint traces", parameters.name),
				ctContents.apply(oracleConfig),
				ctContents.apply(testedConfig));
		}

		// Constraints depended on
		Set<Constraint> oracleConstraintsDep = oracleConfig.getModule().getContext().getConstraintsDependedOn();
		Set<Constraint> testedConstraintsDep = testedConfig.getModule().getContext().getConstraintsDependedOn();
		assertEquals(String.format("%s - equals constraints depended on", parameters.name),
				oracleConstraintsDep, testedConstraintsDep);
	}

	@Override
	protected void disposeConfig(EvlRunConfiguration config) throws Exception {
		if (parameters.name.startsWith(EvlAcceptanceTestUtil.TEST_CONFIG_ID_PREFIX)) {
			/*
			 * Note: the executions with test.evl all need to work on the same model instance,
			 * and we'll also need their stack traces, so don't dispose them. We'll want to
			 * investigate at some point why this is the case.
			 */
			config.reset();
		} else {
			super.disposeConfig(config);
		}
	}

	protected void assertEqualElementsIgnoringOrder(String msg, Collection<?> expected, Collection<?> actual) {
		HashSet<?> onlyInExpected = new HashSet<>(expected);
		onlyInExpected.removeAll(actual);

		HashSet<?> onlyInActual = new HashSet<>(actual);
		onlyInActual.removeAll(expected);

		assertTrue(msg + ": only in actual - "  + onlyInActual, onlyInExpected.isEmpty());
		assertTrue(msg + ": only in expected - " + onlyInExpected, onlyInExpected.isEmpty());
	}

	@Parameters
	public static Collection<EquivalenceTestParameters<EvlRunConfiguration>> configurations() throws Exception {
		// Set up the expected suppliers - there should be one per ID
		Multimap<String, Supplier<EvlRunConfiguration>> expectedSuppliers =
			EvlAcceptanceTestUtil.getScenarioSuppliers(null, true, Collections.singletonList(EvlModule::new), null);
		for (Entry<String, Collection<Supplier<EvlRunConfiguration>>> e : expectedSuppliers.entrySet()) {
			assert e.getValue().size() == 1 : String.format("There should be only one supplier with ID %s", e.getKey());
		}

		// Get the suppliers to be tested now
		Multimap<String, Supplier<EvlRunConfiguration>> testedSuppliers =
			EvlAcceptanceTestUtil.getScenarioSuppliers(null, true, EvlAcceptanceTestUtil.modules(false), null);
		assert expectedSuppliers.keySet().equals(testedSuppliers.keySet())
			: "The keys for the expected and tested suppliers should match";

		return EolAcceptanceTestUtil.getPairedConfigurations(expectedSuppliers, testedSuppliers);
	}

	
}
