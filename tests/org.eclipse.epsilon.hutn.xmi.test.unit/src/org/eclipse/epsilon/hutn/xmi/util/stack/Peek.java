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
package org.eclipse.epsilon.hutn.xmi.util.stack;

import static org.junit.Assert.assertEquals;

import org.eclipse.epsilon.hutn.xmi.util.Stack;
import org.junit.Before;
import org.junit.Test;

public class Peek {
private Stack<String> stack;
	
	@Before
	public void setup() {
		 stack = new Stack<>();
	}
	
	@Test
	public void simple() {
		stack.push("foo");
		assertEquals("foo", stack.peek());
		
		stack.pop();
		assertEquals(null, stack.peek());
	}
	
	@Test
	public void empty() {
		assertEquals(null, stack.peek());
	}
	
	@Test
	public void lifo() {
		stack.push("foo");
		stack.push("bar");
		stack.push("baz");
		assertEquals("baz", stack.peek());
		
		stack.pop();
		assertEquals("bar", stack.peek());
		
		stack.pop();
		assertEquals("foo", stack.peek());
		
		stack.pop();
		assertEquals(null, stack.peek());
	}
}
