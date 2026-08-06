/*******************************************************************************
 * Copyright (c) 2008-2019 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Sina Madani - refactoring
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.operations.declarative;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.epsilon.eol.dom.Expression;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.eclipse.epsilon.eol.dom.Parameter;
import org.eclipse.epsilon.eol.dom.TypeExpression;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.function.CheckedEolPredicate;
import org.eclipse.epsilon.eol.types.EolCollectionType;

public class SelectOperation extends FirstOrderOperation {
	
	/**
	 * 
	 * @param returnOnMatch
	 * @param target
	 * @param operationNameExpression
	 * @param iterators
	 * @param expression
	 * @param context
	 * @return
	 * @throws EolRuntimeException
	 * @since 1.6
	 */
	public Collection<?> execute(boolean returnOnMatch, Object target, NameExpression operationNameExpression, List<Parameter> iterators, Expression expression, IEolContext context)  throws EolRuntimeException {
		
		final Collection<Object> source = resolveSource(target, iterators, context);
		final CheckedEolPredicate<Object> predicate = resolvePredicate(operationNameExpression, iterators, expression, context);
		final Collection<Object> result = EolCollectionType.createSameType(source);
		
		for (Object item : source) {
			if (predicate.testThrows(item)) {
				result.add(item);
				if (returnOnMatch) {
					return result;
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 1.5-compatible implementation of execute()
	 * @deprecated Use one of the other execute methods instead
	 */
	@Deprecated
	public Object execute(Object target, Variable iterator, Expression expression, IEolContext context, boolean returnOnMatch) throws EolRuntimeException {
		List<Parameter> parameters = new ArrayList<>();
		if (iterator != null) parameters = Arrays.asList(new Parameter(new NameExpression(iterator.getName()), new TypeExpression(iterator.getType().getName())));
		return execute(returnOnMatch, target, null, parameters, expression, context);
	}
	
	@Override
	public Collection<?> execute(Object target, NameExpression operationNameExpression, List<Parameter> iterators, List<Expression> expressions, IEolContext context) throws EolRuntimeException {
		Expression expression = null;
		Variable variable = null;
		if (!expressions.isEmpty()) expression = expressions.get(0);
		if (!iterators.isEmpty()) variable = new Variable(iterators.get(0).getName(), iterators.get(0).getType(context));
		return (Collection<?>) execute(target, variable, expression, context, false);
		//return execute(false, target, operationNameExpression, iterators, expressions.get(0), context);
	}
	
}
