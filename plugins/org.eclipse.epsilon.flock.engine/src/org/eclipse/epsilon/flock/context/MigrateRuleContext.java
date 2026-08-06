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

import java.util.HashMap;
import java.util.Map;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.flock.equivalences.Equivalence;
import org.eclipse.epsilon.flock.execute.FlockExecution;
import org.eclipse.epsilon.flock.model.domain.common.ClassifierTypedConstruct;

public class MigrateRuleContext {
	
	private final Equivalence equivalence;
	private final IEolContext context;
	private final FlockExecution execution;
	private final Map<ClassifierTypedConstruct, Boolean> applicabilityCache = new HashMap<>();

	public MigrateRuleContext(Equivalence equivalence, IEolContext context, FlockExecution execution) {
		this.equivalence = equivalence;
		this.context = context;
		this.execution = execution;
	}

	public boolean isEligibleFor(ClassifierTypedConstruct guardedConstruct) throws EolRuntimeException {
		final boolean applicability;
		
		if (applicabilityCache.containsKey(guardedConstruct)) {
			applicability = applicabilityCache.get(guardedConstruct);
			
		} else {			
			applicability = guardedConstruct.appliesIn(getOriginal());
			applicabilityCache.put(guardedConstruct, applicability);
		}

		return applicability;
	}
	
	private GuardedConstructContext getOriginal() {
		return new GuardedConstructContext(equivalence.getOriginal(), context);
	}

	public void execute(ExecutableBlock<Void> body) throws EolRuntimeException {
		equivalence.ruleApplied(execution);
		if (body != null) { body.execute(context, equivalence.getVariables().toArray(new Variable[]{})); }
	}
}
