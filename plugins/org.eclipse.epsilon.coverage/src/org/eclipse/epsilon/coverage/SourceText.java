/*******************************************************************************
 * Copyright (c) 2026 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 ******************************************************************************/
package org.eclipse.epsilon.coverage;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import org.eclipse.epsilon.common.parse.Region;

/**
 * The text of a source file, used to compute content hashes
 * (to detect stale coverage information) and element labels.
 * 
 * @since 2.10
 */
public class SourceText {
	
	protected static final int MAX_SNIPPET_LENGTH = 80;
	
	protected final String text;
	protected final String[] lines;
	
	public SourceText(String text) {
		this.text = text;
		this.lines = text.split("\r\n|\r|\n", -1);
	}
	
	/**
	 * @return the source text at the URI, or null if it cannot be read
	 */
	public static SourceText read(URI uri) {
		if (uri == null) return null;
		try (InputStream is = uri.toURL().openStream()) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buffer = new byte[8192];
			int read;
			while ((read = is.read(buffer)) > 0) bos.write(buffer, 0, read);
			return new SourceText(new String(bos.toByteArray(), StandardCharsets.UTF_8));
		}
		catch (Exception ex) {
			return null;
		}
	}
	
	public String getText() {
		return text;
	}
	
	public String getHash() {
		return hash(text);
	}
	
	/**
	 * Computes the hash used in {@link org.eclipse.epsilon.coverage.model.SourceFile#getContentHash()}.
	 * Line delimiters are normalised so that the hash is not affected by them.
	 */
	public static String hash(String text) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] bytes = digest.digest(text.replaceAll("\r\n|\r", "\n").getBytes(StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder();
			for (byte b : bytes) sb.append(String.format("%02x", b));
			return sb.toString();
		}
		catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * @return the (trimmed and abbreviated) text of the first line of the region
	 */
	public String getSnippet(Region region) {
		int line = region.getStart().getLine();
		if (line < 1 || line > lines.length) return null;
		String lineText = lines[line - 1];
		int start = Math.min(Math.max(0, region.getStart().getColumn()), lineText.length());
		int end = lineText.length();
		if (region.getEnd().getLine() == line) {
			end = Math.max(start, Math.min(region.getEnd().getColumn(), lineText.length()));
		}
		String snippet = lineText.substring(start, end).trim();
		if (region.getEnd().getLine() > line) snippet = snippet + " ...";
		if (snippet.length() > MAX_SNIPPET_LENGTH) {
			snippet = snippet.substring(0, MAX_SNIPPET_LENGTH - 3) + "...";
		}
		return snippet;
	}
}
