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
package org.eclipse.epsilon.hutn.xmi.test.integration.consistent.classObjects;

import static org.eclipse.epsilon.test.util.HutnTestUtil.slotTest;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.eclipse.epsilon.hutn.model.hutn.ContainmentSlot;
import org.eclipse.epsilon.hutn.xmi.test.integration.HutnXmiBridgeIntegrationTest;
import org.junit.BeforeClass;
import org.junit.Test;

public class NestedObjects extends HutnXmiBridgeIntegrationTest {
	
	@BeforeClass
	public static void setup() throws IOException {
		integrationTestWithModelAsRoot("<contents xsi:type=\"families:Family\" xmi:id=\"_cpcsYBeGEd6OrYL62CiH8g\">" +
		                               "	<members xmi:id=\"_kbnoMBeGEd6OrYL62CiH8g\" name=\"John\"/>"             +
		                               "</contents>");
	}
	
	@Test
	public void hasOneNsUri() {
		assertEquals(1, spec.getNsUris().size());
	}
	
	@Test
	public void hasFamiliesNsUri() {
		assertEquals("families", spec.getNsUris().get(0).getValue());
	}
	
	@Test
	public void hasOnePackageObject() {
		assertEquals(1, spec.getObjects().size());
	}
	
	@Test
	public void packageObjectContainsOneClassObject() {
		assertEquals(1, getPackageObject().getClassObjects().size());
	}
	
	@Test
	public void modelContainsOneSlot() {
		assertEquals(1, getModel().getSlots().size());
	}
	
	@Test
	public void modelContainsContainmentSlot() {
		slotTest(getFirstSlotOfModel(), ContainmentSlot.class, "contents", "Family");
	}
	
	@Test
	public void familyContainsOneSlot() {
		assertEquals(1, getFamily().getSlots().size());
	}
	
	@Test
	public void familyContainsContainmentSlot() {
		slotTest(getFirstSlotOfFamily(), ContainmentSlot.class, "members", "Person");		
	}
}
