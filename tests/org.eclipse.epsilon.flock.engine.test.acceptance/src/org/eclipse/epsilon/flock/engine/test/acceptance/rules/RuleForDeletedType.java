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
package org.eclipse.epsilon.flock.engine.test.acceptance.rules;

import static org.eclipse.epsilon.test.util.builders.emf.EPackageBuilder.aMetamodel;
import static org.junit.Assert.assertTrue;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.epsilon.flock.engine.test.acceptance.util.FlockAcceptanceTest;
import org.junit.BeforeClass;
import org.junit.Test;

public class RuleForDeletedType extends FlockAcceptanceTest {

	private static final String strategy = "migrate Person {" +
	                                       "   migrated.println();" +
	                                       "}";
	
	private static final String originalModel = "Families {"             +
	                                            "	Person {"            +
	                                            "		name: \"John\""  +
	                                            "	}"                   +
	                                            "}";
	
	@BeforeClass
	public static void setup() throws Exception {
		final EPackage empty = aMetamodel().build();
		migrateFamilies(strategy, originalModel, empty);
	}
	
	@Test
	public void migratedModelIsEmpty() {
		migrated.assertEquals(0, "Migrated.allContents().size()");
	}
	
	@Test
	public void shouldIncludeWarning() throws Exception {
		final String message = "Rule defined for migrating instances of families::Person but " +
		                       "that type cannot be instantiated in the evolved metamodel.";
		
		assertTrue(result.getWarnings().contains(message));
	}
}
