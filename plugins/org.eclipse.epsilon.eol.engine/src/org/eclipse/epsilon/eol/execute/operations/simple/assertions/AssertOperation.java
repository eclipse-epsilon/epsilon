/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.operations.simple.assertions;

import java.util.List;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.exceptions.EolAssertionException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.operations.simple.SimpleOperation;

public class AssertOperation extends SimpleOperation {

	@Override
	public Void execute(Object source, List<?> parameters, IEolContext context,
			ModuleElement ast) throws EolRuntimeException {
		
		if (!context.isAssertionsEnabled()) {return null;}
		
		Object condition = null;
		Object message = null;
		
		if (parameters.size() > 0) {
			condition = parameters.get(0);
		}
		if (parameters.size() > 1) {
			message = parameters.get(1);
		}
		else {
			message = "Expected an error, but none was reported";
		}

		if (conditionSatisfied(condition)) return null;
		
		throw new EolAssertionException(context.getPrettyPrinterManager().toString(message), ast, true, false, null);
	}
	
	public boolean conditionSatisfied(Object condition) {
		if (condition instanceof Boolean) {
			if (((Boolean) condition).booleanValue()) {return true;}
		}
		return false;
	}
	
}
