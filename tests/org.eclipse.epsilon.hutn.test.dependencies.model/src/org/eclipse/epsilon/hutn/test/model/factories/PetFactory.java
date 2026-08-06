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

import org.eclipse.epsilon.hutn.test.model.families.FamiliesFactory;
import org.eclipse.epsilon.hutn.test.model.families.Pet;

public abstract class PetFactory {

	private PetFactory() {}
	
	public static Pet createPet() {
		return createPet(null);
	}
	
	public static Pet createPet(String name) {
		final Pet pet = FamiliesFactory.eINSTANCE.createPet();
		pet.setName(name);
		return pet;
	}
}
