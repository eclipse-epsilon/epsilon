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
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.eol.exceptions.EolIllegalReturnException;
import org.eclipse.epsilon.eol.exceptions.EolNoReturnException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.Return;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.parse.EolParser;

public class ExecutableBlock<T> extends AbstractExecutableModuleElement {
	
	protected IExecutableModuleElement body;
	protected Class<? extends T> expectedResultClass;
	protected String role = "";
	protected String text = "";
	
	public ExecutableBlock(Class<? extends T> expectedResultClass) {
		this.expectedResultClass = expectedResultClass;
	}
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		text = cst.getText();
		if (cst.getType() == EolParser.BLOCK) {
			StatementBlock statementBlock = new StatementBlock();
			statementBlock.setParent(this);
			Collection<Statement> statements = statementBlock.getStatements();
			
			for (AST childAst : cst.getChildren()) {
				ModuleElement childModuleElement = module.createAst(childAst, statementBlock);
				if (childModuleElement instanceof Statement) {
					statements.add((Statement) childModuleElement);
				}
				else if (childModuleElement instanceof Expression) {
					// Turn the expression into an expression statement so that it can be added to the statementBlock
					ExpressionStatement expressionStatement = new ExpressionStatement((Expression) childModuleElement);
					expressionStatement.setParent(statementBlock);
					statements.add(expressionStatement);
				}
			}
			body = statementBlock;
		}
		else {
			role = cst.getText();
			body = (IExecutableModuleElement) module.createAst(cst.getFirstChild(), this);
		}
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public IExecutableModuleElement getBody() {
		return body;
	}
	
	public void setBody(IExecutableModuleElement body) {
		this.body = body;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public T execute(IEolContext context, Variable... variables) throws EolRuntimeException {
		return execute(context, true, variables);
	}
	
	@Override
	public T execute(IEolContext context) throws EolRuntimeException {
		return execute(context, new Variable[]{});
	}
	
	protected Object executeBlockOrExpressionAst(IExecutableModuleElement ast, IEolContext context) throws EolRuntimeException {
		if (ast == null) return null;
		
		Object result = context.getExecutorFactory().execute(ast, context);
		
		if (ast instanceof StatementBlock) {
			return result;
		}
		else {
			return new Return(result);
		}
	}
	
	@SuppressWarnings("unchecked")
	public T execute(IEolContext context, boolean inNewFrame, FrameType frameType, Variable... variables) throws EolRuntimeException {
		FrameStack frameStack = context.getFrameStack();
		
		if (inNewFrame) frameStack.enterLocal(frameType, this);

		// We temporarily change entry point if needed, to help with debugging
		ModuleElement oldEntryPoint = frameStack.getTopFrame().getEntryPoint();
		if (oldEntryPoint != this) {
			frameStack.getTopFrame().setEntryPoint(this);
		}

		Object result;
		try {
			frameStack.put(variables);
			result = executeBody(context);
		} finally {
			// If we changed the entry point, restore it to its old value
			if (oldEntryPoint != this) {
				frameStack.getTopFrame().setEntryPoint(oldEntryPoint);
			}
		}
		
		if (inNewFrame) frameStack.leaveLocal(this);
		
		T retVal = null;
		Class<?> expResClass = getExpectedResultClass();
		
		if (result instanceof Return) {
			Object value = Return.getValue(result);
			if (expResClass == null) {
				retVal = (T) result;
			}
			else if ((value == null && expResClass == Void.class) || expResClass.isInstance(value)) {
				retVal = (T) value;
			}
			else if (expResClass == String.class && !(value instanceof String)) {
				retVal = (T) (value + "");
			}
			else {
				throw new EolIllegalReturnException(expResClass.getSimpleName(), value, this, context);
			}
		}
		else if (expResClass != Void.class) {
			throw new EolNoReturnException(expResClass.getSimpleName(), this, context);
		}
		
		postExecution();
		return retVal;		
	}
	
	/**
	 * Any chores to be done after execution
	 */
	protected void postExecution() {
		
	}

	/**
	 * @param context
	 * @return
	 * @throws EolRuntimeException
	 */
	public Object executeBody(IEolContext context) throws EolRuntimeException {
		return executeBlockOrExpressionAst(getBody(), context);
	}
	
	public T execute(IEolContext context, boolean inNewFrame, Variable... variables) throws EolRuntimeException {
		return execute(context, inNewFrame, FrameType.UNPROTECTED, variables);
	}
	
	protected Class<? extends T> getExpectedResultClass() {
		return expectedResultClass;
	}
	
	public void accept(IEolVisitor visitor) {
		visitor.visit(this);
	}
}
