/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.exceptions;

import org.eclipse.epsilon.common.module.ModuleElement;

@SuppressWarnings("serial")
public class EolUserException extends EolRuntimeException {

	Object thrown = null;
	
	public EolUserException(Object thrown) {
		this.thrown = thrown;
	}
	
	public EolUserException(Object thrown, ModuleElement ast) {
		this.thrown = thrown;
		this.ast = ast;
	}
	
	@Override
	public String getReason() {
		return String.valueOf(thrown);
	}
	
	public Object getThrown() {
		return thrown;
	}
}
