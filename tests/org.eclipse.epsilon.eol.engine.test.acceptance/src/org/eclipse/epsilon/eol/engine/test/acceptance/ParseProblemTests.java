/*********************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 **********************************************************************/
package org.eclipse.epsilon.eol.engine.test.acceptance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Files;

import org.eclipse.epsilon.eol.EolModule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class ParseProblemTests {

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	private static final String BAD_EOL = "'incomplete string";

	@Test
	public void missingImport() throws Exception {
		EolModule module = new EolModule();
		module.parse("import \"missing.eol\";");
		assertEquals(1, module.getParseProblems().size());

		String sReason = module.getParseProblems().get(0).getReason();
		assertTrue("Expected parse problem is raised", sReason.contains("not found"));
		assertTrue("Expected problem file is mentioned", sReason.contains("missing.eol"));
	}

	@Test
	public void parseProblemsLocal() throws Exception {
		EolModule module = new EolModule();
		module.parse(BAD_EOL);
		assertEquals(1, module.getParseProblems().size());
	}

	@Test
	public void parseProblemsImported() throws Exception {
		File fParseProblems = tempFolder.newFile("parseProblems.eol");
		Files.write(fParseProblems.toPath(), BAD_EOL.getBytes());

		EolModule module = new EolModule();
		module.parse(String.format("import \"%s\";", fParseProblems.toURI()));
		assertEquals(1, module.getParseProblems().size());

		String sReason = module.getParseProblems().get(0).getReason();
		assertTrue("Expected parse problem is raised", sReason.contains("contains errors"));
		assertTrue("Expected problem file is mentioned", sReason.contains("parseProblems.eol"));
	}

}
