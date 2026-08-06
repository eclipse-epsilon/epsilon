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
package org.eclipse.epsilon.flock.execute.context;

import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.erl.execute.context.IErlContext;
import org.eclipse.epsilon.flock.context.ConservativeCopyContext;
import org.eclipse.epsilon.flock.context.EquivalenceEstablishmentContext;
import org.eclipse.epsilon.flock.context.MigrationStrategyCheckingContext;
import org.eclipse.epsilon.flock.execute.FlockResult;
import org.eclipse.epsilon.flock.execute.exceptions.FlockRuntimeException;
import org.eclipse.epsilon.flock.execute.exceptions.FlockUnsupportedModelException;
import org.eclipse.epsilon.flock.model.domain.MigrationStrategy;

public interface IFlockContext extends IErlContext {
	
	public void setOriginalModel(IModel model) throws FlockUnsupportedModelException;
	public void setMigratedModel(IModel model) throws FlockUnsupportedModelException;
	
	public void setOriginalModel(int indexInRepository) throws FlockUnsupportedModelException;
	public void setMigratedModel(int indexInRepository) throws FlockUnsupportedModelException;
				
	public FlockResult execute(MigrationStrategy strategy) throws FlockRuntimeException;
	
	public MigrationStrategyCheckingContext getMigrationStrategyCheckingContext();
	public EquivalenceEstablishmentContext getEquivalenceEstablishmentContext();
	public ConservativeCopyContext getConservativeCopyContext();
}
