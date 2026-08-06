/*******************************************************************************
 * Copyright (c) 2013 The University of York.
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
package org.eclipse.epsilon.flock.engine.test.acceptance.typemappings.deletecascade;

import org.eclipse.epsilon.flock.engine.test.acceptance.util.FlockAcceptanceTest;
import org.junit.BeforeClass;
import org.junit.Test;


public class StrictDeleteCascade extends FlockAcceptanceTest {

	private static final String strategy = "@cascade" + "\n" +
	                                       "@strict" + "\n" +
	                                       "delete District";
	
	private static final String originalModel = "Families {"                      +
	                                            "	District {"                   +
	                                            "		families: Family {"       +
	                                            "			name: \"The Smiths\"" +
	                                            "		}"                        +
	                                            "		dogs: Dog {"              +
	                                            "			name: \"Fido\""       +
	                                            "		}"                        +
	                                            "	}"                            +
	                                            "	Suburb {"                     +
	                                            "		families: Family {"       +
	                                            "			name: \"The Does\""   +
	                                            "		}"                        +
	                                            "		dogs: Dog {"              +
	                                            "			name: \"Rex\""        +
	                                            "		}"                        +
	                                            "	}"                            +
	                                            "}";
	
	@BeforeClass
	public static void setup() throws Exception {
		migrateFamiliesToFamilies(strategy, originalModel);
	}
	
	@Test
	public void migratedShouldContainOneSuburb() {
		migrated.assertEquals(1, "Suburb.all.size()");
	}
	
	@Test
	public void migratedShouldContainOneFamily() {
		migrated.assertEquals(1, "Family.all.size()");
	}
	
	@Test
	public void migratedFamilyShouldBeTheDoesNotTheSmiths() {
		migrated.assertEquals("The Does", "Family.all.first.name");
	}
	
	@Test
	public void migratedShouldContainOneDog() {
		migrated.assertEquals(1, "Dog.all.size()");
	}
	
	public void migratedDogShouldBeRexNotFido() {
		migrated.assertEquals("Rex", "Dog.all.first.name");
	}
}
