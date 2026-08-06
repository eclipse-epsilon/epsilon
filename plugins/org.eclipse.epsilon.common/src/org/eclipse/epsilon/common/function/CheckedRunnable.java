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

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
@FunctionalInterface
public interface CheckedRunnable<E extends Exception> extends Runnable {
	
	@Override
	default void run() throws RuntimeException {
		try {
			runThrows();
		}
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	void runThrows() throws E;
	
}
