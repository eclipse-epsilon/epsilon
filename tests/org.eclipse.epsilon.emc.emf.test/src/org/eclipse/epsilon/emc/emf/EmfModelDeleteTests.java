/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.emf;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.epsilon.emc.emf.EmfModelFactory.AccessMode;
import org.junit.Test;

public class EmfModelDeleteTests {
	
	@Test
	public void testDeleteWithCache() throws Exception {
		
		EmfModel model = EmfModelFactory.getInstance().createEmfModel("Ecore", new File("../org.eclipse.epsilon.emc.emf.test/model/Delete.ecore"), EcorePackage.eINSTANCE.getNsURI(), AccessMode.READ_ONLY);	
		model.setCachingEnabled(true);
		model.load();
		
		assertEquals(1, model.getAllOfType("EClass").size());
		assertEquals(1, model.getAllOfKind("EOperation").size());
		assertEquals(1, model.getAllOfType("EParameter").size());
		
		model.deleteElement(model.getAllOfType("EClass").iterator().next());
		
		assertEquals(0, model.getAllOfKind("EOperation").size());
		assertEquals(0, model.getAllOfType("EParameter").size());
		
	}
	
}
