/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.test.util.builders.emf;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;

public class EReferenceBuilder {
	
	public static int MANY = -1;
	
	public static EReferenceBuilder anEReference() {
		return new EReferenceBuilder();
	}

	private final EReference reference = EcoreFactory.eINSTANCE.createEReference(); 
	
	public EReferenceBuilder named(String name) {
		reference.setName(name);
		return this;
	}

	public EReferenceBuilder references(int lowerbound, int upperbound, EClassifier type) {
		reference.setLowerBound(lowerbound);
		reference.setUpperBound(upperbound);
		reference.setEType(type);
		return this;
	}
	
	public EReferenceBuilder contains(int lowerbound, int upperbound, EClassifier type) {
		references(lowerbound, upperbound, type);
		reference.setContainment(true);
		return this;
	}
	
	public EReferenceBuilder withType(EClassifier type) {
		reference.setEType(type);
		return this;
	}
	
	public EReference build() {
		return reference;
	}
}
