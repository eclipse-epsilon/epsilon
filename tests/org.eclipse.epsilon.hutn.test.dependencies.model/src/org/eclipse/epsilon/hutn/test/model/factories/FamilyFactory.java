/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************
 *
 * $Id$
 */
package org.eclipse.epsilon.hutn.test.model.factories;

import org.eclipse.epsilon.hutn.test.model.families.District;
import org.eclipse.epsilon.hutn.test.model.families.FamiliesFactory;
import org.eclipse.epsilon.hutn.test.model.families.Family;
import org.eclipse.epsilon.hutn.test.model.families.Person;
import org.eclipse.epsilon.hutn.test.model.families.Pet;

public abstract class FamilyFactory {

	private FamilyFactory() {}
	
	public static Family createFamily() {
		return FamiliesFactory.eINSTANCE.createFamily();
	}
	
	public static Family createFamily(String name) {
		final Family family = createFamily();
		
		family.setName(name);
		
		return family;
	}
	
	public static Family createFamily(String... addresses) {
		final Family family = createFamily();
		
		for (String address : addresses) {
			family.getAddress().add(address);
		}
		
		return family;
	}
	
	public static Family createFamily(Pet... pets) {
		final Family family = createFamily();
		
		for (Pet pet : pets) {
			family.getPets().add(pet);
		}
		
		return family;
	}
	
	public static Family createFamily(Person... members) {
		final Family family = createFamily();
		
		for (Person member : members) {
			family.getMembers().add(member);
		}
		
		return family;
	}
	
	public static Family createFamily(District district) {
		final Family family = createFamily();
		family.setDistrict(district);
		return family;
	}
}
