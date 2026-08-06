/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.ewl.execute;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.ewl.dom.Wizard;

public class WizardInstance {

	protected IEolContext context;
	protected Object self;
	protected Wizard wizard;
	protected FrameStack scope;
	
	public WizardInstance(Wizard wizard, Object self, IEolContext context) {
		super();
		this.context = context;
		this.scope = context.getFrameStack().clone();
		this.self = self;
		this.wizard = wizard;
	}

	public WizardInstance() {
		super();
	}

	public IEolContext getContext() {
		return context;
	}

	public void setContext(IEolContext context) {
		this.context = context;
	}

	public Object getSelf() {
		return self;
	}

	public void setSelf(Object self) {
		this.self = self;
	}

	public Wizard getWizard() {
		return wizard;
	}

	public void setWizard(Wizard template) {
		this.wizard = template;
	}
	
	public void process() throws EolRuntimeException {
		FrameStack oldScope = context.getFrameStack();
		context.setFrameStack(scope);
		try {
			context.getModelRepository().getTransactionSupport().startTransaction();
			wizard.process(self, context);
			context.getModelRepository().getTransactionSupport().commitTransaction();
		}
		catch (EolRuntimeException rex) {
			context.getModelRepository().getTransactionSupport().rollbackTransaction();
			throw rex;
		}
		finally {
			context.setFrameStack(oldScope);
		}
	}
	
	public String getTitle() throws EolRuntimeException {
		FrameStack oldScope = context.getFrameStack();
		context.setFrameStack(scope);
		try {
			return wizard.getTitle(self, context);
		}
		catch (EolRuntimeException rex) {
			throw rex;
		}
		finally {
			context.setFrameStack(oldScope);
		}
	}
}
