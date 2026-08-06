/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.hutn.test.acceptance.valid;

import org.eclipse.epsilon.hutn.test.acceptance.util.HutnAcceptanceTest;
import org.junit.BeforeClass;
import org.junit.Test;

public class DoublesAndFloats extends HutnAcceptanceTest {
	
	@BeforeClass
	public static void executeHutn() throws Exception {
		final String hutn = "@Spec {"                              +
	                        "	MetaModel \"FamiliesMetaModel\" {" +
	                        "		nsUri = \"families\""          +
	                        "	}"                                 +
	                        "}"                                    +
                            "Families {"                           +
                            "	Family \"The Smiths\" {"           +
                            "		averageAge: 26.7"              + // averageAge is type EFloat
                            "       averageAgePrecise: 26.7"       + // averageAgePrecise is type EDouble
                            "	}"                                 +
                            "}";
		
		model = generateModel(hutn);
	}
	
	@Test
	public void floatTypedSlotShouldProduceFloatValues() {
		model.assertEquals("java.lang.Float", "Family.all.first.averageAge.class.name");
	}
	
	@Test
	public void doubleTypedSlotShouldProduceDoubleValues() {
		model.assertEquals("java.lang.Double", "Family.all.first.averageAgePrecise.class.name");
	}

}
