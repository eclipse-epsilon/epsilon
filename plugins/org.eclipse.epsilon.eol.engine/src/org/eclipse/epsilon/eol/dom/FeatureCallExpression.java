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
import org.eclipse.epsilon.eol.exceptions.EolIllegalOperationException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.operations.AbstractOperation;
import org.eclipse.epsilon.eol.execute.operations.DynamicOperation;
import org.eclipse.epsilon.eol.execute.operations.declarative.IAbstractOperationContributor;
import org.eclipse.epsilon.eol.execute.operations.declarative.IAbstractOperationContributorProvider;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.types.EolSequence;

public abstract class FeatureCallExpression extends Expression {
		
	protected boolean arrow, safe;
	protected Expression targetExpression;
	
	/**
	 * The feature name expression.
	 * @since 1.6
	 */
	protected NameExpression nameExpression;
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		this.arrow = "->".equals(cst.getText());
		this.safe = "?.".equals(cst.getText());
	}
	
	static Object wrap(Object o) {
		if (o instanceof Object[]) {
			Object[] arr = (Object[]) o;
			EolSequence<Object> seq = new EolSequence<>();
			seq.ensureCapacity(arr.length);
			for (Object element : arr) {
				seq.add(element);
			}
			return seq;
		}
		else return o;
	}
	
	protected AbstractOperation getAbstractOperation(Object target, String name, IModel owningModel, IEolContext context) throws EolIllegalOperationException {
		AbstractOperation operation = null;
		// Objects implementing the IAbstractOperationContributor interface
		// can override the default higher-order operation implementations
		if (target instanceof IAbstractOperationContributor) {
			operation = ((IAbstractOperationContributor) target).getAbstractOperation(name);
			if (operation != null) return operation;
		}
		
		// Since we don't control the interface of all model elements, models
		// can also provide IAbstractOperationContributors for their model elements
		if (owningModel != null && owningModel instanceof IAbstractOperationContributorProvider) {
			IAbstractOperationContributor contributor = ((IAbstractOperationContributorProvider) owningModel).getAbstractOperationContributor(target);
			if (contributor != null) {
				operation = contributor.getAbstractOperation(name);
				if (operation != null) return operation;					
			}
		}
		
		operation = getOperationFromContext(target, name, owningModel, context);
		
		if (operation == null && nameExpression != null) {
			operation = new DynamicOperation();
		}
		
		return operation;
	}
	
	/**
	 * Looks for the operation in the EolContext EolOperationFactory.
	 * 
	 * @param target The object this feature is called on.
	 * @param name The requested operation name.
	 * @param owningModel The model (optional).
	 * @param context The context from which the EolOperationFactory is derived.
	 * @return The operation.
	 * @throws EolIllegalOperationException 
	 * @since 1.6
	 */
	protected AbstractOperation getOperationFromContext(Object target, String name, IModel owningModel, IEolContext context) throws EolIllegalOperationException {
		return context.getOperationFactory().getOperationFor(name);
	}
	
	public Expression getTargetExpression() {
		return targetExpression;
	}
	
	public void setTargetExpression(Expression targetExpression) {
		this.targetExpression = targetExpression;
	}
	
	/**
	 * 
	 * @param nameExpression
	 * @since 1.6
	 */
	public void setNameExpression(NameExpression nameExpression) {
		this.nameExpression = nameExpression;
	}
	
	/**
	 * 
	 * @return
	 * @since 1.6
	 */
	public NameExpression getNameExpression() {
		return nameExpression;
	}
	
	/**
	 * Convenience method for <code>getNameExpression().getName()</code>.
	 * 
	 * @return The name of the feature call, or <code>null</code> if absent.
	 * @since 1.6
	 */
	public String getName() {
		return nameExpression != null ? nameExpression.getName() : null;
	}
	
	public boolean isArrow() {
		return arrow;
	}
	
	/**
	 * 
	 * @return
	 * @since 2.1
	 */
	public boolean isNullSafe() {
		return safe;
	}
	
	public abstract void accept(IEolVisitor visitor);
	
}
