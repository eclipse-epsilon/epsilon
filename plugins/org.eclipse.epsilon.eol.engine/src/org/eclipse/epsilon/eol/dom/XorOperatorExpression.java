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
import org.eclipse.epsilon.eol.execute.ExecutorFactory;
import org.eclipse.epsilon.eol.execute.context.IEolContext;

public class XorOperatorExpression extends OperatorExpression {

	public XorOperatorExpression() {}
	
	public XorOperatorExpression(Expression firstOperand, Expression secondOperand) {
		super(firstOperand, secondOperand);
	}
	
	@Override
	public Boolean execute(IEolContext context) throws EolRuntimeException {
		ExecutorFactory executorFactory = context.getExecutorFactory();
		Object o1 = executorFactory.execute(firstOperand, context);
		Object o2 = executorFactory.execute(secondOperand, context);
		if (o1 instanceof Boolean && o2 instanceof Boolean) {
			return ((boolean) o1) ^ ((boolean) o2);
		}
		else {
			throw new EolRuntimeException("Operator 'xor' applies only to Booleans", this);
		}
	}
	
	public void accept(IEolVisitor visitor) {
		visitor.visit(this);
	}
}
