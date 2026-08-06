/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.etl.engine.test.acceptance;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.test.util.ResourceComparator;

public abstract class EtlTest {
	
	protected EmfModel loadModel(String name, String modelFile, String metamodelUri, boolean read, boolean store) throws Exception {
		EmfModel model = new EmfModel();
		model.setCachingEnabled(false);
		model.setName(name);
		model.setModelFile(getFullPath(modelFile));
		model.setMetamodelUri(metamodelUri);
		model.setReadOnLoad(read);
		model.setStoredOnDisposal(store);
		model.load();
		return model;
	}
	
	/**
	 * 
	 * @param expected
	 * @param actual
	 * @param cleanRes
	 * @throws Exception
	 * @since 1.6
	 */
	protected void testForEquivalence(Resource expected, EmfModel actual, Resource cleanRes) throws Exception {
		try {
			ResourceComparator.compare(expected, actual.getResource());
		}
		catch (AssertionError er) {
			// For debugging purposes
			throw er;
		}
		String dirty = expected.getURI().toFileString();
		String cleanStr = cleanRes.getURI().toFileString();
		actual.getResource().delete(null);
		Path target = Files.copy(Paths.get(cleanStr), Paths.get(dirty), StandardCopyOption.REPLACE_EXISTING);
		assert target.toFile().exists();
	}
	
	protected Resource getResource(String path) throws Exception {
		return getResource(path, getClass());
	}
	
	protected static Resource getResource(String path, Class<?> currentClass) throws Exception {
		ResourceSet rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		Resource r = rs.createResource(URI.createFileURI(getFullPath(path, currentClass)));
		r.load(null);
		return r;
	}
	
	protected final File getFile(String fileName) throws IOException {
		return FileUtil.getFileStandalone(fileName, getClass());
	}
	
	protected final String getFullPath(String path) throws IOException {
		return getFullPath(path, getClass());
	}
	
	protected static final String getFullPath(String path, Class<?> currentClass) throws IOException {
		return FileUtil.getFileStandalone(path, currentClass).getAbsolutePath();
	}
	
	protected static final void registerMetamodel(String path, Class<?> currentClass) throws Exception {
		EmfUtil.register(URI.createFileURI(getFullPath(path, currentClass)), EPackage.Registry.INSTANCE);
	}
}
