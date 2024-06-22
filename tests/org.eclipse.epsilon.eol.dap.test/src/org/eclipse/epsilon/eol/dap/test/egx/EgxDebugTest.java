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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.epsilon.egl.EgxModule;
import org.eclipse.epsilon.eol.dap.test.AbstractEpsilonDebugAdapterTest;
import org.eclipse.epsilon.eol.models.java.JavaModel;
import org.eclipse.lsp4j.debug.BreakpointNotVerifiedReason;
import org.eclipse.lsp4j.debug.ContinueArguments;
import org.eclipse.lsp4j.debug.ScopesResponse;
import org.eclipse.lsp4j.debug.SetBreakpointsResponse;
import org.eclipse.lsp4j.debug.StackFrame;
import org.eclipse.lsp4j.debug.StackTraceResponse;
import org.eclipse.lsp4j.debug.StoppedEventArgumentsReason;
import org.eclipse.lsp4j.debug.ThreadsResponse;
import org.eclipse.lsp4j.debug.Variable;
import org.eclipse.lsp4j.debug.VariablesResponse;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class EgxDebugTest extends AbstractEpsilonDebugAdapterTest {

	private static final File SCRIPT_FILE = new File(BASE_RESOURCE_FOLDER, "10-orchestration.egx");
	private static final File EGL_FILE = new File(BASE_RESOURCE_FOLDER, "10-person.egl");
	private static final File EOL_FILE = new File(BASE_RESOURCE_FOLDER, "10-utils.eol");

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	public static class Person {
		private String name, lastName;

		public Person() {
			// nothing to do
		}

		public Person(String firstName, String lastName) {
			this.name = firstName;
			this.lastName = lastName;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
	}

	@Override
	protected void setupModule() throws Exception {
		this.module = new EgxModule();
		module.parse(SCRIPT_FILE);
		module.getContext().getFrameStack().put("tempFolder", tempFolder.newFolder());

		final List<Object> instances = Collections.singletonList(new Person("John", "Smith"));
		final List<Class<?>> types = Collections.singletonList(Person.class);
		final JavaModel model = new JavaModel(instances, types);
		module.getContext().getModelRepository().addModel(model);
	}

	@Test
	public void canStopOnOperation() throws Exception {
		SetBreakpointsResponse stopResult = adapter.setBreakpoints(
			createBreakpoints(createBreakpoint(1))).get();
		assertTrue("The breakpoint was verified", stopResult.getBreakpoints()[0].isVerified());
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		Map<String, Variable> topVariables = getVariablesFromTopStackFrame();
		Variable firstNameVariable = topVariables.get("firstName");
		assertNotNull("The top scope should have a 'firstName' variable", firstNameVariable);
		assertEquals("John", firstNameVariable.getValue());

		adapter.setBreakpoints(createBreakpoints()).get();
		adapter.continue_(new ContinueArguments());
		assertProgramCompletedSuccessfully();
	}

	@Test
	public void canStopAtBeginningOfTarget() throws Exception {
		SetBreakpointsResponse stopResult = adapter.setBreakpoints(
				createBreakpoints(createBreakpoint(7))).get();
		assertTrue("The breakpoint was verified", stopResult.getBreakpoints()[0].isVerified());
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		Map<String, Variable> topVariables = getVariablesFromTopStackFrame();
		assertNotNull("The top scope should have a 'p' variable", topVariables.get("p"));
		assertNull("The top scope should not have a 't' variable yet", topVariables.get("t"));

		adapter.continue_(new ContinueArguments());
		assertProgramCompletedSuccessfully();
	}

	@Test
	public void canStopWithinTarget() throws Exception {
		SetBreakpointsResponse stopResult = adapter.setBreakpoints(
			createBreakpoints(createBreakpoint(9))).get();
		assertTrue("The breakpoint on the operation was verified",
			stopResult.getBreakpoints()[0].isVerified());
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		Map<String, Variable> topVariables = getVariablesFromTopStackFrame();
		Variable tVariable = topVariables.get("t");
		assertNotNull("The top scope should have a 't' variable", tVariable);

		adapter.continue_(new ContinueArguments());
		assertProgramCompletedSuccessfully();
	}

	@Test
	public void canStopAtTargetExpression() throws Exception {
		final int line = 15;
		SetBreakpointsResponse stopResult = adapter.setBreakpoints(
			createBreakpoints(createBreakpoint(line))).get();
		assertTrue("The breakpoint on the operation was verified",
			stopResult.getBreakpoints()[0].isVerified());
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		final StackTraceResponse stackTrace = getStackTrace();
		final StackFrame topFrame = stackTrace.getStackFrames()[0];
		assertEquals(SCRIPT_FILE.getCanonicalPath(), topFrame.getSource().getPath());
		assertEquals(line, topFrame.getLine());

		adapter.continue_(new ContinueArguments());
		assertProgramCompletedSuccessfully();
	}

	@Test
	public void canStopWithinExecutedTemplate() throws Exception {
		SetBreakpointsResponse stopResult = adapter.setBreakpoints(
			createBreakpoints(EGL_FILE.getCanonicalPath(), createBreakpoint(2))).get();
		assertFalse("The breakpoint on the template was not verified",
			stopResult.getBreakpoints()[0].isVerified());
		assertEquals("The breakpoint on the template was left pending",
			BreakpointNotVerifiedReason.PENDING,
			stopResult.getBreakpoints()[0].getReason());
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		ThreadsResponse threads = adapter.threads().get();
		assertEquals("There should be one thread for EGX and one for EGL", 2, threads.getThreads().length);

		// Second thread is the EGL template
		StackTraceResponse stackTrace = getStackTrace(threads.getThreads()[1].getId());
		assertEquals(EGL_FILE.getCanonicalPath(), stackTrace.getStackFrames()[0].getSource().getPath());
		assertEquals("There should be two stack frames: local, template-specific-global, and global",
			3, stackTrace.getStackFrames().length);

		ScopesResponse localScopes = getScopes(stackTrace.getStackFrames()[0]);
		VariablesResponse localVariables = getVariables(localScopes.getScopes()[0]);
		Map<String, Variable> localVariablesByName = getVariablesByName(localVariables);
		assertNotNull("The local scope should have a 'p' variable", localVariablesByName.get("p"));
		assertNotNull("The local scope should have an 'out' variable", localVariablesByName.get("out"));

		adapter.setBreakpoints(createBreakpoints(EGL_FILE.getCanonicalPath())).get();
		adapter.continue_(new ContinueArguments());
		assertProgramCompletedSuccessfully();
	}

	@Test
	public void canStopWithinImportedEOL() throws Exception {
		SetBreakpointsResponse stopResult = adapter.setBreakpoints(
			createBreakpoints(EOL_FILE.getCanonicalPath(), createBreakpoint(2))).get();
		assertFalse("The breakpoint on the template was not verified",
			stopResult.getBreakpoints()[0].isVerified());
		assertEquals("The breakpoint on the template was left pending",
			BreakpointNotVerifiedReason.PENDING,
			stopResult.getBreakpoints()[0].getReason());
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		ThreadsResponse threads = adapter.threads().get();
		assertEquals("There should be one thread for EGX and one for EGL", 2, threads.getThreads().length);

		// Second thread is the EGL template (which includes invocations of EOL)
		StackTraceResponse stackTrace = getStackTrace(threads.getThreads()[1].getId());
		assertEquals(EOL_FILE.getCanonicalPath(), stackTrace.getStackFrames()[0].getSource().getPath());

		ScopesResponse localScopes = getScopes(stackTrace.getStackFrames()[0]);
		VariablesResponse localVariables = getVariables(localScopes.getScopes()[0]);
		Map<String, Variable> localVariablesByName = getVariablesByName(localVariables);
		assertNotNull("The top scope should have a 'self' variable", localVariablesByName.get("self"));

		// Should stop a second time as we have 2 rules invoking the EOL operation
		adapter.continue_(new ContinueArguments());
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		// After that, it should complete its execution
		adapter.continue_(new ContinueArguments());
		assertProgramCompletedSuccessfully();
	}
}
