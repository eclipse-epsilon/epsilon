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
package org.eclipse.epsilon.hutn.xmi.test.integration.inconsistent.slot.containment.feature.nonEmpty;

import static org.eclipse.epsilon.test.util.HutnTestUtil.slotTest;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.eclipse.epsilon.hutn.model.hutn.AttributeSlot;
import org.eclipse.epsilon.hutn.model.hutn.ContainmentSlot;
import org.eclipse.epsilon.hutn.xmi.test.integration.HutnXmiBridgeIntegrationTest;
import org.junit.BeforeClass;
import org.junit.Test;

public class MultiTypedWithManyValuedAttribute extends HutnXmiBridgeIntegrationTest {
	
	@BeforeClass
	public static void setup() throws IOException {
		integrationTestWithModelAsRoot("<elements xsi:type=\"families:Family\" xmi:id=\"_Ev2KMBhbEd6T2uYUGKXrWQ\">" +
		                    "	<address>10 Main Street</address>"                                       +
		                    "	<address>123 Heslington Road</address>"                                  +
		                    "</elements>");
	}
	
	@Test
	public void elementsSlot() {
		slotTest(getFirstSlotOfModel(), ContainmentSlot.class, "elements", "Family");
	}
	
	@Test
	public void familyHasOneSlot() {
		assertEquals(1, getFamily().getSlots().size());
	}
	
	@Test
	public void nameSlot() {
		slotTest(getFirstSlotOfFamily(), AttributeSlot.class, "address", "10 Main Street", "123 Heslington Road");
	}
}
