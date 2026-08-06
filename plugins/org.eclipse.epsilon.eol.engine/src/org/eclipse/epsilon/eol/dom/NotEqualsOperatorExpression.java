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
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.types.EolObjectComparator;

public class NotEqualsOperatorExpression extends EagerOperatorExpression {
	
	public NotEqualsOperatorExpression() {}
	
	public NotEqualsOperatorExpression(Expression firstOperand, Expression secondOperand) {
		super(firstOperand, secondOperand);
	}
	
	@Override
	public Boolean execute(Object o1, Object o2, IEolContext context) throws EolRuntimeException {
		return !EolObjectComparator.equals(o1, o2);
	}
	
	public void accept(IEolVisitor visitor) {
		visitor.visit(this);
	}
	
}
