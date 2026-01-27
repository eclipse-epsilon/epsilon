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

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.epsilon.eol.execute.context.IModuleURIResolver;

public abstract class AbstractUpURIResolver implements IModuleURIResolver {

	protected void validateUpUri(URI uri) throws URISyntaxException {
		if (uri.getAuthority() == null || uri.getAuthority().trim().isEmpty()) {
			throw new URISyntaxException(uri.toString(), "up:// URI must have an authority");
		}
		Path uriPath = Paths.get(uri.getPath());
		if (uriPath.getNameCount() == 0) {
			throw new URISyntaxException(uri.toString(), "up:// URI must have a path");
		}
	}

}