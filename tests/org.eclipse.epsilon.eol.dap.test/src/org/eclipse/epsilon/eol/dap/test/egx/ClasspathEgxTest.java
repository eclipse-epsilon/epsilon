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

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.epsilon.egl.EgxModule;
import org.eclipse.epsilon.eol.dap.test.AbstractEpsilonDebugAdapterTest;
import org.eclipse.epsilon.eol.dap.test.metamodel.Person;
import org.eclipse.epsilon.eol.models.java.JavaModel;
import org.eclipse.lsp4j.debug.Breakpoint;
import org.eclipse.lsp4j.debug.BreakpointNotVerifiedReason;
import org.eclipse.lsp4j.debug.ContinueArguments;
import org.eclipse.lsp4j.debug.ScopesResponse;
import org.eclipse.lsp4j.debug.SetBreakpointsResponse;
import org.eclipse.lsp4j.debug.StackTraceResponse;
import org.eclipse.lsp4j.debug.StoppedEventArgumentsReason;
import org.eclipse.lsp4j.debug.ThreadsResponse;
import org.eclipse.lsp4j.debug.Variable;
import org.eclipse.lsp4j.debug.VariablesResponse;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class ClasspathEgxTest extends AbstractEpsilonDebugAdapterTest {

	private static File resourceFile(String resourceName) {
		return new File(
			"../org.eclipse.epsilon.eol.dap.test/src/"
			+ ClasspathEgxTest.class.getPackageName().replaceAll("[.]", "/")
			+ "/" + resourceName);
	}

	private static final String EGX_RESOURCE_NAME = "11-orchestrationFromClasspath.egx";
	private static final File EGX_FILE = resourceFile(EGX_RESOURCE_NAME);

	private static final String EGL_RESOURCE_NAME = "11-person.egl";
	private static final File EGL_FILE = resourceFile(EGL_RESOURCE_NAME);

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	@Override
	protected void setupModule() throws Exception {
		this.module = new EgxModule();
		module.parse(ClasspathEgxTest.class.getResource(EGX_RESOURCE_NAME).toURI());
		module.getContext().getFrameStack().put("tempFolder", tempFolder.newFolder());

		final List<Object> instances = Collections.singletonList(new Person("John", "Smith"));
		final List<Class<?>> types = Collections.singletonList(Person.class);
		final JavaModel model = new JavaModel(instances, types);
		module.getContext().getModelRepository().addModel(model);
	}

	@Override
	protected void setupAdapter() throws Exception {
		adapter.getUriToPathMappings().put(
			ClasspathEgxTest.class.getResource(EGX_RESOURCE_NAME).toURI(),
			EGX_FILE.toPath());
		adapter.getUriToPathMappings().put(
			ClasspathEgxTest.class.getResource(EGL_RESOURCE_NAME).toURI(),
			EGL_FILE.toPath());
	}

	@Test
	public void canStopOnTemplate() throws Exception {
		SetBreakpointsResponse stopResult = adapter.setBreakpoints(createBreakpoints(EGL_FILE.getCanonicalPath(), createBreakpoint(1))).get();
		final Breakpoint breakpoint = stopResult.getBreakpoints()[0];
		assertFalse("The breakpoint on the template was not verified", breakpoint.isVerified());
		assertEquals("The breakpoint on the template was left pending", BreakpointNotVerifiedReason.PENDING, breakpoint.getReason());
		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);

		ThreadsResponse threads = adapter.threads().get();
		assertEquals("There should be two threads: the EGX one, and the EGL one", 2, threads.getThreads().length);

		StackTraceResponse stackTrace = getStackTrace(threads.getThreads()[1].getId());
		ScopesResponse localScopes = getScopes(stackTrace.getStackFrames()[0]);
		VariablesResponse localVariables = getVariables(localScopes.getScopes()[0]);
		Map<String, Variable> localVariablesByName = getVariablesByName(localVariables);
		Variable pVariable = localVariablesByName.get("p");
		assertNotNull("EGL should have a 'p' variable", pVariable);

		adapter.setBreakpoints(createBreakpoints(EGL_FILE.getCanonicalPath())).get();
		adapter.continue_(new ContinueArguments()).get();
		assertProgramCompletedSuccessfully();
	}

}
