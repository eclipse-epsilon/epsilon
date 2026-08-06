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

import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.erl.IErlModule;
import org.eclipse.epsilon.erl.execute.RuleExecutorFactory;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
public interface IErlContext extends IEolContext {
	
	@Override
	RuleExecutorFactory getExecutorFactory();
	
	/**
	 * Casts the IModule to IErlModule
	 * @see org.eclipse.epsilon.eol.execute.context.IEolContext#getModule()
	 */
	@Override
	default IErlModule getModule() {
		return (IErlModule) ((IEolContext)this).getModule();
	}
}
