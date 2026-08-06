/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.erl.parse;

import org.eclipse.epsilon.common.util.StringUtil;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.eclipse.epsilon.eol.dom.Expression;
import org.eclipse.epsilon.eol.dom.IExecutableModuleElement;
import org.eclipse.epsilon.eol.dom.StatementBlock;
import org.eclipse.epsilon.eol.parse.EolUnparser;
import org.eclipse.epsilon.erl.ErlModule;
import org.eclipse.epsilon.erl.dom.IErlVisitor;
import org.eclipse.epsilon.erl.dom.NamedStatementBlockRule;
import org.eclipse.epsilon.erl.dom.Post;
import org.eclipse.epsilon.erl.dom.Pre;

public abstract class ErlUnparser extends EolUnparser implements IErlVisitor {
	
	public String unparse(ErlModule module) {
		return super.unparse(module);
	}
	
	@Override
	protected void unparseMain() {
		((ErlModule) module).getDeclaredPre().forEach(p -> {p.accept(this); newline();});
		unparseRules();
		((ErlModule) module).getDeclaredPost().forEach(p -> {p.accept(this); newline();});
	}
	
	protected abstract void unparseRules();
	
	@Override
	public void visit(ExecutableBlock<?> executableBlock) {
		IExecutableModuleElement body = executableBlock.getBody();
		if (body instanceof StatementBlock) {
			space();
			((StatementBlock) body).accept(this);
		}
		else if (body instanceof Expression) {
			buffer.append(": ");
			((Expression) body).accept(this);
		}
	}
	
	@Override
	public void visit(Post post) {
		unparsePreAndPost("post", post);
	}

	@Override
	public void visit(Pre pre) {
		unparsePreAndPost("pre", pre);
	}
	
	protected void unparsePreAndPost(String label, NamedStatementBlockRule preOrPost) {
		unparseAnnotations(preOrPost);
		buffer.append(label + " ");
		String name = preOrPost.getName();
		if (!StringUtil.isEmpty(name)) {
			buffer.append(name + " ");
		}
		preOrPost.getBody().accept(this);
	}
	
	protected void print(String role, ExecutableBlock<?> executableBlock) {
		if (executableBlock != null) {
			newline();
			indent();
			buffer.append(role);
			space();
			executableBlock.accept(this);
			newline();
		}
	}
	
	protected void printGuard(ExecutableBlock<Boolean> guardBlock) {
		print("guard", guardBlock);
	}
}
