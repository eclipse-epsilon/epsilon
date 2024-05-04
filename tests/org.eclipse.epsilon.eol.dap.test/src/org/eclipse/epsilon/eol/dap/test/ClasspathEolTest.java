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

public class ClasspathEolTest extends AbstractEpsilonDebugAdapterTest {

	private static final String RESOURCE_NAME = "03-fromClasspath.eol";
	private static final File SCRIPT_FILE = new File(
		"../org.eclipse.epsilon.eol.dap.test/src/org/eclipse/epsilon/eol/dap/test/" + RESOURCE_NAME);

	@Override
	protected void setupModule() throws Exception {
		this.module = new EolModule();
		module.parse(ClasspathEolTest.class.getResource("03-fromClasspath.eol").toURI());
	}

	@Test
	public void canMapFilesToUriModule() throws Exception {
		SetBreakpointsResponse breakpoints = adapter.setBreakpoints(
			createBreakpoints(SCRIPT_FILE.getCanonicalPath(), createBreakpoint(1))).get();
		assertTrue("The file-based breakpoint was mapped to a script loaded from the classpath",
			breakpoints.getBreakpoints()[0].isVerified());

		attach();
		assertStoppedBecauseOf(StoppedEventArgumentsReason.BREAKPOINT);
		adapter.continue_(new ContinueArguments());
		assertProgramCompletedSuccessfully();
	}
	
}
