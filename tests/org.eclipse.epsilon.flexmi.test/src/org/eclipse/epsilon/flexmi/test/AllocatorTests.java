/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.flexmi.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.eclipse.epsilon.flexmi.AttributeStructuralFeatureAllocator;
import org.junit.Test;

public class AllocatorTests {
	
	@Test
	public void testMoreValues() {
		assertAllocation(list("a", "b", "c"), list("a", "b"), 2, list("a", "a"), list("b", "b"));
	}
	
	@Test
	public void testMoreSlots() {
		assertAllocation(list("a", "b"), list("a", "b", "c"), 2, list("a", "a"), list("b", "b"));
	}
	
	@Test
	public void testNoSlots() {
		assertAllocation(list("a", "b"), list(), 0);
	}

	@Test
	public void testNoValues() {
		assertAllocation(list(), list("a", "b"), 0);
	}	
	
	protected List<String> list(String... s) {
		return new ArrayList<>(Arrays.asList(s));
	}
	
	@SafeVarargs
	protected final void assertAllocation(List<String> values, List<String> slots, int expectedAllocationCount, List<String>... expectedAllocations) {
		AttributeStructuralFeatureAllocator allocator = new AttributeStructuralFeatureAllocator();
		Map<String, String> allocation = allocator.allocate(values, slots);
		assertEquals(expectedAllocationCount, allocation.size());
		for (List<String> expected : expectedAllocations) {
			assertEquals(expected.get(1), allocation.get(expected.get(0)));
		}
	}
	
}
