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
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
			if ("up".equals(uri.getScheme())) {
				return resolveUp(uri, relativeTo);
			}
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

	protected static URI resolveUp(URI uri, URI... relativeTo) throws URISyntaxException {
		assert uri.isAbsolute() : "Input URI should be absolute";
		assert "up".equals(uri.getScheme()) : "Input URI should be a up:// one";

		if (uri.getAuthority() == null || uri.getAuthority().trim().isEmpty()) {
			throw new URISyntaxException(uri.toString(), "up:// URI must have an authority");
		}
		Path uriPath = Paths.get(uri.getPath());
		if (uriPath.getNameCount() == 0) {
			throw new URISyntaxException(uri.toString(), "up:// URI must have a path");
		}

		for (URI rel : relativeTo) {
			FileSystem zipfs = null;
			try {
				if ("jar".equals(rel.getScheme())) {
					/*
					 * We need to open a file system for the zip file before accessing it with Path,
					 * as this is not done automatically.
					 */
					zipfs = FileSystems.newFileSystem(rel, Collections.emptyMap());
				} else if (!"file".equals(rel.getScheme())) {
					// up: URIs are only supported from modules with jar: and file: URIs for now
					continue;
				}

				Path relPath = Paths.get(rel).toAbsolutePath();
				Optional<Path> subPath = firstAncestorDirectoryNamed(relPath, uri.getAuthority());
				if (!subPath.isPresent()) {
					continue;
				}

				// Look for files whose path ends with the desired suffix
				List<Path> candidates = Files.walk(subPath.get())
					.filter(p -> p.toFile().isFile())
					.filter(p -> pathEndsWith(p, uriPath))
					.collect(Collectors.toList());

				if (candidates.size() > 1) {
					throw new URISyntaxException(uri.toString(), String.format(
						"Ambiguous - possible options:\n%s",
						String.join(
							System.lineSeparator(),
							candidates.stream()
								.map(c -> "- " + c.toString())
								.collect(Collectors.toList())
						)
					));
				} else if (candidates.size() == 1) {
					return candidates.get(0).toUri();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				if (zipfs != null) {
					try {
						zipfs.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		return uri;
	}

	/**
	 * Given a path {@code p} and a {@code name}, returns the subpath from the root of
	 * {@code p} to the last name component whose name is equal to {@name} (included),
	 * if it exists.
	 */
	protected static Optional<Path> firstAncestorDirectoryNamed(Path p, String name) {
		for (int i = p.getNameCount() - 1; i >= 0; i--) {
			Path nameElement = p.getName(i);
			if (nameElement.toString().equals(name)) {
				Path subPath = p.getRoot().resolve(p.subpath(0, i + 1));
				if (subPath.toFile().isDirectory()) {
					return Optional.of(subPath);
				}
			}
		}
		return Optional.empty();
	}

	protected static boolean pathEndsWith(Path p, Path suffix) {
		int iPath = p.getNameCount() - 1, iSuffix = suffix.getNameCount() - 1;

		while (iPath >= 0 && iSuffix >= 0) {
			if (!p.getName(iPath).equals(suffix.getName(iSuffix))) {
				return false;
			}
			--iPath;
			--iSuffix;
		}

		/*
		 * p ends with suffix iff we exited the above loop after exhausting all the
		 * suffix name components. There may still be more name components in p, but
		 * those do not matter.
		 */
		return iSuffix < 0;
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
