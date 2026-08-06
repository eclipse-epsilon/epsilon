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
package org.eclipse.epsilon.hutn.xmi.test.integration.inconsistent.slot.attribute.feature;

import static org.eclipse.epsilon.test.util.HutnTestUtil.slotTest;

import java.io.IOException;

import org.eclipse.epsilon.hutn.model.hutn.AttributeSlot;
import org.eclipse.epsilon.hutn.xmi.test.integration.HutnXmiBridgeIntegrationTest;
import org.junit.BeforeClass;
import org.junit.Test;

public class TwoMultiValuedSlots extends HutnXmiBridgeIntegrationTest {
	
	@BeforeClass
	public static void setup() throws IOException {
		integrationTestWithModelAsRoot("<contents xsi:type=\"families:Family\" xmi:id=\"_Ev2KMBhbEd6T2uYUGKXrWQ\">" +
		                    "	<insurance>665544</insurance>" +
		                    "	<insurance>889900</insurance>" +
		                    "	<telNo>01904 440044</telNo>" +
		                    "</contents>");
	}
	

	@Test
	public void insuranceSlot() {
		slotTest(getFamily().findSlot("insurance"), AttributeSlot.class, "insurance", 665544, 889900);
	}
	
	@Test
	public void telNoSlot() {
		slotTest(getFamily().findSlot("telNo"), AttributeSlot.class, "telNo", "01904 440044");
	}
}
