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

import org.eclipse.epsilon.common.function.CheckedRunnable;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
@FunctionalInterface
public interface CheckedEolRunnable extends CheckedRunnable<EolRuntimeException> {
	
	@Override
	void runThrows() throws EolRuntimeException;
	
	@Override
	default void run() {
		try {
			runThrows();
		}
		catch (EolRuntimeException ex) {
			ex.getMessage();
			throw new RuntimeException(ex);
		}
	}
}
