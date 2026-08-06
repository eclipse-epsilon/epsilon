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
 * $Id: AssociationBlock.java,v 1.1 2008/08/14 10:50:24 louis Exp $
 */
package org.eclipse.epsilon.hutn.test.acceptance.valid;

import org.eclipse.epsilon.hutn.test.acceptance.util.HutnAcceptanceTest;
import org.junit.BeforeClass;
import org.junit.Test;

public class AssociationBlock extends HutnAcceptanceTest {
	
	@BeforeClass
	public static void executeHutn() throws Exception {
		final String hutn = "@Spec {"                                  +
	                        "	MetaModel \"FamiliesMetaModel\" {"     +
	                        "		nsUri = \"families\""              +
	                        "	}"                                     +
	                        "}"                                        +
                            "Families {"                               +
                            "	Pet \"Fido\" {"                        +
                            "		name: \"Fido\""                    +
                            "	}"                                     +
                            "   Pet \"Goldie\" {"                      +
                            "		name: \"Goldie\""                  +
                            "	}"                                     +
                            ""                                         +
                            "   pets {"                                +
                            "		Family \"The Smiths\""             +
                            "		Pet \"Fido\""                      +
                            "		Family \"The Smiths\""             +
                            "		Pet \"Tilly\""                     +
                            "	}"                                     +
                            "}";
		
		model = generateModel(hutn);
		
		model.setVariable("theSmiths", "Family.allInstances().first()");
		model.setVariable("fido",      "Pet.allInstances().selectOne(p|p.name = 'Fido')");
		model.setVariable("tilly",     "Pet.allInstances().selectOne(p|p.name.isUndefined())");
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
