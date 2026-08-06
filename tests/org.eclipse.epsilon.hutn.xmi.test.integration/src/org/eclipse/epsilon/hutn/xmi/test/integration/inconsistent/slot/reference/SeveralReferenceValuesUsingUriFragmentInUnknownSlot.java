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
import java.io.IOException;

import org.eclipse.epsilon.hutn.model.hutn.ReferenceSlot;
import org.eclipse.epsilon.hutn.xmi.test.integration.HutnXmiBridgeIntegrationTest;
import org.junit.BeforeClass;
import org.junit.Test;

public class SeveralReferenceValuesUsingUriFragmentInUnknownSlot extends HutnXmiBridgeIntegrationTest {
	
	@BeforeClass
	public static void setup() throws IOException {
		integrationTestWithModelAsRoot("<contents xsi:type=\"families:Family\" animals=\"#//@contents.1 #//@contents.2 #//@contents.3\"/>" + 
		                               "<contents xsi:type=\"families:Pet\"/>" + 
		                               "<contents xsi:type=\"families:Pet\"/>" +
		                               "<contents xsi:type=\"families:Dog\"/>");
	}
	
	@Test
	public void petsSlot() {
		slotTest(getFirstSlotOfFamily(), ReferenceSlot.class, "animals", "Pet", "Pet", "Dog");
	}
}
