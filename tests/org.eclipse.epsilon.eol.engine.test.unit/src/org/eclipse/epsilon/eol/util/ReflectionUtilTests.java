/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.util;

import static org.junit.Assert.*;

import java.lang.reflect.Method;

import org.junit.Test;

public class ReflectionUtilTests {

	@Test
	public void primitiveInstanceForPrimitiveType2() throws Exception {
		Method expected = Fixture.class.getMethod("primitive2", char.class);
		Method actual = ReflectionUtil.getMethodFor(new Fixture(), "primitive2", new Object[]{'a'}, true, true);
		assertEquals(expected, actual);
	}

	@Test
	public void primitiveInstanceForPrimitiveType() throws Exception {
		Method expected = Fixture.class.getMethod("primitive", int.class);
		Method actual = ReflectionUtil.getMethodFor(new Fixture(), "primitive", new Object[]{42}, true, true);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void referenceInstanceForPrimitiveType() throws Exception {
		Method expected = Fixture.class.getMethod("primitive", int.class);
		Method actual = ReflectionUtil.getMethodFor(new Fixture(), "primitive", new Object[]{new Integer(42)}, true, true);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void referenceInstanceForReferenceType() throws Exception {
		Method expected = Fixture.class.getMethod("reference", Integer.class);
		Method actual = ReflectionUtil.getMethodFor(new Fixture(), "reference", new Object[]{new Integer(42)}, true, true);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void primitiveInstanceForReferenceType() throws Exception {
		Method expected = Fixture.class.getMethod("reference", Integer.class);
		Method actual = ReflectionUtil.getMethodFor(new Fixture(), "reference", new Object[]{42}, true, true);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void primitiveInstanceForTopType() throws Exception {
		Method expected = Fixture.class.getMethod("top", Object.class);
		Method actual = ReflectionUtil.getMethodFor(new Fixture(), "top", new Object[]{42}, true, true);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void referenceInstanceForTopType() throws Exception {
		Method expected = Fixture.class.getMethod("top", Object.class);
		Method actual = ReflectionUtil.getMethodFor(new Fixture(), "top", new Object[]{new Integer(42)}, true, true);
		
		assertEquals(expected, actual);
	}
	
	public class Fixture {
		public void primitive2(char c) {}
		public void primitive(int i) {}
		public void reference(Integer i) {}
		public void top(Object o) {}
	}
	
	
	@Test
	public void primitiveInstanceForOverloadedTopType() throws Exception {
		Method expected = OverloadedFixture.class.getMethod("top", Object.class);
		Method actual = ReflectionUtil.getMethodFor(new OverloadedFixture(), "top", new Object[]{42}, true, true);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void referenceInstanceForOverloadedTopType() throws Exception {
		Method expected = OverloadedFixture.class.getMethod("top", Object.class);
		Method actual = ReflectionUtil.getMethodFor(new OverloadedFixture(), "top", new Object[]{new Integer(42)}, true, true);
		
		assertEquals(expected, actual);
	}
	
	public class OverloadedFixture {
		public void top(int o) {}
		public void top(Object o) {}
	}
}
