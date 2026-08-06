/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.context;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;

public class AsyncStatementInstance {
	
	protected ModuleElement ast;
	protected FrameStack localFrameStack;
	
	public ModuleElement getAst() {
		return ast;
	}
	
	public void setAst(ModuleElement ast) {
		this.ast = ast;
	}
	
	public FrameStack getLocalFrameStack() {
		return localFrameStack;
	}
	
	public void setLocalFrameStack(FrameStack localFrameStack) {
		this.localFrameStack = localFrameStack;
	}
	
	public void execute(IEolContext context) throws EolRuntimeException {
		FrameStack frameStack = context.getFrameStack();
		context.setFrameStack(this.localFrameStack);
		context.getExecutorFactory().execute(this.ast, context);
		context.setFrameStack(frameStack);
		this.localFrameStack.dispose();
	}
}
