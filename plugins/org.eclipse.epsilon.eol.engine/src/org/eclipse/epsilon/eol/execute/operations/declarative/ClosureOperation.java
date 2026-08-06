/*******************************************************************************
 * Copyright (c) 2008-2020 The University of York, Aston University.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Antonio Garcia-Dominguez - switch to use a LinkedHashSet
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.operations.declarative;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.epsilon.common.util.CollectionUtil;
import org.eclipse.epsilon.eol.dom.Expression;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.eclipse.epsilon.eol.dom.Parameter;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.ExecutorFactory;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.types.EolOrderedSet;
import org.eclipse.epsilon.eol.types.EolType;

public class ClosureOperation extends FirstOrderOperation {
	
	protected void closure(Collection<Object> closure, Collection<?> source, Parameter parameter, Expression expression, IEolContext context) throws EolRuntimeException {
		FrameStack scope = context.getFrameStack();
		ExecutorFactory executorFactory = context.getExecutorFactory();
		EolType parameterType = parameter.getType(context);
		
		for (Object item : source) {
			scope.enterLocal(FrameType.UNPROTECTED, expression, createIteratorVariable(item, parameter, context));
			Object bodyResult = executorFactory.execute(expression, context);
			
			if (bodyResult != null) {
				Collection<?> bodyCollection = CollectionUtil.asCollection(bodyResult);
				for (Object result : bodyCollection) {
					if (result != null && closure.add(result)) {
						if (parameterType == null || parameterType.isKind(result)) {
							closure(closure, Collections.singletonList(result), parameter, expression, context);
						}
					}
				}
			}
			scope.leaveLocal(expression);
		}
	}

	@Override
	public Object execute(Object target, NameExpression operationNameExpression, List<Parameter> iterators, List<Expression> expressions, IEolContext context) throws EolRuntimeException {
		Collection<?> source = resolveSource(target, iterators, context);
		EolOrderedSet<Object> accumulator = new EolOrderedSet<Object>();
		closure(accumulator, source, iterators.get(0), expressions.get(0), context);
		return accumulator;
	}
	
}
