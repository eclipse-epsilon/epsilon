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

import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.flock.context.ConservativeCopyContext;
import org.eclipse.epsilon.flock.emc.wrappers.ModelElement;
import org.eclipse.epsilon.flock.execute.FlockExecution;
import org.eclipse.epsilon.flock.execute.exceptions.FlockRuntimeException;
import org.eclipse.epsilon.flock.model.domain.rules.IgnoredProperties;

public class NoEquivalence extends Equivalence {

	public final ModelElement original;
	
	public NoEquivalence(IEolContext context, FlockExecution execution, ModelElement original) {
		super(context, execution);
		
		if (original == null)
			throw new IllegalArgumentException("original cannot be null");
		
		this.original = original;
	}
	
	@Override
	public ModelElement getOriginal() {
		return original; 
	}
	
	@Override
	public void automaticallyPopulateEquivalent(ConservativeCopyContext context, IgnoredProperties ignoredProperties) throws FlockRuntimeException {
		// do nothing
	}

	@Override
	public ModelElement getEquivalent() {
		return null;
	}
	
	@Override
	public void ruleApplied(FlockExecution execution) {
		execution.addWarning("Rule defined for migrating instances of " + original.getTypeName() + " " + 
	                         "but that type cannot be instantiated in the evolved metamodel.");
	}
	
	@Override
	public String toString() {
		return original + " <-> null";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof NoEquivalence))
			return false;
		
		final NoEquivalence other = (NoEquivalence)obj;
		
		return original.equals(other.original);
	}
	
	@Override
	public int hashCode() {
		return original.hashCode();
	}
}
