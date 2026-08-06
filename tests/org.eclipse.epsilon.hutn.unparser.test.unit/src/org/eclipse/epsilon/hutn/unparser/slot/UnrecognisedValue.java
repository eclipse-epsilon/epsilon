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

import java.io.File;

import org.eclipse.epsilon.hutn.model.hutn.AttributeSlot;
import org.eclipse.epsilon.hutn.model.hutn.HutnFactory;
import org.eclipse.epsilon.hutn.unparser.internal.AbstractSlotUnparserTest;
import org.junit.Test;

public class UnrecognisedValue {
	
	@Test(expected=IllegalStateException.class)
	public void setup() {
		final AttributeSlot slot = HutnFactory.eINSTANCE.createAttributeSlot();
		slot.setFeature("unrecognised");
		slot.getValues().add(new File("unrecognised.txt")); // java.io.File is not a HUTN type
		
		AbstractSlotUnparserTest.slotTest(slot);
	}
}
