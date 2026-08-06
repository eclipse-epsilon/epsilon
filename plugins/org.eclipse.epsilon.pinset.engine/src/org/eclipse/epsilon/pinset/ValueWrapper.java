/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *********************************************************************/
package org.eclipse.epsilon.pinset;

/**
 * Allows referencing a value from several structures to change, even in type.
 *
 * @author Alfonso de la Vega
 * @since 2.1
 */
public class ValueWrapper {
	private Object value;

	public ValueWrapper(Object value) {
		super();
		this.value = value;
	}

	public Object get() {
		return value;
	}

	public void set(Object value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return (value == null) ? "" : value.toString();
	}
}
