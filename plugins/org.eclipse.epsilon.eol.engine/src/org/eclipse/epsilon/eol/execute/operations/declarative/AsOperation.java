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

import java.util.List;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.dom.Expression;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.eclipse.epsilon.eol.dom.Parameter;
import org.eclipse.epsilon.eol.exceptions.EolIllegalOperationParametersException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.execute.operations.simple.SimpleOperation;
import org.eclipse.epsilon.eol.types.EolAnyType;

public class AsOperation extends SimpleOperation {
	
	@Override
	public Object execute(Object target,
			NameExpression operationNameExpression, List<Parameter> iterators,
			List<Expression> expressions, IEolContext context)
			throws EolRuntimeException {
		
		Expression varAst = expressions.get(0);
		if (varAst instanceof NameExpression) {
			String varName = ((NameExpression) varAst).getName();
			context.getFrameStack().put(new Variable(varName, target, EolAnyType.Instance));
			return target;
		}
		else {
			throw new EolIllegalOperationParametersException("as", operationNameExpression);
		}
	}

	@Override
	public boolean isOverridable() {
		return false;
	}

	@Override
	public Object execute(Object source, List<?> parameters,
			IEolContext context, ModuleElement ast) throws EolRuntimeException {
		return null;
	}
	
}
