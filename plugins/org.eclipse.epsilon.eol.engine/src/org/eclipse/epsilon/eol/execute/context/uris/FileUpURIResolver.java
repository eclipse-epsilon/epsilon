/*******************************************************************************
 * Copyright (c) 2026 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.context.uris;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.eol.execute.context.IModuleURIResolver;

public class FileUpURIResolver extends AbstractUpURIResolver implements IModuleURIResolver {

	@Override
	public Optional<URI> resolve(URI uri, IModule parentModule, URI baseUri) throws URISyntaxException, IOException {
		if (!"up".equals(uri.getScheme()) || !"file".equals(baseUri.getScheme())) {
			// This resolver only handles up:// URIs from file:// modules
			return Optional.empty();
		}

		validateUpUri(uri);
		return resolveUpViaPaths(uri, baseUri);
	}

	protected Optional<URI> resolveUpViaPaths(URI uri, URI sourceModuleURI)
			throws URISyntaxException, IOException
	{
		Path uriPath = Paths.get(uri.getPath());
		Path relPath = Paths.get(sourceModuleURI).toAbsolutePath();
		Optional<Path> subPath = firstAncestorDirectoryNamed(relPath, uri.getAuthority());
		if (!subPath.isPresent()) {
			return Optional.empty();
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
			return Optional.of(candidates.get(0).toUri());
		}
		return Optional.empty();
	}

	/**
	 * Given a path {@code p} and a {@code name}, returns the subpath from the root of
	 * {@code p} to the last name component whose name is equal to {@name} (included),
	 * if it exists.
	 */
	protected Optional<Path> firstAncestorDirectoryNamed(Path p, String name) {
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

	protected boolean pathEndsWith(Path p, Path suffix) {
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

}
