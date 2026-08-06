package org.eclipse.epsilon.examples.eol.streamhandler;

import java.io.File;
import java.net.URI;

import org.eclipse.epsilon.eol.EolModule;

public class Launcher {

	public static void main(String[] args) throws Exception {
		SingletonMapStreamHandlerProvider.Registry
			.getInstance()
			.putCode(new File("util.eol").getAbsolutePath(), "operation f() { return 123; }");

		// We should see 123 instead of 456
		EolModule module = new EolModule();
		module.parse(URI.create("mapentry:" + new File("main.eol").getAbsolutePath()));
		System.out.println(module.execute());
	}

}
