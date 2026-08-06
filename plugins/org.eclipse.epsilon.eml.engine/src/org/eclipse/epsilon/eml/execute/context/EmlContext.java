/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eml.execute.context;

import org.eclipse.epsilon.ecl.trace.MatchTrace;
import org.eclipse.epsilon.eml.IEmlModule;
import org.eclipse.epsilon.eml.strategy.DefaultMergingStrategy;
import org.eclipse.epsilon.eml.strategy.IMergingStrategy;
import org.eclipse.epsilon.eml.trace.MergeTrace;
import org.eclipse.epsilon.etl.execute.context.EtlContext;

public class EmlContext extends EtlContext implements IEmlContext {

	protected MatchTrace matchTrace = new MatchTrace();
	protected MergeTrace mergeTrace = new MergeTrace();
	
	private IMergingStrategy mergingStrategy = new DefaultMergingStrategy();
	
	public EmlContext() {
		super();
	}
	
	@Override
	public IMergingStrategy getMergingStrategy() {
		return mergingStrategy;
	}

	@Override
	public void setMergingStrategy(IMergingStrategy mergingStrategy) {
		this.mergingStrategy = mergingStrategy;
	}

	@Override
	public MatchTrace getMatchTrace() {
		return matchTrace;
	}

	@Override
	public MergeTrace getMergeTrace() {
		return mergeTrace;
	}
	
	@Override
	public IEmlModule getModule() {
		return (IEmlModule) super.getModule();
	}
	
	@Override
	public void setModule(IEmlModule module) {
 		this.module = module;
	}

	@Override
	public void setMatchTrace(MatchTrace matchTrace) {
		this.matchTrace = matchTrace;
	}

	@Override
	public void setMergeTrace(MergeTrace mergeTrace) {
		this.mergeTrace = mergeTrace;
	}
}
