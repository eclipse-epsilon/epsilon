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
import org.eclipse.epsilon.common.util.StringUtil;

public class EolIllegalOperationParametersException extends EolRuntimeException {
	
	private String method = "";
	private String expected, actual;
	
	public EolIllegalOperationParametersException(String method) {
		this.method = method;
	}
	
	public EolIllegalOperationParametersException(String method, ModuleElement ast) {
		this(method);
		this.ast = ast;
	}
	
	/**
	 * 
	 * @param method
	 * @param expected
	 * @param ast
	 * @since 1.6
	 */
	public EolIllegalOperationParametersException(String method, String expected, ModuleElement ast) {
		this(method, ast);
		this.expected = expected;
	}
	
	/**
	 * 
	 * @param method
	 * @param expected
	 * @param actual
	 * @param ast
	 * @since 1.6
	 */
	public EolIllegalOperationParametersException(String method, String expected, String actual, ModuleElement ast) {
		this(method, expected, ast);
		this.actual = actual;
	}
	
	@Override
	public String getReason() {
		String reason = "Invalid number (or types) of arguments for operation '" + method + "'";
		if (!StringUtil.isEmpty(expected)) {
			reason += ": expected '"+expected+"'";
		}
		if (!StringUtil.isEmpty(actual)) {
			reason += " but got '"+actual+"'";
		}
		return reason;
	}
	
	@Override
	public void setReason(String reason) {
		this.method = reason;
	}
}
