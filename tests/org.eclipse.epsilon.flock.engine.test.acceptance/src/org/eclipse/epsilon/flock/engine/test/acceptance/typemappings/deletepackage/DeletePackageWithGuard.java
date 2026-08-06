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
package org.eclipse.epsilon.flock.engine.test.acceptance.typemappings.deletepackage;

import org.eclipse.epsilon.flock.engine.test.acceptance.util.FlockAcceptanceTest;
import org.junit.BeforeClass;
import org.junit.Test;


public class DeletePackageWithGuard extends FlockAcceptanceTest {

	private static final String strategy = "delete package families when: original.name = 'John'";
	
	private static final String originalModel = "Families {"             +
	                                            "	Person {"            +
	                                            "		name: \"John\""  +
	                                            "	}"                   +
	                                            "	Person {"            +
	                                            "		name: \"Jane\""  +
	                                            "	}"                   +
	                                            "	Pet {"               +
	                                            "		name: \"John\""  +
	                                            "	}"                   +
	                                            "	Pet {"               +
	                                            "		name: \"Fido\""  +
	                                            "	}"                   +
	                                            "}";
	
	@BeforeClass
	public static void setup() throws Exception {
		migrateFamiliesToFamilies(strategy, originalModel);
	}
	
	@Test
	public void migratedShouldContainOnePerson() {
		migrated.assertEquals(1, "Person.all.size()");
	}
	
	@Test
	public void migratedShouldContainPersonCalledJane() {
		migrated.assertEquals("Jane", "Person.all.first.name");
	}
	
	@Test
	public void migratedShouldContainOnePet() {
		migrated.assertEquals(1, "Person.all.size()");
	}
	
	@Test
	public void migratedShouldContainPetCalledFido() {
		migrated.assertEquals("Fido", "Pet.all.first.name");
	}
}
