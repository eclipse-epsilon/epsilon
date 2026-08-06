/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.exceptions.models;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;


public class EolNotAnEnumerationValueException extends EolRuntimeException{
	
	protected Object instance;
	
	public EolNotAnEnumerationValueException(Object instance) {
		super();
		this.instance = instance;
	}
	
	@Override
	public String getReason() {
		return instance + " is not an enumeration value";
	}

}
