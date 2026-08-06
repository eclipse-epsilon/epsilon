/*********************************************************************
* Copyright (c) 2008-2018 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.execute.operations.declarative;

import java.util.Collection;
import java.util.List;

import org.eclipse.epsilon.common.util.CollectionUtil;
import org.eclipse.epsilon.eol.dom.Expression;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.eclipse.epsilon.eol.dom.OperationCallExpression;
import org.eclipse.epsilon.eol.dom.Parameter;
import org.eclipse.epsilon.eol.exceptions.EolIllegalOperationParametersException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.execute.operations.AbstractOperation;
import org.eclipse.epsilon.eol.function.CheckedEolFunction;
import org.eclipse.epsilon.eol.function.CheckedEolPredicate;
import org.eclipse.epsilon.eol.function.EolLambdaFactory;
import org.eclipse.epsilon.eol.types.EolCollectionType;
import org.eclipse.epsilon.eol.types.EolType;

/**
 * 
 * @since 1.6 Major refactoring - EOL lambdas are converted to Java lambdas.
 */
public class FirstOrderOperation extends AbstractOperation {
	
	/**
	 * 
	 * @param item
	 * @param parameter
	 * @param context
	 * @return
	 * @throws EolRuntimeException
	 * @since 1.6
	 */
	protected static Variable createIteratorVariable(Object item, Parameter parameter, IEolContext context) throws EolRuntimeException {
		return new Variable(parameter.getName(), item, parameter.getType(context), true);
	}
	
	/**
	 * 
	 * @param target
	 * @param iterators
	 * @param context
	 * @return
	 * @throws EolRuntimeException
	 * @since 1.6
	 */
	protected Collection<Object> resolveSource(Object target, List<Parameter> iterators, IEolContext context) throws EolRuntimeException {
		final Collection<Object> source = CollectionUtil.asCollection(target);
		
		if (!iterators.isEmpty()) {
			EolType iteratorType = iterators.get(0).getType(context);
			Collection<Object> result = EolCollectionType.createSameType(source);
			
			for (Object item : source) {
				if (iteratorType.isKind(item)) {
					result.add(item);
				}
			}
			
			return result;
		}
		else return source;
	}
	
	/**
	 * 
	 * @param operationNameExpression
	 * @param iterators
	 * @param expressions
	 * @param context
	 * @return
	 * @throws EolRuntimeException
	 * @since 1.6
	 */
	protected final CheckedEolFunction<Object, ?> resolveFunction(NameExpression operationNameExpression, List<Parameter> iterators, Expression expression, IEolContext context) throws EolRuntimeException {
		return resolve(CheckedEolFunction.class, operationNameExpression, iterators, expression, context);
	}

	/**
	 * 
	 * @param operationNameExpression
	 * @param iterators
	 * @param expressions
	 * @param context
	 * @return
	 * @throws EolRuntimeException
	 *  @since 1.6
	 */
	protected final CheckedEolPredicate<Object> resolvePredicate(NameExpression operationNameExpression, List<Parameter> iterators, Expression expression, IEolContext context) throws EolRuntimeException {
		return resolve(CheckedEolPredicate.class, operationNameExpression, iterators, expression, context);
	}
	
	/**
	 * 
	 * @param <F>
	 * @param fType
	 * @param operationNameExpression
	 * @param iterators
	 * @param expression
	 * @param context
	 * @return
	 * @throws EolRuntimeException
	 * @since 1.6
	 */
	@SuppressWarnings("unchecked")
	protected <F> F resolve(Class<?> fType, NameExpression operationNameExpression, List<Parameter> iterators, Expression expression, IEolContext context) throws EolRuntimeException {
		if (iterators.isEmpty()) {
			Object exprValue = expression.execute(context);
			
			try {
				return (F) fType.cast(exprValue);
			}
			catch (ClassCastException ccx) {
				throw new EolIllegalOperationParametersException(
					((OperationCallExpression)expression.getParent()).getName(),
					fType.getSimpleName(),
					java.util.Objects.toString(exprValue),
					expression
				);
			}
		}
		else {
			return (F) EolLambdaFactory.resolveFor(fType, iterators, expression, operationNameExpression, context);
		}
	}
	
	/**
	 * 1.5 API-compatible UnsupportedOperationException-throwing implementation of execute()
	 * @deprecated Use one of the other execute methods instead
	 */
	@Deprecated
	public Object execute(Object target, Variable iterator, Expression expression, IEolContext context) throws EolRuntimeException {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Object execute(Object target, NameExpression operationNameExpression, List<Parameter> iterators, List<Expression> expressions, IEolContext context) throws EolRuntimeException {
		return execute(target, new Variable(iterators.get(0).getName(), iterators.get(0).getType(context)), expressions.get(0), context);
	}

}
