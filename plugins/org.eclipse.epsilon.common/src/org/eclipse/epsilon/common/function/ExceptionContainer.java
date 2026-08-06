/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.common.function;

/**
 * Utility class for wrapping an exception, which is useful for exception-handling in
 * commonly used lambda expressions where checked exceptions can't be thrown and
 * variables outside the scope can't be assigned due to "effectively final" requirement.
 * 
 * @author Sina Madani
 * @param <E> The type of the exception.
 * @since 1.6
 */
public class ExceptionContainer<E extends Exception> {
	
	E exception;
	
	public E getException() {
		return exception;
	}
	
	public void setException(E ex) {
		this.exception = ex;
	}
	
	public boolean hasException() {
		return exception != null;
	}
	
	public void throwIfPresent() throws E {
		if (hasException()) throw exception;
	}
}
