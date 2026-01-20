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
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.util.CollectionUtil;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.Break;
import org.eclipse.epsilon.eol.execute.Continue;
import org.eclipse.epsilon.eol.execute.ExecutorFactory;
import org.eclipse.epsilon.eol.execute.Return;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.types.EolModelElementType;
import org.eclipse.epsilon.eol.types.EolPrimitiveType;
import org.eclipse.epsilon.eol.types.EolType;

public class ForStatement extends Statement {
	
	protected static class TypeFilterIterator implements Iterator<Object> {
		private final Iterator<?> rawIterator;
		private final EolType iteratorType;
		Object filteredNext = null;

		protected TypeFilterIterator(Iterator<?> rawIterator, EolType iteratorType) {
			this.rawIterator = rawIterator;
			this.iteratorType = iteratorType;
		}

		@Override
		public boolean hasNext() {
			while (filteredNext == null && rawIterator.hasNext()) {
				Object val = rawIterator.next();
				if (iteratorType.isKind(val)) {
					filteredNext = val;
				}
			}
			return filteredNext != null;
		}

		@Override
		public Object next() {
			if (!hasNext()) throw new NoSuchElementException();
			Object ret = filteredNext;
			filteredNext = null;
			return ret;
		}
	}

	protected Parameter iteratorParameter;
	protected Expression iteratedExpression;
	protected StatementBlock bodyStatementBlock;
	
	public ForStatement() {}
	
	public ForStatement(Parameter iteratorParameter, Expression iteratedExpression, StatementBlock bodyStatementBlock) {
		this.iteratorParameter = iteratorParameter;
		this.iteratedExpression = iteratedExpression;
		this.bodyStatementBlock = bodyStatementBlock;
	}

	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		iteratorParameter = (Parameter) module.createAst(cst.getFirstChild(), this);
		iteratedExpression = (Expression) module.createAst(cst.getSecondChild(), this);
		bodyStatementBlock = toStatementBlock(module.createAst(cst.getThirdChild(), this));
	}
	
	@Override
	public Object execute(IEolContext context) throws EolRuntimeException {
		
		ExecutorFactory executorFactory = context.getExecutorFactory();
		Object iteratedObject = executorFactory.execute(this.iteratedExpression, context);
		
		Iterator<?> rawIterator = null;
		if (iteratedObject instanceof Iterator) {
			rawIterator = (Iterator<?>) iteratedObject;
		}
		else if (iteratedObject instanceof Iterable) {
			rawIterator = ((Iterable<?>) iteratedObject).iterator();
		}
		else if (iteratedObject instanceof EolModelElementType) {
			Collection<Object> col = CollectionUtil.createDefaultList(); 
			col.addAll(((EolModelElementType) iteratedObject).all());
			rawIterator = col.iterator();
		}
		else {
			Collection<Object> col = CollectionUtil.createDefaultList();
			col.add(iteratedObject);
			rawIterator = col.iterator();
		}
		
		EolType iteratorType = iteratorParameter.getType(context);
		Iterator<?> filteredIterator = new TypeFilterIterator(rawIterator, iteratorType);
		FrameStack frameStack = context.getFrameStack();
		
		for (int loop = 1; filteredIterator.hasNext();) {
			Object next = filteredIterator.next();
			
			if (!iteratorType.isKind(next)) continue;
			
			frameStack.enterLocal(FrameType.UNPROTECTED, this,
				new Variable(iteratorParameter.getName(), next, iteratorType),
				new Variable("hasMore", filteredIterator.hasNext(), EolPrimitiveType.Boolean, true),
				new Variable("loopCount", loop++, EolPrimitiveType.Integer, true)
			);
			
			Object result = null; 
			
			result = executorFactory.execute(bodyStatementBlock, context);
			frameStack.leaveLocal(this);
			
			if (result instanceof Return) {
				return (Return) result;
			}
			else if (result instanceof Break) {
				if (frameStack.isInLoop() && ((Break) result).isAll()) {
					return result;
				}
				else {
					break;
				}
			}
			else if (result instanceof Continue) {
				continue;
			}
			
		}
		
		return null;
	}
	
	public Expression getIteratedExpression() {
		return iteratedExpression;
	}
	
	public void setIteratedExpression(Expression iteratedExpression) {
		this.iteratedExpression = iteratedExpression;
	}
	
	public Parameter getIteratorParameter() {
		return iteratorParameter;
	}
	
	public void setIteratorParameter(Parameter iteratorParameter) {
		this.iteratorParameter = iteratorParameter;
	}
	
	public StatementBlock getBodyStatementBlock() {
		return bodyStatementBlock;
	}
	
	public void setBodyStatementBlock(StatementBlock bodyStatementBlock) {
		this.bodyStatementBlock = bodyStatementBlock;
	}
	
	public void accept(IEolVisitor visitor) {
		visitor.visit(this);
	}
}
