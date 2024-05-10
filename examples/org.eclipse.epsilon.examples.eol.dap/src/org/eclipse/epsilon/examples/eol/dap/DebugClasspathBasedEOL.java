/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.examples.eol.dap;

import java.net.URI;
import java.nio.file.Paths;

import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.dap.EpsilonDebugServer;

public class DebugClasspathBasedEOL {
	public static void main(String[] args) throws Exception {
		EolModule module = new EolModule();
		URI classpathUri = DebugFileBasedEOL.class.getResource("03-helloFromClasspath.eol").toURI();
		module.parse(classpathUri);

		EpsilonDebugServer server = new EpsilonDebugServer(module, 4040);

		/*
		 * This mapping is needed to translate classpath resources to files where the
		 * user will set breakpoints:
		 *
		 * - When we set a breakpoint in the src/ file, this will be mapped to a
		 * breakpoint in the module with the matching URI. The mapping can be for a
		 * specific file, or for any module within a given URI prefix (in this second
		 * case, the URI should finish with a slash).
		 *
		 * - When we hit a breakpoint, the mapping will be used to translate its URI
		 * (which may be a JAR-based URI, or the bin/ folder when running from Eclipse)
		 * back to a file that the DAP client can show to the user.
		 */
		server.getDebugAdapter().getUriToPathMappings().put(
			DebugFileBasedEOL.class.getResource("03-helloFromClasspath.eol").toURI(),
			Paths.get("src/org/eclipse/epsilon/examples/eol/dap/03-helloFromClasspath.eol"));

		server.run();
	}
}
