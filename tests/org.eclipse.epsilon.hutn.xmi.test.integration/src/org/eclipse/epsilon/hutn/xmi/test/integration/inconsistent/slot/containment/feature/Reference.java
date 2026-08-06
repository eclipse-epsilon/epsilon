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
package org.eclipse.epsilon.hutn.xmi.test.integration.inconsistent.slot.containment.feature;

import static org.eclipse.epsilon.test.util.HutnTestUtil.slotTest;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.eclipse.epsilon.hutn.model.hutn.ReferenceSlot;
import org.eclipse.epsilon.hutn.xmi.test.integration.HutnXmiBridgeIntegrationTest;
import org.junit.BeforeClass;
import org.junit.Test;

public class Reference extends HutnXmiBridgeIntegrationTest {
	
	@BeforeClass
	public static void setup() throws IOException {
		integrationTestWithModelAsRoot("<contents xsi:type=\"families:Family\" xmi:id=\"_cpcsYBeGEd6OrYL62CiH8g\" animals=\"_4yG-UBiFEd6-Eob-hyWntQ _HiVRYBiGEd6-Eob-hyWntQ\"/>" +
		                    "<contents xsi:type=\"families:Pet\" xmi:id=\"_4yG-UBiFEd6-Eob-hyWntQ\"/>" +
		                    "<contents xsi:type=\"families:Pet\" xmi:id=\"_HiVRYBiGEd6-Eob-hyWntQ\"/>");
	}
	
	@Test
	public void animalsSlot() {
		slotTest(getFirstSlotOfFamily(), ReferenceSlot.class, "animals", "Pet", "Pet"); //"_4yG-UBiFEd6-Eob-hyWntQ _HiVRYBiGEd6-Eob-hyWntQ");
	}
	
	@Test
	public void familyDoesNotContainOldAttributeSlot() {
		assertEquals(1, getFamily().getSlots().size());
	}
}
