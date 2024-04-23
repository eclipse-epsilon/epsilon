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
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.lsp4j.debug.launch.DSPLauncher;
import org.eclipse.lsp4j.debug.services.IDebugProtocolClient;
import org.eclipse.lsp4j.jsonrpc.Launcher;

/**
 * <p>
 * Network-based server for the debug adapter protocol, which listens at a given
 * TCP port and exposes a given {@link IEolModule}.
 * </p>
 *
 * <p>
 * The module should be fully configured the first time that someone connects to
 * this server. The server will automatically call {@link IEolModule#execute()} the
 * first time that anyone connects to it.
 * </p>
 */
public class EpsilonDebugServer implements Runnable {

	private static final Logger LOGGER = Logger.getLogger(EpsilonDebugServer.class.getCanonicalName());

	private final int port;
	private final IEolModule module;

	private AtomicBoolean listening = new AtomicBoolean(false);
	private AtomicBoolean moduleStarted = new AtomicBoolean(false);
	private ServerSocket serverSocket;
	private List<Future<Void>> listeningLaunchers = new ArrayList<>();
	private ExecutorService executorService;

	public EpsilonDebugServer(IEolModule module, int port) {
		if (module == null) {
			throw new IllegalArgumentException("Module must not be null");
		}
		this.port = port;
		this.module = module;
	}

	/**
	 * Runs the server in the current thread. In the absence of
	 * server errors, this method will block until the module
	 * has completed its execution. 
	 */
	@Override
	public void run() {
		try {
			if (listening.compareAndSet(false, true)) {
				serverSocket = new ServerSocket(port);
			} else {
				throw new IllegalStateException("Server is already listening");
			}
			LOGGER.info(() -> "Started Epsilon debug server on port " + port);

			while (listening.get()) {
				Socket conn = serverSocket.accept();

				EpsilonDebugAdapter debugAdapter = new EpsilonDebugAdapter();
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
	 * Returns true iff the server is listening for connections.
	 */
	public boolean isListening() {
		return listening.get();
	}

	/**
	 * Returns true iff the Epsilon module has started its execution.
	 */
	public boolean isModuleStarted() {
		return moduleStarted.get();
	}

	/**
	 * Shuts down the server, if it was listening. Will not
	 * do anything if the server is not listening or has
	 * already been shut down.
	 */
	public void shutdown() {
		if (listening.compareAndSet(true, false)) {
			if (serverSocket != null) {
				// Stop accepting connections
				try {
					serverSocket.close();
				} catch (IOException e) {
					LOGGER.log(Level.SEVERE, "Error while shutting down debug server", e);
				} finally {
					serverSocket = null;
				}
			}

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
				executorService = null;
			}			

			LOGGER.info(() -> "Shut down Epsilon debug server on port " + port);
		}
	}

}
