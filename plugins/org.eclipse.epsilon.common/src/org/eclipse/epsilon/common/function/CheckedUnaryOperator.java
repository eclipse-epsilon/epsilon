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

import java.util.function.UnaryOperator;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
@FunctionalInterface
public interface CheckedUnaryOperator<T, E extends Exception> extends UnaryOperator<T>, CheckedFunction<T, T, E> {

	@Override
	default T apply(T t) {
		return CheckedFunction.super.apply(t);
	}
	
}
