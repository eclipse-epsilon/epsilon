/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.flexmi.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.eclipse.epsilon.flexmi.FlexmiResource;
import org.junit.Test;

public class IdTests extends FlexmiTests {
	
	@Test
	public void testNoWarnings() throws Exception {
		assertNoWarnings("standalone/valid-model-with-fully-qualified-ids.flexmi");
	}
	
	@Test
	public void testTemplatesAndIds() throws Exception {
		FlexmiResource resource =  loadResource("templates/model-with-template-and-fully-qualified-ids.flexmi");
		assertNotNull(resource.getEObject("p1.C1"));
		assertNotNull(resource.getEObject("p1.C2"));
		assertNull(resource.getEObject("p1.C"));
	}
	
	@Test
	public void testFullyQualifiedIds() throws Exception {
		assertEval("EClass.all.get(1).eSuperTypes.first().eContainer().name", "p1", "standalone/valid-model-with-fully-qualified-ids.flexmi");
		assertEval("EClass.all.get(2).eSuperTypes.first().eContainer().name", "p2", "standalone/valid-model-with-fully-qualified-ids.flexmi");
	}
	
}
