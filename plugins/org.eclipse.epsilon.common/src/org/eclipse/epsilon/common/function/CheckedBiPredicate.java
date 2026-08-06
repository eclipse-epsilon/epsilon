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

import java.util.function.BiPredicate;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 * @param <T>
 * @param <U>
 * @param <E>
 */
@FunctionalInterface
public interface CheckedBiPredicate<T, U, E extends Exception> extends BiPredicate<T, U>, CheckedBiFunction<T, U, Boolean, E> {
	
	boolean testThrows(T t, U u) throws E;
	
	@Override
	default Boolean applyThrows(T t, U u) throws E {
		return testThrows(t, u);
	}
	
	@Override
	default boolean test(T t, U u) {
		return CheckedBiFunction.super.apply(t, u);
	}
}
