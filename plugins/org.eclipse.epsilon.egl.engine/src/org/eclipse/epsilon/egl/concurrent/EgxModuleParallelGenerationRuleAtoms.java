/*********************************************************************
 * Copyright (c) 2019 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.egl.concurrent;

import java.nio.file.Path;
import java.util.List;
import org.eclipse.epsilon.egl.exceptions.EglRuntimeException;
import org.eclipse.epsilon.egl.execute.atoms.GenerationRuleAtom;
import org.eclipse.epsilon.egl.execute.context.concurrent.IEgxContextParallel;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.erl.IErlModuleAtomBatches;
import org.eclipse.epsilon.erl.execute.context.concurrent.ErlContextParallel;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class EgxModuleParallelGenerationRuleAtoms extends EgxModuleParallel implements IErlModuleAtomBatches<GenerationRuleAtom> {

	public EgxModuleParallelGenerationRuleAtoms() {
		super();
	}

	public EgxModuleParallelGenerationRuleAtoms(Path outputRoot) throws EglRuntimeException {
		super(outputRoot);
	}

	public EgxModuleParallelGenerationRuleAtoms(IEgxContextParallel context) {
		super(context);
	}
	
	protected List<GenerationRuleAtom> jobsCache;
	
	protected List<GenerationRuleAtom> getAllJobsImpl() throws EolRuntimeException {
		return GenerationRuleAtom.getAllJobs(this);
	}

	@Override
	public final List<? extends GenerationRuleAtom> getAllJobs() throws EolRuntimeException {
		if (jobsCache == null) {
			jobsCache = getAllJobsImpl();
		}
		return jobsCache;
	}
	
	@Override
	protected Object processRules() throws EolRuntimeException {
		if (context instanceof ErlContextParallel) {
			return ((ErlContextParallel) context).executeJob(getAllJobs());
		}
		else return getContext().executeAll(this, getAllJobs());
	}
}
