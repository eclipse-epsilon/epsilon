/*********************************************************************
 * Copyright (c) 2019 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.engine.test.acceptance.firstOrder.nested;

import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.concurrent.EolModuleParallel;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 *
 * @author Sina Madani
 * @since 1.6
 */
public class AutoParallelOperationTests {
	
	IEolModule module;
	String code,
		pre = "var Thread = Native(\"java.lang.Thread\");\n",
		isMain = "(Thread.currentThread().getName() == \"main\")",
		sequence = "Sequence{-16.."+Runtime.getRuntime().availableProcessors()*2+"}";
	
	Object execute() throws Exception {
		module.parse(pre+"\nreturn "+code);
		return module.execute();
	}
	
	@Before
	public void setup() {
		module = new EolModuleParallel();
	}
	
	@Test
	public void testL0Nesting() throws Exception {
		code = "Sequence{0..8192}"
			+ ".collect(i | i.hashCode())"
			+ ".nMatch(i | i > 0 and i < 256 and not "+isMain+", 255);";
		assert (boolean) execute();
	}
	
	@Test
	public void testL1Nesting() throws Exception {
		code =sequence
			+ ".reject(i | i < 0 or "+isMain+")"
			+ ".collect(i | i.hashCode().asString().toCharSequence())"
			+ ".select(cs | not "+isMain+" and cs"
			+ "		.collect(c | c.toUpperCase().asInteger())"
			+ "		.none(c | c > 9 or "+isMain+")"
			+ ")"
			+ ".forAll(cs | cs.count(c | not "+isMain+") >= 1);";
		assert (boolean) execute();
	}
	
	@Test
	public void testSequentialNesting() throws Exception {
		code = sequence
			+ ".sequentialReject(i | i < 0 and "+isMain+")"
			+ ".sequentialCollect(i | i.hashCode().asString().toCharSequence())"
			+ ".sequentialSelect(cs | "+isMain+" and cs"
			+ "		.collect(c | c.toUpperCase().asInteger())"
			+ "		.select(c | not "+isMain+")"
			+ "		.sequentialNone(c | c > 9 or "+isMain+")"
			+ ")"
			+ ".forAll(cs | cs.count(c | not "+isMain+") >= 1);";
		assert (boolean) execute();
	}
}