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
import org.eclipse.epsilon.eol.exceptions.EolTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.EolUndefinedVariableException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelNotFoundException;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.types.EolModelElementType;
import org.eclipse.epsilon.eol.types.EolType;

public class NameExpression extends Expression {
	
	protected String name;
	protected boolean isTypeName = false;
	
	public NameExpression() {}
	
	public NameExpression(String name) {
		this.name = name;
	}
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		this.name = cst.getText();
	}
	
	public Object execute(IEolContext context, boolean returnVariable) throws EolRuntimeException {
		FrameStack scope = context.getFrameStack();
		Variable variable = scope.get(name);
		
		if (variable != null && variable.getDeprecationInfo() != null) {
			context.getWarningStream().println("Warning: " + variable.getDeprecationInfo());
		}
		
		// First look for a model element type
		// if the name contains a !
		if (variable == null) {
			if (name.contains("!")) {
				variable = getModelElementType(name, context);
			}
		}
		
		// Then look for a model with that name
		if (variable == null) {
			try {
				IModel model = context.getModelRepository().getModelByName(name);
			
				if (model != null)
					variable = Variable.createReadOnlyVariable(name, model);
			}
			catch (EolModelNotFoundException mex) {
				// Ignore this exception and go for a 
				// variable in the scope
			}
		}
		
		if (variable == null) {
			try {
				EolType type = new TypeExpression(name).execute(context);
				if (type != null) {
					variable = Variable.createReadOnlyVariable(type.getName(), type);
				}
			}
			catch (EolTypeNotFoundException ex) {}
		}
		
		if (variable == null)
			throw new EolUndefinedVariableException(name, this);

		if (returnVariable) {
			return variable;
		}
		else {
			return variable.getValue();
		}
	}
	
	@Override
	public Object execute(IEolContext context) throws EolRuntimeException {
		return execute(context, false);
	}
	
	public Variable getModelElementType(String name, IEolContext context) {
		try {
			return Variable.createReadOnlyVariable(name, new EolModelElementType(name, context));
		}
		catch (EolRuntimeException rex) {
			return null;
			// Ignore this exception... We just did not
			// find such a model element type and we can
			// proceed
		}
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isTypeName() {
		return isTypeName;
	}
	
	public void setTypeName(boolean isTypeName) {
		this.isTypeName = isTypeName;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName()+": name="+name+", isTypeName="+isTypeName;
	}
	
	public void accept(IEolVisitor visitor) {
		visitor.visit(this);
	}
}
