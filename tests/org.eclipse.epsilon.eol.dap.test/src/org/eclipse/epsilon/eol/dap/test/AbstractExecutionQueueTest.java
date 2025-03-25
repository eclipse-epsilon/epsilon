/*******************************************************************************
 * Copyright (c) 2025 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.eol.dap.test;

import org.eclipse.epsilon.eol.dap.ExecutionQueueModule;
import org.eclipse.lsp4j.debug.TerminateArguments;

public abstract class AbstractExecutionQueueTest extends AbstractEpsilonDebugAdapterTest {

	@Override
	protected void setupModule() throws Exception {
		module = new ExecutionQueueModule();
	}

	@Override
	protected void setupAdapter() throws Exception {
		getModule().setDebugAdapter(adapter);
	}

	protected ExecutionQueueModule getModule() {
		return (ExecutionQueueModule) module;
	}

	protected void shutdown() throws Exception {
		// Shut down the adapter (which terminates the module)
		adapter.terminate(new TerminateArguments());
	
		// Ensures the program has finished running, and that the script thread has died
		assertProgramCompletedSuccessfully();
		epsilonThread.join();
	}

}