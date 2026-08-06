/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.csvpro;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.eclipse.epsilon.eol.dom.EqualsOperatorExpression;
import org.eclipse.epsilon.eol.dom.Expression;
import org.eclipse.epsilon.eol.dom.GreaterEqualOperatorExpression;
import org.eclipse.epsilon.eol.dom.GreaterThanOperatorExpression;
import org.eclipse.epsilon.eol.dom.LessEqualOperatorExpression;
import org.eclipse.epsilon.eol.dom.LessThanOperatorExpression;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.eclipse.epsilon.eol.dom.NotEqualsOperatorExpression;
import org.eclipse.epsilon.eol.dom.OperatorExpression;
import org.eclipse.epsilon.eol.dom.Parameter;
import org.eclipse.epsilon.eol.dom.PropertyCallExpression;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.operations.declarative.SelectOperation;

public class CsvProCollectionSelectOperation extends SelectOperation {
	
	private String index;
	
	public CsvProCollectionSelectOperation(String index) {
		super();
		this.index = index;
	}
	
	@Override
	public Collection<?> execute(boolean returnOnMatch, Object target, NameExpression operationNameExpression,
		List<Parameter> iterators, Expression expression, IEolContext context) throws EolRuntimeException {

		if (!(target instanceof CsvProCollection) || !isOptimisable(expression, iterators.get(0))) {
			return super.execute(returnOnMatch, target, operationNameExpression, iterators, expression, context);
		}
		try {
			return optimisedExecution(returnOnMatch, (CsvProCollection)target, operationNameExpression, iterators, expression, context);

		} catch (Exception e) {
			e.printStackTrace();
			throw new EolRuntimeException("OptimisableCollectionSelectOperation: parseAST(iterator, ast) failed:", expression);
		}

	}

	private boolean isOptimisable(Expression ast, Parameter parameter) {
		// WE support >, <, >=, <=, == and <>
		if (!(ast instanceof EqualsOperatorExpression ||
				ast instanceof GreaterThanOperatorExpression ||
				ast instanceof GreaterEqualOperatorExpression ||
				ast instanceof LessEqualOperatorExpression ||
				ast instanceof LessThanOperatorExpression ||
				ast instanceof NotEqualsOperatorExpression)) {
			return false;
		}
		// LEFT - we should have iterator.property
		// L1. Check for a property call expression
		final OperatorExpression opExp = (OperatorExpression) ast;
		final Expression rawLOperand = opExp.getFirstOperand();
		if (!(rawLOperand instanceof PropertyCallExpression)) {
			return false;
		}
		final PropertyCallExpression lOperand = (PropertyCallExpression) rawLOperand;

		// L2. Check that we're using the iterator
		final Expression rawTargetExpression = lOperand.getTargetExpression();
		if (!(lOperand.getTargetExpression() instanceof NameExpression)) {
			return false;
		}
		final NameExpression nameExpression = (NameExpression) rawTargetExpression;
		if (!parameter.getName().equals(nameExpression.getName())) {
			return false;
		}
		// Check that we are accessing the index property
		final NameExpression propertyNameExpression = lOperand.getPropertyNameExpression();
		if (!index.equals(propertyNameExpression.getName())) {
			return false;
		}
		return true;
	}
	
	private Collection<?> optimisedExecution(boolean returnOnMatch, CsvProCollection target, NameExpression operationNameExpression,
		List<Parameter> iterators, Expression ast, IEolContext context) throws EolRuntimeException {
		
		final OperatorExpression opExp = (OperatorExpression) ast;
		final Expression valueAST = opExp.getSecondOperand();
		Object attributevalue = null;
		try {
			attributevalue = context.getExecutorFactory().execute(valueAST, context);
		}
		catch (Exception e) {
			// if the rhs is invalid or tries to use the iterator of the select
			// (which is outside its scope) -- default to epsilon's select
			// FIXME Make message more Artisan like
			System.err.println("Warning: the RHS of the expression:\n" + ast
					+ "\ncannot be evaluated using indexing,\nas the iterator variable of the current select operation ("
					+ iterators.get(0).getName() + ") is not used in this process.\nDefaulting to Epsilon's select");
		}
		if (attributevalue instanceof String) {
			String id = (String) attributevalue;
			Collection<Integer> result = null;
			if (ast instanceof EqualsOperatorExpression) {
				result = new ArrayList<>(1);
				Integer obj = target.get(id);
				if (obj != null) {
					result.add(obj);
				}
			}
			else if (ast instanceof GreaterThanOperatorExpression) {
				result = target.tailMap(id, false).values();
			}
			else if (ast instanceof GreaterEqualOperatorExpression) {
				result = target.tailMap(id, true).values();
			}
			else if (ast instanceof LessThanOperatorExpression) {
				result = target.headMap(id, false).values();
			}
			else if (ast instanceof LessEqualOperatorExpression) {
				result = target.headMap(id, true).values();
			}
			else if (ast instanceof NotEqualsOperatorExpression) {
				result = new ArrayList<>(target);
				result.remove(target.get(id));
			}
			return result;
		}
		else {
			return super.execute(returnOnMatch, target, operationNameExpression, iterators, ast, context);
		}
	}
	
}
