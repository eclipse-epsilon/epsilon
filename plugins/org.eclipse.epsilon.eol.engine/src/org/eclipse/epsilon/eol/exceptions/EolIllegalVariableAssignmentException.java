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

import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.types.EolType;

public class EolIllegalVariableAssignmentException extends EolRuntimeException{
	
	protected Variable variable;
	protected EolType expected;
	protected Object value;
	
	public EolIllegalVariableAssignmentException(Variable variable, EolType expected, Object value, IEolContext context) {
		super();
		this.variable = variable;
		this.expected = expected;
		this.value = value;
		this.context = context;
	}
	
	@Override
	public String getReason(){
		return "Illegal assignment to variable '" + variable.getName() + "'. Expected " + expected.getName() + " and found " + context.getPrettyPrinterManager().toString(value);
	}
}
