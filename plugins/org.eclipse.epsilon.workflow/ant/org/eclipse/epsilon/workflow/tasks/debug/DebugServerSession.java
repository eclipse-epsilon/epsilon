/*******************************************************************************
 * Copyright (c) 2025 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.workflow.tasks.debug;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.dap.EpsilonDebugServer;
import org.eclipse.epsilon.eol.dap.ExecutionQueueModule;
import org.eclipse.lsp4j.debug.TerminateArguments;

public class DebugServerSession {

	private EpsilonDebugServer server;
	private Thread serverThread;
	private ExecutionQueueModule queueModule;

	public DebugServerSession(int port) {
		queueModule = new ExecutionQueueModule();
		server = new EpsilonDebugServer(queueModule, port);
		serverThread = new Thread(server::run, "Epsilon Debug Server Thread");
	}

	public EpsilonDebugServer getServer() {
		return server;
	}

	public Thread getServerThread() {
		return serverThread;
	}

	public ExecutionQueueModule getQueueModule() {
		return queueModule;
	}

	public void start() throws InterruptedException {
		CountDownLatch latch = new CountDownLatch(1);
		server.setOnStart(latch::countDown);
		serverThread.start();

		// Wait for the server to start before moving on
		latch.await();
	}

	public void shutdown() {
		server.getDebugAdapter().terminate(new TerminateArguments());

		Future<Object> lastModule = queueModule.enqueue(new EolModule());
		try {
			// Wait for the final module to finish running
			lastModule.get();
			// Wait for the server to fully shut down
			serverThread.join();
		} catch (Exception e) {
			e.printStackTrace();
		}

		server = null;
		serverThread = null;
		queueModule = null;
	}
}
