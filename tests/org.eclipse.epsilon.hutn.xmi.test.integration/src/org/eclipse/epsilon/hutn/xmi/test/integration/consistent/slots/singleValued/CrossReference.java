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
package org.eclipse.epsilon.hutn.xmi.test.integration.consistent.slots.singleValued;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.eclipse.epsilon.hutn.model.hutn.ReferenceSlot;
import org.eclipse.epsilon.hutn.xmi.test.integration.HutnXmiBridgeIntegrationTest;
import org.junit.BeforeClass;
import org.junit.Test;


public class CrossReference extends HutnXmiBridgeIntegrationTest {
	
	@BeforeClass
	public static void setup() {
		integrationTestWithModelAsRoot("<contents xsi:type=\"families:Family\">" +
		                               "  <pets href=\"target.model#_YOkQMPoJEd63-uYAr1MMkA\"/>" +
		                               "</contents>");
	}
	
	@Test
	public void firstSlotShouldBeACrossReference() {
		assertTrue(getFirstSlotOfFamily() instanceof ReferenceSlot);
		
		assertEquals("pets",                                                getFirstSlotOfFamily().getFeature());
		assertEquals(Arrays.asList("target.model#_YOkQMPoJEd63-uYAr1MMkA"), getFirstSlotOfFamily().getValues());
	}
	
	@Test
	public void familyShouldHaveNoOtherSlots() {
		assertEquals(1, getFamily().getSlots().size());
	}
}
