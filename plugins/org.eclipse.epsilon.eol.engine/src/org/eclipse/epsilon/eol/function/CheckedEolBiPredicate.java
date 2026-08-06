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

import org.eclipse.epsilon.common.function.CheckedBiPredicate;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 * @param <T>
 * @param <U>
 */
@FunctionalInterface
public interface CheckedEolBiPredicate<T, U> extends CheckedEolBiFunction<T, U, Boolean>, CheckedBiPredicate<T, U, EolRuntimeException> {

	@Override
	boolean testThrows(T t, U u) throws EolRuntimeException;
	
	@Override
	default Boolean applyThrows(T t, U u) throws EolRuntimeException {
		return testThrows(t, u);
	}
	
	@Override
	default Boolean apply(T t, U u) {
		return CheckedEolBiFunction.super.apply(t, u);
	}
	
}
