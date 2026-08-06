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

import org.eclipse.epsilon.hutn.model.hutn.ClassObject;
import org.eclipse.epsilon.hutn.xmi.test.integration.HutnXmiBridgeIntegrationTest;
import org.junit.BeforeClass;
import org.junit.Test;


public class TwoTopLevelObjects extends HutnXmiBridgeIntegrationTest {
	
	@BeforeClass
	public static void setup() {
		integrationTest("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"                                                      +
		                "<xmi:XMI xmi:version=\"2.0\""                                                                    +
		                "	xmlns:xmi=\"http://www.omg.org/XMI\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" +
		                "	xmlns:families=\"families\">"                                                                 +
		                "		<families:Family/>"                                                                       +
		                "		<families:Pet/>"                                                                          +
		                "</xmi:XMI>");
	}
	

	@Test
	public void packageObjectContainsTwoClassObjects() {
		assertEquals(2, getPackageObject().getClassObjects().size());
	}
	
	@Test
	public void firstClassObjectIsFamily() {
		assertEquals("Family", getClassObject(0).getType());
	}
	
	@Test
	public void secondClassObjectIsPet() {
		assertEquals("Pet", getClassObject(1).getType());
	}
	
	private static ClassObject getClassObject(int index) {
		return getPackageObject().getClassObjects().get(index);
	}
}
