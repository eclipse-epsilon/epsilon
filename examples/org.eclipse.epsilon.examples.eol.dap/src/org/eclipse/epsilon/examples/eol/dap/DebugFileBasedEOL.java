package org.eclipse.epsilon.examples.eol.dap;

import java.io.File;

import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.dap.EpsilonDebugServer;

public class DebugFileBasedEOL {

	public static void main(String[] args) throws Exception {
		EolModule module = new EolModule();
		module.parse(new File(args[0]));

		EpsilonDebugServer server = new EpsilonDebugServer(module, 4040);
		server.run();
	}
}
