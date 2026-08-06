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
import java.util.Collection;
import java.util.List;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.ExecutorFactory;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.parse.EolParser;
import org.eclipse.epsilon.eol.types.EolBag;
import org.eclipse.epsilon.eol.types.EolOrderedSet;
import org.eclipse.epsilon.eol.types.EolSequence;
import org.eclipse.epsilon.eol.types.EolSet;
import org.eclipse.epsilon.eol.types.concurrent.EolConcurrentBag;
import org.eclipse.epsilon.eol.types.concurrent.EolConcurrentSet;

public class CollectionLiteralExpression<T> extends LiteralExpression<Collection<T>> {
	
	protected String collectionType;
	protected boolean range;
	protected List<Expression> parameterExpressions = new ArrayList<>();
	
	public CollectionLiteralExpression() {
		super();
	}
	
	public CollectionLiteralExpression(String collectionType, Expression... parameterExpressions) {
		this.collectionType = collectionType;
		this.range = false;
		for (Expression parameterExpression : parameterExpressions) {
			this.parameterExpressions.add(parameterExpression);
		}
	}
	
	public CollectionLiteralExpression(String collectionType, boolean range, Expression... parameterExpressions) {
		this.collectionType = collectionType;
		this.range = range;
		for (Expression parameterExpression : parameterExpressions) {
			this.parameterExpressions.add(parameterExpression);
		}
	}

	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		this.collectionType = cst.getText();

		if (cst.getFirstChild() != null) {
			for (AST parameterAst : cst.getFirstChild().getChildren()) {
				parameterExpressions.add((Expression) module.createAst(parameterAst, this));
			}
			if (cst.getFirstChild().getType() == EolParser.EXPRRANGE) {
				range = true;
			}
		}
	}
	
	/**
	 * 
	 * @param collectionType
	 * @return
	 * @since 2.1
	 */
	public static <T> Collection<T> createCollection(String collectionType) {
		switch (collectionType) {
			case "Sequence": case "List":
				return new EolSequence<>();
			case "Set":
				return new EolSet<>();
			case "OrderedSet":
				return new EolOrderedSet<>();
			case "Bag": case "Collection":
				return new EolBag<>();
			case "ConcurrentBag":
				return new EolConcurrentBag<>();
			case "ConcurrentSet":
				return new EolConcurrentSet<>();
			default:
				return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<T> execute(IEolContext context) throws EolRuntimeException {
		Collection<T> collection = createCollection(collectionType);
		if (collection == null) {
			throw new EolRuntimeException("Unknown collection type: "+collectionType);
		}
		
		ExecutorFactory executorFactory = context.getExecutorFactory();
		
		if (range) {
			Expression rangeStartExpression = parameterExpressions.get(0);
			Expression rangeEndExpression = parameterExpressions.get(1);
			
			Object rangeStart = executorFactory.execute(rangeStartExpression, context);
			Object rangeEnd = executorFactory.execute(rangeEndExpression, context);
			
			if (rangeStart instanceof Integer && rangeEnd instanceof Integer) {
				int s = (int) rangeStart, e = (int) rangeEnd;
				if (s > e) {
					for (int i = s; i >= e; i--) {
						collection.add((T) (Integer) i);
					}
				}
				else {
					for (int i = s; i <= e; i++) {
						collection.add((T) (Integer) i);
					}
				}
			}
			else {
				if (!(rangeStart instanceof Integer)) {
					throw new EolRuntimeException("The start of a range should be of type Integer", rangeStartExpression);
				}
				if (!(rangeEnd instanceof Integer)) {
					throw new EolRuntimeException("The end of a range should be of type Integer", rangeEndExpression);
				}
				
			}
		}
		else {
			for (Expression parameterExpression : parameterExpressions) {
				collection.add((T) executorFactory.execute(parameterExpression, context));
			}
		}

		return collection;
	}
	
	public String getCollectionType() {
		return collectionType;
	}
	
	public void setCollectionType(String collectionType) {
		this.collectionType = collectionType;
	}
	
	public void accept(IEolVisitor visitor) {
		visitor.visit(this);
	}
	
	public List<Expression> getParameterExpressions() {
		return parameterExpressions;
	}
	
	public boolean isRange() {
		return range;
	}
	
	public void setRange(boolean range) {
		this.range = range;
	}
}
