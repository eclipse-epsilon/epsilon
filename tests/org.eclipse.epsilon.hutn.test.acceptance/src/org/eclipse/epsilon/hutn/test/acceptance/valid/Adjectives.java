/**
 * *******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 * ******************************************************************************
 *
 * $Id: Adjectives.java,v 1.3 2008/08/12 12:53:26 louis Exp $
 */
package org.eclipse.epsilon.hutn.test.acceptance.valid;

import org.eclipse.epsilon.hutn.test.acceptance.util.HutnAcceptanceTest;
import org.junit.BeforeClass;
import org.junit.Test;

public class Adjectives extends HutnAcceptanceTest {
	
	@BeforeClass
	public static void executeHutn() throws Exception {
		final String hutn = "@Spec {"                                  +
	                        "	MetaModel \"FamiliesMetaModel\" {"     +
	                        "		nsUri = \"families\""              +
	                        "	}"                                     +
	                        "}"                                        +
                            "Families {"                               +
                            "	male Pet \"Rover\" {"                  +
                            "		name: \"Rover\""                   +
                            "	}"                                     +
                            "	#male Pet \"Fido\" {"                  +
                            "		name: \"Fido\""                    +
                            "	}"                                     +
                            "	~male Pet \"Tilly\" {"                 +
                            "		name: \"Tilly\""                   +
                            "	}"                                     +
                            "}";
		
		model = generateModel(hutn);
		
		model.setVariable("rover",  "Pet.allInstances().selectOne(p|p.name = 'Rover')");
		model.setVariable("fido",   "Pet.allInstances().selectOne(p|p.name = 'Fido')");
		model.setVariable("tilly",  "Pet.allInstances().selectOne(p|p.name = 'Tilly')");
	}
	
	@Test
	public void modelShouldHaveTwoPets() {
		model.assertEquals(3, "Pet.allInstances().size()");
	}
	
	@Test
	public void roverShouldBeMale() {
		model.assertTrue("rover.male");
	}
	
	@Test
	public void fidoShouldBeMale() {
		model.assertTrue("fido.male");
	}
	
	@Test
	public void tillyShouldBeFemale() {
		model.assertFalse("tilly.male");
	}
}
