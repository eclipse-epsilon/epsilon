/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.flock.model.domain;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.Arrays;
import java.util.Collection;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.flock.model.domain.rules.MigrateRule;
import org.eclipse.epsilon.flock.model.domain.typemappings.Deletion;
import org.eclipse.epsilon.flock.model.domain.typemappings.Retyping;
import org.junit.Test;

public class MigrationStrategyTests {

	private final MigrateRule rule = mock(MigrateRule.class);
	private final Retyping retyping = mock(Retyping.class);
	private final Deletion deletion = mock(Deletion.class);
	
	
	@Test
	public void constructorAddsConstructs() {
		final MigrationStrategy strategy = new MigrationStrategy(retyping, rule, deletion);
		assertTrue(strategy.getTypeMappingsAndRules()
			.containsAll(Arrays.asList(retyping, rule, deletion))
		);
	}
	
	@Test
	public void addRule() {
		final MigrationStrategy strategy = new MigrationStrategy();
		strategy.addRule(rule);
		final Collection<ModuleElement> contents = strategy.getTypeMappingsAndRules();
		assertTrue(contents.contains(rule));
	}
	
	@Test
	public void addTypeMappingConstruct() {
		final MigrationStrategy strategy = new MigrationStrategy();
		strategy.addTypeMappingConstruct(retyping);
		strategy.addTypeMappingConstruct(deletion);
		final Collection<ModuleElement> contents = strategy.getTypeMappingsAndRules();
		assertTrue(contents.containsAll(Arrays.asList(retyping, deletion)));
	}
}
