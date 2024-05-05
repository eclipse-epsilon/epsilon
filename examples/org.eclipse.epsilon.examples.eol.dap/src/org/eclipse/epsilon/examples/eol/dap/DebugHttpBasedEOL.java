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
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.dap.EpsilonDebugServer;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.Callback;

public class DebugHttpBasedEOL {
	public static void main(String[] args) throws Exception {
		Server httpServer = new Server();
		ServerConnector connector = new ServerConnector(httpServer);
		connector.setHost("localhost");
		httpServer.addConnector(connector);
		httpServer.setHandler(new Handler.Abstract() {
			@Override
			public boolean handle(Request request, Response response, Callback callback) throws Exception {
				response.setStatus(200);
				response.getHeaders().put(HttpHeader.CONTENT_TYPE, "text/plain; charset=UTF-8");
				ByteBuffer eolByteBuffer = ByteBuffer.wrap(Files.readAllBytes(Paths.get("epsilon", "01-hello.eol")));
				response.write(true, eolByteBuffer, callback);
				return true;
			}
		});
		httpServer.start();
		String serverUri = String.format("http://%s:%d/epsilon/", connector.getHost(), connector.getLocalPort());
		Thread.sleep(1000);

		try {
			EolModule module = new EolModule();
			module.parse(new URI(serverUri + "01-hello.eol"));

			EpsilonDebugServer server = new EpsilonDebugServer(module, 4040);
			server.getDebugAdapter().getUriToPathMappings().put(new URI(serverUri), Paths.get("epsilon"));
			server.run();
		} finally {
			httpServer.stop();
			httpServer.join();
		}
	}
}
