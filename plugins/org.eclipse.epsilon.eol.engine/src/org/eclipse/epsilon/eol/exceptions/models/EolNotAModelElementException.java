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
import org.eclipse.epsilon.eol.execute.context.IEolContext;


public class EolNotAModelElementException extends EolRuntimeException{
	
	protected Object instance;
	
	public EolNotAModelElementException(ModuleElement ast, Object instance, IEolContext context) {
		super();
		this.ast = ast;
		this.instance = instance;
		this.context = context;
	}
	
	@Override
	public String getReason() {
		return context.getPrettyPrinterManager().toString(instance) + " is not a model element";
	}

}
