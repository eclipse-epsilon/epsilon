/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.epl.execute.context.concurrent;

import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.epl.IEplModule;
import org.eclipse.epsilon.epl.execute.context.EplContext;
import org.eclipse.epsilon.epl.execute.context.IEplContext;
import org.eclipse.epsilon.epl.execute.model.PatternMatchModel;
import org.eclipse.epsilon.erl.execute.context.concurrent.ErlContextParallel;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class EplContextParallel extends ErlContextParallel implements IEplContextParallel {

	protected PatternMatchModel matchModel;
	
	public EplContextParallel() {
		this(0);
	}
	
	public EplContextParallel(int parallelism) {
		super(parallelism);
		matchModel = new PatternMatchModel();
	}

	public EplContextParallel(IEolContext other) {
		super(other);
		if (other instanceof IEplContext) {
			setPatternMatchTrace(((IEplContext) other).getPatternMatchTrace());
		}
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
	protected IEplContext createShadowThreadLocalContext() {
		return new EplContext(this);
	}
	
	@Override
	public IEplContext getShadow() {
		return (IEplContext) super.getShadow();
	}
	
	@Override
	public IEplModule getModule() {
		return (IEplModule) super.getModule();
	}
}
