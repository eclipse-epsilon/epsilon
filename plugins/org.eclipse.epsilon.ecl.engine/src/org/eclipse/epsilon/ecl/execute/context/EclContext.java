/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.ecl.execute.context;

import org.eclipse.epsilon.ecl.IEclModule;
import org.eclipse.epsilon.ecl.trace.MatchTrace;
import org.eclipse.epsilon.erl.execute.context.ErlContext;

public class EclContext extends ErlContext implements IEclContext {
	
	protected MatchTrace matchTrace = new MatchTrace();
	protected MatchTrace tempMatchTrace;
	
	public EclContext() {
		super();
	}
	
	/**
	 * Copy constructor, for internal use only.
	 * @since 1.6
	 */
	public EclContext(IEclContext other) {
		super(other);
		this.matchTrace = other.getMatchTrace();
		this.tempMatchTrace = other.getTempMatchTrace();
	}
	
	@Override
	public void setMatchTrace(MatchTrace matchTrace) {
		this.matchTrace = matchTrace;
	}
	
	@Override
	public MatchTrace getMatchTrace() {
		return matchTrace;
	}
	
	@Override
	public MatchTrace getTempMatchTrace() {
		if (tempMatchTrace == null) {
			tempMatchTrace = new MatchTrace();
		}
		return tempMatchTrace;
	}
	
	@Override
	public IEclModule getModule() {
		return (IEclModule) super.getModule();
	}
}
