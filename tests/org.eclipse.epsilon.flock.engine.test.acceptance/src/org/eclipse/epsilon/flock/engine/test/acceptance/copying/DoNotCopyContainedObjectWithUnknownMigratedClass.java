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
package org.eclipse.epsilon.flock.engine.test.acceptance.copying;

import static org.eclipse.epsilon.test.util.builders.emf.EClassBuilder.anEClass;
import static org.eclipse.epsilon.test.util.builders.emf.EReferenceBuilder.anEReference;
import static org.eclipse.epsilon.test.util.builders.emf.EPackageBuilder.aMetamodel;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.epsilon.flock.engine.test.acceptance.util.FlockAcceptanceTest;
import org.junit.BeforeClass;
import org.junit.Test;


public class DoNotCopyContainedObjectWithUnknownMigratedClass extends FlockAcceptanceTest {

	private static final String strategy = "";
	
	private static final String originalModel = "Families {"                         +
	                                            "	Family {"                        +
	                                            "		members: Person \"John\" {}" +
	                                            "	}"                               +
	                                            "}";
	
	private static final EClass memberClass = anEClass().named("Member").build();
	
	private static final EPackage evolvedMetamodel = aMetamodel()
	                                                 	.named("families")
	                                                 	.with(anEClass().named("Family")
	                                                 		.with(anEReference()
	                                                 			.named("members")
	                                                 			.contains(0, -1, memberClass)
	                                                 	)
	                                                 ).build();
	
	@BeforeClass
	public static void setup() throws Exception {
		migrateFamilies(strategy, originalModel, evolvedMetamodel);
		
		migrated.setVariable("family", "Family.all.first");
	}
	
	@Test
	public void familyShouldBeCopied() {
		migrated.assertDefined("family");
	}
	
	@Test
	public void membersShouldBeEmpty() {
		migrated.assertEquals(0, "family.members.size()");
	}
}
