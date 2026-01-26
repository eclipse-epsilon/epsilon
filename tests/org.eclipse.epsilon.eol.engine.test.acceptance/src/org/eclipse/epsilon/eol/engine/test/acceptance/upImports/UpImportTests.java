/*********************************************************************
 * Copyright (c) 2026 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 **********************************************************************/
package org.eclipse.epsilon.eol.engine.test.acceptance.upImports;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.eol.EolModule;
import org.junit.BeforeClass;
import org.junit.Test;

public class UpImportTests {

	private static File resourceDir;

	@BeforeClass
	public static void setUpClass() throws Exception {
		resourceDir = FileUtil.getDirectoryStandalone("../upImports", UpImportTests.class);
	}

	@Test
	public void directRoot() throws Exception {
		EolModule module = parse("level1b/directRoot.eol");
		assertEquals("i am root", module.execute());
	}

	@Test
	public void level1OnlyName() throws Exception {
		EolModule module = parse("level1b/useLevel1.eol");
		assertEquals("level 1", module.execute());
	}

	@Test
	public void level2OnlyName() throws Exception {
		EolModule module = parse("level1b/useLevel2.eol");
		assertEquals("level 2", module.execute());
	}

	@Test
	public void ambiguous() throws Exception {
		EolModule module = new EolModule();
		module.parse(new File(resourceDir, "level1b/useAmbiguous.eol"));
		assertEquals("There should be one parse problem",
			1, module.getParseProblems().size());

		String problem = module.getParseProblems().get(0).getReason();
		assertTrue("Error should mention 'ambiguous'",
			problem.contains("ambiguous"));
		assertTrue("Error should mention the import URI",
			problem.contains("up://upImports/ambiguous.eol"));

		Pattern candidatePattern = Pattern.compile("^[-] .+[\\\\/]ambiguous[.]eol$", Pattern.MULTILINE);
		int count = 0;
		for (Matcher m = candidatePattern.matcher(problem); m.find(); ) {
			++count;
		}
		assertEquals("Error should mention both candidates", 2, count);
	}

	@Test
	public void ambiguousLevel1() throws Exception {
		EolModule module = parse("level1b/useAmbiguousLevel1.eol");
		assertEquals("ambiguous from level 1", module.execute());
	}

	@Test
	public void ambiguousLevel2() throws Exception {
		EolModule module = parse("level1b/useAmbiguousLevel2.eol");
		assertEquals("ambiguous from level 2", module.execute());
	}

	@Test
	public void importMissing() throws Exception {
		EolModule module = new EolModule();
		module.parse(new File(resourceDir, "level1b/importMissing.eol"));
		assertEquals("There should be one parse problem",
			1, module.getParseProblems().size());

		assertEquals(1, module.getParseProblems().size());
		String problem = module.getParseProblems().get(0).getReason();
		assertTrue(problem.contains("not found"));
	}

	protected EolModule parse(String path) throws Exception {
		EolModule module = new EolModule();
		module.parse(new File(resourceDir, path));
		assertEquals("There should not be any parse problems",
			Collections.emptyList(), module.getParseProblems());
		return module;
	}

}
