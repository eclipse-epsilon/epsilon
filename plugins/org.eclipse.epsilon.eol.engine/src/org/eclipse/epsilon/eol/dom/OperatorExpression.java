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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;

public abstract class OperatorExpression extends Expression {

	protected Expression firstOperand;
	protected Expression secondOperand;
	protected String operator;
	
	public OperatorExpression() {}
	
	public OperatorExpression(Expression firstOperand, Expression secondOperand) {
		this.firstOperand = firstOperand;
		this.secondOperand = secondOperand;
	}
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		this.firstOperand = (Expression) module.createAst(cst.getFirstChild(), this);
		this.secondOperand = (Expression) module.createAst(cst.getSecondChild(), this);
		this.operator = cst.getText();
	}
	
	public List<Expression> getOperands() {
		List<Expression> operands = new ArrayList<>(2);
		operands.add(firstOperand);
		if (secondOperand != null) operands.add(secondOperand);
		return operands;
	}
	
	public String getOperator() {
		return operator;
	}
	
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	public Expression getFirstOperand() {
		return firstOperand;
	}
	
	public void setFirstOperand(Expression firstOperand) {
		this.firstOperand = firstOperand;
	}
	
	public Expression getSecondOperand() {
		return secondOperand;
	}
	
	public void setSecondOperand(Expression secondOperand) {
		this.secondOperand = secondOperand;
	}
}
