/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.execute.operations.declarative;

import java.util.Iterator;
import java.util.List;
import org.eclipse.epsilon.common.util.CollectionUtil;
import org.eclipse.epsilon.eol.dom.Expression;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.eclipse.epsilon.eol.dom.Parameter;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.models.ISearchableModel;

public class FindOperation extends SelectBasedOperation {

	@Override
	public Object execute(Object target, NameExpression operationNameExpression, List<Parameter> iterators, List<Expression> expressions, IEolContext context) throws EolRuntimeException {

		Iterator<?> sourceIter = CollectionUtil.asCollection(target).iterator();
		Object next;
		
		if (sourceIter.hasNext() && (next = sourceIter.next()) instanceof ISearchableModel) {
			return ((ISearchableModel)next).findOne(createIteratorVariable(null, iterators.get(0), context), expressions.get(0), context);
		}
		else {
			return getDelegateOperation().execute(target, operationNameExpression, iterators, expressions, context);
		}
	}
	
}
