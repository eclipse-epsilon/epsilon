/*******************************************************************************
 * Copyright (c) 2018 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Sina Madani - Concurrency tests, refactoring, utilities etc.
 ******************************************************************************
 */
package org.eclipse.epsilon.evl.engine.test.acceptance;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.epsilon.common.util.CollectionUtil;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.common.util.Multimap;
import org.eclipse.epsilon.eol.engine.test.acceptance.util.EolAcceptanceTestUtil;
import org.eclipse.epsilon.evl.EvlModule;
import org.eclipse.epsilon.evl.IEvlModule;
import org.eclipse.epsilon.evl.concurrent.EvlModuleParallelAnnotation;
import org.eclipse.epsilon.evl.concurrent.EvlModuleParallelConstraintAtoms;
import org.eclipse.epsilon.evl.concurrent.EvlModuleParallelContextAtoms;
import org.eclipse.epsilon.evl.concurrent.EvlModuleParallelElements;
import org.eclipse.epsilon.evl.execute.context.IEvlContext;
import org.eclipse.epsilon.evl.execute.context.concurrent.EvlContextParallel;
import org.eclipse.epsilon.evl.launch.EvlRunConfiguration;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class EvlAcceptanceTestUtil extends EolAcceptanceTestUtil {

	public static String TEST_CONFIG_ID_PREFIX = "test-opt";

	protected EvlAcceptanceTestUtil() {}
	
	static {
		try {
			// Load imported file
			FileUtil.getFileStandalone("scripts/utils.eol", EvlTests.class);
			FileUtil.getFileStandalone("scripts/thrift-helper-functions.eol", EvlAcceptanceTestUtil.class);
			//FileUtil.getFile("scripts/test.evl", EvlTests.class);
			//FileUtil.getFile("scripts/optimised.evl", EvlTests.class);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static final String
		// Core
		testsBase = getTestBaseDir(EvlAcceptanceTestUtil.class),
		metamodelsRoot = testsBase+"metamodels/",
		scriptsRoot = testsBase+"scripts/",
		modelsRoot = testsBase+"models/",
		
		// Metamodels and scripts
		javaMetamodel = "java.ecore",
		javaModels[] = {
			"epsilon_profiling_test.xmi",
			"test001_java.xmi",
			"emf_cdo-example.xmi",
		},
		javaScripts[] = {
			"java_equals",
			"java_findbugs",
			"java_sequential"
		},
		
		thriftMetamodel = "thrift.ecore",
		thriftScripts[] = {"thrift_validator"},
		thriftModels[] = {
			"fb303.xmi",
			"SimpleService.xmi",
			"ThriftTest.xmi"
		},
		
		cookbookMetamodel = "cookbook.ecore",
		cookbookScripts[] = {"cookbook"},
		cookbookModels[],
		
		imdbMetamodel = "movies.ecore",
		imdbScripts[] = {"imdb_validator"},
		imdbModels[] = {"imdb-small.xmi"};
	
	/** Nx3 array where N is number of test inputs; <br/>
	 *  0 is the script path,
	 *  1 is the model path,
	 *  2 is the metamodel path.
	 */
	public static final List<String[]>
		allInputs,
		javaInputs,
		thriftInputs,
		cookbookInputs,
		imdbInputs;
	
	static {
		cookbookModels = new String[6];
		cookbookModels[0] = "cookbook_cyclic.model";
		for (int i = 1; i < 6; i++) {
			cookbookModels[i] = "cookbook"+i+".model";
		}
		
		javaInputs = addAllInputs(javaScripts, javaModels, javaMetamodel);
		thriftInputs = addAllInputs(thriftScripts, thriftModels, thriftMetamodel);
		cookbookInputs = addAllInputs(cookbookScripts, cookbookModels, cookbookMetamodel);
		imdbInputs = addAllInputs(imdbScripts, imdbModels, imdbMetamodel);
		
		allInputs = CollectionUtil.composeArrayListFrom(
			imdbInputs,
			thriftInputs,
			javaInputs,
			cookbookInputs
		);
	}

	public static Multimap<String, Supplier<EvlRunConfiguration>> getScenarioSuppliers(List<String[]> testInputs, boolean includeTest, Collection<Supplier<? extends IEvlModule>> moduleGetters, Function<String[], Integer> idCalculator) throws Exception {
		if (testInputs == null) testInputs = allInputs;
		if (moduleGetters == null) moduleGetters = modules();

		Multimap<String, Supplier<EvlRunConfiguration>> scenarios
			= EolAcceptanceTestUtil.getScenarioSuppliers(EvlRunConfiguration.class, testInputs,
					moduleGetters, idCalculator, EvlAcceptanceTestUtil.class);

		for (Entry<String, Collection<Supplier<EvlRunConfiguration>>> e : new Multimap<>(scenarios).entrySet()) {
			for (Supplier<EvlRunConfiguration> alt : e.getValue()) {
				scenarios.put(
					e.getKey() + "-alt",
					() -> {
						EvlRunConfiguration conf = alt.get();
						IEvlContext context = conf.getModule().getContext();
						context.setOptimizeConstraintTrace(!context.isOptimizeConstraintTrace());
						return conf;
					}
				);
			}
		}

		if (includeTest) {
			final List<String[]> finalTestInputs = testInputs; 
			for (boolean optimizeTrace = false; !optimizeTrace; optimizeTrace = true) {
				for (Supplier<? extends IEvlModule> moduleGetter : moduleGetters) {
					IEvlModule evlModule = moduleGetter.get();
					Path script = EvlTests.getTestScript(evlModule).toPath();

					final boolean finalOptimize = optimizeTrace;
					final String testIdentifier = TEST_CONFIG_ID_PREFIX + finalOptimize;
					scenarios.put(
						testIdentifier,
						() -> {
							try {
								return EvlRunConfiguration.Builder()
									.withScript(script)
									.withModel(EvlTests.getTestModel(false))
									.loadModels(false)
									.withModule(evlModule)
									.profileExecution(false)
									.showResults(false)
									.optimizeConstraintTrace(finalOptimize)
									.withId(finalTestInputs.size() + 1)
									.build();
							} catch (Exception e1) {
								e1.printStackTrace();
								return null;
							}
						}
					);
				}
			}
		}
		return scenarios;
	}
	
	public static Collection<Supplier<? extends IEvlModule>> modules(boolean includeStandard) {
		return parallelModules(THREADS,
			includeStandard ? EvlModule::new : null,
			p -> new EvlModuleParallelElements(new EvlContextParallel(p)),
			p -> new EvlModuleParallelContextAtoms(new EvlContextParallel(p)),
			p -> new EvlModuleParallelConstraintAtoms(new EvlContextParallel(p)),
			p -> new EvlModuleParallelAnnotation(new EvlContextParallel(p))
		);
	}
	
	// Boilerplate defaults
	public static List<String[]> addAllInputs(String[] scripts, String[] models, String metamodel) {
		return EolAcceptanceTestUtil.addAllInputs(scripts, models, metamodel, "evl", scriptsRoot, modelsRoot, metamodelsRoot);
	}

	public static Collection<Supplier<? extends IEvlModule>> modules() {
		return modules(true);
	}
}
