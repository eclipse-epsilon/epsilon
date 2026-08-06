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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.junit.Test;

public class EmfModelCachedContentsTests {

	protected EmfModel createEmptyModel() throws EolModelLoadingException {
		final EmfModel model = new EmfModel();
		model.setModelFile("dummy.xmi");
		model.setMetamodelUri(EcorePackage.eINSTANCE.getNsURI());
		model.setReadOnLoad(false);
		model.setStoredOnDisposal(false);
		model.load();
		return model;
	}

	@Test
	public void emptyModel() throws Exception {
		final EmfModel model = createEmptyModel();
		assertEquals(0, model.allContents().size());
	}

	@Test
	public void manageThroughModel() throws Exception {
		final EmfModel model = createEmptyModel();
		assertEquals(0, model.allContents().size());
		final EObject eClass = model.createInstance("EClass");
		assertEquals(1, model.allContents().size());
		model.deleteElement(eClass);
		assertEquals(0, model.allContents().size());
	}

	@Test
	public void manageExternally() throws Exception {
		final EmfModel model = createEmptyModel();
		assertEquals(0, model.allContents().size());

		final EClass eClass = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		model.getResource().getContents().add(eClass);
		assertEquals(1, model.allContents().size());

		model.getResource().getContents().remove(eClass);
		assertEquals(0, model.allContents().size());
	}


	@Test
	public void disposeTwoCopiesSameModel() throws Exception {
		final File treeEcore = FileUtil.getFileStandalone("Tree.ecore", EmfModelCachedContentsTests.class);
		
		EmfModel m1 = new EmfModel();
		m1.setModelFile("dummy.xmi");
		m1.setName("ModelA");
		m1.setReadOnLoad(false);
		m1.setMetamodelFile(treeEcore.getAbsolutePath());;
		m1.setStoredOnDisposal(false);
		m1.setCachingEnabled(true);
		m1.load();
		m1.createInstance("Tree");

		EmfModel m2 = new EmfModel();
		m2.setModelFile(m1.getModelFile());
		m2.setName("ModelB");
		m2.setReadOnLoad(false);
		m2.setStoredOnDisposal(false);
		m2.setMetamodelFile(treeEcore.getAbsolutePath());
		m2.setCachingEnabled(true);
		m2.load();

		PrintStream oldErr = System.err;
		try {
			ByteArrayOutputStream bOS = new ByteArrayOutputStream();
			System.setErr(new PrintStream(bOS));

			m1.dispose();
			m2.dispose();

			String errOutput = bOS.toString();
			assertEquals("Loading the same model twice and disposing both copies should not raise any errors", "", errOutput);
		} finally {
			System.setErr(oldErr);
		}
	}

}
