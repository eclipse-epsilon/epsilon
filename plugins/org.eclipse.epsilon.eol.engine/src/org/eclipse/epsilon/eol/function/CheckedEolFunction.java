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

import org.eclipse.epsilon.common.function.CheckedFunction;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
@FunctionalInterface
public interface CheckedEolFunction<T, R> extends CheckedFunction<T, R, EolRuntimeException> {
	
	@Override
	default R apply(T t) {
		try {
			return applyThrows(t);
		}
		catch (EolRuntimeException ex) {
			ex.getMessage();
			throw new RuntimeException(ex);
		}
	}
}
