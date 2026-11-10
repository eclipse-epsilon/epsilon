/*******************************************************************************
 * Copyright (c) 2026 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 ******************************************************************************/
package org.eclipse.epsilon.coverage.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.epsilon.coverage.CoverageElementTypes;
import org.eclipse.epsilon.coverage.CoverageLines;
import org.eclipse.epsilon.coverage.CoverageLines.Status;
import org.eclipse.epsilon.coverage.model.CoverageElement;
import org.eclipse.epsilon.coverage.model.CoverageSession;
import org.eclipse.epsilon.coverage.model.SourceFile;

public class CoverageTestUtil {

	public static File write(File dir, String name, String contents) throws Exception {
		File file = new File(dir, name);
		file.getParentFile().mkdirs();
		Files.write(file.toPath(), contents.getBytes(StandardCharsets.UTF_8));
		return file;
	}

	public static SourceFile getFile(CoverageSession session, File file) {
		String uri = file.toURI().toString();
		for (SourceFile sourceFile : session.getFiles()) {
			if (uri.equals(sourceFile.getUri())) return sourceFile;
		}
		throw new AssertionError("No coverage for " + uri + " in " + uris(session));
	}

	protected static List<String> uris(CoverageSession session) {
		List<String> uris = new ArrayList<>();
		for (SourceFile file : session.getFiles()) uris.add(file.getUri());
		return uris;
	}

	public static List<CoverageElement> getProbes(EObject root) {
		List<CoverageElement> probes = new ArrayList<>();
		for (TreeIterator<EObject> it = root.eAllContents(); it.hasNext();) {
			EObject next = it.next();
			if (next instanceof CoverageElement && ((CoverageElement) next).isProbe()) {
				probes.add((CoverageElement) next);
			}
		}
		return probes;
	}

	public static List<CoverageElement> getMissed(EObject root) {
		List<CoverageElement> missed = new ArrayList<>();
		for (CoverageElement probe : getProbes(root)) {
			if (probe.getHits() == 0) missed.add(probe);
		}
		return missed;
	}

	public static String describe(List<CoverageElement> elements) {
		StringBuilder sb = new StringBuilder();
		for (CoverageElement element : elements) {
			sb.append("\n  ").append(CoverageElementTypes.getSimpleName(element)).append(" @ ")
				.append(element.getStartLine()).append(":").append(element.getStartColumn())
				.append(" '").append(element.getLabel()).append("'");
		}
		return sb.toString();
	}

	public static void assertFullyCovered(EObject root) {
		List<CoverageElement> missed = getMissed(root);
		assertTrue("Expected no missed probes but found:" + describe(missed), missed.isEmpty());
		assertTrue("Expected some probes", !getProbes(root).isEmpty());
	}

	public static void assertLine(SourceFile file, int line, Status expected) {
		assertEquals("Status of line " + line, expected, new CoverageLines(file).getStatus(line));
	}

	/**
	 * @return The (1-based) number of the first line that contains the text
	 */
	public static int lineOf(String code, String text) {
		String[] lines = code.split("\n", -1);
		for (int i = 0; i < lines.length; i++) {
			if (lines[i].contains(text)) return i + 1;
		}
		throw new AssertionError("Text not found: " + text);
	}
}
