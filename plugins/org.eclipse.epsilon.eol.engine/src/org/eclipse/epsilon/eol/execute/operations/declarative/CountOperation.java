/*********************************************************************
* Copyright (c) 2019 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.execute.operations.declarative;

import java.util.List;
import org.eclipse.epsilon.eol.dom.Expression;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.eclipse.epsilon.eol.dom.Parameter;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.function.CheckedEolPredicate;

/**
 * Counts the number of elements satisfying the condition.
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class CountOperation extends FirstOrderOperation {

	@Override
	public Integer execute(Object target, NameExpression operationNameExpression, List<Parameter> iterators,
		List<Expression> expressions, IEolContext context) throws EolRuntimeException {
		
		final Iterable<?> source = resolveSource(target, iterators, context);
		final CheckedEolPredicate<Object> predicate = resolvePredicate(operationNameExpression, iterators, expressions.get(0), context);
		int result = 0;
		
		for (Object item : source) {
			if (predicate.testThrows(item)) {
				++result;
			}
		}
		
		return result;
	}

}
