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
import org.eclipse.epsilon.common.util.AstUtil;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.flowcontrol.EolAbortTransactionException;
import org.eclipse.epsilon.eol.execute.ExecutorFactory;
import org.eclipse.epsilon.eol.execute.Return;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.models.ModelRepository;
import org.eclipse.epsilon.eol.parse.EolParser;

public class TransactionStatement extends Statement {
	
	protected List<NameExpression> modelNames = new ArrayList<>(0);
	protected StatementBlock body;
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		for (AST modelNameAst : AstUtil.getChildren(cst, EolParser.NAME)) {
			modelNames.add((NameExpression) module.createAst(modelNameAst, this));
		}
		body = (StatementBlock) module.createAst(AstUtil.getChild(cst, EolParser.BLOCK), this);
	}
	
	@Override
	public Object execute(IEolContext context) throws EolRuntimeException {
		
		int modelsSize = modelNames.size();
		List<IModel> models = new ArrayList<>(modelsSize);
		ModelRepository modelRepository = context.getModelRepository();
		
		if (modelsSize > 0) {
			for (NameExpression modelNameAst : modelNames) {
				IModel model = modelRepository.getModelByName(modelNameAst.getName());
				models.add(model);
			}
		}
		else {
			models.addAll(modelRepository.getModels());
		}
		
		for (IModel model : models) {
			model.getTransactionSupport().startTransaction();
		}
		
		FrameStack frameStack = context.getFrameStack();
		ExecutorFactory executorFactory = context.getExecutorFactory();
		
		try {
			frameStack.enterLocal(FrameType.UNPROTECTED, this);
			Object result = executorFactory.execute(body, context);
			frameStack.leaveLocal(this);
			
			if (result instanceof Return) {
				for (IModel model : models) {
					model.getTransactionSupport().rollbackTransaction();
				}
				models.clear();
				return result;
			}
			else {
				for (IModel model : models) {
					model.getTransactionSupport().commitTransaction();
				}
			}
		}
		catch (EolRuntimeException ex) {
			frameStack.leaveLocal(this);
			for (IModel model : models) {
				model.getTransactionSupport().rollbackTransaction();
			}
			models.clear();
			if (!(ex instanceof EolAbortTransactionException)) {
				throw ex;
			}
		}
		
		return null;
	}
	
	public List<NameExpression> getModelNames() {
		return modelNames;
	}
	
	public void setModelNames(List<NameExpression> modelNames) {
		this.modelNames = modelNames;
	}
	
	public StatementBlock getBody() {
		return body;
	}
	
	public void setBody(StatementBlock body) {
		this.body = body;
	}
	
	public void accept(IEolVisitor visitor) {
		visitor.visit(this);
	}
}
