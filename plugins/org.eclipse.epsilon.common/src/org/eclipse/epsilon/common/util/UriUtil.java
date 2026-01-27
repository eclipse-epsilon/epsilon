/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.util;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

public class UriUtil {
	private UriUtil() {
	}

	/**
	 * 
	 * @param uriStr
	 * @return
	 * @throws IllegalArgumentException
	 * @since 1.6
	 */
	public static URI sanitize(String uriStr) throws IllegalArgumentException {
		return URI.create(Objects.requireNonNull(uriStr).replace('\\', '/').replaceAll(" ", "%20"));
	}

	public static URI resolve(String path, URI... relativeTo) throws URISyntaxException {
		if (path == null)
			return null;

		path = encode(path, false);
		final URI uri = sanitize(path);

		if (uri.isAbsolute()) {
			return uri;
		} else if (relativeTo != null) {
			for (URI parent : relativeTo) {
				if (parent != null) {
					boolean parentIsJar = false;

					if (parent.toString().startsWith("jar:file:/")) {
						parentIsJar = true;
						parent = new URI(parent.toString().replace("jar:file:/", "jar:/"));
					}

					URI resolved = parent.resolve(path);

					if (resolved.isAbsolute() && resolved.getScheme() != null) {
						if (parentIsJar) {
							resolved = new URI(resolved.toString().replace("jar:/", "jar:file:/"));
						}
						return resolved;
					} else {
						return new URI(parent.toString() + path);
					}
				}
			}
		}

		return new URI("file://" + uri);
	}

	public static String encode(String s, boolean isDirectory) {
		s = s.replaceAll(" ", "%20").replaceAll("\\\\", "/");
		if (isDirectory && !s.endsWith("/"))
			s += "/";

		return s;
	}

	public static URI fileToUri(File file) throws URISyntaxException {
		final String encoded = UriUtil.encode(file.getAbsolutePath(), file.isDirectory());
		return new URI("file://" + (encoded.startsWith("/") ? encoded : '/' + encoded));
	}

	public static String getName(URI uri) {
		if (uri.getPath().contains("/"))
			return uri.getPath().substring(uri.getPath().lastIndexOf('/') + 1);
		else
			return uri.getPath();
	}
}
