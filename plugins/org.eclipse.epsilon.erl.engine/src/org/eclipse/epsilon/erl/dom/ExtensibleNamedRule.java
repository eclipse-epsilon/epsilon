/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.erl.dom;

import java.util.*;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.eclipse.epsilon.eol.dom.Parameter;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelNotFoundException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.types.EolModelElementType;
import org.eclipse.epsilon.erl.exceptions.ErlCircularRuleInheritanceException;
import org.eclipse.epsilon.erl.exceptions.ErlRuleNotFoundException;

public abstract class ExtensibleNamedRule extends NamedRule {
	
	protected List<NameExpression> superRulesIdentifiers = new ArrayList<>();	
	protected List<ExtensibleNamedRule> superRules = new ArrayList<>();
	protected List<ExtensibleNamedRule> allSuperRules = new ArrayList<>();
	protected Boolean isGreedy, isAbstract, isLazy, isParallel;
	protected Map<Parameter, Collection<?>> ofTypeCache = new HashMap<>();
	protected Map<Parameter, Collection<?>> ofKindCache = new HashMap<>();
	
	public Collection<?> getAllOfKind(Parameter parameter, IEolContext context) throws EolRuntimeException {
		return getAllInstances(parameter, context, false);
	}
	
	public Collection<?> getAllOfType(Parameter parameter, IEolContext context) throws EolRuntimeException {
		return getAllInstances(parameter, context, true);
	}
	
	public Collection<?> getAllInstances(Parameter parameter, IEolContext context, boolean ofTypeOnly) throws EolRuntimeException {
		final Map<Parameter, Collection<?>> cache = ofTypeOnly ? ofTypeCache : ofKindCache;
		
		Collection<?> instances = cache.get(parameter);
		
		if (instances == null) {
			try {
				EolModelElementType parameterType = (EolModelElementType) parameter.getType(context);
				instances = ofTypeOnly ? parameterType.getAllOfType() : parameterType.getAllOfKind();
				cache.put(parameter, instances);
			}
			catch (EolModelElementTypeNotFoundException ex) {
				ex.setAst(parameter.getTypeExpression());
				throw ex;
			}
			catch (EolModelNotFoundException ex) {
				ex.setAst(parameter.getTypeExpression());
				throw ex;
			}
		}
		return instances;
	}
	
	/**
	 * 
	 * @param context
	 * @return
	 * @throws EolRuntimeException
	 * @since 2.3
	 */
	public boolean isParallel(IEolContext context) throws EolRuntimeException {
		if (isParallel == null) synchronized (this) {
			if (isParallel == null) {
				isParallel = getBooleanAnnotationValue("parallel", context);
			}
		}
		return isParallel;
	}
	
	public boolean isGreedy(IEolContext context) throws EolRuntimeException {
		if (isGreedy == null) synchronized (this) {
			if (isGreedy == null) {
				isGreedy = getBooleanAnnotationValue("greedy", context);
			}
		}
		return isGreedy;
	}
	
	public boolean isAbstract(IEolContext context) throws EolRuntimeException {
		if (isAbstract == null) synchronized (this) {
			if (isAbstract == null) {
				isAbstract = getBooleanAnnotationValue("abstract", context);
			}
		}
		return isAbstract;
	}
	
	public boolean isLazy(IEolContext context) throws EolRuntimeException {
		if (isLazy == null) synchronized (this) {
			if (isLazy == null) {
				isLazy = getBooleanAnnotationValue("lazy", context);
			}
		}
		return isLazy;
	}
	
	public void calculateSuperRules(List<? extends ExtensibleNamedRule> allRules) throws ErlRuleNotFoundException, ErlCircularRuleInheritanceException {
		superRules = new ArrayList<>();
		calculateSuperRules(this, allRules, superRules, false);
		allSuperRules = new ArrayList<>();
		calculateSuperRules(this, allRules, allSuperRules, true);
	}
	
	protected void calculateSuperRules(ExtensibleNamedRule rule, List<? extends ExtensibleNamedRule> allRules, List<ExtensibleNamedRule> collectedRules, boolean recursive) throws ErlRuleNotFoundException, ErlCircularRuleInheritanceException {
		for (NameExpression superRuleAst : rule.superRulesIdentifiers) {
			String superRuleName = superRuleAst.getName();
			ExtensibleNamedRule superRule = (ExtensibleNamedRule) getRuleByName(allRules, superRuleName);
			if (superRule != null) {
				if (getRuleByName(collectedRules, superRule.getName()) != null) {
					throw new ErlCircularRuleInheritanceException(superRule);
				}
				else {
					collectedRules.add(superRule);
				}
				if (recursive) {
					calculateSuperRules(superRule, allRules, collectedRules, true);
				}
			}
			else {
				throw new ErlRuleNotFoundException(superRuleAst);
			}
		}	
	}
	
	protected NamedRule getRuleByName(List<? extends NamedRule> rules, String name) {
		for (NamedRule rule : rules) {
			if (rule.getName().equals(name)) {
				return rule;
			}
		}
		return null;
	}
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		
		AST superRulesAst = getSuperRulesAst(cst);
		if (superRulesAst != null) {
			AST superRuleAst = superRulesAst.getFirstChild();
			while (superRuleAst != null) {
				superRulesIdentifiers.add((NameExpression) module.createAst(superRuleAst, this));
				superRuleAst = superRuleAst.getNextSibling();
			}
		}
	}
	
	public abstract AST getSuperRulesAst(AST cst);
	
	public List<ExtensibleNamedRule> getAllSuperRules() {
		return allSuperRules;
	}
	
	public List<ExtensibleNamedRule> getSuperRules() {
		return superRules;
	}
	
}
