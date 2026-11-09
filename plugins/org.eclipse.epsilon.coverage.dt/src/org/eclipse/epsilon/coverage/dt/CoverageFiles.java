/*******************************************************************************
 * Copyright (c) 2026 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 ******************************************************************************/
package org.eclipse.epsilon.coverage.dt;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.util.Map;
import java.util.WeakHashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.epsilon.coverage.SourceText;
import org.eclipse.epsilon.coverage.model.CoverageSession;
import org.eclipse.epsilon.coverage.model.SourceFile;

/**
 * Maps the source files of coverage sessions to workspace files.
 */
public class CoverageFiles {
	
	protected static final Map<SourceFile, StaleCheck> staleChecks = new WeakHashMap<>();
	
	protected static class StaleCheck {
		long modificationStamp;
		boolean stale;
	}
	
	private CoverageFiles() {}
	
	/**
	 * @return The workspace file that corresponds to the source file, or null if there is none
	 */
	public static IFile getFile(SourceFile sourceFile) {
		URI uri = toURI(sourceFile.getUri());
		if (uri == null) return null;
		try {
			for (IFile file : ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(uri)) {
				if (file.exists()) return file;
			}
		}
		catch (Exception ex) {
			// Not a location URI
		}
		return null;
	}
	
	/**
	 * @return The source file of the session that corresponds to the URI, or null if there is none
	 */
	public static SourceFile getSourceFile(CoverageSession session, URI uri) {
		if (session == null || uri == null) return null;
		String key = normalise(uri);
		for (SourceFile sourceFile : session.getFiles()) {
			URI sourceUri = toURI(sourceFile.getUri());
			if (sourceUri != null && key.equals(normalise(sourceUri))) return sourceFile;
		}
		return null;
	}
	
	public static String getDisplayName(SourceFile sourceFile) {
		IFile file = getFile(sourceFile);
		if (file != null) return file.getFullPath().toString();
		if (sourceFile.getUri() == null) return "(inline)";
		String uri = sourceFile.getUri();
		return uri.substring(uri.lastIndexOf('/') + 1);
	}
	
	/**
	 * @return Whether the workspace file has been modified since the coverage was recorded
	 */
	public static boolean isStale(SourceFile sourceFile) {
		if (sourceFile.getContentHash() == null) return false;
		IFile file = getFile(sourceFile);
		if (file == null) return false;
		synchronized (staleChecks) {
			StaleCheck check = staleChecks.get(sourceFile);
			if (check != null && check.modificationStamp == file.getModificationStamp()) return check.stale;
			check = new StaleCheck();
			check.modificationStamp = file.getModificationStamp();
			check.stale = !sourceFile.getContentHash().equals(SourceText.hash(read(file)));
			staleChecks.put(sourceFile, check);
			return check.stale;
		}
	}
	
	public static String read(IFile file) {
		try (InputStream is = file.getContents(true)) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buffer = new byte[8192];
			int read;
			while ((read = is.read(buffer)) > 0) bos.write(buffer, 0, read);
			return new String(bos.toByteArray(), file.getCharset());
		}
		catch (Exception ex) {
			return "";
		}
	}
	
	public static URI toURI(String uri) {
		if (uri == null) return null;
		try {
			return new URI(uri);
		}
		catch (Exception ex) {
			return null;
		}
	}
	
	protected static String normalise(URI uri) {
		if ("file".equals(uri.getScheme())) {
			try {
				return new File(uri).getCanonicalPath();
			}
			catch (Exception ex) {
				// Fall back to the URI
			}
		}
		return uri.normalize().toString();
	}
}
