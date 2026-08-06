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

public abstract class EagerOperatorExpression extends OperatorExpression {
	
	public EagerOperatorExpression() {}
	
	public EagerOperatorExpression(Expression firstOperand, Expression secondOperand) {
		super(firstOperand, secondOperand);
	}

	@Override
	public Object execute(IEolContext context) throws EolRuntimeException {
		ExecutorFactory executorFactory = context.getExecutorFactory();
		Object o1 = executorFactory.execute(firstOperand, context);
		Object o2 = null;
		
		if (secondOperand != null) {
			o2 = executorFactory.execute(secondOperand, context);
		}
		return execute(o1, o2, context);
	}
	
	public abstract Object execute(Object o1, Object o2, IEolContext context) throws EolRuntimeException;
	
}
