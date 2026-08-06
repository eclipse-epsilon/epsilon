/*******************************************************************************
 * Copyright (c) 2016 Aston University.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.examples.testlang.engine;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;

public class FailedAssertionException extends EolRuntimeException {

	private static final long serialVersionUID = 1L;

	public FailedAssertionException(String arg0) {
		super(arg0);
	}

}
