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


public class ReferenceUsingFragmentWithTwoTopLevelObjects extends HutnXmiBridgeIntegrationTest {
	
	@BeforeClass
	public static void setup() {
		integrationTest("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"                                                      +
		                "<xmi:XMI xmi:version=\"2.0\""                                                                    +
		                "	xmlns:xmi=\"http://www.omg.org/XMI\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" +
		                "	xmlns:families=\"families\">"                                                                 +
		                "		<families:Model>"                                                                         +
		                "			<contents xsi:type=\"families:Family\">"                                              +
		                "				<members friends=\"#/1/@contents.0/@members.0\"/>"                                +
		                "			</contents>"                                                                          +
		                "		</families:Model>"                                                                        +
		                "		<families:Model>"                                                                         +
		                "			<contents xsi:type=\"families:Family\">"                                              +
		                "				<members/>"                                                                       +
		                "			</contents>"                                                                          +
		                "		</families:Model>"                                                                        +
		                "</xmi:XMI>");
	}
	

	
	@Test
	public void testSlot() {
		slotTest(getFirstSlotOfFirstMember(), ReferenceSlot.class, "friends", "Person");
	}
	
	private static Slot<?> getFirstSlotOfFirstMember() {
		return getFirstMember().getSlots().get(0);
	}
	
	private static ClassObject getFirstMember() {
		return (ClassObject)getFamily().getSlots().get(0).getValues().get(0);
	}
}
