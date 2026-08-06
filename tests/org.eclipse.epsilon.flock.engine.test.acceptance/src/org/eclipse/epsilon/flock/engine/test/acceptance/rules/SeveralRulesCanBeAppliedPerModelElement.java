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


public class SeveralRulesCanBeAppliedPerModelElement extends FlockAcceptanceTest {

	private static final String strategy = "migrate Family " +
	                                       "when: not original.name.startsWith('The ') {" +
	                                       "	migrated.name := 'The ' + original.name;" +
	                                       "}" +
	                                       "migrate Family " +
	                                       "when: original.numberOfChildren < 0 {" +
	                                       "	migrated.numberOfChildren := 0;" +
	                                       "}";
	
	private static final String originalModel = "Families {"                    +
	                                            "	Family {"                   +
	                                            "		name: \"The Jones\""    +
	                                            "       numberOfChildren: 2"    +
	                                            "	}"                          +
	                                            "	Family {"                   +
	                                            "		name: \"Bloggs\""       +
	                                            "       numberOfChildren: -1"   +
	                                            "	}"                          +
	                                            "	Family {"                   +
	                                            "		name: \"Does\""         +
	                                            "       numberOfChildren: 2"    +
	                                            "	}"                          +
	                                            "	Family {"                   +
	                                            "		name: \"The Smiths\""   +
	                                            "       numberOfChildren: -1"   +
	                                            "	}"                          +
	                                            "}";
	
	@BeforeClass
	public static void setup() throws Exception {
		migrateFamiliesToFamilies(strategy, originalModel);
		
		migrated.setVariable("jones",  "Family.all.selectOne(p|p.name.contains('Jones'))");
		migrated.setVariable("bloggs", "Family.all.selectOne(p|p.name.contains('Bloggs'))");
		migrated.setVariable("does",   "Family.all.selectOne(p|p.name.contains('Does'))");
		migrated.setVariable("smiths", "Family.all.selectOne(p|p.name.contains('Smiths'))");
	}
	
	@Test
	public void personForWhomNoRulesApplyIsUnchanged() {
		migrated.assertEquals("The Jones", "jones.name");
		migrated.assertEquals(2, "jones.numberOfChildren");
	}
	
	@Test
	public void personForWhomBothRulesApplyHasBeenMigratedByBothRules() {
		migrated.assertEquals("The " + "Bloggs", "bloggs.name");
		migrated.assertEquals(0, "bloggs.numberOfChildren");
	}
	
	@Test
	public void personForWhomOnlyFirstRuleAppliesHasBeenMigratedByOnlyFirstRule() {
		migrated.assertEquals("The " + "Does", "does.name");
		migrated.assertEquals(2, "does.numberOfChildren");

	}
	
	@Test
	public void personForWhomOnlySecondRuleAppliesHasBeenMigratedByOnlySecondRule() {
		migrated.assertEquals("The Smiths", "smiths.name");
		migrated.assertEquals(0, "smiths.numberOfChildren");
	}
}
