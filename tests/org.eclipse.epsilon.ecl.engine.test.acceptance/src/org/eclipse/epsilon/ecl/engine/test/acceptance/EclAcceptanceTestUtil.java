/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.ecl.engine.test.acceptance;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.ecl.EclModule;
import org.eclipse.epsilon.ecl.IEclModule;
import org.eclipse.epsilon.ecl.concurrent.EclModuleParallelAnnotation;
import org.eclipse.epsilon.ecl.engine.test.acceptance.matches.MatchesOperationTest;
import org.eclipse.epsilon.ecl.execute.context.concurrent.EclContextParallel;
import org.eclipse.epsilon.ecl.launch.EclRunConfiguration;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.engine.test.acceptance.util.EolAcceptanceTestUtil;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class EclAcceptanceTestUtil extends EolAcceptanceTestUtil {
	protected EclAcceptanceTestUtil() {}
	
	static final String testsBase = getTestBaseDir(EclAcceptanceTestUtil.class);

	public static Collection<Supplier<EclRunConfiguration>> getScenarioSuppliers(Iterable<Supplier<? extends IEclModule>> moduleGetters) throws Exception {
		List<Supplier<EclRunConfiguration>> scenarios = new ArrayList<>();

		Path matchesMM = FileUtil.getFileStandalone("mymetamodel.ecore", MatchesOperationTest.class).toPath();
		Path leftModel = FileUtil.getFileStandalone("Left.model", MatchesOperationTest.class).toPath();
		Path rightModel = FileUtil.getFileStandalone("Right.model", MatchesOperationTest.class).toPath();
		Path script = FileUtil.getFileStandalone("CompareInstance.ecl", MatchesOperationTest.class).toPath();
		// TODO - what to do with CompareInstanceParallel.ecl?

		StringProperties
			leftModelProps = createModelProperties(leftModel, matchesMM),
			rightModelProps = createModelProperties(rightModel, matchesMM);

		for (Supplier<? extends IEclModule> moduleSup : moduleGetters) {
			scenarios.add(() -> EclRunConfiguration.Builder()
				.withScript(script)
				.withModel(new EmfModel(), leftModelProps)
				.withModel(new EmfModel(), rightModelProps)
				.withModule(moduleSup.get())
				.build()
			);
		}

		return scenarios;
	}

	public static Collection<Supplier<? extends IEclModule>> modules() {
		return modules(true);
	}

	public static Collection<Supplier<? extends IEclModule>> modules(boolean includeStandard) {
		return parallelModules(THREADS,
			includeStandard ? EclModule::new : null,
			p -> new EclModuleParallelAnnotation(new EclContextParallel(p)));
	}
}
