/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.eol.dap.test.eol;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.concurrent.Future;

import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.dap.test.AbstractExecutionQueueTest;
import org.eclipse.lsp4j.debug.ContinueArguments;
import org.eclipse.lsp4j.debug.StackFrame;
import org.eclipse.lsp4j.debug.StackTraceResponse;
import org.eclipse.lsp4j.debug.StoppedEventArgumentsReason;
import org.junit.Test;

public class EolExecutionQueueTest extends AbstractExecutionQueueTest {

	private static final File SCRIPT_A_FILE = new File(BASE_RESOURCE_FOLDER, "05-smallCollection.eol");
	private static final File SCRIPT_B_FILE = new File(BASE_RESOURCE_FOLDER, "06-largeCollection.eol");

	@Test
	public void canRunSingleModule() throws Exception {
		// Set up sequence of module executions
		Future<Object> eolAResult = enqueueScript(SCRIPT_A_FILE);

		// Set breakpoints for the modules
		adapter.setBreakpoints(createBreakpoints(
			SCRIPT_A_FILE.getAbsolutePath(),
			createBreakpoint(3))
		).get();

		// Attach to the adapter and wait for the first (and only) module to execute
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);
		adapter.continue_(new ContinueArguments()).get();
		eolAResult.get();

		shutdown();
	}

	@Test
	public void canRunTwoModules() throws Exception {
		// Set up sequence of module executions
		enqueueScript(SCRIPT_A_FILE);
		Future<Object> eolBResult = enqueueScript(SCRIPT_B_FILE);

		// Set breakpoints for the modules
		adapter.setBreakpoints(createBreakpoints(
			SCRIPT_B_FILE.getAbsolutePath(),
			createBreakpoint(4))
		).get();

		// Attach to the adapter and wait for the first (and only) module to execute
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);
		final StackTraceResponse stackTrace = getStackTrace();
		StackFrame stackFrame = stackTrace.getStackFrames()[0];
		assertEquals(SCRIPT_B_FILE.getCanonicalPath(), stackFrame.getSource().getPath());
		assertEquals("The stack frame should be on line 4", 4, stackFrame.getLine());

		// Continue execution and wait for the second script to finish running
		adapter.continue_(new ContinueArguments()).get();
		eolBResult.get();

		shutdown();
	}

	protected Future<Object> enqueueScript(File eolFile) throws Exception {
		EolModule eolA = new EolModule();
		eolA.parse(eolFile);
		return getModule().enqueue(eolA);
	}

}
