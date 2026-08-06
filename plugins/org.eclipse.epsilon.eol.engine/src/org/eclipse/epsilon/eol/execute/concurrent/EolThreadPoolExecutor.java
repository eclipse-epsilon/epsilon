/*********************************************************************
* Copyright (c) 2018 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.execute.concurrent;

import java.util.concurrent.*;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.concurrent.EolThreadPoolExecutor;

/**
 * Fixed number of threads with unbounded queueing. The threads
 * are always recycled and never destroyed.
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class EolThreadPoolExecutor extends ThreadPoolExecutor {

	public EolThreadPoolExecutor(int numThreads) {
		super(
			numThreads, numThreads,
			Long.MAX_VALUE, TimeUnit.NANOSECONDS,
			new LinkedBlockingQueue<>(),
			new EolThreadFactory(numThreads, "EOL-Worker"),
			(r, e) -> {}
		);
	}
	
	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		super.afterExecute(r, t);
		if (t instanceof Exception) {
			EolRuntimeException ex = EolRuntimeException.findCause(t);
			if (ex != null) ex.getMessage();
		}
	}
}
