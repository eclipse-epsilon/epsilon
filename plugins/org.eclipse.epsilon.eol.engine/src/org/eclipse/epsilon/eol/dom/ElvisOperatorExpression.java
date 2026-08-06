/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.dom;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.EolUndefinedVariableException;
import org.eclipse.epsilon.eol.execute.ExecutorFactory;
import org.eclipse.epsilon.eol.execute.context.IEolContext;

/**
 * <code>a ?: b</code> is shorthand for
 * <code>a != null ? a : b</code>.
 * 
 * @author Sina Madani
 * @since 2.1
 */
public class ElvisOperatorExpression extends OperatorExpression {
	
	public ElvisOperatorExpression() {}

	public ElvisOperatorExpression(Expression a, Expression b) {
		super(a, b);
	}

	@Override
	public Object execute(IEolContext context) throws EolRuntimeException {
		ExecutorFactory executorFactory = context.getExecutorFactory();
		Object a;
		try {
			a = executorFactory.execute(firstOperand, context);
		}
		catch (EolUndefinedVariableException novar) {
			if (firstOperand instanceof NameExpression) {
				a = null;
			}
			else throw novar;
		}
		return a != null ? a : executorFactory.execute(secondOperand, context);
	}
	
	public void accept(IEolVisitor visitor) {
		visitor.visit(this);
	}
}
