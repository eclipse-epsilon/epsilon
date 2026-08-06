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

import org.eclipse.epsilon.eol.execute.context.Variable;

public class EolReadOnlyVariableException extends EolRuntimeException {
	
	protected Variable variable;
	
	public EolReadOnlyVariableException(Variable variable) {
		super();
		this.variable = variable;
	}

	@Override
	public String getReason() {
		return "Variable '" + variable.getName() + "' is read-only";
	}
}
