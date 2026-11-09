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
import org.eclipse.epsilon.coverage.model.SourceFile;

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
	 * The URIs of the source files are saved relative to the URI where possible, so that
	 * the saved session remains valid if it is moved together with the source files.
	 */
	public static void save(CoverageSession session, URI uri) throws IOException {
		Resource resource = createResourceSet().createResource(uri);
		CoverageSession copy = EcoreUtil.copy(session);
		for (SourceFile sourceFile : copy.getFiles()) {
			sourceFile.setUri(deresolve(sourceFile.getUri(), uri));
		}
		resource.getContents().add(copy);
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
		CoverageSession session = (CoverageSession) resource.getContents().get(0);
		for (SourceFile sourceFile : session.getFiles()) {
			sourceFile.setUri(resolve(sourceFile.getUri(), uri));
		}
		return session;
	}
	
	/**
	 * @return The source file URI relative to the base URI, or the source file URI
	 * unchanged if it cannot be made relative (e.g. different scheme or drive)
	 */
	protected static String deresolve(String sourceUri, URI base) {
		if (sourceUri == null || !base.isHierarchical() || base.isRelative()) return sourceUri;
		try {
			URI uri = URI.createURI(sourceUri);
			if (!uri.isHierarchical() || uri.isRelative()) return sourceUri;
			URI relative = uri.deresolve(base, true, true, false);
			return relative.isRelative() && !relative.hasAbsolutePath() ? relative.toString() : sourceUri;
		}
		catch (IllegalArgumentException ex) {
			return sourceUri;
		}
	}
	
	/**
	 * @return The absolute URI of a (possibly relative) source file URI saved by
	 * {@link #save(CoverageSession, URI)}
	 */
	protected static String resolve(String sourceUri, URI base) {
		if (sourceUri == null || !base.isHierarchical() || base.isRelative()) return sourceUri;
		try {
			URI uri = URI.createURI(sourceUri);
			return uri.isRelative() ? uri.resolve(base).toString() : sourceUri;
		}
		catch (IllegalArgumentException ex) {
			return sourceUri;
		}
	}
}
