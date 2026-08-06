/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.engine.traceability.fine.test.acceptance.contributions;

import static org.eclipse.epsilon.test.util.builders.emf.EClassBuilder.anEClass;
import static org.eclipse.epsilon.test.util.builders.emf.EPackageBuilder.aMetamodel;
import static org.junit.Assert.assertEquals;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.epsilon.egl.engine.traceability.fine.test.acceptance.EglFineGrainedTraceabilityAcceptanceTest;
import org.junit.BeforeClass;
import org.junit.Test;

public class DynamicOutputSectionsContributeToTrace extends EglFineGrainedTraceabilityAcceptanceTest {
	
	private static final String egl = "[%=EClass.all.first.name%]";

	private static final EClass   person = anEClass().named("Person").build();
	private static final EPackage model  = aMetamodel().with(person).build();
	
	@BeforeClass
	public static void setup() throws Exception {
		generateTrace(egl, model);
		
		trace.setVariable("element", "trace.traceLinks.first");
	}
	
	@Test
	public void sourceShouldBeTheNameAttributeOfPerson() throws Throwable {
		assertEquals(person, trace.evaluate("element.source.modelElement"));
		trace.assertEquals("name", "element.source.propertyName");
	}
	
	@Test
	public void destinationShouldStartAtOrigin() {
		trace.assertEquals(0, "element.destination.region.offset");
	}
	
	@Test
	public void destinationShouldEndAtTheEndOfTheValue() {
		trace.assertEquals("Person".length(), "element.destination.region.length");
	}
}
