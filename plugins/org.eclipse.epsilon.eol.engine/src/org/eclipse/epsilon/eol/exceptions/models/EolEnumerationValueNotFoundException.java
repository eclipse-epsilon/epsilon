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

public class EolEnumerationValueNotFoundException extends EolRuntimeException{
	
	protected String enumeration;
	protected String label;
	protected String model;
	
	public EolEnumerationValueNotFoundException(String enumeration, String label, String model) {
		this.enumeration = enumeration;
		this.label = label;
		this.model = model;
	}
	
	@Override
	public String getReason() {
		return "Cannot find enumeration literal " + enumeration + "#" + label + " in model " + model;
	}
	
}
