/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.lsp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.services.LanguageClient;

/**
 * Network-based server for the language server protocol, which listens via TCP
 * on a given host and port.
 *
 * An instance of this class can only be {@link #run()} once. If you need to
 * restart the server, you will need to create another instance.
 */
public class EpsilonLanguageServerLauncher implements Runnable {

	private static final Logger LOGGER = Logger.getLogger(EpsilonLanguageServer.class.getName());

	private final String host;
	private final int port;

	private final CompletableFuture<Boolean> started = new CompletableFuture<>();
	private final AtomicBoolean running = new AtomicBoolean(false);
	private final List<Future<Void>> listeningLaunchers = new ArrayList<>();

	private ServerSocket serverSocket;
	private ExecutorService executorService;
	private final EpsilonLanguageServer languageServer;

	/**
	 * Creates a new instance, without starting the server.
	 *
	 * @param server Server to be exposed via the network.
	 * @param host   Host to bind to, or {@code null} to use the loopback address
	 *               (i.e. {@code localhost}).
	 * @param port   Port to listen on, or {@code 0} to use an available port from
	 *               an ephemeral range.
	 */
	public EpsilonLanguageServerLauncher(EpsilonLanguageServer server, String host, int port) {
		if (server == null) {
			throw new IllegalArgumentException("Language server must not be null");
		}
		this.languageServer = server;
		this.host = host;
		this.port = port;
	}

	@Override
	public void run() {
		try {
			if (running.compareAndSet(false, true)) {
				serverSocket = new ServerSocket(port, 0, InetAddress.getByName(host));
				started.complete(true);
				LOGGER.info(() -> String.format("Started Epsilon language server on %s:%d",
					serverSocket.getInetAddress().getHostName(), serverSocket.getLocalPort()));
			} else {
				throw new IllegalStateException("Server has already been started");
			}
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, String.format(
				"Failed to start server on %s:%d", host, port), e);
			started.complete(false);
		}

		try {
			while (running.get()) {
				Socket conn = serverSocket.accept();

				if (executorService == null) {
					executorService = Executors.newCachedThreadPool();
				}

				Launcher<LanguageClient> launcher = Launcher.createLauncher(
					languageServer,
					LanguageClient.class,
					conn.getInputStream(),
					conn.getOutputStream(),
					executorService,
					null
				);
				languageServer.connect(launcher.getRemoteProxy());

				synchronized (listeningLaunchers) {
					listeningLaunchers.add(launcher.startListening());
				}
			}
		} catch (SocketException sock) {
			// Usually not interesting - the server socket was closed
			LOGGER.log(Level.FINEST, sock.getMessage(), sock);
		} catch (IOException e) {
			throw new RuntimeException("Error during execution of debug server", e);
		} finally {
			shutdown();
		}
	}

	/**
	 * Returns a Future that can be used to check if the server has started and if
	 * it has started successfully, or wait until that moment.
	 */
	public Future<Boolean> isStarted() {
		return started;
	}

	/**
	 * Shuts down the server, if it was listening. Will not
	 * do anything if the server is not listening or has
	 * already been shut down.
	 */
	public void shutdown() {
		if (running.compareAndSet(true, false)) {
			String host = null;
			int localPort = port;

			synchronized(listeningLaunchers) {
				// Cancel all listening launchers
				for (Future<Void> l : listeningLaunchers) {
					l.cancel(true);
				}
				listeningLaunchers.clear();
			}

			if (executorService != null) {
				// Shut down the thread pool used to process incoming messages
				executorService.shutdown();
				try {
					executorService.awaitTermination(10, TimeUnit.SECONDS);
				} catch (InterruptedException e) {
					LOGGER.log(Level.WARNING, e.getMessage(), e);
				}
				executorService = null;
			}

			if (serverSocket != null) {
				// Stop accepting connections
				try {
					host = serverSocket.getInetAddress().getHostName();
					localPort = serverSocket.getLocalPort();
					serverSocket.close();
				} catch (IOException e) {
					LOGGER.log(Level.SEVERE, "Error while shutting down language server", e);
				} finally {
					serverSocket = null;
				}
			}

			LOGGER.info(String.format("Shut down Epsilon language server on %s:%d", host, localPort));
		}
	}

	public String getHost() {
		if (running.get()) {
			return serverSocket.getInetAddress().getHostName();
		} else {
			return host;
		}
	}

	public int getPort() {
		if (running.get()) {
			/*
			 * Best to ask the actual socket if listening: the user may have
			 * specified 0 as the port.
			 */
			return serverSocket.getLocalPort();
		} else {
			return port;
		}
	}

}
