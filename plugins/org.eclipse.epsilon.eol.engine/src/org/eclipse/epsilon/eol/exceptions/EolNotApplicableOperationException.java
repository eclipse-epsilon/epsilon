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
import org.eclipse.epsilon.eol.types.EolType;


public class EolNotApplicableOperationException extends EolRuntimeException {

	protected EolType expected;
	protected String operation;
	
	public EolNotApplicableOperationException(String operation, EolType expected, ModuleElement ast) {
		super();
		this.expected = expected;
		this.ast = ast;
		this.operation = operation;
	}
	
	@Override
	public String getReason(){
		return "Operation '" + operation + "'.applies only to instances of type " + expected.getName();
	}
	
	
	
}
