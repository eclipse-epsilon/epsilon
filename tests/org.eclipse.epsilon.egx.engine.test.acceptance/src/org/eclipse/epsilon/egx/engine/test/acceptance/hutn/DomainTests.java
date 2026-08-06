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

public class DomainTests extends EgxAcceptanceTest {

	private static final String egx = "rule I2T "       +
	                                  "  transform i : Integer in: 1.to(2) {"    +
	                                  "    template: 'i.egl'" +
	                                  "    target: 'out' + i + '.txt' "    + 
	                                  "}";
	
	private static final String model = "Families {}";
	
	@BeforeClass
	public static void setup() throws Exception {
		runEgx(egx, model, template("i.egl", "[%=i%]"));
	}
	
	@Test
	public void targetContainsGeneratedText() {
		assertEquals("1", factory.getContentFor("out1.txt"));
		assertEquals("2", factory.getContentFor("out2.txt"));
	}

}
