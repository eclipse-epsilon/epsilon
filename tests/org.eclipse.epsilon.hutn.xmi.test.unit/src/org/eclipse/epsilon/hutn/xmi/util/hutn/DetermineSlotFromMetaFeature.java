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

import static org.eclipse.epsilon.test.util.HutnTestUtil.slotTest;
import static org.junit.Assert.assertNull;

import org.eclipse.epsilon.hutn.model.hutn.AttributeSlot;
import org.eclipse.epsilon.hutn.model.hutn.ContainmentSlot;
import org.eclipse.epsilon.hutn.model.hutn.ReferenceSlot;
import org.eclipse.epsilon.hutn.model.hutn.Slot;
import org.eclipse.epsilon.hutn.test.model.families.FamiliesPackage;
import org.eclipse.epsilon.hutn.xmi.util.HutnUtil;
import org.junit.Test;

public class DetermineSlotFromMetaFeature {

	public static void determineSlotTest(String featureName, Class<? extends Slot<?>> expectedTypeOfSlot) {
		final Slot<?> slot = HutnUtil.determineSlotFromMetaFeature(new ClassObjectStub(FamiliesPackage.eINSTANCE.getFamily()), featureName);
		
		if (expectedTypeOfSlot == null) {
			assertNull(slot);
		} else {
			slotTest(slot, expectedTypeOfSlot, featureName);
		}
	}
	
	@Test
	public void determineAttributeSlot() {
		determineSlotTest("name", AttributeSlot.class);
	}
	
	@Test
	public void determineReferenceSlot() {
		determineSlotTest("pets", ReferenceSlot.class);
	}
	
	@Test
	public void determineContainmentSlot() {
		determineSlotTest("members", ContainmentSlot.class);
	}
	
	@Test
	public void determineUndefinedFeature() {
		determineSlotTest("foo", null);
	}
}
