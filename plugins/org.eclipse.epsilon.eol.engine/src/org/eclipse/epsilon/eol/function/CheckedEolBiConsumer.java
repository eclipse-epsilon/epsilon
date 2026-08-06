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

import org.eclipse.epsilon.common.function.CheckedBiConsumer;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 * @param <T>
 * @param <U>
 */
@FunctionalInterface
public interface CheckedEolBiConsumer<T, U> extends CheckedBiConsumer<T, U, EolRuntimeException>, CheckedEolBiFunction<T, U, Void> {

	@Override
	void acceptThrows(T t, U u) throws EolRuntimeException;
	
	@Override
	default Void applyThrows(T t, U u) throws EolRuntimeException {
		acceptThrows(t, u);
		return null;
	}
	
	@Override
	default void accept(T t, U u) {
		try {
			acceptThrows(t, u);
		}
		catch (EolRuntimeException ex) {
			ex.getMessage();
			throw new RuntimeException(ex);
		}
	}	
}
