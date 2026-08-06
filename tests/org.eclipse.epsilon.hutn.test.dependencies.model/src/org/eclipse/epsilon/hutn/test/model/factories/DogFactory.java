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

import org.eclipse.epsilon.hutn.test.model.families.Dog;
import org.eclipse.epsilon.hutn.test.model.families.DogBreed;
import org.eclipse.epsilon.hutn.test.model.families.FamiliesFactory;

public abstract class DogFactory {

	private DogFactory() {}
	
	public static Dog createDog() {
		return FamiliesFactory.eINSTANCE.createDog();
	}
	
	public static Dog createDog(String name) {
		final Dog dog = createDog();
		dog.setName(name);
		return dog;
	}
	
	public static Dog createDog(DogBreed breed) {
		final Dog dog = createDog();
		dog.setBreed(breed);
		return dog;
	}
}
