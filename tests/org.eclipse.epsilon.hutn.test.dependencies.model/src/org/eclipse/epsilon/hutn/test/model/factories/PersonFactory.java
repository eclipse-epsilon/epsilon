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
import org.eclipse.epsilon.hutn.test.model.families.Person;

public abstract class PersonFactory {

	private PersonFactory() {}
	
	public static Person createPerson() {
		return createPerson(null);
	}
	
	public static Person createPerson(String name) {
		final Person person = FamiliesFactory.eINSTANCE.createPerson();
		person.setName(name);
		return person;
	}
}
