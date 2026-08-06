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
 * $Id: Associations.java,v 1.2 2008/08/07 12:44:23 louis Exp $
 */
package org.eclipse.epsilon.hutn.test.acceptance.valid;

import org.eclipse.epsilon.hutn.test.acceptance.util.HutnAcceptanceTest;
import org.junit.BeforeClass;
import org.junit.Test;

public class Associations extends HutnAcceptanceTest {
	
	@BeforeClass
	public static void executeHutn() throws Exception {
		final String hutn = "@Spec {"                                  +
	                        "	MetaModel \"FamiliesMetaModel\" {"     +
	                        "		nsUri = \"families\""              +
	                        "	}"                                     +
	                        "}"                                        +
                            "Families {"                               +
                            "	Family \"The Smiths\" {"               +
                            "		pets: Pet \"Fido\", Pet \"Tilly\"" +
                            "	}"                                     +
                            "	Pet \"Fido\" {"                        +
                            "		name: \"Fido\""                    +
                            "	}"                                     +
                            "	Pet \"Tilly\" {"                       +
                            "		name: \"Tilly\""                   +
                            "	}"                                     +
                            "   Pet \"Goldie\" {"                      +
                            "		name: \"Goldie\""                  +
                            "	}"                                     +
                            "}";
		
		model = generateModel(hutn);
//		model = generateModel(hutn, org.epsilon.test.TestUtil.getPath("Debug.model", SingleFamilyWithPets.class).replaceFirst("bin", "src"));
		
		model.setVariable("theSmiths", "Family.allInstances().first()");
		model.setVariable("fido",   "Pet.allInstances().select(p|p.name = 'Fido').first()");
		model.setVariable("tilly",  "Pet.allInstances().select(p|p.name = 'Tilly').first()");
	}
	
	@Test
	public void modelShouldHaveOneFamily() {
		model.assertEquals(1, "Family.allInstances().size()");
	}
	
	@Test
	public void modelShouldHaveThreePets() {
		model.assertEquals(3, "Pet.allInstances().size()");
	}
	
	@Test
	public void familyShouldHaveTwoPets() {
		model.assertEquals(2, "theSmiths.pets.size()");
	}
	
	@Test
	public void familyShouldHaveFido() {
		model.assertTrue("theSmiths.pets.includes(fido)");
	}
	
	@Test
	public void familyShouldHaveTilly() {
		model.assertTrue("theSmiths.pets.includes(tilly)");
	}
}
