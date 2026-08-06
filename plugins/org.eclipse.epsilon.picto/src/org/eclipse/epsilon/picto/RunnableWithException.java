/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.picto;

public abstract class RunnableWithException implements Runnable {
	
	protected Exception exception;
	
	@Override
	public void run() {
		try {
			runWithException();
		} catch (Exception e) {
			this.exception = e;
		}
	}
	
	public abstract void runWithException() throws Exception;

	public Exception getException() {
		return exception;
	}

}
