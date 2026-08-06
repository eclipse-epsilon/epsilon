/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.evl.execute;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.models.transactions.ModelRepositoryTransactionSupport;
import org.eclipse.epsilon.evl.dom.Fix;
import org.eclipse.epsilon.evl.execute.context.IEvlContext;

public class FixInstance {
	
	protected Fix fix;
	protected Object self;
	protected IEvlContext context;
	protected FrameStack scope;
	protected String title;

	public FixInstance(IEvlContext context, Fix fix) {
		this.context = context;
		this.fix = fix;
		this.scope = context.getFrameStack().clone();
	}
	
	public IEvlContext getContext() {
		return context;
	}

	public Fix getFix() {
		return fix;
	}
	
	public Object getSelf() {
		return self;
	}

	public void setSelf(Object self) {
		this.self = self;
	}
	
	public String getTitle() throws EolRuntimeException {
		if (title == null) {
			try {
				FrameStack oldScope = context.getFrameStack();
				context.setFrameStack(this.scope);
				title = fix.getTitle(self, context);
				context.setFrameStack(oldScope);
			}
			catch (EolRuntimeException ex) {
				title = "<error>";
				throw ex;
			}
		}
		return title;
	}
	
	public void perform() throws EolRuntimeException {
		FrameStack oldScope = context.getFrameStack();
		context.setFrameStack(this.scope);
		ModelRepositoryTransactionSupport transactionSupport = context.getModelRepository().getTransactionSupport();
		try {
			transactionSupport.startTransaction();
			fix.execute(self, context);
			transactionSupport.commitTransaction();
		}
		catch (EolRuntimeException ex) {
			transactionSupport.rollbackTransaction();
			throw ex;
		}
		finally {
			context.setFrameStack(oldScope);
		}
	}
	
	@Override
	public String toString() {
		try {
			return getTitle();
		}
		catch (EolRuntimeException erx) {
			context.getErrorStream().println(erx.getMessage());
			return "An exception occured while evaluating the title of the fix";
		}
	}
}
