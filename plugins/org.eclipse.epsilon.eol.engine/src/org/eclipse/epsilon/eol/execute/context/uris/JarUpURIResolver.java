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
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.Collections;
import java.util.Optional;

import org.eclipse.epsilon.common.module.IModule;

public class JarUpURIResolver extends FileUpURIResolver {

	@Override
	public Optional<URI> resolve(URI uri, IModule parentModule, URI rel) throws URISyntaxException, IOException {
		if (!"up".equals(uri.getScheme()) || !"jar".equals(rel.getScheme())) {
			// This resolver only handles up:// URIs from jar:// modules
			return Optional.empty();
		}

		validateUpUri(uri);
		try (FileSystem zipfs = FileSystems.newFileSystem(rel, Collections.emptyMap())) {
			/*
			 * We need to open a file system for the zip file before accessing it with Path,
			 * as this is not done automatically.
			 */
			return resolveUpViaPaths(uri, rel);
		}
	}

}
