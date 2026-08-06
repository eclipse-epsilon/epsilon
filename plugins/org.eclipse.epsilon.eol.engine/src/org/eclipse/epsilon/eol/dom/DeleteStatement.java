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

import java.util.Collection;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.util.CollectionUtil;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.models.ModelRepository;
import org.eclipse.epsilon.eol.types.EolCollectionType;

public class DeleteStatement extends Statement {

	protected Expression expression;
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		expression = (Expression) module.createAst(cst.getFirstChild(), this);
	}
	
	@Override
	public Void execute(IEolContext context) throws EolRuntimeException {
		Object target = null;
		if (expression != null) {
			target = context.getExecutorFactory().execute(expression, context);
		}
		
		Collection<?> col = CollectionUtil.asCollection(target);
		ModelRepository modelRepo = context.getModelRepository();
		
		for (Object instance : EolCollectionType.clone(col)) {
			IModel model = modelRepo.getOwningModel(instance);
				
			if (model != null) {
				model.deleteElement(instance);
			}
		}
		return null;
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
