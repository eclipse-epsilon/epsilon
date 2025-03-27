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

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.dap.test.AbstractEpsilonDebugAdapterTest;
import org.eclipse.lsp4j.debug.ContinueArguments;
import org.eclipse.lsp4j.debug.SetBreakpointsResponse;
import org.eclipse.lsp4j.debug.StoppedEventArgumentsReason;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * <p>
 * Tests that we can set breakpoints on scripts loaded from the classpath, and
 * that the breakpoints operate as intended. This is a version that uses a prefix
 * instead of a full path.
 * </p>
 */
@RunWith(Parameterized.class)
public class ClasspathPrefixEolTest extends AbstractEpsilonDebugAdapterTest {

	private static final String SUBPKG_FOLDER = "subpkg";
	private static final String SCRIPT_BASENAME = "30-fromClasspathNested.eol";
	private static final Path RESOURCE_PATH = Paths.get(SUBPKG_FOLDER, SCRIPT_BASENAME);

	private static final Path SCRIPT_PREFIX =
		Paths.get("..", "org.eclipse.epsilon.eol.dap.test", "src")
			.resolve(Paths.get("", ClasspathPrefixEolTest.class.getPackageName().split("[.]")));

	private static final File SCRIPT_FILE = SCRIPT_PREFIX.resolve(RESOURCE_PATH).toFile();

	private boolean useTrailingSlash;

	@Parameters(name = "useTrailingSlash={0}")
	public static Object[] data() {
		return new Object[]{ false, true };
	}

	public ClasspathPrefixEolTest(boolean useTrailingSlash) {
		this.useTrailingSlash = useTrailingSlash;
	}

	@Override
	protected void setupModule() throws Exception {
		this.module = new EolModule();
		module.parse(ClasspathPrefixEolTest
			.class.getResource(String.format(
				"%s/%s", SUBPKG_FOLDER, SCRIPT_BASENAME
			)).toURI());
	}

	@Override
	protected void setupAdapter() throws Exception {
		final String baseFolderFile = "03-fromClasspath.eol";
		String sBaseFileURL = ClasspathPrefixEolTest.class.getResource(baseFolderFile).toString();
		String sFolderURL = sBaseFileURL.substring(0, sBaseFileURL.length() - baseFolderFile.length());
		if (!useTrailingSlash) {
			sFolderURL = sFolderURL.substring(0, sFolderURL.length() - 1);
		}
		URL folderURL = new URL(sFolderURL);

		adapter.getUriToPathMappings().put(folderURL.toURI(), SCRIPT_PREFIX);
	}

	@Test
	public void canMapFilesToUriModule() throws Exception {
		assertTrue("The script file exists at " + SCRIPT_FILE, SCRIPT_FILE.isFile());

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
