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

import static org.eclipse.epsilon.test.util.builders.emf.EPackageBuilder.aMetamodel;
import static org.junit.Assert.assertFalse;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.epsilon.flock.engine.test.acceptance.util.FlockAcceptanceTest;
import org.junit.BeforeClass;
import org.junit.Test;


public class IgnoreInstancesWithUnknownMigratedClass extends FlockAcceptanceTest {

	private static final String strategy = "";
	
	private static final String originalModel = "Families {"             +
	                                            "	Person {"            +
	                                            "		name: \"John\""  +
	                                            "	}"                   +
	                                            "}";
	
	private static final EPackage evolvedMetamodel = aMetamodel().build();
	
	@BeforeClass
	public static void setup() throws Exception {
		migrateFamilies(strategy, originalModel, evolvedMetamodel);
	}
	
	@Test
	public void migratedModelShouldBeEmpty() {
		assertFalse(migrated.getResource().getAllContents().hasNext());
	}
}
