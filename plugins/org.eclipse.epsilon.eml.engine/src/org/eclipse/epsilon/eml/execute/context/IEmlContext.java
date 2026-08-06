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
import org.eclipse.epsilon.eml.strategy.IMergingStrategy;
import org.eclipse.epsilon.eml.trace.MergeTrace;
import org.eclipse.epsilon.etl.execute.context.IEtlContext;

public interface IEmlContext extends IEtlContext {

	public IMergingStrategy getMergingStrategy();

	public void setMergingStrategy(IMergingStrategy mergingStrategy);

	public MatchTrace getMatchTrace();

	public MergeTrace getMergeTrace();

	@Override
	public default IEmlModule getModule() {
		return (IEmlModule) ((IEtlContext)this).getModule();
	}
	
	public void setModule(IEmlModule module);
	
	public void setMatchTrace(MatchTrace matchTrace);

	public void setMergeTrace(MergeTrace mergeTrace);

}
