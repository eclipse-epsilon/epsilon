/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.common.concurrent;

import java.util.function.Supplier;

/**
 * Utility class which allows for co-ordinating concurrent execution with waiting
 * semantics for successful and exceptional completion. This class is only suitable
 * for single-condition locking, where there is only one success condition registered
 * at any given time. Multiple conditions can be supported using serial thread confinement
 * or {@link MultiConcurrentExecutionStatus}.
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class SingleConcurrentExecutionStatus extends ConcurrentExecutionStatus {
	
	protected long waitTimeout = 0;
	private volatile boolean inProgress = false;
	private Object result;
	private Object currentLock;

	@Override
	protected final Object getResult(Object lockObj) {
		return result;
	}
	
	@Override
	protected final boolean register(Object lockObj) {
		if (!inProgress) {
			exception = null;
			result = null;
			return (inProgress = true);
		}
		else return false;
	}
	
	@Override
	protected final boolean isInProgress(Object lockObj) {
		return inProgress;
	}
	
	@Override
	protected final void completeSuccessfully(Object lockObj) {
		inProgress = false;
		if (lockObj != null) synchronized (lockObj) {
			lockObj.notifyAll();
		}
		else if (currentLock != lockObj) synchronized (currentLock) {
			currentLock.notifyAll();
		}
		currentLock = null;
	}
	
	@Override
	protected final void completeWithResult(Object lockObj, Object result) {
		this.result = result;
		completeSuccessfully(lockObj);
	}
	
	@Override
	public final void completeExceptionally(Throwable exception) {
		completeExceptionallyBase(exception);
		completeSuccessfully(currentLock);
	}
	
	/**
	 * Waits until either exceptional or successful completion conditions are signalled.
	 * 
	 * @return Whether the completion was successful (<code>true</code>) or exceptional (<code>false</code>).
	 */
	@Override
	protected final boolean waitForCompletion(final Object lockObj, final Supplier<Boolean> targetState) {
		assert lockObj != null;
		currentLock = lockObj;
		synchronized (lockObj) {
			while (inProgress && (targetState == null || !targetState.get())) {
				try {
					// Don't wait forever just in case someone forgets to notify or interrupt
					lockObj.wait(waitTimeout);
				}
				catch (InterruptedException ie) {}
			}
		}
		currentLock = null;
		return exception == null;
	}
	
	@Override
	public Throwable waitForExceptionalCompletion() {
		waitForCompletion(() -> exception != null);
		return exception;
	}
}