/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.eol.dap;

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

import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.lsp4j.debug.launch.DSPLauncher;
import org.eclipse.lsp4j.debug.services.IDebugProtocolClient;
import org.eclipse.lsp4j.jsonrpc.Launcher;

/**
 * <p>
 * Network-based server for the debug adapter protocol, which listens via TCP at
 * a given host and port, and exposes a given {@link IEolModule}.
 * </p>
 *
 * <p>
 * The module should be fully configured the first time that someone connects to
 * this server. The server will automatically call {@link IEolModule#execute()} the
 * first time that anyone connects to it.
 * </p>
 *
 * <p>
 * An instance of this class can only be {@link #run()} once. If you need to
 * restart the server, you will need to create another instance.
 * </p>
 */
public class EpsilonDebugServer implements Runnable {

	private static final Logger LOGGER = Logger.getLogger(EpsilonDebugServer.class.getCanonicalName());

	private final String host;
	private final int port;
	private final IEolModule module;

	private final CompletableFuture<Boolean> started = new CompletableFuture<>();
	private final AtomicBoolean running = new AtomicBoolean(false);
	private final AtomicBoolean moduleStarted = new AtomicBoolean(false);
	private final List<Future<Void>> listeningLaunchers = new ArrayList<>();

	private ServerSocket serverSocket;
	private ExecutorService executorService;
	private final EpsilonDebugAdapter debugAdapter = new EpsilonDebugAdapter();

	/**
	 * Convenience version of
	 * {@link EpsilonDebugServer#EpsilonDebugServer(IEolModule, String, int)} for
	 * listening at the loopback address (i.e. {@code localhost}).
	 */
	public EpsilonDebugServer(IEolModule module, int port) {
		this(module, null, port);
	}

	/**
	 * Creates a new instance, without starting the server.
	 *
	 * @param module Module to be debugged.
	 * @param port   Port to listen on, or {@code 0} to use an available port from
	 *               an ephemeral range.
	 */
	public EpsilonDebugServer(IEolModule module, String host, int port) {
		if (module == null) {
			throw new IllegalArgumentException("Module must not be null");
		}
		this.host = host;
		this.port = port;
		this.module = module;
	}

	/**
	 * Runs the server in the current thread. In the absence of
	 * server errors, this method will block until a DAP client
	 * has attached to the module, and the module has completed
	 * its execution.
	 */
	@Override
	public void run() {
		try {
			if (running.compareAndSet(false, true)) {
				serverSocket = new ServerSocket(port, 0, InetAddress.getByName(host));
				started.complete(true);
				LOGGER.info(() -> String.format("Started Epsilon debug server on %s:%d",
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

				debugAdapter.setModule(module);
				debugAdapter.setOnAttach(this::onAttach);

				if (executorService == null) {
					executorService = Executors.newCachedThreadPool();
				}
				Launcher<IDebugProtocolClient> launcher = DSPLauncher.createServerLauncher(
					debugAdapter,
					conn.getInputStream(),
					conn.getOutputStream(),
					executorService,
					null
				);
				debugAdapter.connect(launcher.getRemoteProxy());

				synchronized(listeningLaunchers) {
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
	 * Starts the module in a background thread upon first attachment.
	 * Needed because modules don't track the fact that they are running
	 * themselves. 
	 */
	protected void onAttach() {
		if (moduleStarted.compareAndSet(false, true)) {
			Thread epsilonThread = new Thread(this::runModule);
			epsilonThread.setName("EpsilonDebuggee");
			epsilonThread.start();
		}
	}

	/**
	 * Runs the module, and shuts down the server when execution completes
	 * (whether successfully or unsuccessfully).
	 */
	protected void runModule() {
		try {
			module.execute();
		} catch (Throwable e) {
			LOGGER.log(Level.SEVERE, "Error while running script", e);
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
					LOGGER.log(Level.SEVERE, "Error while shutting down debug server", e);
				} finally {
					serverSocket = null;
				}
			}

			LOGGER.info(String.format("Shut down Epsilon debug server on %s:%d", host, localPort));
		}
	}

	public EpsilonDebugAdapter getDebugAdapter() {
		return this.debugAdapter;
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
