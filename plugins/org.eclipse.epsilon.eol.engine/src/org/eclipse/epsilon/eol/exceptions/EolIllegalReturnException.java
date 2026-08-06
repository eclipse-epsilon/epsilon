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
import org.eclipse.epsilon.eol.execute.context.IEolContext;

public class EolIllegalReturnException extends EolRuntimeException {
	
	private String expectedType = "";
	private Object returnedValue = null;
	
	public EolIllegalReturnException(String expectedType, Object returnedValue, ModuleElement ast, IEolContext context) {
		this.expectedType = expectedType;
		this.returnedValue = returnedValue;
		this.context = context;
		this.ast = ast;
	}
	
	@Override
	public String getReason() {
		return "Expected to return '" + expectedType + "' instead of '" 
		+ context.getPrettyPrinterManager().toString(returnedValue) + "'";
	}

	public String getExpectedType() {
		return expectedType;
	}

	public Object getReturnedValue() {
		return returnedValue;
	}
}
