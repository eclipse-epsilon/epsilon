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

import java.util.function.BiFunction;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 * @param <T>
 * @param <U>
 * @param <R>
 * @param <E>
 */
@FunctionalInterface
public interface CheckedBiFunction<T, U, R, E extends Exception> extends BiFunction<T, U, R> {
	
	@Override
	default R apply(T t, U u) {
		try {
			return applyThrows(t, u);
		}
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	R applyThrows(T t, U u) throws E;
	
}
