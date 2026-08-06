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
 * $Id: Containment.java,v 1.2 2008/08/07 12:44:20 louis Exp $
 */
package org.eclipse.epsilon.hutn.generate.model;

import static org.eclipse.epsilon.hutn.test.util.HutnUtil.*;

import org.eclipse.epsilon.hutn.model.hutn.ClassObject;
import org.junit.BeforeClass;
import org.junit.Test;

public class Containment extends HutnModelGeneratorTest {

	private static ClassObject createPerson(String name) {
		return createClassObject(name, "Person", createAttributeSlot("name", name));
	}
	
	@BeforeClass
	public static void generateModel() throws Exception {
		model = modelGeneratorTest(createSpec("families", createPackageObject(createClassObject("The Smiths", "Family",
		                                                                            createContainmentSlot("members",
		                                                                                                createPerson("John"),
		                                                                                                createPerson("Gill"))))));
		
		model.setVariable("family", "Family.allInstances().first()");
	}
	
	@Test
	public void modelShouldHaveOneFamily() {
		model.assertEquals(1, "Family.allInstances().size()");
	}
	
	@Test
	public void modelShouldHaveTwoPeople() {
		model.assertEquals(2, "Person.allInstances().size()");
	}
	
	@Test
	public void familyShouldContainTwoPeople() {
		model.assertEquals(2, "family.members.size()");
	}
	
	@Test
	public void familyShouldContainJohn() {
		model.assertEquals("John", "family.members.at(0).name");
	}
	
	@Test
	public void familyShouldContainGill() {
		model.assertEquals("Gill", "family.members.at(1).name");
	}
}
