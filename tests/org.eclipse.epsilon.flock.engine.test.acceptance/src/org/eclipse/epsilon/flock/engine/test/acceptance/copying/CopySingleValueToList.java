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

import static org.eclipse.epsilon.test.util.builders.emf.EAttributeBuilder.anEAttribute;
import static org.eclipse.epsilon.test.util.builders.emf.EClassBuilder.anEClass;
import static org.eclipse.epsilon.test.util.builders.emf.EReferenceBuilder.anEReference;
import static org.eclipse.epsilon.test.util.builders.emf.EPackageBuilder.aMetamodel;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.epsilon.flock.engine.test.acceptance.util.FlockAcceptanceTest;
import org.junit.BeforeClass;
import org.junit.Test;


public class CopySingleValueToList extends FlockAcceptanceTest {

	private static final String strategy = "";

	private static final String originalModel = "Families {"                  +
	                                            "	Bike {"                   +
	                                            "       owner: Family \"f\""  +
	                                            "	}"                        +
	                                            "   Family \"f\" {"           +
	                                            "		name: \"The Smiths\"" +
	                                            "	}"                        +
	                                            "}";
	
	private static final EClass familyClass = anEClass()
	                                          	.named("Family")
	                                          	.with(anEAttribute()
	                                          			.named("name")
	                                          			.withType(EcorePackage.eINSTANCE.getEString())
	                                          	      )
	                                          .build();

	private static final EPackage evolvedMetamodel = aMetamodel()
	                                                 	.named("families")
	                                                 	.with(familyClass)
	                                                 	.with(anEClass().named("Bike")
	                                                 		.with(anEReference()
	                                                 			.named("owner")
	                                                 			.references(0, -1, familyClass)
	                                                 		)
	                                                 	)
	                                                 .build();
	
	@BeforeClass
	public static void setup() throws Exception {
		migrateFamilies(strategy, originalModel, evolvedMetamodel);
		
		migrated.setVariable("bike",   "Bike.all.first");
		migrated.setVariable("family", "Family.all.first");
	}
	
	@Test
	public void shouldProduceOneBike() {
		migrated.assertEquals(1, "Bike.all.size");
	}
	
	@Test
	public void shouldProduceOneFamily() {
		migrated.assertEquals(1, "Family.all.size");
	}
	
	@Test
	public void ownerShouldBeCopied() {
		migrated.assertEquals(1, "bike.owner.size");
		migrated.assertTrue("family = bike.owner.first");
	}
}
