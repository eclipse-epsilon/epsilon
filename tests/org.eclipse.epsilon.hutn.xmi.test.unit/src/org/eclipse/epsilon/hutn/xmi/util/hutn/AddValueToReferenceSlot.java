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
import org.eclipse.epsilon.hutn.model.hutn.ReferenceSlot;
import org.eclipse.epsilon.hutn.model.hutn.impl.ReferenceSlotImpl;
import org.eclipse.epsilon.hutn.test.model.families.FamiliesPackage;
import org.eclipse.epsilon.hutn.xmi.util.HutnUtil;
import org.junit.Test;

public class AddValueToReferenceSlot {
	
	@Test
	public void addValueToReferenceSlot() {
		final ReferenceSlot slot = new ReferenceSlotStub(FamiliesPackage.eINSTANCE.getFamily_Pets());
		
		HutnUtil.addValueToSlot(slot, "fido");
		
		assertEquals(1, slot.getValues().size());
		assertEquals("fido", slot.getValues().get(0));
	}
	
	@Test
	public void addMultipleValuesToReferenceSlot() {
		final ReferenceSlot slot = new ReferenceSlotStub(FamiliesPackage.eINSTANCE.getFamily_Pets());
		
		HutnUtil.addValueToSlot(slot, "fido lassie goldy");
		
		assertEquals(3, slot.getValues().size());
		
		assertEquals("fido",   slot.getValues().get(0));
		assertEquals("lassie", slot.getValues().get(1));
		assertEquals("goldy",  slot.getValues().get(2));
	}

	
	private static class ReferenceSlotStub extends ReferenceSlotImpl {

		private final EStructuralFeature feature;
		
		public ReferenceSlotStub(EStructuralFeature feature) {
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
