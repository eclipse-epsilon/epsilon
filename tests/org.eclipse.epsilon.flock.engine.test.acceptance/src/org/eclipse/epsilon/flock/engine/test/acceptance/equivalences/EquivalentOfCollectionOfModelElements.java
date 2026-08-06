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


public class EquivalentOfCollectionOfModelElements extends FlockAcceptanceTest {

	private static final String strategy = "migrate Family { " +
	                                       "	migrated.people := original.members.equivalent();" +
	                                       "}" +
	                                       "migrate Person {" +
	                                       "   migrated.name := original.name + ' Smith';" +
	                                       "}";

	private static final String originalModel = "Families {"                                      +
	                                            "	Family {"                                     +
	                                            "       members: Person \"p\" { name: \"John\"} " +
	                                            "	}"                                            +
	                                            "}";
		
	private static final EClass personClass = anEClass().named("Person")
	                                          	.with(anEAttribute()
	                                          			.named("name")
	                                          			.withType(EcorePackage.eINSTANCE.getEString())
	                                          	)
	                                          .build();
	
	private static final EPackage evolvedMetamodel = aMetamodel()
	                                                    .named("families")
	                                                    .with(personClass)
	                                                 	.with(anEClass().named("Family")
	                                                 		.with(anEReference()
	                                                 			.named("people")
	                                                 			.contains(0, -1, personClass)
	                                                 	)
	                                                 ).build();
	
	@BeforeClass
	public static void setup() throws Exception {
		migrateFamilies(strategy, originalModel, evolvedMetamodel);
		
		migrated.setVariable("family", "Family.all.first");
	}
	
	@Test
	public void membersShouldBeCopied() {
		migrated.assertEquals(1,            "family.people.size");
		migrated.assertEquals("John Smith", "family.people.first.name");
	}
}
