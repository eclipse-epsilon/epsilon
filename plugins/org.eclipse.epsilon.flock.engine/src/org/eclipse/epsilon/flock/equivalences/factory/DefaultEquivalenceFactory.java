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
package org.eclipse.epsilon.flock.equivalences.factory;

import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.flock.context.EquivalenceEstablishmentContext.EquivalentFactory;
import org.eclipse.epsilon.flock.emc.wrappers.ModelElement;
import org.eclipse.epsilon.flock.equivalences.Equivalence;
import org.eclipse.epsilon.flock.equivalences.NoEquivalence;
import org.eclipse.epsilon.flock.equivalences.TypeBasedEquivalence;
import org.eclipse.epsilon.flock.execute.FlockExecution;
import org.eclipse.epsilon.flock.execute.exceptions.FlockRuntimeException;

public class DefaultEquivalenceFactory implements EquivalenceFactory {

	private static final EquivalenceFactory instance = new DefaultEquivalenceFactory();
	
	public static EquivalenceFactory getInstance() {
		return instance;
	}

	@Override
	public Equivalence createEquivalence(IEolContext context, FlockExecution execution, ModelElement original, EquivalentFactory equivalentFactory) throws FlockRuntimeException {
		if (equivalentFactory.typeConformsToEvolvedMetamodel(original.getTypeName())) {
			return new TypeBasedEquivalence(context, execution, original, equivalentFactory.createModelElementInMigratedModel(original.getTypeName()));
		} else {
			return new NoEquivalence(context, execution, original);
		}
	}
}
