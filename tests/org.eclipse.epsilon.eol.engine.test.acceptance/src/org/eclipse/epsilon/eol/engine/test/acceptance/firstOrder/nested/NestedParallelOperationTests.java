/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.engine.test.acceptance.firstOrder.nested;

import static org.junit.Assert.fail;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.concurrent.EolModuleParallel;
import org.eclipse.epsilon.eol.exceptions.concurrent.EolNestedParallelismException;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class NestedParallelOperationTests {

	IEolModule module;
	
	String
		pre = "Sequence{0.."+Short.MAX_VALUE+"}.parallelSelect(n | n.hashCode().asString().toCharSequence()",
		midLambda = "(c | c.asInteger())",
		endLambda = "(i | i == 8));";
	
	@Before
	public void setup() {
		module = new EolModuleParallel();
	}
	
	@Test
	public void testLegalNesting() throws Exception {
		module.parse(pre + ".collect" + midLambda + ".one" + endLambda);
		module.execute();
	}
	
	void testIllegalNesting(String code) throws Exception {
		module.parse(code);
		try {
			module.execute();
		}
		catch (EolNestedParallelismException ex) {
			return;
		}
		
		fail("Expected "+EolNestedParallelismException.class.getSimpleName());
	}
	
	@Test
	public void testIllegalNestingOuter() throws Exception {
		testIllegalNesting(pre + ".parallelCollect" + midLambda + ".one" + endLambda);
	}
	
	@Test
	public void testIllegalNestingInner() throws Exception {
		testIllegalNesting(pre + ".collect" + midLambda + ".parallelOne" + endLambda);
	}
	
	@Test
	public void testIllegalNestingInnerOuter() throws Exception {
		testIllegalNesting(pre + ".parallelCollect" + midLambda + ".parallelOne" + endLambda);
	}
}
