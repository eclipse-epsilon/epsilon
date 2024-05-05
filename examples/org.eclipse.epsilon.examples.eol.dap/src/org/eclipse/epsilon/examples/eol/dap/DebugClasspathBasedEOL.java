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
		 * This mapping is only needed if we're running from a JAR file.
		 *
		 * If our classpath is the bin/ folder from the Eclipse project,
		 * then the debug adapter will ignore this mapping and directly
		 * report the path to the .eol folder inside the bin/ folder,
		 * since we can be sure that is the code we are running.
		 */
		server.getDebugAdapter().getUriToPathMappings().put(
			DebugFileBasedEOL.class.getResource("03-helloFromClasspath.eol").toURI(),
			Paths.get("src/org/eclipse/epsilon/examples/eol/dap/03-helloFromClasspath.eol"));

		server.run();
	}
}
