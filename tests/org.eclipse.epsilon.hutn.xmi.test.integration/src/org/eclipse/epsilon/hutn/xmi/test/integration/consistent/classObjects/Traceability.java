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

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.eclipse.epsilon.hutn.model.hutn.ClassObject;
import org.eclipse.epsilon.hutn.xmi.test.integration.HutnXmiBridgeIntegrationTest;
import org.junit.BeforeClass;
import org.junit.Test;

public class Traceability extends HutnXmiBridgeIntegrationTest {
	
	@BeforeClass
	public static void setup() throws IOException {
		integrationTestWithModelAsRoot("<contents xsi:type=\"families:Family\" xmi:id=\"_cpcsYBeGEd6OrYL62CiH8g\">" + "\n" +
		                               "	<members xmi:id=\"_kbnoMBeGEd6OrYL62CiH8g\" name=\"John\"/>" +            "\n" +
		                               "</contents>");
	}
	
	@Test
	public void modelIsOnLine2() {
		assertEquals(2, getModel().getLine());
	}
	
	@Test
	public void modelContentsIsOnLine3() {
		assertEquals(3, getFirstSlotOfModel().getLine());
	}
	
	@Test
	public void familyIsOnLine3() {
		assertEquals(3, getFamily().getLine());
	}
	
	@Test
	public void familyMembersIsOnLine4() {
		assertEquals(4, getFirstSlotOfFamily().getLine());		
	}
	
	@Test
	public void personNameIsOnLine4() {
		final ClassObject person = (ClassObject)getFirstSlotOfFamily().getValues().get(0);
		
		assertEquals(4, person.getSlots().get(0).getLine());		
	}
}
