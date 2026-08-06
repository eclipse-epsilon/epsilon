/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.engine.test.acceptance.firstOrder.lambda;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.concurrent.EolModuleParallel;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class ParallelStreamTests {

	IEolModule module;
	
	@Before
	public void setup() {
		module = new EolModuleParallel();
	}
	
	@Test
	public void testParallelStreamCanExecute() throws Exception {
		String script =
			"return Sequence{0..4095}.parallelStream()" + 
			"		.filter(i | i.mod(32) == 0)" + 
			"		.map(i | i * i)" + 
			"		.filter(i | i > 20000)" + 
			"		.findAny()" + 
			"		.orElse(null);";
		
		module.parse(script);
		assertNotNull(module.execute());
	}
	
	@Test
	public void testParallelStreamEquivalence() throws Exception {
		String script =
			"var Collectors = Native('java.util.stream.Collectors');" +
			"var testData = Sequence{-9999..99999};" +
		
			"var positiveOddsSquaredEol = testData" + 
			"	.parallelSelect(i | i >= 0 and i.mod(2) > 0)" + 
			"	.parallelCollect(i | i * i)" + 
			"	.asSet();" + 

			"var positiveOddsSquaredJava = testData" + 
			"	.parallelStream()" + 
			"	.filter(i | i >= 0 and i.mod(2) > 0)" + 
			"	.map(i | i * i)" + 
			"	.collect(Collectors.toSet());" + 
			
			"return positiveOddsSquaredEol == positiveOddsSquaredJava;";
		
		module.parse(script);
		assertTrue((boolean) module.execute());
	}
}
