/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eml.strategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.eclipse.epsilon.common.util.CollectionUtil;
import org.eclipse.epsilon.ecl.trace.Match;
import org.eclipse.epsilon.eml.dom.MergeRule;
import org.eclipse.epsilon.eml.execute.context.IEmlContext;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.erl.execute.context.IErlContext;
import org.eclipse.epsilon.etl.strategy.DefaultTransformationStrategy;

public class DefaultMergingStrategy extends DefaultTransformationStrategy implements IMergingStrategy {
	
	protected IEmlContext context;
	
	@Override
	public void mergeModels(IEmlContext context) throws EolRuntimeException {
		
		this.context = context;
		
		for (Match match : context.getMatchTrace()) {
			List<MergeRule> rules = getRulesFor(match, context);
			for (MergeRule mergeRule : rules) {
				if (!mergeRule.isLazy(context) && !mergeRule.hasMerged(match)) {
					mergeRule.merge(match, context);
				}
			}
		}
		
		transformModels(context);
	}

	public List<MergeRule> getRulesFor(Match match, IEmlContext context) throws EolRuntimeException{
		
		List<MergeRule> rules = new ArrayList<>();
		
		// First we try to find rules that apply to instance of type only
		for (MergeRule mergeRule : context.getModule().getMergeRules()) {
			if (!mergeRule.isAbstract(context)) {
				if (mergeRule.appliesTo(match, context)) { 
					rules.add(mergeRule);
				}
			}
		}
		
		return rules;
	}
	
	@Override
	public Collection<?> getEquivalents(Object source, IErlContext context, List<String> rules) throws EolRuntimeException {
		
		if (!getExcluded().contains(source)) {
			return super.getEquivalents(source, context, rules);
		}
		else {
			return merge(source, context, rules);
		}
	}

	
	//TODO : Implement this
	private Collection<Object> merge(Object source, IEolContext context_,
			List<String> rules) throws EolRuntimeException {
		
		Collection<Match> matches = context.getMatchTrace().getMatches(source);
		
		List<Object> targets = CollectionUtil.createDefaultList();
		
		for (Match match : matches) {
			for (MergeRule mergeRule : getRulesFor(match, context)) {
				if (rules == null || rules.contains(mergeRule.getName())) {
					
					Collection<?> merged = mergeRule.merge(match, context);
					
					if (!mergeRule.isPrimary(context)) {
						targets.addAll(merged);
					}
					else {
						int i = 0;
						for (Object target : merged) {
							targets.add(i, target);
							i++;
						}
					}
				}
			}
		}
		return targets;
	}


	//TODO : Improve performance by turning this into a HashSet?
	protected List<Object> excluded = null;
	
	@Override
	public List<Object> getExcluded() {
		if (excluded == null) {
			Collection<Match> matches = context.getMatchTrace();
			excluded = new ArrayList<>(matches.size()*2);
			for (Match match : matches) {
				excluded.add(match.getLeft());
				excluded.add(match.getRight());
			}
		}
		return excluded;
	}
	
}
