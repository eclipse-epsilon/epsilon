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
package org.eclipse.epsilon.test.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.epsilon.hutn.model.hutn.ClassObject;
import org.eclipse.epsilon.hutn.model.hutn.ClassObjectSlot;
import org.eclipse.epsilon.hutn.model.hutn.Slot;

public abstract class HutnTestUtil {
	
	private HutnTestUtil() {}
	
	public static void slotTest(Slot<?> slot, Class<? extends Slot<?>> expectedType, String expectedFeature, Object... expectedValues) {
		assertTrue("Expected slot to be an " + expectedType.getSimpleName() + " but was: " + slot.getClass().getSimpleName(),
		           expectedType.isInstance(slot));
		assertEquals(expectedFeature, slot.getFeature());
		assertEquals(expectedValues.length, slot.getValues().size());
		
		
		if (slot instanceof ClassObjectSlot<?>) {
			assertEquals(expectedValues.length, ((ClassObjectSlot<?>)slot).getClassObjects().size());
			
			// compare types of class objects
			for (int index = 0; index < expectedValues.length; index++) {
				final ClassObject value     = ((ClassObjectSlot<?>)slot).getClassObjects().get(index);
				final Object expectedValue = expectedValues[index];
				
				if (value == null) {
					assertEquals(expectedValue, null);
				} else {
					assertEquals(expectedValue, value.getType());
				}
			}
		
		
		} else {
			// compare values
			for (int index = 0; index < slot.getValues().size(); index++) {
				final Object value         = slot.getValues().get(index);
				final Object expectedValue = expectedValues[index];
	
				if (expectedValue instanceof EnumValue) {
					final EnumValue enumValue = (EnumValue)expectedValue;
					
					assertTrue("Expected an instance of EEnumLiteral but got: " + enumValue.getValue() + " [" + enumValue.getValue().getClass().getCanonicalName() + "]",
				               value instanceof EEnumLiteral);
					
					assertEquals(enumValue.getValue(), ((EEnumLiteral)value).getLiteral());
				
				} else {
					assertTrue("Expected an instance of " + expectedValue.getClass().getCanonicalName() + " but got: " + value + " [" + value.getClass().getCanonicalName() + "]",
					           expectedValue.getClass().isInstance(value));
					
					assertEquals(expectedValue, value);
				}
			}
		}
	}
	
	public static class EnumValue {
		private final String value;
		
		public EnumValue(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
}
