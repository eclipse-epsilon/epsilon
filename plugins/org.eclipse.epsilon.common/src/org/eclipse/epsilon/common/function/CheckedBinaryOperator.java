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

import java.util.function.BinaryOperator;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 * @param <T>
 * @param <E>
 */
@FunctionalInterface
public interface CheckedBinaryOperator<T, E extends Exception> extends BinaryOperator<T>, CheckedBiFunction<T, T, T, E> {

	@Override
	default T apply(T t, T u) {
		return CheckedBiFunction.super.apply(t, u);
	}
	
}
