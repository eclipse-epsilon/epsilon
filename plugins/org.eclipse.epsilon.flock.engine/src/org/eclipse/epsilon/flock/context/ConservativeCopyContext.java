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
import org.eclipse.epsilon.flock.emc.wrappers.ModelElement;
import org.eclipse.epsilon.flock.emc.wrappers.ModelValue;
import org.eclipse.epsilon.flock.equivalences.Equivalence;
import org.eclipse.epsilon.flock.execute.FlockExecution;
import org.eclipse.epsilon.flock.execute.exceptions.ConservativeCopyException;
import org.eclipse.epsilon.flock.model.domain.MigrationStrategy;
import org.eclipse.epsilon.flock.model.domain.rules.IgnoredProperties;

public class ConservativeCopyContext {
	
	private final Model originalModel;
	private final Model migratedModel;
	private final FlockExecution execution;
		
	public ConservativeCopyContext(Model originalModel, Model migratedModel, FlockExecution execution) {
		this.originalModel = originalModel;
		this.migratedModel = migratedModel;
		this.execution = execution;
	}

	public ModelElement getEquivalent(ModelElement originalModelElement) {
		return execution.getEquivalent(originalModelElement);
	}
	
	public Object getEquivalent(Object unwrappedModelElement) throws ConservativeCopyException {
		return originalModel.getUnwrappedEquivalent(unwrappedModelElement, migratedModel, this);
	}
	
	public void addWarning(String warning) {
		execution.addWarning(warning);
	}

	public ModelValue<?> getEquivalentValue(ModelValue<?> originalValue) throws ConservativeCopyException {
		return originalValue.getEquivalentIn(migratedModel, this);
	}
	
	public void automaticallyPopulateEquivalent(MigrationStrategy strategy, Equivalence equivalence) throws EolRuntimeException {
		equivalence.automaticallyPopulateEquivalent(this, getIgnoredPropertiesFor(strategy, equivalence));
	}

	private IgnoredProperties getIgnoredPropertiesFor(MigrationStrategy strategy, Equivalence equivalence) throws EolRuntimeException {
		return strategy.ignoredPropertiesFor(equivalence.getContext());
	}
}
