/*******************************************************************************
 * Copyright (c) 2008 The University of York.
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
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.function.CheckedEolFunction;
import org.eclipse.epsilon.eol.types.EolMap;
import org.eclipse.epsilon.eol.types.EolSequence;

public class MapByOperation extends FirstOrderOperation {

	@Override
	public EolMap<Object, EolSequence<Object>> execute(Object target, NameExpression operationNameExpression, List<Parameter> iterators, List<Expression> expressions, IEolContext context) throws EolRuntimeException {
		
		Collection<?> source = resolveSource(target, iterators, context);
		CheckedEolFunction<Object, ?> function = resolveFunction(operationNameExpression, iterators, expressions.get(0), context);
		
		EolMap<Object, EolSequence<Object>> result = new EolMap<>();
		if (source.isEmpty()) return result;

		for (Object item : source) {
			Object bodyResult = function.applyThrows(item);
			EolSequence<Object> sequence = result.get(bodyResult);
			if (sequence == null) sequence = new EolSequence<>();
			sequence.add(item);
			result.put(bodyResult, sequence);
		}
		
		return result;
	}
}
