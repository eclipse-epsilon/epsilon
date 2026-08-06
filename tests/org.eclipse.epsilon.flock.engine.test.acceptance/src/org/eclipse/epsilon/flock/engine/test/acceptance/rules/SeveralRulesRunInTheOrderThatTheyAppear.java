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

import org.eclipse.epsilon.flock.engine.test.acceptance.util.FlockAcceptanceTest;
import org.junit.BeforeClass;
import org.junit.Test;


public class SeveralRulesRunInTheOrderThatTheyAppear extends FlockAcceptanceTest {

	private static final String strategy = "migrate Dog {" +
	                                       "	migrated.name := original.name + ' Dog';" +
	                                       "}" +
	                                       "migrate NamedElement {" +
	                                       "	migrated.name := original.name + ' NE';" +
	                                       "}" +
	                                       "migrate Pet {" +
	                                       "	migrated.name := original.name + ' Pet';" +
	                                       "}";
	
	private static final String originalModel = "Families {"             +
	                                            "   Dog {"               +
	                                            "       name: \"Fido\""  +
	                                            "   }"                   +
	                                            "}";
	
	@BeforeClass
	public static void setup() throws Exception {
		migrateFamiliesToFamilies(strategy, originalModel);
		
		migrated.setVariable("dog", "Dog.all.first");
	}
	
	@Test
	public void nameShouldBeSetBeLastRule() {
		migrated.assertEquals("Fido Pet", "dog.name");
	}
}
