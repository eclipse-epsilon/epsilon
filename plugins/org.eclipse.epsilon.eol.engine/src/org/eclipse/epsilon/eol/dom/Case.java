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

import org.eclipse.epsilon.common.module.AbstractModuleElement;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;

public class Case extends AbstractModuleElement {
	
	protected Expression condition;
	protected StatementBlock body;
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		if (cst.getChildCount() == 2) { // regular case
			condition = (Expression) module.createAst(cst.getFirstChild(), this);
			body = (StatementBlock) module.createAst(cst.getSecondChild(), this);
		} else { // default
			body = (StatementBlock) module.createAst(cst.getFirstChild(), this);
		}
	}
	
	public Expression getCondition() {
		return condition;
	}
	
	public void setCondition(Expression condition) {
		this.condition = condition;
	}
	
	public StatementBlock getBody() {
		return body;
	}
	
	public void setBody(StatementBlock body) {
		this.body = body;
	}
	
	public void accept(IEolVisitor visitor) {
		visitor.visit(this);
	}
}
