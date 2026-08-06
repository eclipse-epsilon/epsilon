/*******************************************************************************
 * Copyright (c) 2014 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitris Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.graphml.tests;

import static org.junit.Assert.*;

import org.eclipse.epsilon.emc.graphml.ValuedSlotFeatureLabelParser;
import org.eclipse.epsilon.emc.muddle.Feature;
import org.eclipse.epsilon.emc.muddle.PrimitiveTypes;
import org.junit.Test;

public class ValuedFeatureLabelParserTests {
	
	public void testSimple() {
		Feature prototype = parse("foo");
		assertEquals("foo", prototype.getName());
		assertNull(prototype.getType());
		assertFalse(prototype.isMany());
	}
	
	public void testSimpleValue() {
		Feature prototype = parse("foo = 5");
		assertEquals("foo", prototype.getName());
		assertNull(prototype.getType());
		assertFalse(prototype.isMany());	
	}
	
	@Test
	public void testMutliplicityAndType() {
		Feature prototype = parse("Integer[] x = 5");
		assertEquals(PrimitiveTypes.getIntegerType(), prototype.getType());
		assertEquals("x", prototype.getName());
		assertTrue(prototype.isMany());
	}
	
	@Test
	public void testTypeOnly() {
		Feature prototype = parse("Integer x = 5");
		assertEquals(PrimitiveTypes.getIntegerType(), prototype.getType());
		assertEquals("x", prototype.getName());
		assertTrue(!prototype.isMany());
	}
	
	@Test
	public void testUnknownType() {
		Feature prototype = parse("Date foo = 12/3/2012");
		assertEquals(prototype.getType(), PrimitiveTypes.getStringType());
		assertEquals("foo", prototype.getName());
		assertEquals(false, prototype.isMany());
	}
	
	@Test
	public void testUnknownTypeAndMultiplicity() {
		Feature prototype = parse("Date[] foo = 12/3/2012");
		assertEquals(prototype.getType(), PrimitiveTypes.getStringType());
		assertEquals("foo", prototype.getName());
		assertEquals(true, prototype.isMany());
	}
	
	protected Feature parse(String s) {
		return new ValuedSlotFeatureLabelParser(s).getFeature();
	}
}
