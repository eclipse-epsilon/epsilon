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

import org.eclipse.core.runtime.IProgressMonitor;

public interface ContentPromise {
	
	String getContent() throws Exception;

	/**
	 * Return the monitor used by the promise to detect if the user has requested the cancellation of its computation.
	 */
	default IProgressMonitor getProgressMonitor() {
		return null;
	}

	/**
	 * Change the monitor used by the promise to detect if the user has requested the cancellation of its computation.
	 */
	default void setProgressMonitor(IProgressMonitor monitor) {
		// ignore request
	}
	
}
