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
package org.eclipse.epsilon.hutn.xmi.test.integration.inconsistent.slot;

import static org.eclipse.epsilon.test.util.HutnTestUtil.slotTest;

import java.io.IOException;

import org.eclipse.epsilon.hutn.model.hutn.AttributeSlot;
import org.eclipse.epsilon.hutn.model.hutn.ContainmentSlot;
import org.eclipse.epsilon.hutn.xmi.test.integration.HutnXmiBridgeIntegrationTest;
import org.junit.BeforeClass;
import org.junit.Test;

public class SeveralSlotsWithNoFeature extends HutnXmiBridgeIntegrationTest {
	
	@BeforeClass
	public static void setup() throws IOException {
		integrationTestWithModelAsRoot("name=\"m\" age=\"12\"",
				
		                    "<elements xsi:type=\"families:Family\" xmi:id=\"_Ev2KMBhbEd6T2uYUGKXrWQ\"/>" +
		                    "<elements xsi:type=\"families:Person\" xmi:id=\"_cMvCsBhsEd6T2uYUGKXrWQ\"/>" +
		                    "<elements2 xsi:type=\"families:Dog\" xmi:id=\"_qH5NkBk-Ed6-Eob-hyWntQ\"/>"   +
		                    "<telNo>01904 440044</telNo>");
	}
	
	@Test
	public void nameSlot() {
		slotTest(getModel().findSlot("name"), AttributeSlot.class, "name", "m");
	}
	
	@Test
	public void ageSlot() {
		slotTest(getModel().findSlot("age"), AttributeSlot.class, "age", 12);
	}
	
	@Test
	public void elementsSlot() {
		slotTest(getModel().findSlot("elements"), ContainmentSlot.class, "elements", "Family", "Person");
	}
	
	@Test
	public void elements2Slot() {
		slotTest(getModel().findSlot("elements2"), ContainmentSlot.class, "elements2", "Dog");
	}
	
	@Test
	public void telNoSlot() {
		slotTest(getModel().findSlot("telNo"), AttributeSlot.class, "telNo", "01904 440044");
	}
}
