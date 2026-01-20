/*******************************************************************************
 * Copyright (c) 2011-2018 The University of York, Aston University.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 *     Antonio Garcia-Dominguez - add tests for fragmented models
 ******************************************************************************/
package org.eclipse.epsilon.emc.emf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.junit.Test;

public class EmfModelLoadTests {

	/**
	 * @see https://bugs.eclipse.org/bugs/show_bug.cgi?id=366578
	 */
	@Test(expected=EolModelLoadingException.class)
	public void unregisteredMetamodelResultsInEpsilonException() throws Exception {
		final File modelOnDisk = FileUtil.getFileStandalone("Simple.uml", EmfModelLoadTests.class);
		EmfModelFactory.getInstance().loadEmfModel("UML", modelOnDisk, "http://www.eclipse.org/uml2/3.0.0/UML");	
	}

	@Test
	public void fragmentedModelSizeWithoutExpand() throws Exception {
		// Isolate this test from others in terms of caching
		CachedResourceSet.getCache().clear();
		EmfModel model = createFragmentedModel();
		model.setExpand(false);
		model.load();
		assertEquals("Contents for cross-file containment should work with expand=false", 2, model.allContents().size());
		model.dispose();
		assertEquals(0, CachedResourceSet.getCache().getItems().size());
	}

	@Test
	public void fragmentedModelSizeWithExpand() throws Exception {
		// Isolate this test from others in terms of caching
		CachedResourceSet.getCache().clear();

		EmfModel model = createFragmentedModel();
		model.setExpand(true);
		model.load();
		assertEquals("Contents for cross-file containment should work with expand=true", 2, model.allContents().size());
		model.dispose();
		assertEquals(0, CachedResourceSet.getCache().getItems().size());
	}

	@Test
	public void expandFalseWithCachingResolvesProxies() throws Exception {
		EmfModel model = createHControlModel();
		model.setExpand(false);
		model.setCachingEnabled(true);
		model.load();

		// Adding the eAdapter makes EMF go through direct and indirect contents
		assertEquals(20, countWithoutResolving(model));

		model.dispose();
	}

	@Test
	public void expandFalseWithNoCachingLeavesProxiesUnresolved() throws Exception {
		EmfModel model = createHControlModel();
		model.setExpand(false);
		model.setCachingEnabled(false);
		model.load();

		// HControl1.xmi only has 1 real object and 7 proxies inside it
		assertEquals(8, countWithoutResolving(model));

		model.dispose();
	}
	
	@Test
	public void loadNonExistingModel() throws Exception {
		
		CachedResourceSet.getCache().clear();
		
		EmfModel model = new EmfModel();
		model.setName("M");
		model.setMetamodelUri(EcorePackage.eNS_URI);
		model.setModelFile("/non-existent.xmi");
		model.setReadOnLoad(true);
		
		try {
			model.load();
			assertFalse("Loading should have failed", true);
		}
		catch (Exception ex) {
			assertEquals(0, CachedResourceSet.getCache().size());
		}
		
	}

	protected int countWithoutResolving(EmfModel model) {
		int itSize = 0;
		for (TreeIterator<Object> it = EcoreUtil.getAllContents(model.getResource(), false); it.hasNext(); itSize++) {
			Object eob = it.next();
			System.out.println(eob);
		}
		return itSize;
	}

	protected EmfModel createHControlModel() throws Exception {
		Class<?> relC = EmfModelLoadTests.class;
		// Load dependencies
		FileUtil.getFileStandalone("xml/fragmented/HControl1/H1Arquitecture.arq", relC);
		FileUtil.getFileStandalone("xml/fragmented/HControl1/H2.control", relC);
		FileUtil.getFileStandalone("xml/fragmented/HControl1/H3.control", relC);
		FileUtil.getFileStandalone("xml/fragmented/HControl1/H4.control", relC);
		FileUtil.getFileStandalone("xml/fragmented/HControl1/HControlDefinition.control", relC);
		FileUtil.getFileStandalone("xml/fragmented/HControl1/new_file.control", relC);
		FileUtil.getFileStandalone("xml/fragmented/HControl1/HControl2/H2Control1.control", relC);
		FileUtil.getFileStandalone("xml/fragmented/HControl1/HControl2/H2Control2.control", relC);
		FileUtil.getFileStandalone("xml/fragmented/HControl1/HControl2/H2Control3.control", relC);
		FileUtil.getFileStandalone("xml/fragmented/HControl1/HControl2/H2Control4.control", relC);
		FileUtil.getFileStandalone("xml/fragmented/HControl1/HControl2/H2Control5.control", relC);
		FileUtil.getFileStandalone("xml/fragmented/HControl1/HControl2/HControl2.xmi", relC);
		File fEcore = FileUtil.getFileStandalone("xml/fragmented/WT_DesignPatterns.ecore", relC);
		File fXMI = FileUtil.getFileStandalone("xml/fragmented/HControl1/HControl1.xmi", relC);
		EmfModel model = EmfModelFactory.getInstance().createEmfModel("Model", fXMI, fEcore);
		return model;
	}

	protected EmfModel createFragmentedModel() throws Exception {
		File fXMI = FileUtil.getFileStandalone("tree-0.xmi", EmfModelLoadTests.class);
		File fEcore = FileUtil.getFileStandalone("Tree.ecore", EmfModelLoadTests.class);
		return EmfModelFactory.getInstance().createEmfModel("Tree", fXMI, fEcore);
	}
}
