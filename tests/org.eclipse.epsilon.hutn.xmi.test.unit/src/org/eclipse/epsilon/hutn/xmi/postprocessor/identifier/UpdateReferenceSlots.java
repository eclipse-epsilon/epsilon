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
package org.eclipse.epsilon.hutn.xmi.postprocessor.identifier;

import static org.eclipse.epsilon.hutn.test.util.HutnUtil.*;
import static org.junit.Assert.*;

import org.eclipse.epsilon.hutn.model.hutn.ClassObject;
import org.eclipse.epsilon.hutn.model.hutn.ReferenceSlot;
import org.eclipse.epsilon.hutn.model.hutn.Spec;
import org.eclipse.epsilon.hutn.xmi.postprocessor.IdentifierPostProcessor;
import org.junit.Test;

public class UpdateReferenceSlots {

	@Test
	public void multiValuedSlot() {
		final ClassObject person1 = createClassObject("_fauxId1", "Person");
		final ClassObject family1 = createClassObject("_fauxId2", "Family");
		final ClassObject person2 = createClassObject("_fauxId3", "Person");
		
		final ReferenceSlot slot = createReferenceSlot("elements", "_fauxId1", "_fauxId2", "_fauxId3");
		final ClassObject referencer = createClassObject("Referencer", slot);
		
		final Spec spec = createSpec(createPackageObject(person1, family1, person2, referencer));
		
		new IdentifierPostProcessor(spec).process();
		
		assertEquals("Person1", slot.getValues().get(0));
		assertEquals("Family1", slot.getValues().get(1));
		assertEquals("Person2", slot.getValues().get(2));
	}
	
	@Test
	public void nonExistentReferenceValue() {
		final ReferenceSlot slot = createReferenceSlot("elements", "_unusedId");
		final ClassObject referencer = createClassObject("Referencer", slot);
		
		final Spec spec = createSpec(createPackageObject(referencer));
		
		new IdentifierPostProcessor(spec).process();
		
		assertEquals("_unusedId", slot.getValues().get(0));
	}
}
