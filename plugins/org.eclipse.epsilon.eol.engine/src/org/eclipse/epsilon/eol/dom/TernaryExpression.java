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

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.flowcontrol.EolTernaryException;
import org.eclipse.epsilon.eol.execute.ExecutorFactory;
import org.eclipse.epsilon.eol.execute.context.IEolContext;

/**
 * Ternary operator, similar to Java's.
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class TernaryExpression extends OperatorExpression {

	protected Expression thirdOperand;
	
	public TernaryExpression() {}

	public TernaryExpression(Expression condition, Expression rIfTrue, Expression rIfFalse) {
		super(condition, rIfTrue);
		this.thirdOperand = rIfFalse;
	}
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		this.thirdOperand = (Expression) module.createAst(cst.getFourthChild(), this);
	}

	@Override
	public Object execute(IEolContext context) throws EolRuntimeException {
		ExecutorFactory executorFactory = context.getExecutorFactory();
		Object condition = executorFactory.execute(firstOperand, context);
		if (!(condition instanceof Boolean)) {
			throw new EolTernaryException(firstOperand, condition);
		}
		return executorFactory.execute(((boolean) condition) ? secondOperand : thirdOperand, context);
	}
	
	public Expression getThirdOperand() {
		return thirdOperand;
	}
	
	public void setThirdOperand(Expression thirdOperand) {
		this.thirdOperand = thirdOperand;
	}
	
	public void accept(IEolVisitor visitor) {
		visitor.visit(this);
	}
}
