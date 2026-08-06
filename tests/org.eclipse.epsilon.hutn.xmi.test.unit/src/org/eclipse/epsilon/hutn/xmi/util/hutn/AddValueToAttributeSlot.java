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
package org.eclipse.epsilon.hutn.xmi.util.hutn;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.epsilon.hutn.model.hutn.AttributeSlot;
import org.eclipse.epsilon.hutn.model.hutn.HutnFactory;
import org.eclipse.epsilon.hutn.model.hutn.impl.AttributeSlotImpl;
import org.eclipse.epsilon.hutn.test.model.families.FamiliesPackage;
import org.eclipse.epsilon.hutn.xmi.util.HutnUtil;
import org.junit.Test;

public class AddValueToAttributeSlot {
	
	@Test
	public void addValueToAttributeSlot() {
		final AttributeSlot slot = new AttributeSlotStub(FamiliesPackage.eINSTANCE.getNamedElement_Name());
		
		HutnUtil.addValueToSlot(slot, "John");
		
		assertEquals("John", slot.getValues().get(0));
	}
	
	@Test
	public void addDifferentValueToAttributeSlot() {
		final AttributeSlot slot = new AttributeSlotStub(FamiliesPackage.eINSTANCE.getNamedElement_Name());
		
		HutnUtil.addValueToSlot(slot, "Jill");
		
		assertEquals("Jill", slot.getValues().get(0));
	}
	
	@Test
	public void addNumericValueToAttributeSlot() {
		final AttributeSlot slot = new AttributeSlotStub(FamiliesPackage.eINSTANCE.getFamily_AverageAge());
		
		HutnUtil.addValueToSlot(slot, "23.6");
		
		assertEquals(23.6f, slot.getValues().get(0));
	}
	
	@Test
	public void addFloatValueToSlotWithUndefinedFeature() {
		final AttributeSlot slot = HutnFactory.eINSTANCE.createAttributeSlot();
		
		HutnUtil.addValueToSlot(slot, "23.6");
		
		assertEquals("23.6", slot.getValues().get(0));
	}
	
	@Test
	public void addStringValueToFloatTypedFeature() {
		final AttributeSlot slot = new AttributeSlotStub(FamiliesPackage.eINSTANCE.getFamily_AverageAge());
		
		HutnUtil.addValueToSlot(slot, "twenty-three point six");
		
		assertEquals("twenty-three point six", slot.getValues().get(0));
	}
	
	@Test
	public void addStringValueToIntegerTypedFeature() {
		final AttributeSlot slot = new AttributeSlotStub(FamiliesPackage.eINSTANCE.getFamily_NumberOfChildren());
		
		HutnUtil.addValueToSlot(slot, "four");
		
		assertEquals("four", slot.getValues().get(0));
	}
	
	@Test
	public void addStringValueToBooleanTypedFeature() {
		final AttributeSlot slot = new AttributeSlotStub(FamiliesPackage.eINSTANCE.getFamily_Nuclear());
		
		HutnUtil.addValueToSlot(slot, "true");
		HutnUtil.addValueToSlot(slot, "false");
		HutnUtil.addValueToSlot(slot, "neither");
		HutnUtil.addValueToSlot(slot, "another");

		
		assertEquals(true, slot.getValues().get(0));
		assertEquals(false, slot.getValues().get(1));
		assertEquals("neither", slot.getValues().get(2));
		assertEquals("another", slot.getValues().get(3));
	}
	
	private static class AttributeSlotStub extends AttributeSlotImpl {

		private final EStructuralFeature feature;
		
		public AttributeSlotStub(EStructuralFeature feature) {
			this.feature = feature;
		}
		
		@Override
		public String getFeature() {
			return feature.getName();
		}

		@Override
		public boolean hasEStructuralFeature() {
			return true;
		}
		
		@Override
		public EStructuralFeature getEStructuralFeature() {
			return feature;
		}
	}
}
