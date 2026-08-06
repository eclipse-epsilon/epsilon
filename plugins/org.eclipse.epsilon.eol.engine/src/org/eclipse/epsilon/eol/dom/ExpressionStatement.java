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

public class ExpressionStatement extends Statement {
	
	protected Expression expression = null;
	
	public ExpressionStatement() {}
	
	public ExpressionStatement(Expression expression) {
		this.setExpression(expression);
		if (expression.getParent() != null) expression.getParent().getChildren().remove(expression);
		expression.setParent(this);
		this.setUri(expression.getUri());
		this.setRegion(expression.getRegion());
	}
	
	@Override
	public Object execute(IEolContext context) throws EolRuntimeException {
		return context.getExecutorFactory().execute(expression, context);
	}
	
	public void setExpression(Expression expression) {
		this.expression = expression;
	}
	
	public Expression getExpression() {
		return expression;
	}
	
	public void accept(IEolVisitor visitor) {
		visitor.visit(this);
	}
}
