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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;


public class EClassBuilder implements EClassifierBuilder {
	
	public static EClassBuilder anEClass() {
		return new EClassBuilder();
	}
	
	public static EClassBuilder anAbstractEClass() {
		return anEClass().isAbstract();
	}

	private final EClass eClass = EcoreFactory.eINSTANCE.createEClass();
	
	public EClassBuilder named(String name) {
		eClass.setName(name);
		return this;
	}
	
	public EClassBuilder isAbstract() {
		eClass.setAbstract(true);
		return this;
	}
	
	public EClassBuilder with(EStructuralFeature feature) {
		eClass.getEStructuralFeatures().add(feature);
		return this;
	}
	
	public EClassBuilder with(EAttributeBuilder eAttributeBuilder) {
		eClass.getEStructuralFeatures().add(eAttributeBuilder.build());
		return this;
	}
	
	public EClassBuilder with(EReferenceBuilder eReferenceBuilder) {
		eClass.getEStructuralFeatures().add(eReferenceBuilder.build());
		return this;
	}

	public EClassBuilder subclasses(EClass superclass) {
		eClass.getESuperTypes().add(superclass);
		return this;
	}
	
	public EClass build() {
		return eClass;
	}
}
