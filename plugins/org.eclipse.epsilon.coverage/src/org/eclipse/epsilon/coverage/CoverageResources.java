/*******************************************************************************
 * Copyright (c) 2026 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 ******************************************************************************/
package org.eclipse.epsilon.coverage;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.epsilon.coverage.model.CoverageElement;
import org.eclipse.epsilon.coverage.model.CoverageFactory;
import org.eclipse.epsilon.coverage.model.CoveragePackage;
import org.eclipse.epsilon.coverage.model.CoverageSession;
import org.eclipse.epsilon.coverage.model.SourceFile;

/**
 * Utilities for saving, loading and merging coverage sessions (*.ecv files).
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
	
	public static CoverageSession merge(CoverageSession... sessions) {
		return merge(Arrays.asList(sessions));
	}
	
	/**
	 * Merges (copies of) the sessions into a new session. Hits of elements of the same 
	 * file (same URI and content hash) at the same position are summed up. If the 
	 * content hashes of a file differ between sessions, the file of the latest session
	 * in the list replaces the earlier ones. The input sessions are not modified.
	 */
	public static CoverageSession merge(Collection<CoverageSession> sessions) {
		CoverageSession merged = CoverageFactory.eINSTANCE.createCoverageSession();
		StringBuilder name = new StringBuilder();
		Date timestamp = null;
		
		for (CoverageSession session : sessions) {
			if (session.getName() != null) {
				if (name.length() > 0) name.append(" + ");
				name.append(session.getName());
			}
			if (session.getTimestamp() != null && (timestamp == null || timestamp.before(session.getTimestamp()))) {
				timestamp = session.getTimestamp();
			}
			for (SourceFile file : session.getFiles()) {
				SourceFile existing = file.getUri() == null ? null : findFile(merged, file.getUri());
				if (existing == null) {
					merged.getFiles().add(EcoreUtil.copy(file));
				}
				else if (!Objects.equals(existing.getContentHash(), file.getContentHash())) {
					merged.getFiles().set(merged.getFiles().indexOf(existing), EcoreUtil.copy(file));
				}
				else {
					mergeElements(existing.getElements(), file.getElements());
				}
			}
		}
		
		merged.setName(name.length() > 0 ? name.toString() : null);
		merged.setTimestamp(timestamp);
		return merged;
	}
	
	protected static SourceFile findFile(CoverageSession session, String uri) {
		for (SourceFile file : session.getFiles()) {
			if (uri.equals(file.getUri())) return file;
		}
		return null;
	}
	
	protected static void mergeElements(EList<CoverageElement> targets, List<CoverageElement> sources) {
		for (CoverageElement source : sources) {
			CoverageElement target = findMatching(targets, source);
			if (target == null) {
				targets.add(EcoreUtil.copy(source));
			}
			else {
				target.setHits(target.getHits() + source.getHits());
				mergeElements(target.getChildren(), source.getChildren());
			}
		}
	}
	
	protected static CoverageElement findMatching(List<CoverageElement> candidates, CoverageElement element) {
		for (CoverageElement candidate : candidates) {
			if (candidate.getStartLine() == element.getStartLine() &&
				candidate.getStartColumn() == element.getStartColumn() &&
				candidate.getEndLine() == element.getEndLine() &&
				candidate.getEndColumn() == element.getEndColumn() &&
				candidate.isProbe() == element.isProbe() &&
				Objects.equals(candidate.getKind(), element.getKind())) {
				return candidate;
			}
		}
		return null;
	}
}
