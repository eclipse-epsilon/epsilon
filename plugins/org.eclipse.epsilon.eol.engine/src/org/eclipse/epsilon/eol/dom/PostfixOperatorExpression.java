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

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.ExecutorFactory;
import org.eclipse.epsilon.eol.execute.context.IEolContext;

public class PostfixOperatorExpression extends OperatorExpression {
	
	protected boolean increase;
	protected AssignmentStatement assignmentStatement;
	
	public PostfixOperatorExpression(boolean increase) {
		this.increase = increase;
	}
	
	public PostfixOperatorExpression(Expression operand, boolean increase) {
		super(operand, null);
		this.increase = increase;
	}
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		Expression valueExpression = null;
		if (increase) valueExpression = new PlusOperatorExpression(firstOperand, new IntegerLiteral(1));
		else valueExpression = new MinusOperatorExpression(firstOperand, new IntegerLiteral(1));
		assignmentStatement = new AssignmentStatement(firstOperand, valueExpression);		
	}
	
	@Override
	public Object execute(IEolContext context) throws EolRuntimeException {
		ExecutorFactory executorFactory = context.getExecutorFactory();
		executorFactory.execute(assignmentStatement, context);
		return executorFactory.execute(firstOperand, context);
	}
	
	public boolean isIncrease() {
		return increase;
	}
	
	public void setIncrease(boolean increase) {
		this.increase = increase;
	}
	
	public AssignmentStatement getAssignmentStatement() {
		return assignmentStatement;
	}
	
	public void setAssignmentStatement(AssignmentStatement assignmentStatement) {
		this.assignmentStatement = assignmentStatement;
	}
	
	public void accept(IEolVisitor visitor) {
		visitor.visit(this);
	}
}
