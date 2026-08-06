/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.evl.execute.exceptions;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;

public class EvlConstraintNotFoundException extends EolRuntimeException {
	
	protected String constraintName;
	
	public EvlConstraintNotFoundException(String constraintName, ModuleElement ast) {
		this.setAst(ast);
		this.constraintName = constraintName;
	}
	
	@Override
	public String getReason() {
		return "Constraint " + constraintName + " not found";
	}
	
}
