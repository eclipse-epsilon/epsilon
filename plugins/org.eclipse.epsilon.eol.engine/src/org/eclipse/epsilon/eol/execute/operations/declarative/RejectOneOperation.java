/*********************************************************************
* Copyright (c) 2018 The University of York.
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
import org.eclipse.epsilon.eol.dom.Expression;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.eclipse.epsilon.eol.dom.Parameter;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;

/**
 * Returns a new Collection containing one less element for which the predicate is not satisfied.
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class RejectOneOperation extends SelectBasedOperation {

	@Override
	public Collection<?> execute(Object target, NameExpression operationNameExpression, List<Parameter> iterators, List<Expression> expressions, IEolContext context) throws EolRuntimeException {
		
		Collection<?> rejectedCol = getDelegateOperation().execute(true, target, operationNameExpression, iterators, expressions.get(0), context);
		Collection<?> source = resolveSource(target, iterators, context);
		if (rejectedCol != null && !rejectedCol.isEmpty()) {
			source.remove(rejectedCol.iterator().next());
		}
		return source;
	}
}
