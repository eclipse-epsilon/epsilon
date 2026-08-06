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
package org.eclipse.epsilon.emc.emf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class AbstractEmfModelTests {

	private static AbstractEmfModel emfModel;
	private static EPackage pkg;
	private static EClass cls;
	
	@BeforeClass
	public static void setup() {
		pkg = EcoreFactory.eINSTANCE.createEPackage();
		cls = EcoreFactory.eINSTANCE.createEClass();
	
		emfModel = new InMemoryEmfModel(EmfUtil.createResource(pkg));
	}
	
	@Test
	public void getTypeNameOf() {
		assertEquals("EPackage", emfModel.getTypeNameOf(pkg));
		assertEquals("EClass",   emfModel.getTypeNameOf(cls));
	}
	
	@Test
	public void getFullyQualifiedTypeNameOf() {
		assertEquals("ecore::EPackage", emfModel.getFullyQualifiedTypeNameOf(pkg));
		assertEquals("ecore::EClass",   emfModel.getFullyQualifiedTypeNameOf(cls));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void getTypeNameOfForPlainJavaObject() {
		emfModel.getTypeNameOf("foo");
	}
	
	@Test
	public void isInstantiable() {
		assertTrue(emfModel.isInstantiable("EClass"));
		assertFalse(emfModel.isInstantiable("EStructuralFeature"));
	}
}
