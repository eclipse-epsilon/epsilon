/*********************************************************************
 * Copyright (c) 2019 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.erl;

import java.util.List;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.erl.execute.data.JobBatch;
import org.eclipse.epsilon.erl.execute.data.RuleAtom;

/**
 * Interface for rule-based languages with deterministically ordered jobs which can be
 * split into independent units of execution.
 * 
 * @author Sina Madani
 * @since 1.6
 * @param <D> The type of Rule-element pair.
 */
public interface IErlModuleAtomBatches<D extends RuleAtom<?>> extends IErlModule {

	/**
	 * The atomic units of work.
	 * 
	 * @return A deterministically ordered List of executable rule-element pairs.
	 * @throws EolRuntimeException If any problems are encountered when retrieving the jobs.
	 */
	List<? extends D> getAllJobs() throws EolRuntimeException;
	
	/**
	 * Splits the job list (as returned from {@link #getAllJobs()} into the specified number of batches.
	 * 
	 * @param batchSize This is interpreted as the size of batches, i.e. the distance between start and end indices.
	 * @return The list of start and end indices in order.
	 * @see {@link JobBatch#getBatches(int, int)}.
	 * @throws EolRuntimeException
	 */
	default List<JobBatch> getBatchJobs(int batchSize) throws EolRuntimeException {
		return JobBatch.getBatches(getAllJobs().size(), batchSize);
	}
}
