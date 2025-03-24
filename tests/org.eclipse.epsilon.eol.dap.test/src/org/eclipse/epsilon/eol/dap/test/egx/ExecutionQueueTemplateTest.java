/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.eol.dap.test.egx;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;

import org.eclipse.epsilon.egl.EgxModule;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.dap.ExecutionQueueModule;
import org.eclipse.epsilon.eol.dap.test.AbstractEpsilonDebugAdapterTest;
import org.eclipse.epsilon.eol.dap.test.metamodel.Person;
import org.eclipse.epsilon.eol.models.java.JavaModel;
import org.eclipse.lsp4j.debug.ContinueArguments;
import org.eclipse.lsp4j.debug.StoppedEventArgumentsReason;
import org.eclipse.lsp4j.debug.TerminateArguments;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class ExecutionQueueTemplateTest extends AbstractEpsilonDebugAdapterTest {

	private static final File SCRIPT_FILE = new File(BASE_RESOURCE_FOLDER, "10-orchestration.egx");
	private static final File EGL_FILE = new File(BASE_RESOURCE_FOLDER, "10-person.egl");

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	@Override
	protected void setupModule() throws Exception {
		this.module = new ExecutionQueueModule();
	}

	private ExecutionQueueModule getModule() {
		return (ExecutionQueueModule) this.module;
	}

	@Test
	public void canStopWithinTemplate() throws Exception {
		EgxModule egxModule = new EgxModule();
		egxModule.parse(SCRIPT_FILE);
		egxModule.getContext().getFrameStack().put("tempFolder", tempFolder.newFolder());

		final List<Object> instances = Collections.singletonList(new Person("John", "Smith"));
		final List<Class<?>> types = Collections.singletonList(Person.class);
		final JavaModel model = new JavaModel(instances, types);
		egxModule.getContext().getModelRepository().addModel(model);

		// Set a breakpoint within the template
		Future<Object> egxResult = getModule().enqueue(egxModule);
		adapter.setBreakpoints(createBreakpoints(
			EGL_FILE.getAbsolutePath(),
			createBreakpoint(2)
		)).get();

		// Attach and check we stopped
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		// Clear breakpoints and continue until the program ends
		adapter.setBreakpoints(createBreakpoints(
			EGL_FILE.getAbsolutePath()
		)).get();
		adapter.continue_(new ContinueArguments()).get();
		egxResult.get();
		shutdown();
	}

	protected void shutdown() throws Exception {
		// Shut down the adapter (which terminates the module)
		adapter.terminate(new TerminateArguments());

		// We enqueue a module to get the queue module to re-check the terminate flag
		getModule().enqueue(new EolModule()).get();

		// Ensures the program has finished running, and that the script thread has died
		assertProgramCompletedSuccessfully();
		epsilonThread.join();
	}

	protected Future<Object> enqueueScript(File eolFile) throws Exception {
		EolModule eolA = new EolModule();
		eolA.parse(eolFile);
		return getModule().enqueue(eolA);
	}

}
