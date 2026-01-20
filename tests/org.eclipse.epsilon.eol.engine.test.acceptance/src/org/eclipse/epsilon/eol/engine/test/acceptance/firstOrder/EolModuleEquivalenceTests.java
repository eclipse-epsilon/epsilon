/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.eol.engine.test.acceptance.firstOrder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.concurrent.EolModuleParallel;
import org.eclipse.epsilon.eol.engine.test.acceptance.firstOrder.lambda.LambdaExpressionTests;
import org.eclipse.epsilon.eol.engine.test.acceptance.util.AbstractEolEquivalenceTests;
import org.eclipse.epsilon.eol.execute.context.concurrent.EolContextParallel;
import org.eclipse.epsilon.eol.launch.EolRunConfiguration;
import org.junit.runners.Parameterized.Parameters;

public class EolModuleEquivalenceTests extends AbstractEolEquivalenceTests<EolRunConfiguration> {

	public EolModuleEquivalenceTests(EquivalenceTestParameters<EolRunConfiguration> params) {
		super(params);
	}

	@Parameters
	public static Collection<EquivalenceTestParameters<EolRunConfiguration>> configurations() throws Exception {
		String[] scripts = new String[] {
			FileUtil.getFileStandalone("LambdaExpressionTests.eol", LambdaExpressionTests.class).getAbsolutePath(),
			FileUtil.getFileStandalone("FirstOrderOperationTests.eol", FirstOrderOperationTests.class).getAbsolutePath(),
			FileUtil.getFileStandalone("FirstOrderOperationAdvancedTests.eol", FirstOrderOperationAdvancedTests.class).getAbsolutePath(),
			FileUtil.getFileStandalone("SequentialFirstOrderOperationTests.eol", SequentialFirstOrderOperationTests.class).getAbsolutePath(),
			FileUtil.getFileStandalone("ParallelFirstOrderOperationTests.eol", ParallelFirstOrderOperationTests.class).getAbsolutePath(),
			FileUtil.getFileStandalone("ParallelFirstOrderOperationEquivalenceTests.eol", ParallelFirstOrderOperationEquivalenceTests.class).getAbsolutePath()
		};

		List<EquivalenceTestParameters<EolRunConfiguration>> configs = new ArrayList<>();
		for (String script : scripts) {
			Supplier<EolRunConfiguration> oracleSupplier = getOracleSupplier(script);

			for (int threadCount : THREAD_COUNTS) {
				configs.add(new EquivalenceTestParameters<>(
					String.format("EOL: %s (sequential vs %d threads)", script, threadCount),
					oracleSupplier,
					getConfigSupplier(script, threadCount)
				));
			}
		}

		return configs;
	}

	public static Supplier<EolRunConfiguration> getOracleSupplier(String script) {
		return () -> EolRunConfiguration.Builder()
			.withScript(script)
			.withModule(new EolModule())
			.build();
	}

	public static Supplier<EolRunConfiguration> getConfigSupplier(String script, int threadCount) {
		return () -> EolRunConfiguration.Builder()
			.withScript(script)
			.withModule(new EolModuleParallel(new EolContextParallel(threadCount)))
			.build();
	}

}
