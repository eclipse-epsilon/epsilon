/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.operations.declarative;

import java.util.Collection;
import java.util.List;
import org.eclipse.epsilon.eol.dom.Expression;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.eclipse.epsilon.eol.dom.Parameter;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.ExecutorFactory;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.types.EolMap;

public class AggregateOperation extends FirstOrderOperation {
	
	@Override
	public EolMap<?, ?> execute(Object target, NameExpression operationNameExpression, List<Parameter> iterators, List<Expression> expressions, IEolContext context) throws EolRuntimeException {

		EolMap<Object, Object> result = new EolMap<>();
		
		Collection<Object> source = resolveSource(target, iterators, context);
		if (source.isEmpty()) return result;

		Expression keyExpression = expressions.get(0);
		Expression valueExpression = expressions.get(1);
		Expression initialExpression = expressions.size() > 2 ? expressions.get(2) : null;
		
		FrameStack scope = context.getFrameStack();
		ExecutorFactory executorFactory = context.getExecutorFactory();
		
		for (Object item : source) {
			scope.enterLocal(FrameType.UNPROTECTED, keyExpression, createIteratorVariable(item, iterators.get(0), context));
			
			Object total, keyResult = executorFactory.execute(keyExpression, context);
			
			if (result.containsKey(keyResult)) {
				total = result.get(keyResult);
			}
			else {
				total = executorFactory.execute(initialExpression, context);
			}
			
			scope.put(Variable.createReadOnlyVariable("total", total));
			Object valueResult = executorFactory.execute(valueExpression, context);
			result.put(keyResult, valueResult);
			scope.leaveLocal(keyExpression);
		}
		
		return result;
	}
}
