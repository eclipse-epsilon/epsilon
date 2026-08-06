/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.ecl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.eclipse.epsilon.ecl.dom.MatchRule;
import org.eclipse.epsilon.ecl.execute.context.IEclContext;
import org.eclipse.epsilon.ecl.trace.Match;
import org.eclipse.epsilon.ecl.trace.MatchTrace;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.erl.IErlModule;

public interface IEclModule extends IErlModule {
		
	public List<MatchRule> getMatchRules();
	
	public List<MatchRule> getDeclaredMatchRules();
	
	@Override
	public MatchTrace execute() throws EolRuntimeException;
	
	@Override
	default IEclContext getContext() {
		return (IEclContext) IErlModule.super.getContext();
	}
	
	public void matchModels() throws EolRuntimeException;
	
	default Match match(Object left, Object right, boolean forcedMatch) throws EolRuntimeException {
		IEclContext context = getContext();
		
		Match traceMatch = context.getMatchTrace().getMatch(left, right);
		if (traceMatch != null) return traceMatch;
		
		Collection<MatchRule> matchRules = getRulesFor(left, right, context, true);
		
		if (!matchRules.isEmpty()) {
			MatchRule matchRule = matchRules.iterator().next();
			return matchRule.match(left, right, context, null, forcedMatch);
		}
		else {
			matchRules = getRulesFor(left, right, context, false);
			Match match = new Match(left, right, !matchRules.isEmpty(), null);
			
			for (MatchRule matchRule : matchRules) {
				if (matchRule.isGreedy(context)) {
					Match tempMatch = matchRule.match(left, right, context, match.getInfo(), forcedMatch);
					match.setMatching(match.isMatching() && tempMatch.isMatching());
					match.setRule(matchRule);
				}
			}
			
			context.getMatchTrace().add(match);
			return match;
		}
	}
	
	default List<MatchRule> getRulesFor(Object left, Object right, IEclContext context, boolean ofClassOnly) throws EolRuntimeException {
		ArrayList<MatchRule> rules = new ArrayList<>();
		for (MatchRule matchRule : getMatchRules()) {
			if (!matchRule.isAbstract(context) && matchRule.appliesTo(left, right, context, ofClassOnly)) {
				rules.add(matchRule);
			}
		}
		return rules;
	}
}
