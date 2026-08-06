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
import org.junit.BeforeClass;
import org.junit.Test;

public class TemplateAndTargetCanBeDynamic extends EgxAcceptanceTest {

	private static final String egx = "rule Person2Greeting "           +
	                                  "  transform p : Person {"        +
	                                  "    template: p.name + \".egl\"" +
	                                  "    target: p.name + \".txt\" "  + 
	                                  "}";
	
	private static final String model = "Families { "        +
	                                    "  Person { "        +
	                                    "    name: \"John\"" +
	                                    "  }"                +
	                                    "  Person { "        +
	                                    "    name: \"Jane\"" +
	                                    "  }"                + 
	                                    "}";
	
	@BeforeClass
	public static void setup() throws Exception {
		runEgx(egx, model,
			template("John.egl", "Hello Mr. [%=p.name%]"),
			template("Jane.egl", "Hello Ms. [%=p.name%]")
		);
	}
	
	@Test
	public void eachTargetContainsCorrectText() {
		assertEquals("Hello Mr. John", factory.getContentFor("John.txt"));
		assertEquals("Hello Ms. Jane", factory.getContentFor("Jane.txt"));
	}

}
