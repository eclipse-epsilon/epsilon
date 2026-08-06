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

public class RetypeAppliesToSubtypes extends FlockAcceptanceTest {
	
	private static final String strategy = "retype NamedElement to Family";
	
	private static final String originalModel = "Families {"                   +
	                                            "	Person {"                  +
	                                            "		name: \"John\""        +
	                                            "	}"                         +
	                                            "	Dog {"                     +
	                                            "		name: \"Fido\""        +
	                                            "	}"                         +
	                                            "}";

	@BeforeClass
	public static void setup() throws Exception {
		migrateFamiliesToFamilies(strategy, originalModel);
		
		migrated.setVariable("john", "NamedElement.all.selectOne(e|e.name == 'John')");
		migrated.setVariable("fido", "NamedElement.all.selectOne(e|e.name == 'Fido')");
	}
	
	@Test
	public void retypingShouldHaveBeenAppliedToDirectSubtype() {
		migrated.assertInstanceOf("Family", "john");
	}
	
	@Test
	public void retypingShouldHaveBeenAppliedToIndirectSubtype() {
		migrated.assertInstanceOf("Family", "fido");
	}
}
