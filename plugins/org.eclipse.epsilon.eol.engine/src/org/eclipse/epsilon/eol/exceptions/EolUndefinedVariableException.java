/*******************************************************************************
 * Copyright (c) 2008-2018 The University of York, Aston University.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Antonio Garcia-Dominguez - reuse EolRuntimeException ctor, add accessor
 ******************************************************************************/
package org.eclipse.epsilon.eol.exceptions;

import org.eclipse.epsilon.common.module.ModuleElement;

public class EolUndefinedVariableException extends EolRuntimeException {

	private static final long serialVersionUID = 1L;
	private final String variableName;

	public EolUndefinedVariableException(String variableName, ModuleElement ast) {
		super("Undefined variable, type or model: '" + variableName + "'", ast);
		this.variableName = variableName;
	}

	public String getVariableName() {
		return variableName;
	}

}
