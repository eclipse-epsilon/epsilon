/*******************************************************************************
 * Copyright (c) 2026 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 ******************************************************************************/
package org.eclipse.epsilon.coverage;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.epsilon.coverage.model.CoveragePackage;
import org.eclipse.epsilon.coverage.model.CoverageSession;

/**
 * Utilities for saving and loading coverage sessions (*.ecv files).
 * 
 * @since 2.10
 */
public class CoverageResources {
	
	public static final String FILE_EXTENSION = "ecv";
	
	private CoverageResources() {}
	
	public static ResourceSet createResourceSet() {
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		resourceSet.getPackageRegistry().put(CoveragePackage.eNS_URI, CoveragePackage.eINSTANCE);
		return resourceSet;
	}
	
	public static void save(CoverageSession session, File file) throws IOException {
		save(session, URI.createFileURI(file.getAbsolutePath()));
	}
	
	/**
	 * Saves a copy of the session to the URI (the session is not moved to a new resource).
	 */
	public static void save(CoverageSession session, URI uri) throws IOException {
		Resource resource = createResourceSet().createResource(uri);
		resource.getContents().add(EcoreUtil.copy(session));
		Map<String, Object> options = new HashMap<>();
		options.put(XMLResource.OPTION_ENCODING, "UTF-8");
		resource.save(options);
	}
	
	public static CoverageSession load(File file) throws IOException {
		return load(URI.createFileURI(file.getAbsolutePath()));
	}
	
	public static CoverageSession load(URI uri) throws IOException {
		Resource resource = createResourceSet().createResource(uri);
		resource.load(Collections.emptyMap());
		if (resource.getContents().isEmpty() || !(resource.getContents().get(0) instanceof CoverageSession)) {
			throw new IOException(uri + " does not contain a coverage session");
		}
		return (CoverageSession) resource.getContents().get(0);
	}
}
