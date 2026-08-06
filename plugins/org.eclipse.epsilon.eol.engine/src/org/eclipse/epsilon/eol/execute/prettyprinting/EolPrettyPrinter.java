/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.prettyprinting;

import java.util.Collections;

import org.eclipse.epsilon.common.util.StringUtil;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.dom.Operation;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;

public class EolPrettyPrinter implements PrettyPrinter {
	
	protected IEolContext context;
	
	
	public EolPrettyPrinter (IEolContext context) {
		this.context = context;
	}

	@Override
	public boolean appliesTo(Object o) {
		
		try {
			Operation operation = ((IEolModule)context.getModule()).getOperations().getOperation(o, "toString", Collections.EMPTY_LIST, context);
			if (operation != null) return true;
		} catch (EolRuntimeException e) {
			return false;
		}
		
		return false;
	}

	@Override
	public String print(Object o) {
		Operation operation;
		try {
			operation = ((IEolModule)context.getModule()).getOperations().getOperation(o, "toString", Collections.emptyList(), context);
			return StringUtil.toString(operation.execute(o, Collections.emptyList(), context));
		} catch (EolRuntimeException e) {
			return e.getMessage();
		}
	}
}
