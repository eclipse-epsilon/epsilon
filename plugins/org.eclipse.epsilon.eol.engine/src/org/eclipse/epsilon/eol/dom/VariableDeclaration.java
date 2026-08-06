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
import org.eclipse.epsilon.eol.exceptions.EolRedefinedVariableException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.types.EolAnyType;
import org.eclipse.epsilon.eol.types.EolType;

public class VariableDeclaration extends TypeInitialiser {
	
	protected List<Expression> parameterExpressions = new ArrayList<>();
	protected NameExpression nameExpression;
	protected boolean instantiate;
	protected boolean external;
	protected TypeExpression typeExpression;
	
	public VariableDeclaration() {}
	
	public VariableDeclaration(NameExpression nameExpression, TypeExpression typeExpression, boolean instantiate, Expression... parameterExpressions) {
		this.nameExpression = nameExpression;
		this.typeExpression = typeExpression;
		this.instantiate = instantiate;
		for (Expression parameterExpression : parameterExpressions) {
			this.parameterExpressions.add(parameterExpression);
		}
	}
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		nameExpression = (NameExpression) module.createAst(cst.getFirstChild(), this);
		instantiate = cst.getText().equalsIgnoreCase("new");
		external = cst.getText().equals("ext");
		AST typeExpressionAst = cst.getSecondChild();
		typeExpression = (TypeExpression) module.createAst(typeExpressionAst, this);
		if (typeExpression != null) {
			AST parametersAst = typeExpressionAst.getNextSibling();
			if (parametersAst != null) {
				for (AST parameterAst : parametersAst.getChildren()) {
					parameterExpressions.add((Expression) module.createAst(parameterAst, this));
				}
			}
		}
	}
	
	@Override
	public Variable execute(IEolContext context) throws EolRuntimeException {
		FrameStack frameStack = context.getFrameStack();
		Variable variable;

		if (external && (variable = frameStack.get(getName())) != null) {
			return variable;
		}

		EolType variableType = typeExpression == null ? EolAnyType.Instance :
			(EolType) context.getExecutorFactory().execute(typeExpression, context);
		
		if ((variable = frameStack.getTopFrame().get(getName())) != null && variable.getType() != variableType) {
			throw new EolRedefinedVariableException(getName(), this);
		}
		
		//TODO : Add try-catch and support for EolInstantiationExceptions
		Object newInstance = initialiseType(variableType, parameterExpressions, context, instantiate);
		
		variable = new Variable(getName(), newInstance, variableType);
		frameStack.put(variable);
		return variable;
	}
	
	public String getName() {
		return nameExpression.getName();
	}
	
	public boolean isInstantiate() {
		return instantiate;
	}
	
	public void setInstantiate(boolean instantiate) {
		this.instantiate = instantiate;
	}
	
	public boolean isExternal() {
		return external;
	}
	
	public void setExternal(boolean external) {
		this.external = external;
	}
	
	public TypeExpression getTypeExpression() {
		return typeExpression;
	}
	
	public void setTypeExpression(TypeExpression typeExpression) {
		this.typeExpression = typeExpression;
	}
	
	public NameExpression getNameExpression() {
		return nameExpression;
	}
	
	public void setNameExpression(NameExpression nameExpression) {
		this.nameExpression = nameExpression;
	}
	
	public List<Expression> getParameterExpressions() {
		return parameterExpressions;
	}
	
	public void accept(IEolVisitor visitor) {
		visitor.visit(this);
	}
}
