/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.epl.execute;

import org.eclipse.epsilon.common.function.ExceptionHandler;

public class RuntimeExceptionThrower<E extends Exception> implements ExceptionHandler<E> {
	
	@Override
	public void handleException(Exception ex) {
		throw new RuntimeException(ex.getMessage());
	}
}
