/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.eol.dap.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.dap.EpsilonDebugServer;
import org.eclipse.lsp4j.debug.Capabilities;
import org.eclipse.lsp4j.debug.ExitedEventArguments;
import org.eclipse.lsp4j.debug.InitializeRequestArguments;
import org.eclipse.lsp4j.debug.TerminatedEventArguments;
import org.eclipse.lsp4j.debug.launch.DSPLauncher;
import org.eclipse.lsp4j.debug.services.IDebugProtocolClient;
import org.eclipse.lsp4j.debug.services.IDebugProtocolServer;
import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class EpsilonDebugServerTest {

	private static class TestClient implements IDebugProtocolClient {
		private List<Object> messages = new ArrayList<>();

		private Semaphore hasExited = new Semaphore(0);
		private Semaphore hasTerminated = new Semaphore(0);

		@Override
		public void exited(ExitedEventArguments args) {
			synchronized (messages) {
				messages.add(args);
				hasExited.release();
			}
		}

		@Override
		public void terminated(TerminatedEventArguments args) {
			synchronized (messages) {
				messages.add(args);
				hasTerminated.release();
			}
		}
	}

	/* 10 seconds max per method tested. */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(10);

    /**
     * This test just checks that the server can be started and run through a program,
     * with no checkpoints. The rest of the DAP testing can operate directly on the
     * debug adapter.
     */
    @Test
	public void runWithoutBreakpoints() throws Exception {
		EolModule module = new EolModule();
		module.parse(new File("../org.eclipse.epsilon.eol.dap.test/epsilon/01-hello.eol"));
		
		final EpsilonDebugServer server = new EpsilonDebugServer(module, 0);
		final Thread serverThread = new Thread(server);
		serverThread.start();
		assertTrue("Server should have started successfully", server.isStarted().get(5, TimeUnit.SECONDS));

		Future<Void> launcherTask = null;
		try (Socket sock = connect("localhost", server.getPort(), Duration.ofSeconds(5))) {
			final TestClient testClient = new TestClient();
			Launcher<IDebugProtocolServer> launcher = DSPLauncher.createClientLauncher(testClient, sock.getInputStream(), sock.getOutputStream());
			launcherTask = launcher.startListening();

			InitializeRequestArguments args = new InitializeRequestArguments();
			args.setClientID("test-client");
			args.setAdapterID("epsilon-adapter");
			IDebugProtocolServer remoteProxy = launcher.getRemoteProxy();
			Capabilities initResponse = remoteProxy.initialize(args).get(5, TimeUnit.SECONDS);
			assertNotNull("The adapter should have replied with its capabilities", initResponse);
			assertTrue("The adapter should say terminate is supported", initResponse.getSupportsTerminateRequest());
			assertTrue("The adapter should say conditional breakpoints are supported", initResponse.getSupportsConditionalBreakpoints());

			CompletableFuture<Void> waitForAttach = remoteProxy.attach(Collections.emptyMap());
			waitForAttach.get(5, TimeUnit.SECONDS);

			assertTrue("The adapter should send a terminated message within 5 seconds",
				testClient.hasTerminated.tryAcquire(5, TimeUnit.SECONDS));
			assertTrue("The adapter should send an exited message within 5 seconds",
				testClient.hasExited.tryAcquire(5, TimeUnit.SECONDS));

			int terminatedIndex = indexOf(testClient.messages, o -> o instanceof TerminatedEventArguments);
			int exitedIndex = indexOf(testClient.messages, o -> o instanceof ExitedEventArguments);
			assertTrue(
				String.format("terminated index (%d) should be less than exited index (%d)", terminatedIndex, exitedIndex),
				terminatedIndex < exitedIndex);

			assertEquals("The module should report its produced result", 123, server.getResult().get());
		} finally {
			if (launcherTask != null) {
				launcherTask.cancel(true);
			}
			server.shutdown();
		}
	}

    protected Socket connect(String host, int port, Duration maxWait) throws IOException {
    	final Instant deadline = Instant.now().plus(maxWait);

		Socket socket = null;
		try {
			do {
				try {
					socket = new Socket((String)null, port);
				} catch (ConnectException e) {
					Thread.sleep(500);
				}
			} while (socket == null && Instant.now().isBefore(deadline));
		} catch (InterruptedException e1) {
			// exit the loop
		}

		if (socket == null) {
			throw new ConnectException(String.format(
				"Failed to connect to %s:%d after %d ms",
				host, port, maxWait.toMillis()
			));
		} else {
			return socket;
		}
    }
   
	protected <T> int indexOf(Iterable<T> iterable, Function<T, Boolean> filter) {
		int i = 0;
		for (T elem : iterable) {
			if (filter.apply(elem)) return i;
			i++;
		}
		return -1;
	}
}
