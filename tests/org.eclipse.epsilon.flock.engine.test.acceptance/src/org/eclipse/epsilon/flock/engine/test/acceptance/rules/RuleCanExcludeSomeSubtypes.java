/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.flock.engine.test.acceptance.rules;

import org.eclipse.epsilon.flock.engine.test.acceptance.util.FlockAcceptanceTest;
import org.junit.BeforeClass;
import org.junit.Test;

public class RuleCanExcludeSomeSubtypes extends FlockAcceptanceTest  {
	
	private static final String strategy = "migrate NamedElement when: not original.isKindOf(Original!Dog) {" +
	                                       "	migrated.name := original.name + ' Smith';" +
	                                       "}";

	private static final String originalModel = "Families {"             +
	                                            "	Person {"            +
	                                            "		name: \"John\""  +
	                                            "	}"                   +
	                                            "   Pet {"               +
	                                            "       name: \"Tom\""   +
	                                            "   }"                   +
	                                            "   Dog {"               +
	                                            "       name: \"Fido\""  +
	                                            "   }"                   +
	                                            "}";

	@BeforeClass
	public static void setup() throws Exception {
		migrateFamiliesToFamilies(strategy, originalModel);

		migrated.setVariable("person", "Person.all.first");
		migrated.setVariable("pet",    "Pet.all.selectOne(p|p.name.startsWith('Tom'))");
		migrated.setVariable("dog",    "Dog.all.first");
	}

	@Test
	public void subtypesOfNamedElementShouldBeMigrated() {
		migrated.assertEquals("John Smith", "person.name");
		migrated.assertEquals("Tom Smith", "pet.name");
	}

	@Test
	public void specifiedSubtypeShouldBeExcluded() {
		migrated.assertEquals("Fido", "dog.name");
	}
}
