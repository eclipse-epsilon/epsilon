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
import org.eclipse.epsilon.eol.execute.context.IEolContext;

public class ExecutableAnnotation extends Annotation implements IExecutableModuleElement {
	
	protected Expression expression = null;
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		name = cst.getFirstChild().getText();
		expression = (Expression) module.createAst(cst.getSecondChild(), this);
	}
	
	@Override
	public boolean hasValue() {
		return expression != null;
	}
	
	@Override
	public Object getValue(IEolContext context) throws EolRuntimeException {
		return execute(context);
	}
	
	@Override
	public Object execute(IEolContext context) throws EolRuntimeException {
		return context.getExecutorFactory().execute(expression, context);
	}
	
	public Expression getExpression() {
		return expression;
	}
	
	public void setExpression(Expression expression) {
		this.expression = expression;
	}
	
	public void accept(IEolVisitor visitor) {
		visitor.visit(this);
	}
}
