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
package org.eclipse.epsilon.hutn.xmi.coerce;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.eclipse.epsilon.hutn.model.hutn.AttributeSlot;
import org.eclipse.epsilon.hutn.model.hutn.ReferenceSlot;
import org.eclipse.epsilon.hutn.model.hutn.Slot;
import org.eclipse.epsilon.hutn.test.util.HutnUtil;
import org.junit.Test;

public class SlotCoercerTest {

	private static final SlotCoercer coercer = new SlotCoercer();
	
	@Test
	public void noCoercionOfEmptyString() {
		coerceToAttributeSlotTest("", "");
	}
	
	@Test
	public void coercesToFloat() {
		coerceToAttributeSlotTest("3.0", 3.0f);
	}
	
	@Test
	public void fractionCoercedToFloat() {
		coerceToAttributeSlotTest(".5", .5f);
	}
	
	@Test
	public void noCoercionOfNumberFollowedByPoint() {
		coerceToAttributeSlotTest("3.", "3.");
	}
	
	@Test
	public void coercesToInteger() {
		coerceToAttributeSlotTest("3", 3);
	}

	@Test
	public void coercesToBoolean() {
		coerceToAttributeSlotTest("true", true);
	}
	
	@Test
	public void noCoercionOfString() {
		coerceToAttributeSlotTest("foo", "foo");
	}
	
	@Test
	public void coercesAllToFloats() {
		coerceToAttributeSlotTest(array("3.0", "4.0", "5.0"), 3.0f, 4.0f, 5.0f);
	}
	
	@Test
	public void noCoercionOfMixedValues() {
		coerceToAttributeSlotTest(array("3.0", "true", "foo"), "3.0", "true", "foo");
	}
	
	@Test
	public void coercesToReference() {
		coerceToReferenceSlotTest("_abc", "_abc");
	}
	
	@Test
	public void coercesAllToReferences() {
		coerceToReferenceSlotTest("_abc _def _ghi", "_abc", "_def", "_ghi");
	}
	
	@Test
	public void noCoercionToReferencesWhenMoreThanOneValue() {
		coerceToAttributeSlotTest(array("_abc", "foo", "bar"), "_abc", "foo", "bar");
	}
	
	@Test
	public void noCoercionOfFloat() {
		coerceToAttributeSlotTest(4.3f, 4.3f);
	}
	
	private static String[] array(String... elements) {
		return elements;
	}
	
	private static void coerceToAttributeSlotTest(Object value, Object... expectedCoercedValues) {
		coerceToAttributeSlotTest(new Object[]{value}, expectedCoercedValues);
	}
	
	private static void coerceToAttributeSlotTest(Object[] values, Object... expectedCoercedValues) {
		slotCoercerTest(values, AttributeSlot.class, expectedCoercedValues);
	}
	
	private static void coerceToReferenceSlotTest(Object value, Object... expectedCoercedValues) {
		coerceToReferenceSlotTest(new Object[]{value}, expectedCoercedValues);
	}
	
	private static void coerceToReferenceSlotTest(Object[] values, Object... expectedCoercedValues) {
		slotCoercerTest(values, ReferenceSlot.class, expectedCoercedValues);
	}
	
	private static void slotCoercerTest(Object[] values, Class<? extends Slot<?>> expectedSlotType, Object... expectedCoercedValues) {
		final AttributeSlot slot = HutnUtil.createAttributeSlot(values);
		slot.setLine(4);
		slot.setCol(2);
		
		final Slot<?> coerced = coercer.coerce(slot);

		org.junit.Assert.assertEquals("Expected line number of slot to be preserved throughout coercion.", 4, coerced.getLine());
		org.junit.Assert.assertEquals("Expected col number of slot to be preserved throughout coercion.", 2, coerced.getCol()); 
		
		assertTrue("Expected an " + expectedSlotType.getSimpleName() + ", but was: " + coerced, expectedSlotType.isInstance(coerced));
		assertEquals(Arrays.asList(expectedCoercedValues), coerced.getValues());
	}
	
	private static void assertEquals(List<?> expected, List<?> actual) {
		org.junit.Assert.assertEquals("Unequal number of elements.", expected.size(), actual.size());
		
		for (int index = 0; index < expected.size(); index++) {
			org.junit.Assert.assertEquals("Unequal element at index: " + index, expected.get(index), actual.get(index));
		}
	}
}
