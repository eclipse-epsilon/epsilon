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

import java.util.Iterator;
import org.eclipse.epsilon.eol.engine.test.acceptance.util.EolAcceptanceTestUtil;
import org.eclipse.epsilon.eol.exceptions.EolInternalException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.epl.EplModule;
import org.eclipse.epsilon.epl.concurrent.EplModuleParallelPatterns;
import org.eclipse.epsilon.epl.engine.test.acceptance.EplAcceptanceTestUtil;
import org.eclipse.epsilon.epl.execute.context.concurrent.EplContextParallel;
import org.eclipse.epsilon.epl.launch.EplRunConfiguration;
import org.junit.Before;
import org.junit.Test;

public class EplModuleStackTraceEquivalenceTests {

	EplRunConfiguration oracleConfig, parallelPatternsConfig;
	final String expectedStart = "Type 'Person1' not found",
			expectedContains = "project.epl@4:10-4:17)";
	
	@Before
	public void setUp() throws Exception {
		Iterator<EplRunConfiguration> configsIter = EolAcceptanceTestUtil.getScenarios(
			EplRunConfiguration.class,
			EplAcceptanceTestUtil.projectInputs,
			EolAcceptanceTestUtil.parallelModules(
				new int[]{1, 2, 4},
				EplModule::new,
				p -> new EplModuleParallelPatterns(new EplContextParallel(p))
			),
			null,
			EplAcceptanceTestUtil.class
		).iterator();
		
		oracleConfig = configsIter.next();
		parallelPatternsConfig = configsIter.next();
	}
	
	
	@Test
	public void testSequential() throws Exception {
		try {
			oracleConfig.call();
			fail("Expected EolInternalException");
		}
		catch (EolInternalException ex) {
			String exStr = ex.toString();
			assertTrue(exStr.startsWith(expectedStart));
			assertTrue(exStr.contains(expectedContains));
			assertTrue(ex.getAst() instanceof EplModule);
		}
	}
	
	@Test
	public void testParallelPatterns() throws Exception {
		try {
			parallelPatternsConfig.call();
			fail("Expected EolInternalException");
		}
		catch (EolRuntimeException ex) {
			assertTrue(ex.getAst() instanceof EplModuleParallelPatterns);
			Throwable nested = ex.getCause().getCause();
			assertTrue(nested instanceof RuntimeException);
			String exceptionStr = nested.toString();
			assertTrue(exceptionStr.startsWith("java.lang.RuntimeException: "+expectedStart));
			assertTrue(exceptionStr.contains(expectedContains));
		}
	}
}
