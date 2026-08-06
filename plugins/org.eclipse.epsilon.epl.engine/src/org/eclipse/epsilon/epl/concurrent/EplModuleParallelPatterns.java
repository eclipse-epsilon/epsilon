/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.epl.concurrent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.epl.dom.Pattern;
import org.eclipse.epsilon.epl.execute.PatternMatch;
import org.eclipse.epsilon.epl.execute.context.concurrent.IEplContextParallel;
import org.eclipse.epsilon.epl.execute.model.PatternMatchModel;

/**
 * Executes each pattern independently.
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class EplModuleParallelPatterns extends EplModuleParallel {

	public EplModuleParallelPatterns() {
		super();
	}

	public EplModuleParallelPatterns(IEplContextParallel context) {
		super(context);
	}
	
	@Override
	protected Set<PatternMatch> matchPatterns(final int level, PatternMatchModel model) throws EolRuntimeException {
		IEplContextParallel context = getContext();
		Collection<? extends Pattern> patterns = getPatterns();
		Collection<Callable<Collection<PatternMatch>>> jobs = new ArrayList<>(patterns.size());
		
		// Keep track of the matches identified during this pattern matching loop
		List<PatternMatch> currentLoopMatches = new ArrayList<>();
		
		for (final Pattern pattern : patterns) {
			if (pattern.getLevel() == level) {
				jobs.add(() -> match(pattern));
			}
		}
		
		for (Collection<PatternMatch> matches : context.executeAll(this, jobs)) {
			currentLoopMatches.addAll(matches);
		}
		
		// When pattern matching is over, discard old matches and keep
		// only those identified during this loop
		model.dispose();
		model.addMatches(currentLoopMatches);
		
		return model.getMatches();
	}
}
