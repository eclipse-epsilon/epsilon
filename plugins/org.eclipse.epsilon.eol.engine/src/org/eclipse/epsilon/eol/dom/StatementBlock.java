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
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.Break;
import org.eclipse.epsilon.eol.execute.Continue;
import org.eclipse.epsilon.eol.execute.ExecutorFactory;
import org.eclipse.epsilon.eol.execute.Return;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.IEolContext;

public class StatementBlock extends AbstractExecutableModuleElement {
	
	protected final ArrayList<Statement> statements = new ArrayList<>();
	
	public StatementBlock(Statement... statements) {
		if (statements != null) {
			this.statements.ensureCapacity(statements.length);
			for (Statement statement : statements) {
				if (statement != null) this.statements.add(statement);
			}
		}
	}
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		List<AST> children = cst.getChildren();
		statements.ensureCapacity(statements.size()+children.size());
		for (AST ast : children) {
			ModuleElement moduleElement = module.createAst(ast, this);
			if (moduleElement instanceof Statement) {
				statements.add((Statement) moduleElement);
			}
			else if (moduleElement instanceof Expression) {
				ExpressionStatement expressionStatement = new ExpressionStatement((Expression) moduleElement);
				expressionStatement.setParent(this);
				statements.add(expressionStatement);
			}
		}
	}
	
	public List<Statement> getStatements() {
		return statements;
	}
	
	@Override
	public Object execute(IEolContext context) throws EolRuntimeException {
		FrameStack frameStack = context.getFrameStack();
		ExecutorFactory executorFactory = context.getExecutorFactory();
		
		for (Statement statement : statements) {
			frameStack.setCurrentStatement(statement);
			Object result = executorFactory.execute(statement, context);
			if (result instanceof Return || result instanceof Break || result instanceof Continue) {
				return result;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "{...}";
	}
	
	public void accept(IEolVisitor visitor) {
		visitor.visit(this);
	}
}
