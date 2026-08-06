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
package org.eclipse.epsilon.hutn.xmi.test.integration.inconsistent.object;

import static org.eclipse.epsilon.test.util.HutnTestUtil.slotTest;

import java.net.URISyntaxException;

import org.eclipse.epsilon.hutn.model.hutn.AttributeSlot;
import org.eclipse.epsilon.hutn.model.hutn.ContainmentSlot;
import org.eclipse.epsilon.hutn.xmi.test.integration.HutnXmiBridgeIntegrationTest;
import org.junit.BeforeClass;
import org.junit.Test;

public class AbstractClass extends HutnXmiBridgeIntegrationTest {
	
	@BeforeClass
	public static void setup() throws URISyntaxException {
		integrationTestWithModelAsRoot("<contents xsi:type=\"families:NamedElement\" xmi:id=\"_KJh0YBhKEd6d_-caKAfnUw\" name=\"element\"/>");
	}
	
	@Test
	public void contentsSlot() {
		slotTest(getFirstSlotOfModel(), ContainmentSlot.class, "contents", "NamedElement");
	}
	
	@Test
	public void nameSlot() {
		slotTest(getFirstSlotOfFamily(), AttributeSlot.class, "name", "element");
	}
}
