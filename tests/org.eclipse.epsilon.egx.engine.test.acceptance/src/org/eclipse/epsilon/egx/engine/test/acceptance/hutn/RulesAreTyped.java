/*******************************************************************************
 * Copyright (c) 2014 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egx.engine.test.acceptance.hutn;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

public class RulesAreTyped extends EgxAcceptanceTest {

	private static final String egx = "rule Person2Greeting "       +
	                                  "  transform p : Person {"    +
	                                  "    template: \"hello.egl\"" +
	                                  "    target: \"out.txt\" "    + 
	                                  "}";

	private static final String model = "Families { "              +
	                                    "  Person { "              +
	                                    "    name: \"John\""       +
	                                    "  }"                      + 
	                                    "  Family { "              +
	                                    "    name: \"The Smiths\"" +
	                                    "  }"                      + 
	                                    "}";

	@BeforeClass
	public static void setup() throws Exception {
		runEgx(egx, model, template("hello.egl", "Hello [%=p.name%]"));
	}
	
	@Test
	public void targetContainsGeneratedText() {
		assertEquals("Hello John", factory.getContentFor("out.txt"));
	}
	
	@Test
	public void noOtherFilesAreGeneratd() {
		assertTrue(factory.getOutputFiles().contains("out.txt"));
	}
}
