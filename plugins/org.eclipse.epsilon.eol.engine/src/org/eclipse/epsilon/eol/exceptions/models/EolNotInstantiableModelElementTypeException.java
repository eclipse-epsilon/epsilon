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
import org.eclipse.epsilon.eol.types.EolModelElementType;

public class EolNotInstantiableModelElementTypeException extends EolRuntimeException {
	
	protected String model;
	protected String typeName;
	
	public EolNotInstantiableModelElementTypeException(String model, String typeName){
		this.model = model;
		this.typeName = typeName;
	}
	
	public EolNotInstantiableModelElementTypeException(EolModelElementType type) throws EolModelElementTypeNotFoundException {
		this.model = type.getModel().getName();
		this.typeName = type.getTypeName();
	}
	
	@Override
	public String getReason(){
		return "Type '" + typeName + "' of model '" + model + "' cannot be instantiated";
	}
	
}
