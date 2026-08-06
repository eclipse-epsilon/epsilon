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
import org.eclipse.epsilon.eol.models.IReflectiveModel;
import org.eclipse.epsilon.erl.execute.context.ErlContext;
import org.eclipse.epsilon.flock.IFlockModule;
import org.eclipse.epsilon.flock.context.ConservativeCopyContext;
import org.eclipse.epsilon.flock.context.EquivalenceEstablishmentContext;
import org.eclipse.epsilon.flock.context.MigrationStrategyCheckingContext;
import org.eclipse.epsilon.flock.emc.wrappers.Model;
import org.eclipse.epsilon.flock.execute.FlockExecution;
import org.eclipse.epsilon.flock.execute.FlockResult;
import org.eclipse.epsilon.flock.execute.exceptions.FlockRuntimeException;
import org.eclipse.epsilon.flock.execute.exceptions.FlockUnsupportedModelException;
import org.eclipse.epsilon.flock.execute.operations.FlockOperationFactory;
import org.eclipse.epsilon.flock.model.domain.MigrationStrategy;

public class FlockContext extends ErlContext implements IFlockContext {

	protected Model originalModel;
	protected Model migratedModel;
	
	public FlockContext() {
		setOperationFactory(new FlockOperationFactory());
	}
	
	public Model getOriginalModel() {
		return originalModel;
	}
	
	public Model getMigratedModel() {
		return migratedModel;
	}
	
	@Override
	public void setOriginalModel(IModel originalModel) throws FlockUnsupportedModelException {
		this.originalModel = addAndWrapModel(originalModel);
	}
	
	@Override
	public void setMigratedModel(IModel migratedModel) throws FlockUnsupportedModelException {
		this.migratedModel = addAndWrapModel(migratedModel);
	}
	
	private Model addAndWrapModel(IModel model) throws FlockUnsupportedModelException {
		if (model == null)
			throw new NullPointerException("model cannot be null");
		
		addModel(model);
		return wrapModel(model);
	}
	
	private void addModel(IModel model) {
		getModelRepository().addModel(model);
	}
	
	private Model wrapModel(IModel model) throws FlockUnsupportedModelException {
		if (model instanceof IReflectiveModel)
			return new Model((IReflectiveModel)model, getPrettyPrinterManager());
		else
			throw new FlockUnsupportedModelException("Flock can only be used with models that implement IReflectiveModel. " + model.getName() + " does not.");
	}
	
	@Override
	public void setOriginalModel(int indexInRepository) throws FlockUnsupportedModelException {
		this.originalModel = getModelFromRepositoryByIndex(indexInRepository);
	}
	
	@Override
	public void setMigratedModel(int indexInRepository) throws FlockUnsupportedModelException {
		this.migratedModel = getModelFromRepositoryByIndex(indexInRepository);
	}
	
	private Model getModelFromRepositoryByIndex(int index) throws FlockUnsupportedModelException {
		return wrapModel(getModelRepository().getModels().get(index));
	}
	
	private FlockExecution execution;
	
	@Override
	public FlockResult execute(MigrationStrategy strategy) throws FlockRuntimeException {
		ensureRolesAreAssignedToModels();
		
		execution = new FlockExecution(this, strategy);
		return execution.run(originalModel);
	}
	
	private void ensureRolesAreAssignedToModels() throws FlockUnsupportedModelException {
		if (originalModel == null) setOriginalModel(0);
		if (migratedModel == null) setMigratedModel(1);
	}
	
	
	@Override
	public EquivalenceEstablishmentContext getEquivalenceEstablishmentContext() {
		return new EquivalenceEstablishmentContext(originalModel, migratedModel, this, execution);
	}

	@Override
	public MigrationStrategyCheckingContext getMigrationStrategyCheckingContext() {
		return new MigrationStrategyCheckingContext(originalModel, execution);
	}
	
	@Override
	public ConservativeCopyContext getConservativeCopyContext() {
		return new ConservativeCopyContext(originalModel, migratedModel, execution);
	}
	
	@Override
	public IFlockModule getModule() {
		return (IFlockModule) super.getModule();
	}
}
