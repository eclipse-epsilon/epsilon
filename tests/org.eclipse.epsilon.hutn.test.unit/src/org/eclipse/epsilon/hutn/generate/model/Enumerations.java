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
 * $Id: Enumerations.java,v 1.1 2008/08/08 17:19:01 louis Exp $
 */
package org.eclipse.epsilon.hutn.generate.model;

import static org.eclipse.epsilon.hutn.test.util.HutnUtil.*;

import org.eclipse.epsilon.hutn.model.hutn.ClassObject;
import org.eclipse.epsilon.hutn.test.model.families.DogBreed;
import org.eclipse.epsilon.hutn.test.model.families.FamiliesFactory;
import org.eclipse.epsilon.hutn.test.model.families.FamiliesPackage;
import org.junit.BeforeClass;
import org.junit.Test;

public class Enumerations extends HutnModelGeneratorTest {

	private static ClassObject createDog(String name, String breed) {
		final DogBreed breedEnum = (DogBreed)FamiliesFactory.eINSTANCE.createFromString(FamiliesPackage.eINSTANCE.getDogBreed(), breed);
		return createClassObject(name, "Dog", createAttributeSlot("name", name), createAttributeSlot("breed", breedEnum));
	}
	
	@BeforeClass
	public static void generateModel() throws Exception {
		model = modelGeneratorTest(createSpec("families", createPackageObject(createDog("Fido", "labrador"), createDog("Lassie", "poodle"))));
		
		model.setVariable("fido",   "Dog.allInstances().selectOne(d|d.name='Fido')");
		model.setVariable("lassie", "Dog.allInstances().selectOne(d|d.name='Lassie')");
	}

	@Test
	public void modelShouldHaveTwoPets() {
		model.assertEquals(2, "Pet.allInstances().size()");
	}
	
	@Test
	public void fidoShouldBeALabrador() {
		model.assertEquals("DogBreed#labrador", "fido.breed");
	}
	
	@Test
	public void lassieShouldBeAPoodle() {
		model.assertEquals("DogBreed#poodle", "lassie.breed");
	}
}
