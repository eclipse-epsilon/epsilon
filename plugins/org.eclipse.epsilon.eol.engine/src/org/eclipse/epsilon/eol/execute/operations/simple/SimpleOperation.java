/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.operations.simple;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.dom.Expression;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.eclipse.epsilon.eol.dom.Parameter;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.ExecutorFactory;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.operations.AbstractOperation;

public abstract class SimpleOperation extends AbstractOperation {
	
	@Override
	public Object execute(Object target, NameExpression operationNameExpression, List<Parameter> iterators, List<Expression> expressions, IEolContext context) throws EolRuntimeException {
		List<Object> parameters = new ArrayList<>(expressions.size());
		Iterator<Expression> it = expressions.iterator();
		ExecutorFactory executorFactory = context.getExecutorFactory();
		
		for (int parameterIndex = 0; it.hasNext(); parameterIndex++) {
			try {
				parameters.add(executorFactory.execute(it.next(), context));
			}
			catch (EolRuntimeException ex) {
				if (getTolerateExceptionInParameter(parameterIndex)) {
					parameters.add(ex);
				}
				else {
					throw ex;
				}
			}
			catch (Throwable t) {
				context.getErrorStream().println("THROWABLE " + t.getClass().getName());
			}
		}

		try {
			return execute(target, parameters, context, operationNameExpression);
		}
		catch (EolRuntimeException ex) {
			if (ex.getAst() == null) {
				ex.setAst(operationNameExpression);
			}
			throw ex;
		}
	}
	
	public abstract Object execute(Object source, List<?> parameters, IEolContext context, ModuleElement ast) throws EolRuntimeException;
	
	public boolean getTolerateExceptionInParameter(int parameterIndex) {
		return false;
	}
}
