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
package org.eclipse.epsilon.flock.engine.test.acceptance.equivalences;

import org.eclipse.epsilon.flock.engine.test.acceptance.util.FlockAcceptanceTest;
import org.junit.BeforeClass;
import org.junit.Test;


public class EquivalentOfModelElement extends FlockAcceptanceTest {

	private static final String strategy = "migrate Family { "    +
	                                       "	migrated.members.add(original.members.first.equivalent());" +
	                                       "}" +
	                                       "migrate Person {" +
	                                       "   migrated.name := original.name + ' Smith';" +
	                                       "}";

	private static final String originalModel = "Families {"                                      +
	                                            "	Family {"                                     +
	                                            "       members: Person \"p\" { name: \"John\"} " +
	                                            "	}"                                            +
	                                            "}";
		
	@BeforeClass
	public static void setup() throws Exception {
		migrateFamiliesToFamilies(strategy, originalModel);
		
		migrated.setVariable("family", "Family.all.first");
	}
	
	@Test
	public void membersShouldBeCopied() {
		migrated.assertEquals(1,            "family.members.size");
		migrated.assertEquals("John Smith", "family.members.first.name");
	}
}
