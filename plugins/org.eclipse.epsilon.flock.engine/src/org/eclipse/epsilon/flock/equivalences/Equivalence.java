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

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.flock.context.ConservativeCopyContext;
import org.eclipse.epsilon.flock.context.MigrateRuleContext;
import org.eclipse.epsilon.flock.emc.wrappers.ModelElement;
import org.eclipse.epsilon.flock.execute.FlockExecution;
import org.eclipse.epsilon.flock.execute.exceptions.FlockRuntimeException;
import org.eclipse.epsilon.flock.model.domain.rules.IgnoredProperties;

public abstract class Equivalence {

	private final MigrateRuleContext context;
	
	public Equivalence(IEolContext context, FlockExecution execution) {
		this.context = new MigrateRuleContext(this, context, execution);
	}
	
	public MigrateRuleContext getContext() {
		return this.context;
	}
	
	public abstract ModelElement getOriginal();

	public abstract ModelElement getEquivalent();
	
	public abstract void ruleApplied(FlockExecution execution);
	
	public Collection<Variable> getVariables() {
		final List<Variable> variables = new LinkedList<>();
		
		variables.add(createVariable("original", getOriginal()));
		variables.add(createVariable("migrated", getEquivalent()));
		
		return variables;
	}
	
	private static Variable createVariable(String name, ModelElement element) {
		if (element == null) {
			return Variable.createReadOnlyVariable(name, null);
		} else {
			return element.createReadOnlyVariable(name);
		}
			
	}

	public abstract void automaticallyPopulateEquivalent(ConservativeCopyContext context, IgnoredProperties ignoredProperties) throws FlockRuntimeException;
	
	@Override
	public abstract boolean equals(Object obj);

	@Override
	public abstract int hashCode();

	@Override
	public abstract String toString();
}
