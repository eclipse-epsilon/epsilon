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


public class DoNotCopyListWhenLowerboundIncreases extends FlockAcceptanceTest {

	private static final String strategy = "";

	private static final String originalModel = "Families {"                     +
	                                            "	Person \"a\" {"             +
	                                            "		name: \"Alice\""        +
	                                            "	}"                          +
	                                            "   Person \"b\" {"             +
	                                            "		name: \"Bob\""          +
	                                            "	}"                          +
	                                            "   Person \"c\" {"             +
	                                            "		name: \"Carol\""        +
	                                            "	}"                          +
	                                            "   Person \"d\" {"             +
	                                            "		name: \"David\""        +
	                                            "	}"                          +
	                                            "	Band \"acbc\" {"            +
	                                            "		members: Person \"a\"," +
	                                            "                Person \"b\"," +
	                                            "                Person \"c\""  +
	                                            "	}"                          +
	                                            "   Band \"four\" {"            +
	                                            "		members: Person \"a\"," +
	                                            "                Person \"b\"," +
	                                            "                Person \"c\"," +
	                                            "                Person \"d\""  +
	                                            "	}"                          +
	                                            "}";
	
	private static final EClass personClass = anEClass()
	                                          	.named("Person")
	                                          	.with(anEAttribute()
	                                          			.named("name")
	                                          			.withType(EcorePackage.eINSTANCE.getEString())
	                                          	)
	                                          .build();

	private static final EPackage evolvedMetamodel = aMetamodel()
	                                                 	.named("families")
	                                                 	.with(personClass)
	                                                 	.with(anEClass().named("Band")
	                                                 	      	.with(anEReference()
	                                                 	      	      	.named("members")
	                                                 	      	      	.references(4, -1, personClass)
	                                                 	      	)
	                                                 	)
	                                                 .build();

	
	@BeforeClass
	public static void setup() throws Exception {
		migrateFamilies(strategy, originalModel, evolvedMetamodel);
		
		migrated.setVariable("trio",    "Band.all.first");
		migrated.setVariable("quartet", "Band.all.second");
	}
	
	@Test
	public void shouldProduceFourPeople() {
		migrated.assertEquals(4, "Person.all.size");
	}
	
	@Test
	public void shouldProduceTwoBands() {
		migrated.assertEquals(2, "Band.all.size");
	}
	
	@Test
	public void trioMembersShouldNotBeCopied() {
		migrated.assertEquals(0, "trio.members.size");
	}
	
	@Test
	public void quartetMembersShouldBeCopied() {
		migrated.assertEquals(4,       "quartet.members.size");
		migrated.assertEquals("Alice", "quartet.members.first.name");
		migrated.assertEquals("Bob",   "quartet.members.second.name");
		migrated.assertEquals("Carol", "quartet.members.third.name");
		migrated.assertEquals("David", "quartet.members.fourth.name");

	}
}
