/*******************************************************************************
 * Copyright (c) 2009 The University of York.
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
package org.eclipse.epsilon.hutn.xmi.test.integration.inconsistent.slot.reference;

import static org.eclipse.epsilon.test.util.HutnTestUtil.slotTest;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.eclipse.epsilon.hutn.model.hutn.ReferenceSlot;
import org.eclipse.epsilon.hutn.xmi.test.integration.HutnXmiBridgeIntegrationTest;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReferenceValueInUnknownSlot extends HutnXmiBridgeIntegrationTest {
	
	@BeforeClass
	public static void setup() throws IOException {
		integrationTestWithModelAsRoot("<contents xsi:type=\"families:Family\" xmi:id=\"_Qy0uEBPgEd6ysY9kXe1lIA\" animals=\"_OI43YBPgEd6ysY9kXe1lIA\"/>" +
		                               "<contents xsi:type=\"families:Pet\" xmi:id=\"_OI43YBPgEd6ysY9kXe1lIA\" />");
	}
	
	@Test
	public void petsSlot() {
		slotTest(getFirstSlotOfFamily(), ReferenceSlot.class, "animals", "Pet");
	}
	
	@Test
	public void hasCorrectIdentifier() {
		assertEquals("Pet1", getFirstSlotOfFamily().getValues().get(0));
	}
}
