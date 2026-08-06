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

import static org.eclipse.epsilon.test.util.builders.emf.EAttributeBuilder.MANY;
import static org.eclipse.epsilon.test.util.builders.emf.EAttributeBuilder.anEAttribute;
import static org.eclipse.epsilon.test.util.builders.emf.EClassBuilder.anEClass;
import static org.eclipse.epsilon.test.util.builders.emf.EPackageBuilder.aMetamodel;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.epsilon.eol.exceptions.EolIllegalPropertyException;
import org.eclipse.epsilon.test.util.builders.emf.EClassBuilder;
import org.eclipse.epsilon.test.util.builders.emf.EPackageBuilder;
import org.junit.Before;
import org.junit.Test;

public class EmfPropertySetterCoerceTests {
	
	private static EPackageBuilder metamodelBuilder;
	
	@Before
	public void setup() {
		metamodelBuilder = aMetamodel();
	}
		
	@Test
	public void singleValueShouldNotBeChangedWhenPropertyIsSingleValued() throws EolIllegalPropertyException {
		final EStructuralFeature feature = anEAttribute()
		                                   	.named("name")
		                                   	.withBounds(0, 1)
		                                   	.withType(EcorePackage.eINSTANCE.getEString())
		                                   .build();
		
		assertEquals("foo", coerce(feature, "foo"));
	}
	
	@Test
	public void singleValueShouldBecomeCollectionWhenPropertyIsMany() throws EolIllegalPropertyException {
		final EStructuralFeature feature = anEAttribute()
		                                   	.named("names")
		                                   	.withBounds(0, MANY)
		                                   	.withType(EcorePackage.eINSTANCE.getEString())
		                                   .build();
		
		assertEquals(Arrays.asList("foo"),
		            coerce(feature, ("foo")));
	}
	
	@Test
	public void collectionValueShouldNotBeChanged() throws EolIllegalPropertyException {
		final EStructuralFeature feature = anEAttribute()
		                                   	.named("age")
		                                   	.withBounds(0, MANY)
		                                   	.withType(EcorePackage.eINSTANCE.getEInt())
		                                   .build();
		
		assertEquals(Arrays.asList(14, 6),
		            coerce(feature, Arrays.asList(14, 6)));
	}
	
	
	// Illegal property values
	
	@Test(expected=EolIllegalPropertyException.class)
	public void illegalObject() throws EolIllegalPropertyException {
		new EmfPropertySetter().coerce("foo", "names", "foo", null);
	}
	
	@Test(expected=EolIllegalPropertyException.class)
	public void illegalProperty() throws EolIllegalPropertyException {		
		final EObject objectWithNoSlots = instantiateClass();
		new EmfPropertySetter().coerce(objectWithNoSlots, "names", "foo", null);
	}

	
	private static Object coerce(EStructuralFeature feature, Object value) throws EolIllegalPropertyException {
		return new EmfPropertySetter().coerce(instantiateClass(feature), feature.getName(), value, null);
	}

	private static EObject instantiateClass(EStructuralFeature... features) {
		final EClassBuilder clsBuilder = anEClass();
		for (EStructuralFeature feature : features) {
			clsBuilder.with(feature);
		}
		return instantiateClass(clsBuilder.build());
	}

	private static EObject instantiateClass(EClass cls) {
		return metamodelBuilder.with(cls).build().getEFactoryInstance().create(cls);
	}
}
