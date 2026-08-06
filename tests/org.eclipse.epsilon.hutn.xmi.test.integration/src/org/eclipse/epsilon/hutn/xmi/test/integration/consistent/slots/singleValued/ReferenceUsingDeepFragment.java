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

import static org.eclipse.epsilon.test.util.HutnTestUtil.slotTest;

import org.eclipse.epsilon.hutn.model.hutn.ClassObject;
import org.eclipse.epsilon.hutn.model.hutn.ReferenceSlot;
import org.eclipse.epsilon.hutn.model.hutn.Slot;
import org.eclipse.epsilon.hutn.xmi.test.integration.HutnXmiBridgeIntegrationTest;
import org.junit.BeforeClass;
import org.junit.Test;


public class ReferenceUsingDeepFragment extends HutnXmiBridgeIntegrationTest {
	
	@BeforeClass
	public static void setup() {
		integrationTestWithModelAsRoot("<contents xsi:type=\"families:Family\">"              +
		                               "	<members/>"                                       +
		                               "</contents>"                                          +
		                               "<contents xsi:type=\"families:Family\">"              +
		                               "	<members friends=\"#//@contents.0/@members.0\"/>" +
		                               "</contents>");
	}
	
	@Test
	public void testSlot() {
		slotTest(getFirstSlotOfFirstMemberOfSecondFamily(), ReferenceSlot.class, "friends", "Person");
	}
	
	private static Slot<?> getFirstSlotOfFirstMemberOfSecondFamily() {
		return getFirstMemberOfSecondFamily().getSlots().get(0);
	}
	
	private static ClassObject getFirstMemberOfSecondFamily() {
		return (ClassObject)getSecondFamily().getSlots().get(0).getValues().get(0);
	}
	
	private static ClassObject getSecondFamily() {
		return getFirstSlotOfModel().getValues().get(1);
	}
}
