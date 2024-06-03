package org.eclipse.epsilon.examples.eol.dap;

import java.io.File;

import org.eclipse.epsilon.egl.EglModule;
import org.eclipse.epsilon.eol.dap.EpsilonDebugServer;

public class DebugFileBasedEGL {

	public static void main(String[] args) throws Exception {
		EglModule module = new EglModule();
		module.parse(new File(args[0]));

		EpsilonDebugServer server = new EpsilonDebugServer(module, 4040);
		server.run();
	}

}
