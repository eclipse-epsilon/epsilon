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
package org.eclipse.epsilon.flock.equivalences;

import java.util.LinkedList;
import java.util.List;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.flock.context.ConservativeCopyContext;
import org.eclipse.epsilon.flock.context.EquivalenceEstablishmentContext;
import org.eclipse.epsilon.flock.context.TypeMappingContext;
import org.eclipse.epsilon.flock.emc.wrappers.ModelElement;
import org.eclipse.epsilon.flock.model.domain.MigrationStrategy;

public class Equivalences {

	private final List<Equivalence> equivalences = new LinkedList<>();
	
	/**
	 * Factory method for establishing original to migrated model Equivalences from a MigrationStrategy and an IFlockContext.
	 */
	public static Equivalences establishFrom(MigrationStrategy strategy, EquivalenceEstablishmentContext context) throws EolRuntimeException {
		final Equivalences equivalences = new Equivalences();
		
		for (TypeMappingContext c : context.getTypeMappingContexts()) {
			equivalences.add(strategy.createEquivalence(c));
		}
		
		return equivalences;
	}
	
	
	protected void add(Equivalence equivalence) {
		equivalences.add(equivalence);
	}
	
	public ModelElement getEquivalent(ModelElement original) {
		for (Equivalence candidate : equivalences) {
			if (candidate.getOriginal().equals(original))
				return candidate.getEquivalent();
		}
		
		return null;
	}

	public void conservativeCopy(MigrationStrategy strategy, ConservativeCopyContext context) throws EolRuntimeException {		
		for (Equivalence equivalence : equivalences) {
			context.automaticallyPopulateEquivalent(strategy, equivalence);
		}
	}
	
	public void applyRules(MigrationStrategy strategy) throws EolRuntimeException {
		for (Equivalence equivalence : equivalences) {
			strategy.applyRulesTo(equivalence.getContext());
		}
	}
	
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		
		for (Equivalence equivalence : equivalences) {
			builder.append(equivalence);
			builder.append('\n');
		}
		
		return builder.toString();
	}
}
