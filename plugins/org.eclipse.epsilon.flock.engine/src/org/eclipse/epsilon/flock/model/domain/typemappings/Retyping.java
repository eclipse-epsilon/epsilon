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

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.flock.context.EquivalenceEstablishmentContext.EquivalentFactory;
import org.eclipse.epsilon.flock.emc.wrappers.ModelElement;
import org.eclipse.epsilon.flock.equivalences.Equivalence;
import org.eclipse.epsilon.flock.equivalences.TypeBasedEquivalence;
import org.eclipse.epsilon.flock.execute.FlockExecution;
import org.eclipse.epsilon.flock.execute.exceptions.FlockRuntimeException;
import org.eclipse.epsilon.flock.model.domain.common.ClassifierTypedConstruct;

public class Retyping extends ClassifierTypedConstruct implements TypeMappingConstruct {

	private String evolvedType;
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		
		evolvedType = cst.getSecondChild().getText();
		if (evolvedType == null) throw new IllegalStateException("evolvedType cannot be null");
	}

	public String getEvolvedType() {
		return evolvedType;
	}

	public Equivalence createEquivalence(IEolContext context, FlockExecution execution, ModelElement original, EquivalentFactory factory) throws FlockRuntimeException {
		final ModelElement equivalent = factory.createModelElementInMigratedModel(evolvedType);
		return new TypeBasedEquivalence(context, execution, original, equivalent);
	}
	
	@Override
	public String toString() {
		return "retype " + getOriginalType() + " to " + evolvedType + " when " + getGuard();
	}
	
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Retyping))
			return false;
		
		final Retyping other = (Retyping)object;
		
		return super.equals(object) &&
		       this.evolvedType.equals(other.evolvedType);
	}
	
	@Override
	public int hashCode() {
		return super.hashCode() + evolvedType.hashCode();
	}
}
