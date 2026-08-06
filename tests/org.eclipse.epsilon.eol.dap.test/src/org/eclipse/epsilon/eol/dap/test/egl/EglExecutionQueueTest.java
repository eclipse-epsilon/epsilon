/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.eol.dap.test.egl;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.concurrent.Future;

import org.eclipse.epsilon.egl.EglModule;
import org.eclipse.epsilon.eol.dap.test.AbstractExecutionQueueTest;
import org.eclipse.lsp4j.debug.ThreadsResponse;
import org.junit.Test;

public class EglExecutionQueueTest extends AbstractExecutionQueueTest {

	private static final File SCRIPT_FILE = new File(BASE_RESOURCE_FOLDER, "08-hello.egl");

	@Test
	public void threadIsRemoved() throws Exception {
		EglModule eglModule = new EglModule();
		eglModule.parse(SCRIPT_FILE);
		Future<Object> eglResult = getModule().enqueue(eglModule);

		// Attach to the debug adapter so we start executing programs
		attach();

		// Wait for the EGL script to run
		eglResult.get();

		// The thread from the EGL script should have been cleaned up
		ThreadsResponse threadsResponse = adapter.threads().get();
		assertEquals(0, threadsResponse.getThreads().length);

		shutdown();
	}

}
