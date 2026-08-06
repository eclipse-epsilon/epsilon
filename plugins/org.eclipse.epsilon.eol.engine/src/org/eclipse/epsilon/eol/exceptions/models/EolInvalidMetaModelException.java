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

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;


public class EolInvalidMetaModelException extends EolRuntimeException {
	
	protected String modelName;
	protected String metaModelName;
	protected String actualMetaModelName;
	
	public EolInvalidMetaModelException(String modelName, String metaModelName, String actualMetaModelName, ModuleElement ast) {
		this.modelName = modelName;
		this.metaModelName = metaModelName;
		this.actualMetaModelName = actualMetaModelName;
		this.ast = ast;
	}
	
	@Override
	public String getReason(){
		return "Model " + modelName + " is declared to be an instance of meta-model " 
		+ metaModelName + " while it is an instance of meta-model " + actualMetaModelName;
	}
	
}
