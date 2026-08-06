/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.eol.engine.test.acceptance.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.eclipse.epsilon.common.concurrent.ConcurrencyUtils;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.SingleFrame;
import org.eclipse.epsilon.eol.execute.operations.contributors.OperationContributor;
import org.eclipse.epsilon.eol.launch.EolRunConfiguration;
import org.eclipse.epsilon.eol.models.IModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class AbstractEolEquivalenceTests<C extends EolRunConfiguration> {

	public static final int[] THREAD_COUNTS = {
		ConcurrencyUtils.DEFAULT_PARALLELISM, 1, 2, 57
	};

	public static class EquivalenceTestParameters<C> {
		public EquivalenceTestParameters(String name,
				Supplier<C> oracleSupplier,
				Supplier<C> configSupplier) {
			this.name = name;
			this.oracleProvider = oracleSupplier;
			this.configProvider = configSupplier;
		}
	
		public final String name;
		public final Supplier<C> oracleProvider;
		public final Supplier<C> configProvider;
	}

	protected final EquivalenceTestParameters<C> parameters;

	protected C oracleConfig;
	protected C testedConfig;

	public AbstractEolEquivalenceTests(EquivalenceTestParameters<C> params) {
		this.parameters = params;
	}

	@Before
	public void setUp() throws Exception {
		this.oracleConfig = parameters.oracleProvider.get();
		this.testedConfig = parameters.configProvider.get();
	}

	@After
	public void tearDown() throws Exception {
		if (oracleConfig != null) {
			try {
				disposeConfig(oracleConfig);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		if (testedConfig != null) {
			try {
				disposeConfig(testedConfig);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	@Test
	public void equivalentResult() throws Exception {
		checkEquivalentResultAssumptions(oracleConfig, testedConfig);
		oracleConfig.run();
		testedConfig.run();
		assertEquivalentConfigurations(oracleConfig, testedConfig);
	}

	protected void checkEquivalentResultAssumptions(C oracleConfig, C testedConfig) {
		// nothing to do here
	}

	protected void disposeConfig(C testedConfig) throws Exception {
		testedConfig.dispose();
	}

	protected void assertEquivalentConfigurations(C oracleConfig, C testedConfig) throws Exception {
		assertScenariosMatch(oracleConfig, testedConfig);

		assertTrue(String.format("%s - at least the variables in the oracle", parameters.name),
			getVariables(testedConfig).containsAll(getVariables(oracleConfig))
		);

		assertEquivalentStackTraces(oracleConfig, testedConfig);

		final int expectedOCCount = getOperationContributors(oracleConfig).size();
		final int testedOCCount = getOperationContributors(testedConfig).size();
		assertTrue(String.format("%s - at least %d operation contributors", parameters.name, expectedOCCount),
			expectedOCCount >= testedOCCount
		);
	}

	private void assertEquivalentStackTraces(C oracleConfig, C testedConfig) {
		List<ModuleElement> oracleStackElements = getStackTraceModuleElements(oracleConfig);
		List<ModuleElement> testedStackElements = getStackTraceModuleElements(testedConfig);
		assertEquals(
			String.format("%s - same stack trace size", parameters.name),
			oracleStackElements.size(), testedStackElements.size());

		for (int i = 0; i < oracleStackElements.size(); i++) {
			ModuleElement oracleStackElement = oracleStackElements.get(i);
			ModuleElement testedStackElement = testedStackElements.get(i);
			if (oracleStackElement instanceof IModule && testedStackElement instanceof IModule) {
				assertEquals(
					String.format("%s - modules at element %d of stack trace point to same URI", parameters.name, i),
					((IModule) oracleStackElement).getUri(),
					((IModule) testedStackElement).getUri());
			} else {
				assertEquals(
					String.format("%s - element %d of stack trace is equal", parameters.name, i),
					oracleStackElement,	testedStackElement);
			}
		}
	}

	protected void assertScenariosMatch(C expectedConfig, C testConfig) throws Exception {
		Function<C, Collection<String>> modelCollector = cfg ->
			cfg.getModule().getContext()
				.getModelRepository().getModels()
				.stream()
				.map(IModel::getName)
				.collect(Collectors.toList());
	
		assertEquals(String.format("%s - same script", parameters.name),
			expectedConfig.getModule().getUri(),
			testConfig.getModule().getUri()
		);
			
		assertEquals(String.format("%s - same models", parameters.name),
			modelCollector.apply(expectedConfig),
			modelCollector.apply(testConfig)
		);
	}

	protected Set<String> getVariables(C config) {
		Set<String> variables = new TreeSet<>();
	
		FrameStack frameStack = config.getModule().getContext().getFrameStack();
		for (SingleFrame f : frameStack.getFrames()) {
			Set<String> frameVariableNames = f.getAll().keySet();
			variables.addAll(frameVariableNames);
		}
	
		return variables;
	}

	protected List<ModuleElement> getStackTraceModuleElements(C config) {
		return config.getModule().getContext().getExecutorFactory().getStackTraceManager().getStackTrace();
	}

	protected Collection<OperationContributor> getOperationContributors(C config) {
		return config.getModule().getContext().getOperationContributorRegistry().stream().collect(Collectors.toSet());
	}

}