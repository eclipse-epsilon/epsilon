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
package org.eclipse.epsilon.hutn.xmi.test.integration.consistent.slots.multiValued;

import static org.eclipse.epsilon.test.util.HutnTestUtil.slotTest;
import static org.junit.Assert.assertEquals;

import org.eclipse.epsilon.hutn.model.hutn.ReferenceSlot;
import org.eclipse.epsilon.hutn.xmi.HutnXmiBridgeException;
import org.eclipse.epsilon.hutn.xmi.test.integration.HutnXmiBridgeIntegrationTest;
import org.junit.BeforeClass;
import org.junit.Test;


public class MixedUUIDAndFragmentReferenceValues extends HutnXmiBridgeIntegrationTest {

	@BeforeClass
	public static void setup() throws HutnXmiBridgeException {
		integrationTestWithModelAsRoot("<contents xsi:type=\"families:Family\" xmi:id=\"_HFEnoCT9Ed6m9JDbGM4gGg\" pets=\"#//@contents.1 _IgdnkCT9Ed6m9JDbGM4gGg #//@contents.3\"/>" +
		                               "<contents xsi:type=\"families:Pet\"/>" +
		                               "<contents xsi:type=\"families:Pet\" xmi:id=\"_IgdnkCT9Ed6m9JDbGM4gGg\"/>" +
		                               "<contents xsi:type=\"families:Dog\"/>");
	}
	
	@Test
	public void hasOneSlot() {
		assertEquals(1, getFamily().getSlots().size());
	}
	
	@Test
	public void testSlot() {
		slotTest(getFirstSlotOfFamily(), ReferenceSlot.class, "pets", "Pet", "Pet", "Dog");
	}
}
