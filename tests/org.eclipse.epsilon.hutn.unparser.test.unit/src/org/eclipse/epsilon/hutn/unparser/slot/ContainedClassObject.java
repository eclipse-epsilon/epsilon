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

import static org.eclipse.epsilon.hutn.test.util.HutnUtil.createAttributeSlot;
import static org.eclipse.epsilon.hutn.test.util.HutnUtil.createClassObject;

import org.eclipse.epsilon.hutn.model.hutn.ClassObject;
import org.eclipse.epsilon.hutn.unparser.internal.AbstractSlotUnparserTest;
import org.junit.BeforeClass;

public class ContainedClassObject extends AbstractSlotUnparserTest {
	
	@BeforeClass
	public static void setup() {
		containmentSlotTest("members",
		                    new ClassObject[] {createClassObject("John",
		                                                         "Person",
		                                                         createAttributeSlot("name", "John"))},
		                    new String[] {"Person \"John\" {name: \"John\"\n}"});
	}
}
