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
package org.eclipse.epsilon.hutn.unparser.slot;

import org.eclipse.epsilon.hutn.model.hutn.ClassObject;
import org.eclipse.epsilon.hutn.model.hutn.HutnFactory;
import org.eclipse.epsilon.hutn.unparser.internal.AbstractSlotUnparserTest;
import org.junit.BeforeClass;

public class ReferencedClassObject extends AbstractSlotUnparserTest {
	
	@BeforeClass
	public static void setup() {
		final ClassObject co = HutnFactory.eINSTANCE.createClassObject();
		co.setType("Person");
		co.setIdentifier("John");
		
		referenceSlotTest("familyFriends",
		                  new ClassObject[] {co},
		                  new String[] {"Person \"John\""});
	}
}
