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
package org.eclipse.epsilon.hutn.xmi.test.integration.consistent.slots;

import static org.eclipse.epsilon.test.util.HutnTestUtil.slotTest;
import static org.junit.Assert.assertEquals;

import org.eclipse.epsilon.hutn.model.hutn.AttributeSlot;
import org.eclipse.epsilon.hutn.model.hutn.ReferenceSlot;
import org.eclipse.epsilon.hutn.xmi.HutnXmiBridgeException;
import org.eclipse.epsilon.hutn.xmi.test.integration.HutnXmiBridgeIntegrationTest;
import org.junit.BeforeClass;
import org.junit.Test;

public class SeveralSlots extends HutnXmiBridgeIntegrationTest {
	
	@BeforeClass
	public static void setup() throws HutnXmiBridgeException {
		integrationTestWithModelAsRoot("<contents xsi:type=\"families:Family\" xmi:id=\"_seJr0BPDEd6ysY9kXe1lIA\" " + getAttributes() + ">" +
		                               getContents() +
		                               "</contents>" +
		                               "<contents xsi:type=\"families:Pet\" xmi:id=\"_JYTOgCT9Ed6m9JDbGM4gGg\" />" +
		                               "<contents xsi:type=\"families:Dog\" xmi:id=\"_HigxkCT9Ed6m9JDbGM4gGg\" />" +
		                               "<contents xsi:type=\"families:Pet\" xmi:id=\"_IgdnkCT9Ed6m9JDbGM4gGg\" />");
	}
	
	private static java.lang.String getAttributes() {
		return "pets=\"_JYTOgCT9Ed6m9JDbGM4gGg _HigxkCT9Ed6m9JDbGM4gGg _IgdnkCT9Ed6m9JDbGM4gGg\" " +
		       "name=\"The Smiths\" " +
		       "numberOfChildren=\"2\" " +
		       "nuclear=\"false\"";
	}
	
	private static java.lang.String getContents() {
		return "<address>10 Main Street</address>"      +      
		       "<lotteryNumbers>3</lotteryNumbers>"     +
		       "<address>123 Heslington Road</address>" +
		       "<lotteryNumbers>12</lotteryNumbers>"    +
		       "<lotteryNumbers>32</lotteryNumbers>";
	}

	@Test
	public void correctNumberOfSlots() {
		assertEquals(6, getFamily().getSlots().size());
	}
	
	@Test
	public void petsSlot() {
		slotTest(getFamily().findSlot("pets"), ReferenceSlot.class, "pets", "Pet", "Dog", "Pet");
	}
	
	@Test
	public void nameSlot() {
		slotTest(getFamily().findSlot("name"), AttributeSlot.class, "name", "The Smiths");
	}
	
	@Test
	public void numberOfChildrenSlot() {
		slotTest(getFamily().findSlot("numberOfChildren"), AttributeSlot.class, "numberOfChildren", 2);
	}
	
	@Test
	public void nuclearSot() {
		slotTest(getFamily().findSlot("nuclear"), AttributeSlot.class, "nuclear", false);
	}
	
	@Test
	public void addresSlot() {
		slotTest(getFamily().findSlot("address"), AttributeSlot.class, "address", "10 Main Street", "123 Heslington Road");
	}
		
	@Test
	public void lotteryNumbersSlot() {
		slotTest(getFamily().findSlot("lotteryNumbers"), AttributeSlot.class, "lotteryNumbers", 3, 12, 32);
	}
}
