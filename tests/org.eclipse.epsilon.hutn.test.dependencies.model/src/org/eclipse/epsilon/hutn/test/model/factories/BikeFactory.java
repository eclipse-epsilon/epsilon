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

import org.eclipse.epsilon.hutn.test.model.families.Bike;
import org.eclipse.epsilon.hutn.test.model.families.FamiliesFactory;
import org.eclipse.epsilon.hutn.test.model.families.Family;
import org.eclipse.epsilon.hutn.test.model.families.Person;

public abstract class BikeFactory {

	private BikeFactory() {}
	
	public static Bike createBike() {
		return FamiliesFactory.eINSTANCE.createBike();
	}
	
	public static Bike createBike(Person rider) {
		final Bike bike = createBike();
		
		bike.setRider(rider);
		
		return bike;
	}
	
	public static Bike createBike(Family owner) {
		final Bike bike = createBike();
		
		bike.setOwner(owner);
		
		return bike;
	}
}
