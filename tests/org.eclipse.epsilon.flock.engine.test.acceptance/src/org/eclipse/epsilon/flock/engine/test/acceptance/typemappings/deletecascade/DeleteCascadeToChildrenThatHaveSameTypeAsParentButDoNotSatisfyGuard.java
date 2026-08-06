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

/**
 * This test covers an edge case of cascading delete behaviour. When a cascading delete
 * is to be applied to a model element of type X which also contains other model elements
 * of type X, we need to be sure to delete the children of type X even if they do not satisfy
 * the guard of the deletion rule. In fact, we do not even want to check the applicability
 * of the cascading deletion rule to any children (even if those children are of the same type
 * as the parent element).
 */
public class DeleteCascadeToChildrenThatHaveSameTypeAsParentButDoNotSatisfyGuard extends FlockAcceptanceTest {
	
	private static final String strategy = "@cascade" + "\n" +
	                                       "delete Model when: original.name == \"A\"";
	
	private static final String originalModel = "Families {"                 +
	                                            "	Model {"                 +
	                                            "		name: \"A\""         +
	                                            "		contents: Model { "  +
	                                            "				name: \"B\"" +
	                                            "			}"               +
	                                            "	}"                       +
	                                            "}";
	
	@BeforeClass
	public static void setup() throws Exception {
		migrateFamiliesToFamilies(strategy, originalModel);
	}
	
	@Test
	public void migratedShouldContainNoModels() {
		migrated.assertEquals(0, "Model.all.size()");
	}
}
