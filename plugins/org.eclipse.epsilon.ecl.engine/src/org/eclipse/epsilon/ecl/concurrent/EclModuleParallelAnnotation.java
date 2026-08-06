/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.ecl.concurrent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import org.eclipse.epsilon.ecl.dom.MatchRule;
import org.eclipse.epsilon.ecl.execute.context.concurrent.*;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.erl.concurrent.IErlModuleParallelAnnotation;

/**
 * Executes rule-element pairs marked with the <code>@parallel</code> annotation in parallel.
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class EclModuleParallelAnnotation extends EclModuleParallel implements IErlModuleParallelAnnotation {
	
	public EclModuleParallelAnnotation() {
		super();
	}
	
	public EclModuleParallelAnnotation(IEclContextParallel context) {
		super(context);
	}
	
	@Override
	protected void matchAllRules(boolean greedy) throws EolRuntimeException {
		boolean ofTypeOnly = !greedy;
		EclContextParallel context = (EclContextParallel) getContext();
		
		for (MatchRule matchRule : getMatchRules()) {
			if (!matchRule.isAbstract(context) && !matchRule.isLazy(context) && (ofTypeOnly || matchRule.isGreedy(context))) {
				Collection<?> leftInstances = matchRule.getLeftInstances(context, ofTypeOnly);
				
				if (matchRule.getBooleanAnnotationValue("parallel", context, () ->
					new Variable[] {
						Variable.createReadOnlyVariable("leftInstances", leftInstances),
						Variable.createReadOnlyVariable("matchRule", matchRule),
						Variable.createReadOnlyVariable("THREADS", context.getParallelism())
					})
				) {
					final Collection<Callable<?>> jobs = new ArrayList<>(leftInstances.size() * leftInstances.size());
					for (Object left : leftInstances) {
						Collection<?> rightInstances;
						if(matchRule.isRightDomainDynamic()) {
							rightInstances = matchRule.getRightInstances(context, ofTypeOnly, left);
						}
						else {
							rightInstances = matchRule.getRightInstances(context, ofTypeOnly);
						}
						
						for (Object right : rightInstances) {
							jobs.add(() -> matchRule.matchPair(context.getShadow(), ofTypeOnly, left, right));
						}
					}
					context.executeAll(matchRule, jobs);
				}
				else {
					for (Object left : leftInstances) {
						Collection<?> rightInstances;
						if(matchRule.isRightDomainDynamic()) {
							rightInstances = matchRule.getRightInstances(context, ofTypeOnly, left);
						}
						else {
							rightInstances = matchRule.getRightInstances(context, ofTypeOnly);
						}
						for (Object right : rightInstances) {
							matchRule.matchPair(context, ofTypeOnly, left, right);
						}
					}
				}
			}
		}
	}
}
