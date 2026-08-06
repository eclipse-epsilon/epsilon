/*******************************************************************************
 * Copyright (c) 2009 The University of York.
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
/**
 * 
 */
package org.eclipse.epsilon.test.util.builders.emf;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EcoreFactory;

public class EAttributeBuilder {
	
	public static int MANY = -1;
	
	public static EAttributeBuilder anEAttribute() {
		return new EAttributeBuilder();
	}

	private final EAttribute attribute = EcoreFactory.eINSTANCE.createEAttribute(); 
	
	public EAttributeBuilder named(String name) {
		attribute.setName(name);
		return this;
	}

	public EAttributeBuilder withType(EClassifier type) {
		attribute.setEType(type);
		return this;
	}
	
	public EAttribute build() {
		return attribute;
	}

	public EAttributeBuilder withBounds(int lower, int upper) {
		attribute.setLowerBound(lower);
		attribute.setUpperBound(upper);
		return this;
	}
}
