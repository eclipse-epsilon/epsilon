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

public class EolNoReturnException extends EolRuntimeException {
	
	protected String expectedType = "";
	
	public EolNoReturnException(String expectedType, ModuleElement ast, IEolContext context) {
		this.ast = ast;
		this.context = context;
		this.expectedType = expectedType;
	}
	
	@Override
	public String getReason() {
		return "Expected to return '" + expectedType + "'";
	}
}
