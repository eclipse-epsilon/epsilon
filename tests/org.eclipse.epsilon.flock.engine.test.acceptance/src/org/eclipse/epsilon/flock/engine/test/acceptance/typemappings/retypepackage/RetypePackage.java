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
package org.eclipse.epsilon.flock.engine.test.acceptance.typemappings.retypepackage;

import static org.eclipse.epsilon.test.util.builders.emf.EAttributeBuilder.anEAttribute;
import static org.eclipse.epsilon.test.util.builders.emf.EClassBuilder.anEClass;
import static org.eclipse.epsilon.test.util.builders.emf.EPackageBuilder.aMetamodel;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.epsilon.flock.engine.test.acceptance.util.FlockAcceptanceTest;
import org.junit.BeforeClass;
import org.junit.Test;


public class RetypePackage extends FlockAcceptanceTest {

	private static final String strategy = "retype package families to families2";
	
	private static final String originalModel = "Families {"             +
	                                            "	Person {"            +
	                                            "		name: \"John\""  +
	                                            "	}"                   +
	                                            "	Family {"			 +
	                                            "		name: \"Does\""  +
	                                            "   }"                   +
	                                            "   Pet {"               +
	                                            "		name: \"Fido\""  +
	                                            "   }"                   +
	                                            "}";
	
	private static final EPackage evolvedMetamodel = aMetamodel()
	                                                 	.named("families2")
	                                                 	.with(anEClass().named("Person")
	                                                 		.with(anEAttribute()
	                                                 			.named("name")
	                                                 			.withType(EcorePackage.eINSTANCE.getEString())
	                                               			)
	                                               		).with(anEClass().named("Family")
	                                               		).build();
	
	@BeforeClass
	public static void setup() throws Exception {
		migrateFamilies(strategy, originalModel, evolvedMetamodel);		
	}
	
	@Test
	public void personShouldBeMigrated() {
		migrated.assertEquals("John", "families2::Person.all.first.name");
	}
	
	@Test
	public void familyShouldBeMigrated() {
		migrated.assertEquals(1, "families2::Family.all.size");
	}
	
	@Test
	public void petShouldNotBeMigrated() {
		migrated.assertNumberOfModelElementsIs(2);
	}
}
