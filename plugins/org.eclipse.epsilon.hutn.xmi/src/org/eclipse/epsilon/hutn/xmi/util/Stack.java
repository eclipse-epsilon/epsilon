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
package org.eclipse.epsilon.hutn.xmi.util;

import java.util.LinkedList;
import java.util.List;

public class Stack<T> {

	private final List<T> stack = new LinkedList<>();
	
	public T peek() {
		if (stack.isEmpty())
			return null;
		
		return stack.get(0);
	}
	
	public T pop() {
		if (peek() == null)
			throw new IllegalStateException("Cannot pop from an empty stack.");
		
		final T value = peek();
		stack.remove(0);
		return value;
	}
	
	public void push(T element) {
		stack.add(0, element);
	}

	public boolean isEmpty() {
		return stack.isEmpty();
	}
}
