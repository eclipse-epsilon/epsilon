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

public class EolRuntimeExceptionReference extends EolRuntimeException {
	
	protected EolRuntimeException exception;
	
	public EolRuntimeExceptionReference(EolRuntimeException original) {
		this.exception = original;
	}
	
	public EolRuntimeException getException() {
		return exception;
	}
	
	public void setException(EolRuntimeException exception) {
		this.exception = exception;
	}
	
	@Override
	public ModuleElement getAst() {
		// TODO Auto-generated method stub
		return exception.getAst();
	}

	@Override
	public int getColumn() {
		// TODO Auto-generated method stub
		return exception.getColumn();
	}

	@Override
	public int getLine() {
		// TODO Auto-generated method stub
		return exception.getLine();
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return exception.getMessage();
	}

	@Override
	public String getReason() {
		// TODO Auto-generated method stub
		return exception.getReason();
	}

	@Override
	public void setAst(ModuleElement ast) {
		// TODO Auto-generated method stub
		exception.setAst(ast);
	}

	@Override
	public void setReason(String reason) {
		// TODO Auto-generated method stub
		exception.setReason(reason);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return exception.toString();
	}

}
