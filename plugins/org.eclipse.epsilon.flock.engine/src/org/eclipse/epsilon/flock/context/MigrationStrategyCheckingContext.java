/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.flock.context;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.flock.emc.wrappers.Model;
import org.eclipse.epsilon.flock.execute.FlockExecution;
import org.eclipse.epsilon.flock.execute.exceptions.FlockRuntimeException;

public class MigrationStrategyCheckingContext {
	
	private final Model originalModel;
	private final FlockExecution execution;
	
	public MigrationStrategyCheckingContext(Model originalModel, FlockExecution execution) {
		this.originalModel = originalModel;
		this.execution = execution;
	}
	
	public boolean isTypeInOriginalMetamodel(String type) {
		return originalModel.hasType(type);
	}
	
	public boolean isPropertyInOriginalMetamodel(String type, String property) throws FlockRuntimeException {
		try {
			return originalModel.hasProperty(type, property);
		
		} catch (EolRuntimeException e) {
			throw new FlockRuntimeException("Could not check existence of " + type + "#" + property + " in original metamodel", e);
		}
	}
	
	public boolean isPackageInOriginalMetamodel(String originalPackage) {
		return originalModel.hasPackage(originalPackage);
	}
	
	public void addWarning(String warning) {
		execution.addWarning(warning);
	}
}
