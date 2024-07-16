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

import static org.eclipse.epsilon.evl.engine.test.acceptance.EvlAcceptanceTestUtil.addAllInputs;
import static org.eclipse.epsilon.evl.engine.test.acceptance.EvlAcceptanceTestUtil.javaMetamodel;
import static org.eclipse.epsilon.evl.engine.test.acceptance.EvlAcceptanceTestUtil.javaModels;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeTrue;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.epsilon.common.util.Multimap;
import org.eclipse.epsilon.eol.engine.test.acceptance.util.EolAcceptanceTestUtil;
import org.eclipse.epsilon.eol.exceptions.concurrent.EolNestedParallelismException;
import org.eclipse.epsilon.evl.EvlModule;
import org.eclipse.epsilon.evl.concurrent.EvlModuleParallelAnnotation;
import org.eclipse.epsilon.evl.engine.test.acceptance.EvlAcceptanceTestUtil;
import org.eclipse.epsilon.evl.execute.context.concurrent.EvlContextParallel;
import org.eclipse.epsilon.evl.launch.EvlRunConfiguration;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

public class EvlParallelOperationsTests extends EvlModuleEquivalenceTests {

	public EvlParallelOperationsTests(EquivalenceTestParameters<EvlRunConfiguration> params) {
		super(params);
	}

	@Override
	protected void checkEquivalentResultAssumptions(EvlRunConfiguration oracleConfig, EvlRunConfiguration testedConfig) {
		assumeFalse(isNestedParallelScript(testedConfig));
	}

	private boolean isNestedParallelScript(EvlRunConfiguration testedConfig) {
		return "java_parallelNested.evl".equals(testedConfig.script.getFileName() + "");
	}

	@Test
	public void testIllegalNesting() throws Exception {
		assumeTrue(isNestedParallelScript(testedConfig));
		assumeFalse(testedConfig.getModule() instanceof EvlModuleParallelAnnotation);

		try {
			testedConfig.preExecute();
			assertScenariosMatch(oracleConfig, testedConfig);
			testedConfig.execute();
		} catch (EolNestedParallelismException px) {
			return;
		} finally {
			testedConfig.postExecute();
		}

		fail(String.format("Expected %s: %s %s",
			EolNestedParallelismException.class.getSimpleName(),
			testedConfig.getModule().getClass().getSimpleName(),
			testedConfig.getModule().getContext()));
	}

	@Parameters
	public static Collection<EquivalenceTestParameters<EvlRunConfiguration>> configurations() throws Exception {
		List<String[]> inputsWithNesting = addAllInputs(
			new String[]{"java_parallel", "java_parallelNested"},
			javaModels, javaMetamodel
		);
		List<String[]> inputsWithoutNesting = addAllInputs(
			new String[]{"java_sequential", "java_annotated"},
			javaModels, javaMetamodel
		);

		final int[] testThreads = {2, 3};

		Function<String[], Integer> idCalculator = inputs -> {
			int scriptHash;
			if (inputs[0].startsWith("java_parallel") || inputs[0].startsWith("java_sequential") || inputs[0].startsWith("java_annotated")) {
				scriptHash = "java_sequential".hashCode(); 
			}
			else {
				scriptHash = inputs[0].hashCode();
			}
			return Objects.hash(scriptHash, inputs[1], inputs[2]);
		};

		Multimap<String, Supplier<EvlRunConfiguration>> expectedScenarios = EvlAcceptanceTestUtil.getScenarioSuppliers(
				inputsWithNesting, false, Collections.singleton(EvlModule::new), idCalculator
			);
		expectedScenarios.putAll(EvlAcceptanceTestUtil.getScenarioSuppliers(inputsWithoutNesting, false, EvlAcceptanceTestUtil.modules(), idCalculator));

		Multimap<String, Supplier<EvlRunConfiguration>> testedScenarios = EvlAcceptanceTestUtil.getScenarioSuppliers(inputsWithNesting, false,
				EolAcceptanceTestUtil.parallelModules(testThreads, null,
					p -> new EvlModuleParallelAnnotation(new EvlContextParallel(p))
				),
				idCalculator
			);
		testedScenarios.putAll(EvlAcceptanceTestUtil.getScenarioSuppliers(inputsWithoutNesting, false, EvlAcceptanceTestUtil.modules(), idCalculator));

		return EvlAcceptanceTestUtil.getPairedConfigurations(expectedScenarios, testedScenarios);
	}

}
