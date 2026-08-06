/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.function;

import org.eclipse.epsilon.common.function.CheckedPredicate;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
@FunctionalInterface
public interface CheckedEolPredicate<T> extends CheckedPredicate<T, EolRuntimeException>, CheckedEolFunction<T, Boolean> {
	
	@Override
	boolean testThrows(T t) throws EolRuntimeException;
	
	@Override
	default Boolean applyThrows(T t) throws EolRuntimeException {
		return testThrows(t);
	}
	
	@Override
	default boolean test(T t) {
		try {
			return testThrows(t);
		}
		catch (EolRuntimeException ex) {
			ex.getMessage();
			throw new RuntimeException(ex);
		}
	}
}
