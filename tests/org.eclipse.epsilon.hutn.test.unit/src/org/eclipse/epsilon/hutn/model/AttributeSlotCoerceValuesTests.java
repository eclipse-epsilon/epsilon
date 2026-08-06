/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.hutn.model;

import static org.junit.Assert.*;
import static org.eclipse.epsilon.test.util.builders.emf.EAttributeBuilder.anEAttribute;
import static org.eclipse.epsilon.test.util.builders.emf.EClassBuilder.anEClass;
import static org.eclipse.epsilon.test.util.builders.emf.EPackageBuilder.aMetamodel;

import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.epsilon.hutn.model.hutn.AttributeSlot;
import org.eclipse.epsilon.hutn.model.hutn.ClassObject;
import org.eclipse.epsilon.hutn.model.hutn.HutnFactory;
import org.eclipse.epsilon.hutn.model.hutn.PackageObject;
import org.junit.Test;

public class AttributeSlotCoerceValuesTests {

	@Test
	public void shouldChangeADoubleToAFloat() throws Exception {
		final AttributeSlot slot = createSlot(EcorePackage.eINSTANCE.getEFloat());
		
		slot.getValues().add(2.5d);
		slot.coerceValues();
		
		assertEquals(2.5f, slot.getValues().get(0));
	}
	
	@Test
	public void shouldChangeAFloatToADouble() throws Exception {
		final AttributeSlot slot = createSlot(EcorePackage.eINSTANCE.getEDouble());

		slot.getValues().add(2.5f);
		slot.coerceValues();
		
		assertEquals(2.5d, slot.getValues().get(0));
	}
	
	@Test
	public void shouldChangeAllFloatsToDoubles() throws Exception {
		final AttributeSlot slot = createSlot(EcorePackage.eINSTANCE.getEDouble());
		
		slot.getValues().add(2.5f);
		slot.getValues().add(3.4d);
		slot.getValues().add(5.2f);
		slot.coerceValues();
		
		assertEquals(2.5d, slot.getValues().get(0));
		assertEquals(3.4d, slot.getValues().get(1));
		assertEquals(5.2d, slot.getValues().get(2));
	}
	
	@Test
	public void shouldChangeAnIntegerToALong() throws Exception {
		final AttributeSlot slot = createSlot(EcorePackage.eINSTANCE.getELong());

		slot.getValues().add(42);
		slot.coerceValues();
		
		assertEquals(42l, slot.getValues().get(0));
	}
	
	@Test
	public void shouldChangeALongToAnInteger() throws Exception {
		final AttributeSlot slot = createSlot(EcorePackage.eINSTANCE.getEInt());

		slot.getValues().add(42l);
		slot.coerceValues();
		
		assertEquals(42, slot.getValues().get(0));
	}
	
	@Test
	public void shouldChangeAShortToALong() throws Exception {
		final AttributeSlot slot = createSlot(EcorePackage.eINSTANCE.getELong());

		slot.getValues().add((short)42);
		slot.coerceValues();
		
		assertEquals(42l, slot.getValues().get(0));
	}
	
	@Test
	public void shouldNotCoerceValuesWhenSlotHasNoType() throws Exception {
		final AttributeSlot slot = createSlot(null);

		slot.getValues().add(26.5d);
		slot.getValues().add(42.5f);
		slot.coerceValues();
		
		assertEquals(26.5d, slot.getValues().get(0));
		assertEquals(42.5f, slot.getValues().get(1));
	}
	
	@Test
	public void shouldNotCoerceValuesWhenSlotHasFeatureNotDefinedInMetamodel() throws Exception {
		final PackageObject pkg = HutnFactory.eINSTANCE.createPackageObject();
		final ClassObject owner = HutnFactory.eINSTANCE.createClassObject();
		final AttributeSlot slot = HutnFactory.eINSTANCE.createAttributeSlot();
		
		pkg.getClassObjects().add(owner);
		owner.getSlots().add(slot);
		
		owner.setType("Stats");
		slot.setFeature("average");
		
		pkg.getMetamodel().add(aMetamodel()
		                       	.with(anEClass()
		                       			.named("Stats")
		                       	).build());

		slot.getValues().add(26.5d);
		slot.getValues().add(42.5f);
		slot.coerceValues();
		
		assertEquals(26.5d, slot.getValues().get(0));
		assertEquals(42.5f, slot.getValues().get(1));
	}
	
	
	private static AttributeSlot createSlot(EDataType type) {
		final PackageObject pkg = HutnFactory.eINSTANCE.createPackageObject();
		final ClassObject owner = HutnFactory.eINSTANCE.createClassObject();
		final AttributeSlot slot = HutnFactory.eINSTANCE.createAttributeSlot();
		
		pkg.getClassObjects().add(owner);
		owner.getSlots().add(slot);
		
		owner.setType("Stats");
		slot.setFeature("average");
		
		pkg.getMetamodel().add(aMetamodel()
		                       	.with(anEClass()
		                       			.named("Stats")
		                       			.with(anEAttribute()
		                       					.named("average")
		                       					.withType(type)
		                       			)
		                       	).build());
		return slot;
	}
}
