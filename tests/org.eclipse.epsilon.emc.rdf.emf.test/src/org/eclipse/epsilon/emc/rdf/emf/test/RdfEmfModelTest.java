/*******************************************************************************
 * Copyright (c) 2025 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.rdf.emf.test;

import static org.eclipse.epsilon.emc.rdf.emf.test.RdfEmfModelTestSuite.RESOURCE_FOLDER;
import static org.junit.Assert.assertEquals;

import java.io.File;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.epsilon.emc.rdf.emf.RdfEmfModel;
import org.eclipse.epsilon.eol.EolModule;
import org.junit.Test;

public class RdfEmfModelTest {

	@Test
	public void runSpidermanQuery() throws Exception {
		RdfEmfModel m = new RdfEmfModel();
		m.setName("Model");
		m.setModelFile(new File(RESOURCE_FOLDER, "spiderman.rdfres").getCanonicalPath());
		m.setMetamodelFile(new File(RESOURCE_FOLDER, "people.ecore").getCanonicalPath());
		m.setReadOnLoad(true);
		m.setStoredOnDisposal(false);
		m.load();

		EolModule eol = new EolModule();
		eol.parse(new File(RESOURCE_FOLDER, "example.eol"));
		eol.getContext().getModelRepository().addModel(m);

		// EOL program should return the first friend of Captain America (Spiderman) 
		EObject eob = (EObject) eol.execute();
		EStructuralFeature attrName = eob.eClass().getEStructuralFeature("name");
		assertEquals("Spiderman", eob.eGet(attrName));
	}

}
