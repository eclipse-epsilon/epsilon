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
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.exceptions.EolIllegalOperationException;
import org.eclipse.epsilon.eol.exceptions.EolIllegalPropertyException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.EolUndefinedVariableException;
import org.eclipse.epsilon.eol.execute.ExecutorFactory;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.introspection.java.ObjectMethod;
import org.eclipse.epsilon.eol.execute.operations.AbstractOperation;
import org.eclipse.epsilon.eol.execute.operations.contributors.IOperationContributorProvider;
import org.eclipse.epsilon.eol.execute.operations.contributors.OperationContributor;
import org.eclipse.epsilon.eol.execute.operations.simple.SimpleOperation;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.types.EolNoType;
import org.eclipse.epsilon.eol.types.EolUndefined;

public class OperationCallExpression extends FeatureCallExpression {
	
	protected final ArrayList<Expression> parameterExpressions = new ArrayList<>(0);
	protected boolean contextless;
	
	public OperationCallExpression() {
		this(false);
	}
	
	public OperationCallExpression(boolean contextless) {
		this.contextless = contextless;
	}
	
	public OperationCallExpression(Expression targetExpression, NameExpression nameExpression, Expression... parameterExpressions) {
		this.targetExpression = targetExpression;
		this.nameExpression = nameExpression;
		this.contextless = (targetExpression == null);
		if (parameterExpressions != null) {
			this.parameterExpressions.ensureCapacity(parameterExpressions.length);
			for (Expression parameterExpression : parameterExpressions) {
				this.parameterExpressions.add(parameterExpression);
			}
		}
	}
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		AST parametersAst = null;
		if (!contextless) {
			targetExpression = (Expression) module.createAst(cst.getFirstChild(), this);
			nameExpression = (NameExpression) module.createAst(cst.getSecondChild(), this);
			parametersAst = cst.getSecondChild().getFirstChild();
		}
		else {
			nameExpression = new NameExpression(cst.getText());
			nameExpression.setRegion(cst.getRegion());
			nameExpression.setUri(cst.getUri());
			nameExpression.setModule(cst.getModule());
			parametersAst = cst.getFirstChild();
		}
		
		List<AST> parametersChildren = parametersAst.getChildren();
		parameterExpressions.ensureCapacity(parameterExpressions.size()+parametersChildren.size());
		for (AST parameterAst : parametersChildren) {
			parameterExpressions.add((Expression) module.createAst(parameterAst, this));
		}
	}
	
	@Override
	public Object execute(IEolContext context) throws EolRuntimeException {
		Object targetObject;
		String operationName = nameExpression.getName();
		final ExecutorFactory executorFactory = context.getExecutorFactory();
		
		if (!contextless) {
			try {
				targetObject = executorFactory.execute(targetExpression, context);
			}
			catch (EolUndefinedVariableException | EolIllegalPropertyException npe) {
				switch (operationName) {
					default: throw npe;
					case "isDefined": case "isUndefined": case "ifDefined": case "ifUndefined": {
						targetObject = EolUndefined.INSTANCE;
						break;
					}
				}
			}
		}
		else {
			targetObject = EolNoType.NoInstance;
		}
		
		if (targetObject == null && isNullSafe()) {
			return null;
		}
		
		IModel owningModel = context.getModelRepository().getOwningModel(targetObject);
		
		// Non-overridable operations
		AbstractOperation operation = getAbstractOperation(targetObject, operationName, owningModel, context);
		if (operation != null && !operation.isOverridable()) {
			return operation.execute(targetObject, nameExpression, new ArrayList<Parameter>(0), parameterExpressions, context);
		}
		
		// Operation contributor for model elements
		OperationContributor operationContributor = null;
		
		// Method contributors that use the unevaluated AST
		ObjectMethod objectMethod = null;
		
		try {
			if (targetObject instanceof IOperationContributorProvider) {
				operationContributor = ((IOperationContributorProvider) targetObject).getOperationContributor();
			}
			else if (owningModel != null && owningModel instanceof IOperationContributorProvider) {
				operationContributor = ((IOperationContributorProvider) owningModel).getOperationContributor();
			}
			
			if (operationContributor != null) {
				objectMethod = operationContributor
					.findContributedMethodForUnevaluatedParameters(targetObject, operationName, parameterExpressions, context);
			}
			if (objectMethod == null) {
				objectMethod = context.getOperationContributorRegistry()
					.findContributedMethodForUnevaluatedParameters(targetObject, operationName, parameterExpressions, context);
			}
			
			if (objectMethod != null) {
				return wrap(objectMethod.execute(nameExpression, context, nameExpression)); 
			}
	
			// Evaluate the parameters
			ArrayList<Object> parameterValues = new ArrayList<>(parameterExpressions.size());
			for (Expression parameter : parameterExpressions) {
				parameterValues.add(executorFactory.execute(parameter, context));
			}
			
			Object module = context.getModule();
			// Execute user-defined operation (if isArrow() == false)
			if (module instanceof IEolModule && !isArrow()) {
				OperationList operations = ((IEolModule) module).getOperations();
				Operation helper = operations.getOperation(targetObject, nameExpression, parameterValues, context);
				if (helper != null) {
					return helper.execute(targetObject, parameterValues, context);
				}
			}
			
			Object[] parameterValuesArray = parameterValues.toArray();
			
			// Method contributors that use the evaluated parameters
			if (operationContributor != null) {
				// Try contributors that override the context's operation contributor registry
				objectMethod = operationContributor
					.findContributedMethodForEvaluatedParameters(targetObject, operationName, parameterValuesArray, context, true);
			}
			
			if (objectMethod == null) {
				objectMethod = context.getOperationContributorRegistry()
					.findContributedMethodForEvaluatedParameters(targetObject, operationName, parameterValuesArray, context);
			}
			
			if (operationContributor != null && objectMethod == null) {
				// Try contributors that do not override the context's operation contributor registry
				objectMethod = operationContributor
					.findContributedMethodForEvaluatedParameters(targetObject, operationName, parameterValuesArray, context, false);
			}
			if (objectMethod != null) {
				return wrap(objectMethod.execute(nameExpression, context, parameterValuesArray));
			}
	
			// Execute user-defined operation (if isArrow() == true)
			if (operation instanceof SimpleOperation) {
				return ((SimpleOperation) operation).execute(targetObject, parameterValues, context, nameExpression);
			}
	
			// Most likely a FirstOrderOperation or DynamicOperation
			if (operation != null && targetObject != null && !parameterExpressions.isEmpty()) {
				return operation.execute(targetObject, nameExpression, new ArrayList<>(0), parameterExpressions, context);
			}
			
			// No operation found
			throw new EolIllegalOperationException(targetObject, operationName, nameExpression, context.getPrettyPrinterManager());
		}
		finally {
			// Clean up ThreadLocal
			if (operationContributor != null) {
				operationContributor.close();
			}
			if (objectMethod != null) {
				objectMethod.close();
			}
		}
	}
	
	public void setContextless(boolean contextless) {
		this.contextless = contextless;
	}
	
	public boolean isContextless() {
		return contextless;
	}
	
	public List<Expression> getParameterExpressions() {
		return parameterExpressions;
	}
	
	public void accept(IEolVisitor visitor) {
		visitor.visit(this);
	}
}
