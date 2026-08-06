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
 * A status object used in co-ordinating concurrent jobs.
 * 
 * @author Sina Madani
 * @since 1.6
 */
public abstract class ConcurrentExecutionStatus {
	
	protected Throwable exception;

	public Throwable getException() {
		return exception;
	}
	
	protected abstract Object getResult(Object lockObj);
	
	public final Object getResult() {
		return getResult(this);
	}
	
	public final boolean register() {
		return register(this);
	}
	
	/**
	 * 
	 * @param lockObj
	 * @return <code>true</code> if registration was successful.
	 */
	protected abstract boolean register(Object lockObj);
	
	public final boolean isInProgress() {
		return isInProgress(this);
	}
	
	protected abstract boolean isInProgress(Object lockObj);
	
	public final void completeSuccessfully() {
		completeSuccessfully(this);
	}
	
	protected abstract void completeSuccessfully(Object lockObj);
	
	public final void completeWithResult(Object result) {
		completeWithResult(this, result);
	}
	
	protected abstract void completeWithResult(Object lockObj, Object result);
	
	protected final boolean completeExceptionallyBase(Throwable t) {
		boolean firstFail = this.exception == null;
		if (firstFail) {
			this.exception = t;
		}
		return firstFail;
	}
	
	public abstract void completeExceptionally(Throwable exception);
	
	public synchronized Throwable waitForExceptionalCompletion() {
		while (exception == null) {
			try {
				wait();
			}
			catch (InterruptedException ie) {
				// May be desirable
			}
		}
		return exception;
	}
	
	/**
	 * Waits until either exceptional or successful completion conditions are signalled.
	 * 
	 * @return Whether the completion was successful (<code>true</code>) or exceptional (<code>false</code>).
	 */
	protected abstract boolean waitForCompletion(Object lockObj, Supplier<Boolean> targetState);
	
	public final boolean waitForCompletion(Supplier<Boolean> targetState) {
		return waitForCompletion(this, targetState);
	}
	
	protected final boolean waitForCompletion(Object lockObj) {
		return waitForCompletion(lockObj, null);
	}
	
	public final boolean waitForCompletion() {
		return waitForCompletion(this);
	}
}
