package org.eclipse.epsilon.examples.eol.dap;

import java.net.URI;

import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.dap.EpsilonDebugServer;

public class DebugClasspathBasedEOL {
	public static void main(String[] args) throws Exception {
		EolModule module = new EolModule();

		URI classpathUri = DebugFileBasedEOL.class.getResource("03-helloFromClasspath.eol").toURI();
		module.parse(classpathUri);

		EpsilonDebugServer server = new EpsilonDebugServer(module, 4040);
		server.run();
	}
}
