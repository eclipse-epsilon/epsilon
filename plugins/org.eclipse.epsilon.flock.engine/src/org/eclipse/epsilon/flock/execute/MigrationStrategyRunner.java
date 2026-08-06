/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************
 *
 * $Id$
 */
package org.eclipse.epsilon.flock.execute;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.flock.emc.wrappers.ModelElement;
import org.eclipse.epsilon.flock.equivalences.Equivalences;
import org.eclipse.epsilon.flock.execute.context.IFlockContext;
import org.eclipse.epsilon.flock.model.domain.MigrationStrategy;

public class MigrationStrategyRunner {

	private final IFlockContext context;
	private final MigrationStrategy strategy;
	
	public MigrationStrategyRunner(IFlockContext context, MigrationStrategy strategy) {
		this.context  = context;
		this.strategy = strategy;
	}
	
	private Equivalences equivalences;
	
	public void run() throws EolRuntimeException {
		checkStrategyAgainstModels();
		establishEquivalences();
		conservativeCopy();
		applyRules();
	}
	
	private void checkStrategyAgainstModels() {
		strategy.checkTypeMappingsAndRules(context.getMigrationStrategyCheckingContext());
	}

	private void establishEquivalences() throws EolRuntimeException {
		equivalences = Equivalences.establishFrom(strategy, context.getEquivalenceEstablishmentContext());
	}

	private void conservativeCopy() throws EolRuntimeException {
		equivalences.conservativeCopy(strategy, context.getConservativeCopyContext());
	}
	
	private void applyRules() throws EolRuntimeException {
		equivalences.applyRules(strategy);
	}
	
	public ModelElement getEquivalent(ModelElement originalModelElement) {
		return equivalences.getEquivalent(originalModelElement);
	}
}
