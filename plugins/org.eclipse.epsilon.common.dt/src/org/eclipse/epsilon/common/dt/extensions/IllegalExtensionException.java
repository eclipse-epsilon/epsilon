/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.dt.extensions;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;

/**
 * Exception for illegal extensions for EUnit listeners.
 *
 * @author Antonio Garcia-Dominguez
 * @version 1.0
 */
public class IllegalExtensionException extends EolRuntimeException {

	private static final long serialVersionUID = 1L;
	private Throwable cause;

	public IllegalExtensionException(String message) {
		super(message);
	}

	public IllegalExtensionException(String message, ModuleElement ast) {
		super(message, ast);
	}

	public IllegalExtensionException(Throwable cause) {
		this.setStackTrace(cause.getStackTrace());
		this.cause = cause;
	}

	@Override
	public Throwable getCause() {
		return this.cause;
	}

}
