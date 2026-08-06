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
import static org.eclipse.epsilon.test.util.builders.emf.EPackageBuilder.anEPackage;
import static org.eclipse.epsilon.test.util.builders.emf.MetamodelBuilder.aMetamodelWithSeveralPackages;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.epsilon.flock.engine.test.acceptance.util.FlockAcceptanceTest;
import org.junit.BeforeClass;
import org.junit.Test;


public class RetypePackageWithGuard extends FlockAcceptanceTest {

	private static final String strategy = "retype package families to families2 when: original.name == 'John'";
	
	private static final String originalModel = "Families {"             +
	                                            "	Person {"            +
	                                            "		name: \"John\""  +
	                                            "	}"                   +
	                                            "   Person {"            +
	                                            "		name: \"Jane\""	 +
	                                            "	}"                   +
	                                            "	Pet {"               +
	                                            "		name: \"John\""  +
	                                            "	}"                   +
	                                            "   Pet {"               +
	                                            "		name: \"Fido\""	 +
	                                            "	}"                   +
	                                            "}";
	
	private static final EPackage[] evolvedMetamodel = aMetamodelWithSeveralPackages()
	                                                 	.with(anEPackage().named("families").withNsURI("families")
	                                                 		.with(anEClass().named("Person")
	                                                 			.with(anEAttribute()
	                                                 				.named("name")
	                                                 				.withType(EcorePackage.eINSTANCE.getEString())
	                                                 			)
	                                                 		)
	                                                 		.with(anEClass().named("Pet")
	                                                 			.with(anEAttribute()
	                                                 				.named("name")
	                                                 				.withType(EcorePackage.eINSTANCE.getEString())
	                                                 			)
	                                                 		)
	                                                 	).with(anEPackage().named("families2").withNsURI("families2")
		                                               		.with(anEClass().named("Person")
			                                                	.with(anEAttribute()
			                                                		.named("name")
			                                                		.withType(EcorePackage.eINSTANCE.getEString())
			                                                	)
			                                                )
			                                                .with(anEClass().named("Pet")
	                                                 			.with(anEAttribute()
	                                                 				.named("name")
	                                                 				.withType(EcorePackage.eINSTANCE.getEString())
	                                                 			)
	                                                 		)
		                                               	).build();
	
	@BeforeClass
	public static void setup() throws Exception {
		migrateFamilies(strategy, originalModel, evolvedMetamodel);
	}	
	
	@Test
	public void johnPersonShouldBeInNewPackage() {
		migrated.assertEquals("John", "families2::Person.all.first.name");
	}
	
	@Test
	public void johnPetShouldBeInNewPackage() {
		migrated.assertEquals("John", "families2::Pet.all.first.name");
	}
	
	@Test
	public void janeShouldBeInOldPackage() {
		migrated.assertEquals("Jane", "families::Person.all.first.name");
	}
	
	@Test
	public void fidoShouldBeInOldPackage() {
		migrated.assertEquals("Fido", "families::Pet.all.first.name");
	}
}
