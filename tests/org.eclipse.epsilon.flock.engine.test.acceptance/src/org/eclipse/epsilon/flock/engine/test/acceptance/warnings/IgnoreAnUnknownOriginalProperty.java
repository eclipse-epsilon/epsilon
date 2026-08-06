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
package org.eclipse.epsilon.flock.engine.test.acceptance.warnings;

import static org.junit.Assert.assertEquals;

import org.eclipse.epsilon.flock.engine.test.acceptance.util.FlockAcceptanceTest;
import org.junit.BeforeClass;
import org.junit.Test;


public class IgnoreAnUnknownOriginalProperty extends FlockAcceptanceTest {

	private static final String strategy = "migrate Person ignoring notoriety";
	
	private static final String originalModel = "Families {"             +
	                                            "	Person {"            +
	                                            "		name: \"John\""  +
	                                            "	}"                   +
	                                            "}";
	
	@BeforeClass
	public static void setup() throws Exception {
		migrateFamiliesToFamilies(strategy, originalModel);
	}
	
	@Test
	public void shouldHaveWarningForNotorietyProperty() {
		assertEquals(1, result.getWarnings().size());
		assertEquals("The notoriety property should be ignored, but there is no notoriety property defined for Person in the original metamodel.", result.getWarnings().iterator().next());
	}
}
