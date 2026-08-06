/*******************************************************************************
 * Copyright (c) 2011 The University of York.
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
package org.eclipse.epsilon.workflow.tasks;

import org.eclipse.epsilon.flock.FlockModule;
import org.eclipse.epsilon.flock.IFlockModule;

public class FlockTask extends ExecutableModuleTask {

	private String originalModel, migratedModel;
	
	@Override
	protected IFlockModule createDefaultModule() throws Exception {
		return new FlockModule();
	}
	
	@Override
	protected void initialize() throws Exception {
		final IFlockModule migrator = (IFlockModule)module;
		
		migrator.getContext().setOriginalModel(migrator.getContext().getModelRepository().getModelByName(originalModel));
		migrator.getContext().setMigratedModel(migrator.getContext().getModelRepository().getModelByName(migratedModel));
	}

	@Override
	protected void examine() throws Exception {
		// Do nothing
	}

	public String getOriginalModel() {
		return originalModel;
	}

	public void setOriginalModel(String originalModel) {
		this.originalModel = originalModel;
	}

	public String getMigratedModel() {
		return migratedModel;
	}

	public void setMigratedModel(String migratedModel) {
		this.migratedModel = migratedModel;
	}
}
