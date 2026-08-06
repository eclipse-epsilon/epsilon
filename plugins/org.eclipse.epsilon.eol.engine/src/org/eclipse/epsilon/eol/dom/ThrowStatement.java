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

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.EolUserException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;

public class ThrowStatement extends Statement {
	
	protected Expression thrown;
	
	public ThrowStatement() {}
	
	public ThrowStatement(Expression thrown) {
		setThrown(thrown);
	}
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		thrown = (Expression) module.createAst(cst.getFirstChild(), this);
	}
	
	@Override
	public Void execute(IEolContext context) throws EolRuntimeException {
		Object thrownObject = null;
		if (thrown != null) {
			thrownObject = context.getExecutorFactory().execute(thrown, context);
		}
		throw new EolUserException(thrownObject, this);	
	}
	
	public Expression getThrown() {
		return thrown;
	}
	
	public void setThrown(Expression thrown) {
		this.thrown = thrown;
	}
	
	public void accept(IEolVisitor visitor) {
		visitor.visit(this);
	}
}
