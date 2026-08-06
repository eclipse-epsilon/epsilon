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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CachedResourceSetTests {
	
	protected EmfModel model1;
	protected EmfModel model2;
	
	@Before
	public void setup() throws Exception {
		CachedResourceSet.getCache().clear();
		model1 = getSimpleEcore();
		model2 = getSimpleEcore();
	}
	
	@After
	public void teardown() {
		CachedResourceSet.getCache().clear();
	}
	
	@Test
	public void testSameResource() throws Exception {
		model1.load();
		model2.load();
		assertEquals(model1.getResource(), model2.getResource());
	}
	
	@Test
	public void testUnloadModels() throws Exception {
		model1.load();
		model2.load();
		
		model1.dispose();
		assertTrue(CachedResourceSet.getCache().isCached(model1.getModelFileUri()));
		model2.dispose();
		assertFalse(CachedResourceSet.getCache().isCached(model1.getModelFileUri()));
		
		assertEquals(0, CachedResourceSet.getCache().size());
	}

	@Test
	public void testDisposeBadModel() throws Exception {
		EmfModel badModel = getSimpleEcore();
		badModel.setModelFile("i-do-not-exist.ecore");

		try {
			badModel.load();
			fail("should throw an exception");
		} catch (Exception e) {
			// do nothing - this is expected
		}

		badModel.dispose();
		assertEquals("Disposing a model which failed to load should result in an empty cache",
				0, CachedResourceSet.getCache().size());		
	}

	@SuppressWarnings("resource")
	@Test
	public void testDisposeFailedSave() throws Exception {
		// Register a file extension whose saves will always fail
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("brokensave", new ResourceFactoryImpl());
		
		EmfModel model = new EmfModel();
		model.setModelFileUri(URI.createFileURI(new File("f.brokensave").getAbsolutePath()));
		model.setMetamodelUri(EcorePackage.eNS_URI);
		model.setReadOnLoad(false);
		model.setStoredOnDisposal(true);
		model.load();

		try {
			model.dispose();
			fail("Saving the model should have failed");
		} catch (Exception ex) {
			// do nothing - this is expected
		}

		assertEquals("Disposing a model which failed to save should result in an empty cache",
				0, CachedResourceSet.getCache().size());
	}

	@Test
	public void testGc() throws Exception {
		model1.load();
		model2.load();
		
		model1.dispose();
		assertTrue(CachedResourceSet.getCache().isCached(model1.getModelFileUri()));
		model2 = null;
		// This test depends on GC actually running before the assertFalse statement
		// This works fine in my setup but since GC is not deterministic it may fail sometimes
		System.gc();
		assertFalse(CachedResourceSet.getCache().isCached(model1.getModelFileUri()));		
	}
	
	protected EmfModel getSimpleEcore() throws Exception {
		EmfModel model = new EmfModel();
		File file = new File("../org.eclipse.epsilon.emc.emf.test/model/Simple.ecore");
		model.setModelFileUri(URI.createFileURI(file.getCanonicalPath()));
		model.setMetamodelUri(EcorePackage.eINSTANCE.getNsURI());
		return model;
	}
	
}
