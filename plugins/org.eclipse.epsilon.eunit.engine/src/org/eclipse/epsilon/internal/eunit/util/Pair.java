/*******************************************************************************
 * Copyright (c) 2011 Antonio García-Domínguez.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Antonio García-Domínguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.internal.eunit.util;

/**
 * Generic immutable container which has two values of possibly different types.
 */
public class Pair<T1, T2> {

	private T1 left;
	private T2 right;

	public Pair(T1 left, T2 right) {
		this.left  = left;
		this.right = right;
	}

	public T1 getLeft() {
		return left;
	}

	public T2 getRight() {
		return right;
	}
}
