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
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.types.EolType;

public class NewInstanceExpression extends TypeInitialiser {

	protected TypeExpression typeExpression;
	protected List<Expression> parameterExpressions = new ArrayList<>();
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		typeExpression = (TypeExpression) module.createAst(cst.getFirstChild(), this);
		if (cst.getChildCount() == 2) {
			for (AST parameterAst : cst.getSecondChild().getChildren()) {
				parameterExpressions.add((Expression) module.createAst(parameterAst, this));
			}
		}
	}

	@Override
	public Object execute(IEolContext context) throws EolRuntimeException {
		Object result = context.getExecutorFactory().execute(typeExpression, context);
		if (!(result instanceof EolType)) {
			throw new EolRuntimeException("Expected type, found " + result, typeExpression);
		}
		return initialiseType((EolType) result, parameterExpressions, context, true);
	}
	
	public TypeExpression getTypeExpression() {
		return typeExpression;
	}
	
	public void setTypeExpression(TypeExpression typeExpression) {
		this.typeExpression = typeExpression;
	}
	
	public List<Expression> getParameterExpressions() {
		return parameterExpressions;
	}
	
	public void accept(IEolVisitor visitor) {
		visitor.visit(this);
	}
	
}
