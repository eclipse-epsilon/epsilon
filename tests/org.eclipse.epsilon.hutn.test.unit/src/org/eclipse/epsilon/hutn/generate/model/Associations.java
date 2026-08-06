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
 * $Id: Associations.java,v 1.2 2008/08/07 12:44:20 louis Exp $
 */
package org.eclipse.epsilon.hutn.generate.model;

import static org.eclipse.epsilon.hutn.test.util.HutnUtil.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class Associations extends HutnModelGeneratorTest {

	@BeforeClass
	public static void generateModel() throws Exception {
		model = modelGeneratorTest(createSpec("families", createPackageObject(createClassObject("Fido",   "Pet", createAttributeSlot("name", "Fido")),
		                                                                createClassObject("Lassie", "Pet", createAttributeSlot("name", "Lassie")),
																		createClassObject("The Smiths", "Family",
		                                                                            createReferenceSlot("pets", "Fido", "Lassie")))));
		
		model.setVariable("family", "Family.allInstances().first()");
	}
	
	@Test
	public void modelShouldHaveOneFamily() {
		model.assertEquals(1, "Family.allInstances().size()");
	}
	
	@Test
	public void modelShouldHaveTwoPets() {
		model.assertEquals(2, "Pet.allInstances().size()");
	}
	
	@Test
	public void familyShouldHaveTwoPets() {
		model.assertEquals(2, "family.pets.size()");
	}
	
	@Test
	public void familyShouldHaveFidoAsAPet() {
		model.assertEquals("Fido", "family.pets.at(0).name");
	}
	
	@Test
	public void familyShouldHaveLassieAsAPet() {
		model.assertEquals("Lassie", "family.pets.at(1).name");
	}
}
