/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.eol.dap.test;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.lsp4j.debug.ContinueArguments;
import org.eclipse.lsp4j.debug.SetBreakpointsResponse;
import org.eclipse.lsp4j.debug.StoppedEventArgumentsReason;
import org.junit.Test;

/**
 * <p>
 * Tests that we can set breakpoints on scripts loaded from the classpath, and
 * that the breakpoints operate as intended. Unfortunately, this test works
 * differently when run from Eclipse and when run from Tycho:
 * </p>
 *
 * <ul>
 * <li>When run from Eclipse, the module is parsed from a file:/ URL so the
 * stack trace when stopped can refer to a filesystem path.</li>
 * <li>When run from Tycho, the module is not parsed from a file:/ URL, and the
 * stack trace can only mention the filename at the end of the URL but not
 * really the path (as that makes LSP4E try to open it as a filesystem path,
 * which produces errors).</li>
 * </ul>
 */
public class ClasspathEolTest extends AbstractEpsilonDebugAdapterTest {

	private static final String RESOURCE_NAME = "03-fromClasspath.eol";
	private static final File SCRIPT_FILE = new File(
		"../org.eclipse.epsilon.eol.dap.test/src/org/eclipse/epsilon/eol/dap/test/" + RESOURCE_NAME);

	@Override
	protected void setupModule() throws Exception {
		this.module = new EolModule();
		module.parse(ClasspathEolTest.class.getResource(RESOURCE_NAME).toURI());
	}

	@Override
	protected void setupAdapter() throws Exception {
		adapter.getUriToPathMappings().put(
			ClasspathEolTest.class.getResource(RESOURCE_NAME).toURI(),
			SCRIPT_FILE.toPath());
	}

	@Test
	public void canMapFilesToUriModule() throws Exception {
		SetBreakpointsResponse breakpoints = adapter.setBreakpoints(
			createBreakpoints(SCRIPT_FILE.getCanonicalPath(), createBreakpoint(1))
		).get();
		assertTrue("The file-based breakpoint was mapped to a script loaded from the classpath",
			breakpoints.getBreakpoints()[0].isVerified());

		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);
		
		adapter.continue_(new ContinueArguments());
		assertProgramCompletedSuccessfully();
	}
	
}
