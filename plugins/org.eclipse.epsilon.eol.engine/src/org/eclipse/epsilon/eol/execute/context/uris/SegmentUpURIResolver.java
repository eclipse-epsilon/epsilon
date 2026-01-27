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
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.eclipse.epsilon.common.module.IModule;

public class SegmentUpURIResolver extends AbstractUpURIResolver {

	@Override
	public Optional<URI> resolve(URI uri, IModule parentModule, URI baseUri) throws URISyntaxException, IOException {
		if (!"up".equals(uri.getScheme()) || !baseUri.isAbsolute() || baseUri.isOpaque()) {
			// This resolver only handles up:// URIs from modules with absolute non-opaque URIs
			return Optional.empty();
		}

		validateUpUri(uri);
		return resolveUpViaSegments(uri, baseUri);
	}

	protected Optional<URI> resolveUpViaSegments(URI uri, URI rel) throws URISyntaxException, IOException {
		String[] segments = rel.getPath().split("/");

		for (int iSegment = segments.length - 1; iSegment >= 0; --iSegment) {
			if (uri.getAuthority().equals(segments[iSegment])) {
				List<String> subSegments = Arrays.asList(segments).subList(0, iSegment+1);
				String pathToSegment = String.join("/", subSegments);
				URI resolved = new URI(rel.getScheme(), rel.getAuthority(),
					pathToSegment + uri.getPath(), null);

				// Try to open the URL - if we succeed, resolve it
				try (InputStream is = resolved.toURL().openStream()) {
					return Optional.of(resolved);
				}
			}
		}

		return Optional.empty();
	}

}
