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
package org.eclipse.epsilon.flock.model.domain;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.flock.context.MigrateRuleContext;
import org.eclipse.epsilon.flock.context.MigrationStrategyCheckingContext;
import org.eclipse.epsilon.flock.context.TypeMappingContext;
import org.eclipse.epsilon.flock.equivalences.Equivalence;
import org.eclipse.epsilon.flock.model.domain.common.ClassifierTypedConstruct;
import org.eclipse.epsilon.flock.model.domain.rules.IgnoredProperties;
import org.eclipse.epsilon.flock.model.domain.rules.MigrateRule;
import org.eclipse.epsilon.flock.model.domain.rules.MigrateRules;
import org.eclipse.epsilon.flock.model.domain.typemappings.TypeMappingConstruct;
import org.eclipse.epsilon.flock.model.domain.typemappings.TypeMappingConstructs;

public class MigrationStrategy {
	
	protected List<ModuleElement> children = new LinkedList<>();
	protected TypeMappingConstructs typeMappingConstructs;
	protected MigrateRules migrateRules = new MigrateRules();
	
	public MigrationStrategy(ClassifierTypedConstruct... constructs) {
		for (ClassifierTypedConstruct construct : constructs) {
			if (construct instanceof TypeMappingConstruct) {
				addTypeMappingConstruct((TypeMappingConstruct)construct);
				
			} else if (construct instanceof MigrateRule) {
				addRule((MigrateRule)construct);
			
			} else {
				throw new IllegalArgumentException("constructs contains an unrecognsied construct: " + construct);
			}
		}
	}
	
	public TypeMappingConstructs getTypeMappingConstructs() {
		if (typeMappingConstructs == null) {
			typeMappingConstructs = new TypeMappingConstructs();
		}
		return typeMappingConstructs;
	}
	
	public Collection<ModuleElement> getTypeMappingsAndRules() {
		return children;
	}
	
	public void addTypeMappingConstruct(TypeMappingConstruct typeMappingConstruct) {
		getTypeMappingConstructs().add(typeMappingConstruct);
		children.add(typeMappingConstruct);
	}
	
	public Equivalence createEquivalence(TypeMappingContext context) throws EolRuntimeException {
		return getTypeMappingConstructs().createEquivalence(context);
	}
	
	
	public void addRule(MigrateRule rule) {
		migrateRules.add(rule);
		children.add(rule);
	}
	
	public void checkTypeMappingsAndRules(MigrationStrategyCheckingContext context) {
		getTypeMappingConstructs().check(context);
		migrateRules.check(context);
	}
	
	public IgnoredProperties ignoredPropertiesFor(MigrateRuleContext context) throws EolRuntimeException {
		return migrateRules.ignoredPropertiesFor(context);
	}
	
	public void applyRulesTo(MigrateRuleContext context) throws EolRuntimeException {
		migrateRules.applyTo(context);
	}
}
