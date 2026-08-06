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

import java.util.function.BiConsumer;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 * @param <T>
 * @param <U>
 * @param <E>
 */
@FunctionalInterface
public interface CheckedBiConsumer<T, U, E extends Exception> extends BiConsumer<T, U> {
	
	@Override
	default void accept(T t, U u) {
		try {
			acceptThrows(t, u);
		}
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	void acceptThrows(T t, U u) throws E;
	
}
