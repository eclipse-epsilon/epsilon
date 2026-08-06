/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.flock.model.domain.rules;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.flock.context.MigrateRuleContext;
import org.eclipse.epsilon.flock.context.MigrationStrategyCheckingContext;

public class MigrateRules {
	private final List<MigrateRule> migrateRules;
	
	public MigrateRules(MigrateRule... rules) {
		this.migrateRules = new LinkedList<>(Arrays.asList(rules));
	}

	public void add(MigrateRule rule) {
		migrateRules.add(rule);
	}

	public void check(MigrationStrategyCheckingContext context) {
		for (MigrateRule rule : migrateRules) {
			rule.check(context);
		}
	}
	
	public IgnoredProperties ignoredPropertiesFor(MigrateRuleContext context) throws EolRuntimeException {
		final IgnoredProperties ignored = new IgnoredProperties();
		
		for (MigrateRule rule : migrateRules) {
			rule.gatherIgnoredPropertiesFor(context, ignored);
		}
		
		return ignored;
	}
	
	public void applyTo(MigrateRuleContext context) throws EolRuntimeException {
		for (MigrateRule rule : migrateRules) {
			rule.applyTo(context);
		}
	}
}
