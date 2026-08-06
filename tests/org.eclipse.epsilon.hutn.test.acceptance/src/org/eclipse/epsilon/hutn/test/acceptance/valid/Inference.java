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
 * $Id: Inference.java,v 1.1 2008/08/13 16:22:21 louis Exp $
 */
package org.eclipse.epsilon.hutn.test.acceptance.valid;

import org.eclipse.epsilon.hutn.test.acceptance.util.HutnAcceptanceTest;
import org.junit.BeforeClass;
import org.junit.Test;

public class Inference extends HutnAcceptanceTest {
	
	@BeforeClass
	public static void executeHutn() throws Exception {
		final String hutn = "@Spec {"                                     +
	                        "	MetaModel \"FamiliesMetaModel\" {"        +
	                        "		nsUri     = \"families\""             +
	                        "		configFile = \"" + CONFIG_FILE + "\"" +
	                        "	}"                                        +
	                        "}"                                           +
                            "Families {"                                  +
                            "	Family \"id-001\" {"                      +
                            "     pets: Pet \"Fido\""                     +
                            "	}"                                        +
                            "	Pet {"                                    +
                            "	  name: \"Fido\""                         +
                            "	}"                                        +
                            "}";
		
		model = generateModel(hutn);
		
		model.setVariable("family", "Family.allInstances().first()");
	}
	
	@Test
	public void modelShouldHaveOneFamily() {
		model.assertEquals(1, "Family.allInstances().size()");
	}
	
	@Test
	public void modelShouldHaveOnePet() {
		model.assertEquals(1, "Pet.allInstances().size()");
	}
	
	@Test
	public void familyShouldHaveCorrectInferredId() {
		model.assertEquals("id-001", "family.id");
	}
	
	@Test
	public void familyShouldHaveOnePet() {
		model.assertEquals(1, "family.pets.size()");
	}
	
	@Test
	public void familyShouldHavePetCalledFido() {
		model.assertEquals("Fido", "family.pets.first().name");
	}
}
