/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.flock.engine.test.acceptance.typemappings.retype;

import org.eclipse.epsilon.flock.engine.test.acceptance.util.FlockAcceptanceTest;
import org.junit.BeforeClass;
import org.junit.Test;

public class RetypeCanExcludeSomeSubtypes extends FlockAcceptanceTest  {
	
	private static final String strategy = "retype NamedElement to Family when: not original.isKindOf(Original!Dog)";

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
		
		migrated.setVariable("john", "NamedElement.all.selectOne(e|e.name == 'John')");
		migrated.setVariable("tom",  "NamedElement.all.selectOne(e|e.name == 'Tom')");
		migrated.setVariable("fido", "NamedElement.all.selectOne(e|e.name == 'Fido')");
	}

	@Test
	public void subtypesOfNamedElementShouldBeRetyped() {
		migrated.assertInstanceOf("Family", "john");
		migrated.assertInstanceOf("Family", "tom");
	}

	@Test
	public void specifiedSubtypeShouldBeExcluded() {
		migrated.assertInstanceOf("Dog", "fido");
	}
}
