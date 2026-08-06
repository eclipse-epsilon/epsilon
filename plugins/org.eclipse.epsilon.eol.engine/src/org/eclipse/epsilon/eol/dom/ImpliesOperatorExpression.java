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

public class ImpliesOperatorExpression extends OperatorExpression {

	public ImpliesOperatorExpression() {}
	
	public ImpliesOperatorExpression(Expression firstOperand, Expression secondOperand) {
		super(firstOperand, secondOperand);
	}
	
	@Override
	public Boolean execute(IEolContext context) throws EolRuntimeException {
		ExecutorFactory executorFactory = context.getExecutorFactory();
		Object o1 = executorFactory.execute(firstOperand, context);
		
		if (o1 instanceof Boolean) {
			if (!(boolean) o1) {
				return true;
			}
			else {
				Object o2 = executorFactory.execute(secondOperand, context);
				if (o2 instanceof Boolean) {
					return ((boolean) o2) && ((boolean) o1);
				}
			}
		}
		
		throw new EolRuntimeException("Operator 'implies' applies only to operands of type Boolean", this);
	}
	
	public void accept(IEolVisitor visitor) {
		visitor.visit(this);
	}
}
