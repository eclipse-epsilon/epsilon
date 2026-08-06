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
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.flock.context.EquivalenceEstablishmentContext.EquivalentFactory;
import org.eclipse.epsilon.flock.emc.wrappers.ModelElement;
import org.eclipse.epsilon.flock.equivalences.Equivalence;
import org.eclipse.epsilon.flock.equivalences.factory.EquivalenceFactory;
import org.eclipse.epsilon.flock.execute.FlockExecution;
import org.eclipse.epsilon.flock.execute.exceptions.FlockRuntimeException;
import org.eclipse.epsilon.flock.model.domain.typemappings.TypeMappingConstruct;

public class TypeMappingContext {
	
	private final ModelElement original;
	private final IEolContext context;
	private final FlockExecution execution;
	private final EquivalentFactory equivalentFactory;

	public TypeMappingContext(ModelElement original, IEolContext context, FlockExecution execution, EquivalentFactory equivalentFactory) {
		this.original = original;
		this.context = context;
		this.execution = execution;
		this.equivalentFactory = equivalentFactory;
	}
	
	public boolean isEligibleFor(TypeMappingConstruct typeMapping) throws EolRuntimeException {
		return typeMapping.appliesIn(getOriginal());
	}

	private GuardedConstructContext getOriginal() {
		return new GuardedConstructContext(original, context);
	}

	public Equivalence createEquivalenceUsing(EquivalenceFactory equivalenceFactory) throws FlockRuntimeException {
		return equivalenceFactory.createEquivalence(context, execution, original, equivalentFactory);
	}
}
