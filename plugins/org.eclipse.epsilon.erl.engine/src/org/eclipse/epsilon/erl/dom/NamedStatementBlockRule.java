/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.erl.dom;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.util.AstUtil;
import org.eclipse.epsilon.eol.dom.IExecutableModuleElement;
import org.eclipse.epsilon.eol.dom.StatementBlock;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.Return;
import org.eclipse.epsilon.eol.execute.context.IEolContext;

public class NamedStatementBlockRule extends NamedRule implements IExecutableModuleElement {
	
	protected StatementBlock body;
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		body = (StatementBlock) module.createAst(getBodyAst(cst), this);
	}
	
	protected AST getBodyAst(AST cst) {
		int childrenCount = AstUtil.getChildrenCount(cst);
		if (childrenCount == 2) {
			return AstUtil.getChildAt(cst, 1);
		}
		else if (childrenCount == 1) {
			return AstUtil.getChildAt(cst, 0);			
		}
		return null;
	}
	
	@Override
	protected AST getNameAst(AST cst) {
		int childrenCount = AstUtil.getChildrenCount(cst);
		if (childrenCount == 2) {
			return AstUtil.getChildAt(cst, 0);
		}
		return null;
	}
	
	public StatementBlock getBody() {
		return body;
	}
	
	public void setBody(StatementBlock body) {
		this.body = body;
	}

	@Override
	public Return execute(IEolContext context) throws EolRuntimeException {
		return (Return) context.getExecutorFactory().execute(body, context);
	}
	
}
