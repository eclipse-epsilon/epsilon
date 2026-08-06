/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.dom;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.flowcontrol.EolAbortTransactionException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;

public class AbortStatement extends Statement {

	@Override
	public Object execute(IEolContext context) throws EolRuntimeException {
		throw new EolAbortTransactionException();
	}
	
	public void accept(IEolVisitor visitor) {
		visitor.visit(this);
	}
	
}
