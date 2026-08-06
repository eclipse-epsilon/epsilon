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

import java.util.function.Supplier;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
@FunctionalInterface
public interface CheckedSupplier<R, E extends Exception> extends Supplier<R> {

	@Override
	default R get() throws RuntimeException {
		try {
			return getThrows();
		}
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	R getThrows() throws E;
	
}
