/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.dom;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;

public class NotOperatorExpression extends OperatorExpression {

	public NotOperatorExpression() {}
	
	public NotOperatorExpression(Expression operand) {
		super(operand, null);
	}
	
	@Override
	public Boolean execute(IEolContext context) throws EolRuntimeException {
		Object o1 = context.getExecutorFactory().execute(firstOperand,context);
		if (o1 instanceof Boolean) {
			return !((boolean) o1);
		}
		else {
			throw new EolRuntimeException("Operator 'not' applies only to Booleans", this);
		}
	}
	
	public void accept(IEolVisitor visitor) {
		visitor.visit(this);
	}
}
