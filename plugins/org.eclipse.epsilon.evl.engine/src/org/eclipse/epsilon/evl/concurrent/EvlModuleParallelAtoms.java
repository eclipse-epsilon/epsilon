/*********************************************************************
 * Copyright (c) 2019 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.evl.concurrent;

import java.util.List;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.erl.IErlModuleAtomBatches;
import org.eclipse.epsilon.erl.execute.context.concurrent.ErlContextParallel;
import org.eclipse.epsilon.evl.execute.atoms.EvlAtom;
import org.eclipse.epsilon.evl.execute.context.concurrent.IEvlContextParallel;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
public abstract class EvlModuleParallelAtoms<A extends EvlAtom<?>> extends EvlModuleParallel implements IErlModuleAtomBatches<A> {

	protected List<A> jobsCache;

	@Override
	public final List<? extends A> getAllJobs() throws EolRuntimeException {
		if (jobsCache == null) {
			jobsCache = getAllJobsImpl();
		}
		return jobsCache;
	}
	
	/**
	 * Non-cached implementation as called by {@link #getAllJobs()}.
	 * @return The deterministically ordered jobs.
	 */
	protected abstract List<A> getAllJobsImpl() throws EolRuntimeException;
	
	public Object executeAll(ErlContextParallel context) throws EolRuntimeException {
		return context.executeJob(getAllJobs());
	}
	
	@Override
	protected void checkConstraints() throws EolRuntimeException {
		if (context instanceof ErlContextParallel) {
			((ErlContextParallel) context).executeJob(getAllJobs());
		}
		else getContext().executeAll(this, getAllJobs());
	}
	
	protected EvlModuleParallelAtoms() {
		super();
	}

	protected EvlModuleParallelAtoms(IEvlContextParallel context) {
		super(context);
	}
}
