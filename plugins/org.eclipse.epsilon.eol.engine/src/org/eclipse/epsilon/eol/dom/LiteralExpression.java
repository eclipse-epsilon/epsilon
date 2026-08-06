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

public abstract class LiteralExpression<T> extends Expression {
	
	public LiteralExpression() {}
	
	public LiteralExpression(T value) {
		setValue(value);
	}
	
	protected T value;

	public T getValue() {
		return value;
	}
	
	public void setValue(T value) {
		this.value = value;
	}
	
	@Override
	public T execute(IEolContext context) throws EolRuntimeException {
		return getValue();
	}
	
	public abstract void accept(IEolVisitor visitor);
	
}
