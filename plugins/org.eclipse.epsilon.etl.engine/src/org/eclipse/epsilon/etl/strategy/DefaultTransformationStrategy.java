/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.etl.strategy;

import java.util.Collection;
import java.util.List;
import org.eclipse.epsilon.common.util.CollectionUtil;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.erl.execute.context.IErlContext;
import org.eclipse.epsilon.etl.dom.TransformationRule;
import org.eclipse.epsilon.etl.execute.context.IEtlContext;

public class DefaultTransformationStrategy extends AbstractTransformationStrategy {
	
	@Override
	public Collection<?> transform(Object source, IEtlContext context, List<String> rules) throws EolRuntimeException{	
		List<Object> targets = CollectionUtil.createDefaultList();
		
		//TODO : Change this to be less restrictive...
		if (!canTransform(source)) return targets;
		
		for (TransformationRule rule : getRulesFor(source, context)) {
			if (rules == null || rules.contains(rule.getName())) {
				
				Collection<?> transformed = rule.transform(source, context);
				
				if (!rule.isPrimary(context)) {
					targets.addAll(transformed);
				}
				else {
					int i = 0;
					for (Object target : transformed) {
						targets.add(i++, target);
					}
				}
			}
		}
		
		return targets;
	}
	
	@Override
	public Collection<?> getEquivalents(Object source, IErlContext context_, List<String> rules) throws EolRuntimeException {
		IEtlContext context = (IEtlContext) context_;
		return transform(source, context, rules);
	}
	
	@Override
	public void transformModels(IEtlContext context) throws EolRuntimeException {
		for (TransformationRule transformRule : getExecutableRules(context)) {			
			transformRule.transformAll(context, getExcluded(), true);
		}
	}
	
}
