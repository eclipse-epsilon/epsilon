/*******************************************************************************
 * Copyright (c) 2025 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.eol.dap;

import java.time.Duration;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.ExecutorFactory;
import org.eclipse.epsilon.eol.execute.control.ExecutionController;
import org.eclipse.epsilon.eol.execute.control.IExecutionListener;

/**
 * <p>
 * Dummy module intended to allow the reuse of a single
 * {@link EpsilonDebugServer} across multiple Epsilon programs. Users should
 * call the {@link #enqueue(IEolModule)} method to add more programs to be
 * debugged, which will be executed in FIFO order.
 * </p>
 *
 * <p>
 * The module will run indefinitely until it is terminated, running every
 * enqueued module and waiting when all modules have been executed so far, until
 * more modules are enqueued or execution is terminated.
 * </p>
 *
 * <p>
 * The module can automatically attach the modules to be run to a given adapter,
 * if set via {@link #setDebugAdapter(EpsilonDebugAdapter)}.
 * This is recommended, as otherwise there is a risk that certain events will be
 * missed by the debug adapter (e.g. the pre-execute checks for the root of its AST).
 * </p>
 *
 * <p>
 * The queue is polled according to a given interval, configured via
 * {@link #setPollInterval(Duration)} (which should be measured at least
 * in milliseconds).
 * </p>
 */
public class ExecutionQueueModule extends EolModule {

	public static final Duration DEFAULT_DURATION = Duration.ofMillis(200);

	private final BlockingQueue<Runnable> taskQueue = new LinkedBlockingDeque<>();
	private Duration pollInterval = DEFAULT_DURATION;
	private EpsilonDebugAdapter debugAdapter;

	/**
	 * Adds a module to the end of the queue of modules to be executed.
	 *
	 * @return A {@code Future} which will eventually contain the execution result
	 *         of the enqueued module.
	 */
	public Future<Object> enqueue(IEolModule module) {
		CompletableFuture<Object> futureResult = new CompletableFuture<>();
		taskQueue.add(() -> {
			try {
				// Copy the execution listeners to the submodule
				ExecutorFactory moduleExecFactory = module.getContext().getExecutorFactory();
				ExecutorFactory queueExecFactory = getContext().getExecutorFactory();
				for (IExecutionListener l : queueExecFactory.getExecutionListeners()) {
					moduleExecFactory.addExecutionListener(l);
				}

				// Attach the module to the debug adapter, then execute it
				if (debugAdapter != null) {
					debugAdapter.attachTo(module);
				}
				Object result = module.execute();
				futureResult.complete(result);
			} catch (EolRuntimeException e) {
				futureResult.completeExceptionally(e);
			}
		});
		return futureResult;
	}

	@Override
	public Object executeImpl() throws EolRuntimeException {
		ExecutionController executionController = getContext().getExecutorFactory().getExecutionController();

		while (!executionController.isTerminated()) {
			try {
				Runnable r = taskQueue.poll(
					pollInterval.toMillis(),
					TimeUnit.MILLISECONDS
				);
				if (r != null) {
					r.run();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public Duration getPollInterval() {
		return pollInterval;
	}

	public void setPollInterval(Duration pollInterval) {
		this.pollInterval = pollInterval;
	}

	public EpsilonDebugAdapter getDebugAdapter() {
		return debugAdapter;
	}

	public void setDebugAdapter(EpsilonDebugAdapter debugAdapter) {
		this.debugAdapter = debugAdapter;
	}

}
