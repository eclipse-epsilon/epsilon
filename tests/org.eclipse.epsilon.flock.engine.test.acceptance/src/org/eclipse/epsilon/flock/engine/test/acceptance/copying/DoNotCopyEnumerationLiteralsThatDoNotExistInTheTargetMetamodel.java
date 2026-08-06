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
import static org.eclipse.epsilon.test.util.builders.emf.EEnumBuilder.anEEnum;
import static org.eclipse.epsilon.test.util.builders.emf.EPackageBuilder.aMetamodel;
import static org.junit.Assert.assertEquals;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.epsilon.flock.engine.test.acceptance.util.FlockAcceptanceTest;
import org.junit.BeforeClass;
import org.junit.Test;


public class DoNotCopyEnumerationLiteralsThatDoNotExistInTheTargetMetamodel extends FlockAcceptanceTest {

	private static final String strategy = "";

	private static final String originalModel = "Families {"              +
	                                            "	Dog {"                +
	                                            "       breed: labrador"  +
	                                            "	}"                    +
	                                            "}";
	
	private static final EEnum dogBreedEnum = anEEnum()
		.named("DogBreed")
		.withLiteral("poodle", 0)
		.build();
	
	private static final EClass dogClass = anEClass()
		.named("Dog")
      	.with(anEAttribute()
      			.named("breed")
      			.withType(dogBreedEnum)
      	)
      	.build();

	private static final EPackage evolvedMetamodel = aMetamodel()
     	.named("families")
     	.with(dogClass)
     	.build();
	
	
	@BeforeClass
	public static void setup() throws Exception {
		migrateFamilies(strategy, originalModel, evolvedMetamodel);
		
		migrated.setVariable("dog", "Dog.all.first");
	}
	
	@Test
	public void shouldIssueAWarning() {
		assertEquals(1, result.getWarnings().size());
	}
	
	@Test
	public void shouldWarnAboutEnum() {
		final String expected = "Could not find in migrated metamodel an enumeration value equivalent to: labrador";
		assertEquals(expected, result.getWarnings().iterator().next());
	}
}
