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

import static org.eclipse.epsilon.test.util.builders.emf.EPackageBuilder.aMetamodel;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.epsilon.egl.engine.traceability.fine.test.acceptance.EglFineGrainedTraceabilityAcceptanceTest;
import org.junit.BeforeClass;
import org.junit.Test;

public class StaticSectionsDoNotContributeToTrace extends EglFineGrainedTraceabilityAcceptanceTest {

	private static final String egl = "Hello world!";

	private static final EPackage model = aMetamodel().build();
	
	@BeforeClass
	public static void setup() throws Exception {
		generateTrace(egl, model);	
	}
	
	@Test
	public void thereShouldBeNoTraceLinks() {
		trace.assertEquals(0, "trace.traceLinks.size()");
	}
	
	@Test
	public void thereShouldBeNoTextLocations() {
		trace.assertEquals(0, "trace.locations.size()");
	}	
}
