/*******************************************************************************
 * Copyright (c) 2026 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.context;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import org.eclipse.epsilon.common.module.IModule;

/**
 * Resolves URIs from the context of an Epsilon module.
 *
 * @since 2.9
 */
@FunctionalInterface
public interface IModuleURIResolver {

	/**
	 * Attempts to resolve the given {@code uri}. The resolver is given the
	 * {@code parentModule} and {@code baseUri} that should be used as context.
	 *
	 * @return Non-empty Optional with the resolved URI if successful, or empty
	 *         Optional if the URI could not be resolved.
	 */
	Optional<URI> resolve(URI uri, IModule parentModule, URI baseUri)
		throws URISyntaxException, IOException;

}
