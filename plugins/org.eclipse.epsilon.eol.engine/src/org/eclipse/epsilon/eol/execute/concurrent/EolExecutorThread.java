/*********************************************************************
 * Copyright (c) 2019 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.execute.concurrent;

/**
 * Used to identify threads used in executing EOL. Can have an
 * associated "finally" block in the form of a Runnable which
 * is executed before the thread terminates.
 *
 * @author Sina Madani
 * @since 1.6
 */
public class EolExecutorThread extends Thread {

	protected Runnable cleanup;
	
	public EolExecutorThread(Runnable target, String name) {
		super(target, name);
		setDaemon(true);
	}

	public void setTerminationHandler(Runnable fin) {
		this.cleanup = fin;
	}
	
	@Override
	public final void run() {
		try {
			super.run();
		}
		finally {
			if (cleanup != null) {
				cleanup.run();
				cleanup = null;
			}
		}
	}
}
