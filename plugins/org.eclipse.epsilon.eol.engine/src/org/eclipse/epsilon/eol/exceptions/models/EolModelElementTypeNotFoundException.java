/*******************************************************************************
 * Copyright (c) 2008-2018 The University of York, Aston University.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Antonio Garcia-Dominguez - allow custom reason, add accessors
 ******************************************************************************/
package org.eclipse.epsilon.eol.exceptions.models;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;

public class EolModelElementTypeNotFoundException extends EolRuntimeException{
	
	private static final long serialVersionUID = 1L;

	private final String model;
	private final String metaClass;

	public EolModelElementTypeNotFoundException(String model, String metaClass) {
		this(model, metaClass, "Cannot find meta-class '" + metaClass + "' in model '" + model + "'");
	}

	public String getModel() {
		return model;
	}

	public String getMetaClass() {
		return metaClass;
	}

	public EolModelElementTypeNotFoundException(String model, String metaClass, String reason){
		super(reason);
		this.model = model;
		this.metaClass = metaClass;
	}

}
