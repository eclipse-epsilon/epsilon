/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.egx.engine.test.acceptance;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.common.util.Multimap;
import org.eclipse.epsilon.egl.EgxModule;
import org.eclipse.epsilon.egl.IEgxModule;
import org.eclipse.epsilon.egl.concurrent.EgxModuleParallelAnnotation;
import org.eclipse.epsilon.egl.concurrent.EgxModuleParallelGenerationRuleAtoms;
import org.eclipse.epsilon.egl.execute.context.concurrent.EgxContextParallel;
import org.eclipse.epsilon.egl.launch.EgxRunConfiguration;
import org.eclipse.epsilon.eol.engine.test.acceptance.util.EolAcceptanceTestUtil;

public class EgxAcceptanceTestUtil extends EolAcceptanceTestUtil {
	private EgxAcceptanceTestUtil() {}
	
	public static final String
		testsBase = getTestBaseDir(EgxAcceptanceTestSuite.class),
		ecoreBase = testsBase+"ecore2dot/",
		ecoreMetamodel = "Ecore.ecore",
		ecoreModels[] = {"java.ecore"},
		ecoreScripts[] = {"ecore"};
		
	public static final List<String[]>
		ecoreInputs = new ArrayList<>(),
		allInputs = new ArrayList<>();
	
	static {
		ecoreInputs.addAll(addAllInputs(
			ecoreScripts,
			ecoreModels, ecoreMetamodel, "egx",
			ecoreBase, ecoreBase, ecoreBase
		));
		allInputs.addAll(ecoreInputs);
		
		try {
			FileUtil.getFileStandalone("ecore2dot/eclass2dot.egl", EgxAcceptanceTestUtil.class);
			FileUtil.getFileStandalone("ecore2dot/ecore2dot.egl", EgxAcceptanceTestUtil.class);
			FileUtil.getFileStandalone("ecore2dot/Ecore.ecore", EgxAcceptanceTestUtil.class);
			FileUtil.getFileStandalone("ecore2dot/java.ecore", EgxAcceptanceTestUtil.class);
			FileUtil.getFileStandalone("ecore2dot/ecore.egx", EgxAcceptanceTestUtil.class);
			FileUtil.getFileStandalone("ecore2dot/ecore23d.egl", EgxAcceptanceTestUtil.class);
			FileUtil.getFileStandalone("ecore2dot/objectdiagram.egl", EgxAcceptanceTestUtil.class);
			FileUtil.getFileStandalone("ecore2dot/stats.egl", EgxAcceptanceTestUtil.class);
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public static Collection<Supplier<? extends IEgxModule>> modules(boolean includeStandard) {
		return parallelModules(THREADS,
			includeStandard ? EgxModule::new : null,
			p -> new EgxModuleParallelAnnotation(new EgxContextParallel(p)),
			p -> new EgxModuleParallelGenerationRuleAtoms(new EgxContextParallel(p))
		);
	}

	public static Multimap<String, Supplier<EgxRunConfiguration>> getScenarioSuppliers(
			List<String[]> testInputs,
			Collection<Supplier<? extends IEgxModule>> moduleGetters) throws Exception {
				return getScenarioSuppliers(EgxRunConfiguration.class, testInputs, moduleGetters, null, EgxAcceptanceTestUtil.class);
	}

	public static void deleteOutputDirectories() throws IOException {
		try {
			FileUtil.deleteDirectory(ecoreBase+"output");
		}
		catch (java.nio.file.NoSuchFileException ignore) {}
	}
	
	public static Map<Path, byte[]> getOutputFiles() throws IOException {
		Map<Path, byte[]> outputs = new java.util.HashMap<>();
		try {
			outputs.putAll(FileUtil.readDirectory(ecoreBase+"output"));
		}
		catch (java.nio.file.NoSuchFileException ignore) {}
		return outputs;
	}
	
	public static Map<Path, byte[]> getResult(EgxRunConfiguration scenario) throws IOException {
		return FileUtil.readDirectory(Paths.get(scenario.outputRoot));
	}
}
