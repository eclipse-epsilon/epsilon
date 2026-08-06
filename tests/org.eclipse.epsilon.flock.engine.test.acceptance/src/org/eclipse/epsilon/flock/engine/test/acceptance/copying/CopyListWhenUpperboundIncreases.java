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


public class CopyListWhenUpperboundIncreases extends FlockAcceptanceTest {

	private static final String strategy = "";

	private static final String originalModel = "Families {"                                     +
	                                            "	Person \"mum\" {"                            +
	                                            "		name: \"Alice\""                         +
	                                            "	}"                                           +
	                                            "   Person \"dad\" {"                            +
	                                            "		name: \"Bob\""                           +
	                                            "	}"                                           +
	                                            "	Person \"daughter\" {"                       +
	                                            "		name: \"Carol\""                         +
	                                            "		parents: Person \"mum\", Person \"dad\"" +
	                                            "	}"                                           +
	                                            "}";
	
	private static final EClass personClass = anEClass()
	                                            .named("Person")
	                                          	.with(anEAttribute()
	                                          			.named("name")
	                                          			.withType(EcorePackage.eINSTANCE.getEString())
	                                          	      )
	                                          	 // with a parents reference defined below, in setup()
	                                          .build();

	private static final EPackage evolvedMetamodel = aMetamodel().named("families").with(personClass).build();
	
	@BeforeClass
	public static void setup() throws Exception {
		personClass.getEStructuralFeatures().add(anEReference().named("parents").references(0, 3, personClass).build());
		
		migrateFamilies(strategy, originalModel, evolvedMetamodel);
		
		migrated.setVariable("carol", "Person.all.selectOne(p|p.name = 'Carol')");
	}
	
	@Test
	public void shouldProduceThreePeople() {
		migrated.assertEquals(3, "Person.all.size");
	}
	
	@Test
	public void parentsShouldBeCopied() {
		migrated.assertEquals(2,       "carol.parents.size");
		migrated.assertEquals("Alice", "carol.parents.first.name");
		migrated.assertEquals("Bob",   "carol.parents.second.name");
	}
}
