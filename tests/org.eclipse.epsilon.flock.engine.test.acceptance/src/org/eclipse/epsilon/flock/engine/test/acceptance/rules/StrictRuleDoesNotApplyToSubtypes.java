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

public class StrictRuleDoesNotApplyToSubtypes extends FlockAcceptanceTest {

	private static final String strategy = "@strict" + "\n" +
	                                       "migrate Pet {" +
	                                       "	migrated.name := original.name + ' Smith';" +
	                                       "}";

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

		migrated.setVariable("tom",  "NamedElement.all.selectOne(e|e.name.startsWith('Tom'))");
		migrated.setVariable("fido", "NamedElement.all.selectOne(e|e.name.startsWith('Fido'))");
	}

	@Test
	public void ruleShouldHaveBeenAppliedToDirectSubtype() {
		migrated.assertEquals("Tom Smith", "tom.name");
	}

	@Test
	public void ruleShouldNotHaveBeenAppliedToIndirectSubtype() {
		migrated.assertEquals("Fido", "fido.name");
	}
}
