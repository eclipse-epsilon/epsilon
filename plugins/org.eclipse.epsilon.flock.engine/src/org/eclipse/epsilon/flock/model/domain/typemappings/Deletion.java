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
package org.eclipse.epsilon.flock.model.domain.typemappings;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.flock.context.EquivalenceEstablishmentContext.EquivalentFactory;
import org.eclipse.epsilon.flock.context.GuardedConstructContext;
import org.eclipse.epsilon.flock.emc.wrappers.ModelElement;
import org.eclipse.epsilon.flock.equivalences.Equivalence;
import org.eclipse.epsilon.flock.equivalences.NoEquivalence;
import org.eclipse.epsilon.flock.execute.FlockExecution;
import org.eclipse.epsilon.flock.execute.exceptions.FlockRuntimeException;
import org.eclipse.epsilon.flock.model.domain.common.ClassifierTypedConstruct;

public class Deletion extends ClassifierTypedConstruct implements TypeMappingConstruct {
	
	public Equivalence createEquivalence(IEolContext context, FlockExecution execution, ModelElement original, EquivalentFactory equivalentFactory) throws FlockRuntimeException {
		return new NoEquivalence(context, execution, original);
	}
	
	@Override
	public boolean appliesIn(GuardedConstructContext context) throws EolRuntimeException {
		if (this.typedFor(context) && super.appliesIn(context)) {
			return true;
		
		} else if (this.isCascading()) {
			return this.appliesInAncestorOf(context);
		}

		return false;
	}
	
	private boolean appliesInAncestorOf(GuardedConstructContext context) throws EolRuntimeException {
		// Attempt to find an ancestor context to which this deletion construct applies
		// by navigating through the parents 
		while (context.isContextForParentElement()) {
			if (super.appliesIn(context.getContextForParentElement())) {
				return true;
			}
			context = context.getContextForParentElement();
		}
		
		return false;
	}

	protected boolean isCascading() {
		return hasAnnotation("cascade");
	}
	
	@Override
	public String toString() {
		return "delete " + getOriginalType() + " when " + getGuard();
	}
}
