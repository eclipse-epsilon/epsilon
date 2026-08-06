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

public class ReusableEpsilonDebugServer extends EpsilonDebugServer {

	public ReusableEpsilonDebugServer(int port) {
		this(null, port);
	}

	public ReusableEpsilonDebugServer(String host, int port) {
		super(new ExecutionQueueModule(), host, port);
		getModule().setDebugAdapter(getDebugAdapter());
	}

	@Override
	public ExecutionQueueModule getModule() {
		return (ExecutionQueueModule) module;
	}

}
