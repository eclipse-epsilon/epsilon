/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.flock.engine.test.acceptance.typemappings.delete;

import org.eclipse.epsilon.flock.engine.test.acceptance.util.FlockAcceptanceTest;
import org.junit.BeforeClass;
import org.junit.Test;

public class StrictDeleteDoesNotApplyToSubtypes extends FlockAcceptanceTest {

	private static final String strategy = "@strict" + "\n" +
	                                       "delete Pet";

	private static final String originalModel = "Families {"            +
	                                            "	Pet {"              +
	                                            "		name: \"Tom\""  +
	                                            "	}"                  +
	                                            "	Dog {"              +
	                                            "		name: \"Fido\"" +
	                                            "	}"                  +
	                                            "}";

	@BeforeClass
	public static void setup() throws Exception {
		migrateFamiliesToFamilies(strategy, originalModel);
	}
	
	@Test
	public void petsShouldBeDeleted() {
		migrated.assertEquals(0, "NamedElement.all.select(e|e.name == \"Tom\").size()");
	}

	@Test
	public void dogsShouldBeMigrated() {
		migrated.assertEquals("Fido", "Dog.all.first.name");
	}
}
