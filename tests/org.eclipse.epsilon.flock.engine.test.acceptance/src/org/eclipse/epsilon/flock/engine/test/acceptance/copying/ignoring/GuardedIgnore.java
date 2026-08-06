/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.flock.engine.test.acceptance.copying.ignoring;

import org.eclipse.epsilon.flock.engine.test.acceptance.util.FlockAcceptanceTest;
import org.junit.BeforeClass;
import org.junit.Test;

public class GuardedIgnore extends FlockAcceptanceTest {
	
	private static final String strategy = "migrate Family ignoring numberOfChildren " +
	                                       "when: original.members.isEmpty()";

	private static final String originalModel = "Families {"                  +
	                                            "	Family {"                 +
	                                            "		name: \"The Smiths\"" +
	                                            "       members: Person \"p\" { name: \"John\" } " +
	                                            "		numberOfChildren: 1"  +
	                                            "	}"                        +
	                                            "	Family {"                 +
	                                            "		name: \"The Does\""   +
	                                            "		numberOfChildren: 2"  +
	                                            "	}"                        +
	                                            "}";

	@BeforeClass
	public static void setup() throws Exception {
		migrateFamiliesToFamilies(strategy, originalModel);

		migrated.setVariable("smiths", "Family.all.selectOne(f|f.name == 'The Smiths')");
		migrated.setVariable("does",   "Family.all.selectOne(f|f.name == 'The Does')");
	}


	@Test
	public void ignoredPropertyIsIgnoredWhenGuardHolds() {
		migrated.assertEquals(0, "does.numberOfChildren");
	}

	@Test
	public void ignoredPropertyIsCopiedWhenGuardDoesNotHold() {
		migrated.assertEquals(1, "smiths.numberOfChildren");
	}
}
