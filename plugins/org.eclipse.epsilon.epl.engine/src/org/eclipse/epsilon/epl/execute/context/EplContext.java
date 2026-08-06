/*********************************************************************
 * Copyright (c) 2019 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.epl.execute.context;

import org.eclipse.epsilon.epl.IEplModule;
import org.eclipse.epsilon.epl.execute.model.PatternMatchModel;
import org.eclipse.epsilon.erl.execute.context.ErlContext;

/**
 * 
 * @since 1.6
 */
public class EplContext extends ErlContext implements IEplContext {

	protected PatternMatchModel matchModel;

	public EplContext() {
		matchModel = new PatternMatchModel();
	}
	
	/**
	 * Copy constructor, for internal use only.
	 * @param other
	 */
	public EplContext(IEplContext other) {
		super(other);
		setPatternMatchTrace(other.getPatternMatchTrace());
	}
	
	@Override
	public void setPatternMatchTrace(PatternMatchModel model) {
		this.matchModel = model;
	}

	@Override
	public PatternMatchModel getPatternMatchTrace() {
		return this.matchModel;
	}
	
	@Override
	public IEplModule getModule() {
		return (IEplModule) super.getModule();
	}
}
