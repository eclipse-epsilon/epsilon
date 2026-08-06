/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.erl.execute.context;

import org.eclipse.epsilon.eol.execute.ExecutorFactory;
import org.eclipse.epsilon.eol.execute.context.EolContext;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.erl.IErlModule;
import org.eclipse.epsilon.erl.execute.RuleExecutorFactory;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class ErlContext extends EolContext implements IErlContext {

	public ErlContext() {
		super();
		setExecutorFactory(new RuleExecutorFactory());
	}
	
	/**
	 * Copy constructor, intended for internal use only.
	 * @param other The parent context.
	 */
	public ErlContext(IEolContext other) {
		super(other);
		setExecutorFactory(executorFactory);
	}

	@Override
	public void setProfilingEnabled(boolean profilingEnabled) {
		super.setProfilingEnabled(profilingEnabled);
		getExecutorFactory().setProfilingEnabled(profilingEnabled);
	}
	
	@Override
	public void setExecutorFactory(ExecutorFactory executorFactory) {
		super.setExecutorFactory(executorFactory instanceof RuleExecutorFactory ?
			executorFactory : new RuleExecutorFactory(executorFactory)
		);
	}
	
	@Override
	public RuleExecutorFactory getExecutorFactory() {
		return (RuleExecutorFactory) super.getExecutorFactory();
	}
	
	@Override
	public IErlModule getModule() {
		return (IErlModule) super.getModule();
	}
}
