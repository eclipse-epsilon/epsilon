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

import java.util.concurrent.ThreadFactory;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class EolThreadFactory implements ThreadFactory {

	protected int threadCount;
	protected final int maxThreads;
	protected final String namePrefix;
	
	protected EolThreadFactory(int threadLimit, String threadNamePrefix) {
		this.namePrefix = threadNamePrefix != null ? threadNamePrefix : "EOL-Worker";
		this.maxThreads = threadLimit;
	}
	
	@Override
	public EolExecutorThread newThread(Runnable target) {
		if (++threadCount > maxThreads) {
			throw new IllegalStateException("Exceeded maximum number of threads: "+maxThreads);
		}
		return new EolExecutorThread(target, namePrefix + threadCount);
	}
}
