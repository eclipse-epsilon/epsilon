/*********************************************************************
 * Copyright (c) 2019 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.etl.strategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.erl.strategy.IEquivalentProvider;
import org.eclipse.epsilon.etl.dom.TransformationRule;
import org.eclipse.epsilon.etl.execute.context.IEtlContext;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
public abstract class AbstractTransformationStrategy implements ITransformationStrategy {

	protected IEquivalentProvider equivalentProvider = this;

	protected Collection<Object> getExcluded() {
		return Collections.emptyList();
	}
	
	protected List<TransformationRule> getExecutableRules(IEtlContext context) throws EolRuntimeException {
		Collection<TransformationRule> allRules = context.getModule().getTransformationRules();
		List<TransformationRule> filtered = new ArrayList<>(allRules.size());
		for (TransformationRule transformRule : allRules) {
			if (!transformRule.isAbstract(context) && !transformRule.isLazy(context)) {
				filtered.add(transformRule);
			}
		}
		return filtered;
	}
	
	protected List<TransformationRule> getRulesFor(Object source, IEtlContext context) throws EolRuntimeException {
		List<TransformationRule> rules = new ArrayList<>();
		for (TransformationRule rule : context.getModule().getTransformationRules()) {
			if (!rule.isAbstract(context) && rule.appliesTo(source, context, false)) {
				rules.add(rule);
			}
		}
		return rules;
	}
	
	@Override
	public boolean canTransform(Object source) {
		return !getExcluded().contains(source);
	}
	
	@Override
	public void setEquivalentProvider(IEquivalentProvider equivalentProvider) {
		this.equivalentProvider = equivalentProvider;
	}

	@Override
	public IEquivalentProvider getEquivalentProvider() {
		return equivalentProvider;
	}
}
