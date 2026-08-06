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
package org.eclipse.epsilon.flock.engine.test.acceptance.typemappings.retype;

import static org.eclipse.epsilon.test.util.builders.emf.EAttributeBuilder.anEAttribute;
import static org.eclipse.epsilon.test.util.builders.emf.EClassBuilder.anEClass;
import static org.eclipse.epsilon.test.util.builders.emf.EPackageBuilder.aMetamodel;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.epsilon.flock.engine.test.acceptance.util.FlockAcceptanceTest;
import org.junit.BeforeClass;
import org.junit.Test;


public class RetypeWithGuard extends FlockAcceptanceTest {

	private static final String strategy = "retype Person to Salesperson when: original.name == 'John'";
	
	private static final String originalModel = "Families {"             +
	                                            "	Person {"            +
	                                            "		name: \"John\""  +
	                                            "	}"                   +
	                                            "   Person {"            +
	                                            "		name: \"Jane\""	 +
	                                            "	}"                   +
	                                            "}";
	
	private static final EPackage evolvedMetamodel = aMetamodel().named("families")
	                                                 	.with(anEClass().named("Person")
	                                                 		.with(anEAttribute()
	                                                 			.named("name")
	                                                 			.withType(EcorePackage.eINSTANCE.getEString())
	                                               			)
	                                               		).
	                                               		with(anEClass().named("Salesperson")
		                                                	.with(anEAttribute()
		                                                		.named("name")
		                                                		.withType(EcorePackage.eINSTANCE.getEString())
		                                                	)
		                                               	).build();
	
	@BeforeClass
	public static void setup() throws Exception {
		migrateFamilies(strategy, originalModel, evolvedMetamodel);
		
		migrated.setVariable("sp", "Salesperson.all.first");
		migrated.setVariable("p",  "Person.all.first");
	}
	
	@Test
	public void salespersonShouldBeJohn() {
		migrated.assertEquals("John", "sp.name");
	}
	
	@Test
	public void personShouldBeJane() {
		migrated.assertEquals("Jane", "p.name");
	}
}
