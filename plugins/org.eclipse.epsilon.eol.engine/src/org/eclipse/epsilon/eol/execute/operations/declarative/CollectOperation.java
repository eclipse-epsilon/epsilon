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
import org.eclipse.epsilon.eol.types.EolBag;
import org.eclipse.epsilon.eol.types.EolCollectionType;
import org.eclipse.epsilon.eol.types.EolSequence;

public class CollectOperation extends FirstOrderOperation {

	@Override
	public Collection<?> execute(Object target, NameExpression operationNameExpression, List<Parameter> iterators, List<Expression> expressions, IEolContext context) throws EolRuntimeException {
		
		Collection<Object> source = resolveSource(target, iterators, context);
		CheckedEolFunction<Object, ?> function = resolveFunction(operationNameExpression, iterators, expressions.get(0), context);
		
		Collection<Object> result = EolCollectionType.isOrdered(source) ?
			new EolSequence<>() : new EolBag<>();
		
		if (result instanceof EolSequence) {
			((EolSequence<Object>) result).ensureCapacity(source.size());
		}
		
		for (Object item : source) {
			result.add(function.applyThrows(item));
		}

		return result;
	}
}
