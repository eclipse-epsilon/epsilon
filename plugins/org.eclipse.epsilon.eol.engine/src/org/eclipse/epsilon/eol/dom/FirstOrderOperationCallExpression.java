/*********************************************************************
* Copyright (c) 2008-2018 The University of York.
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
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.util.StringUtil;
import org.eclipse.epsilon.eol.exceptions.EolIllegalOperationException;
import org.eclipse.epsilon.eol.exceptions.EolNullPointerException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.operations.AbstractOperation;
import org.eclipse.epsilon.eol.execute.operations.declarative.CollectBasedOperation;
import org.eclipse.epsilon.eol.execute.operations.declarative.CollectOperation;
import org.eclipse.epsilon.eol.execute.operations.declarative.DelegateBasedOperation;
import org.eclipse.epsilon.eol.execute.operations.declarative.FirstOrderOperation;
import org.eclipse.epsilon.eol.execute.operations.declarative.SelectBasedOperation;
import org.eclipse.epsilon.eol.execute.operations.declarative.SelectOperation;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.parse.EolParser;
import org.eclipse.epsilon.eol.types.EolModelElementType;
import org.eclipse.epsilon.eol.types.EolNoType;
import org.eclipse.epsilon.eol.types.EolType;

public class FirstOrderOperationCallExpression extends FeatureCallExpression {
	
	protected List<Parameter> parameters = new ArrayList<>(2);
	protected List<Expression> expressions = new ArrayList<>(3);
	
	public FirstOrderOperationCallExpression() {}
	
	public FirstOrderOperationCallExpression(Expression targetExpression, NameExpression nameExpression, Parameter parameter, Expression lambdaExpression) {
		this.targetExpression = targetExpression;
		this.nameExpression = nameExpression;
		this.parameters.add(parameter);
		this.expressions.add(lambdaExpression);
	}
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		
		AST lambdaParamsContainer, exprAst;
		
		if (cst.getType() != EolParser.NAME && cst.getFirstChild().getType() != EolParser.PARAMLIST) { 
			targetExpression = (Expression) module.createAst(cst.getFirstChild(), this);
			exprAst = cst.getSecondChild();
			nameExpression = (NameExpression) module.createAst(exprAst, this);
		}
		else {
			nameExpression = new NameExpression(cst.getText());
			nameExpression.setRegion(cst.getRegion());
			nameExpression.setUri(cst.getUri());
			nameExpression.setModule(cst.getModule());
			exprAst = cst;
		}

		lambdaParamsContainer = exprAst != null ? exprAst.getFirstChild() : null;
		
		if (lambdaParamsContainer != null && lambdaParamsContainer.getType() == EolParser.PARAMLIST) {
			for (AST ast : lambdaParamsContainer.getChildren()) {
				parameters.add((Parameter) module.createAst(ast, this));
			}
		}
		
		if (exprAst != null) for (AST ast : exprAst.getChildren()) {
			if (parameters.isEmpty() || ast != lambdaParamsContainer) {
				ModuleElement resolved = module.createAst(ast, this);
				if (resolved instanceof Expression) {
					expressions.add((Expression) resolved);
				}
			}
		}
	}
	
	@Override
	public Object execute(IEolContext context) throws EolRuntimeException {
		Object target = EolNoType.Instance;
		
		if (targetExpression != null) {
			target = context.getExecutorFactory().execute(targetExpression, context);
		}
		else if (!parameters.isEmpty()) {
			EolType iterator = parameters.get(0).getType(context);
			if (iterator instanceof EolModelElementType) {
				target = ((EolModelElementType) iterator).getAllOfKind();
			}
		}
		
		String operationName = nameExpression.getName();
		
		if (target == null) {
			if (isNullSafe()) {
				return null;
			}
			else {
				throw new EolNullPointerException(operationName, targetExpression);
			}
		}
		
		IModel owningModel = context.getModelRepository().getOwningModel(target);
		AbstractOperation operation = getAbstractOperation(target, operationName, owningModel, context);
		
		replaceWithDelegateOperation(SelectBasedOperation.class, SelectOperation.class, operation, target, operationName, owningModel, context);
		replaceWithDelegateOperation(CollectBasedOperation.class, CollectOperation.class, operation, target, operationName, owningModel, context);
		
		return operation.execute(target, nameExpression, parameters, expressions, context);
	}
	
	/**
	 * @since 1.6
	 */
	@Override
	protected AbstractOperation getOperationFromContext(Object target, String name, IModel owningModel, IEolContext context) throws EolIllegalOperationException {
		return context.getOperationFactory().getOptimisedOperation(name, target, owningModel, context);
	}
	
	/**
	 * 
	 * @param <O>
	 * @param <D>
	 * @param delegateClass
	 * @param originalClass
	 * @param operation
	 * @param target
	 * @param originalOpName
	 * @param owningModel
	 * @param context
	 * @throws EolIllegalOperationException
	 * @since 1.6
	 */
	@SuppressWarnings("unchecked")
	private <O extends FirstOrderOperation, D extends DelegateBasedOperation<O>>
		void replaceWithDelegateOperation(Class<D> delegateClass, Class<O> originalClass,
			AbstractOperation operation, Object target, String originalOpName, IModel owningModel, IEolContext context) throws EolIllegalOperationException {
		
		if (delegateClass.isInstance(operation)) {
			D dbo = (D) operation;
			if (dbo.getDelegateOperation().getClass().equals(originalClass)) {
				String delegateOpName = originalClass.getSimpleName();
				delegateOpName = delegateOpName.substring(0, delegateOpName.indexOf("Operation"));
				if (originalOpName.startsWith("parallel") && !StringUtil.firstToLower(delegateOpName).startsWith("parallel")) {
					delegateOpName = "parallel" + delegateOpName;
				}
				else if (originalOpName.startsWith("sequential") && !StringUtil.firstToLower(delegateOpName).startsWith("sequential")) {
					delegateOpName = "sequential" + delegateOpName;
				}
				else {
					delegateOpName = StringUtil.firstToLower(delegateOpName);
				}
				O delegateOp = (O) getAbstractOperation(target, delegateOpName, owningModel, context);
				if (!delegateOp.getClass().equals(originalClass)) {
					dbo.setDelegateOperation(delegateOp);
				}
			}
		}
	}
		
	public List<Parameter> getParameters() {
		return parameters;
	}
	
	public List<Expression> getExpressions() {
		return expressions;
	}
	
	public void accept(IEolVisitor visitor) {
		visitor.visit(this);
	}
}
