/*******************************************************************************
 * Copyright (c) 2012 Antonio García-Domínguez.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Antonio García-Domínguez - initial API and implementation
******************************************************************************/
package org.eclipse.epsilon.eol.engine.test.acceptance.circularImports;

import static org.junit.Assert.assertEquals;
import java.io.File;
import java.util.ArrayList;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.eol.EolModule;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests that ensure that EOL import cycles are properly detected
 * and reported.
 */
public class CircularImportTests {

	static File immediate, two_a, three_a;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Class<?> relClass = CircularImportTests.class;
		// Load dependencies
		FileUtil.getFileStandalone("two-b.eol", relClass);
		FileUtil.getFileStandalone("three-c.eol", relClass);
		FileUtil.getFileStandalone("three-b.eol", relClass);
		immediate = FileUtil.getFileStandalone("immediate.eol", relClass);
		two_a = FileUtil.getFileStandalone("two-a.eol", relClass);
		three_a = FileUtil.getFileStandalone("three-a.eol", relClass);
	}
	
	@Test
	public void immediate() throws Exception {
		parse(immediate);
	}

	@Test
	public void oneLevel() throws Exception {
		parse(two_a);
	}

	@Test
	public void twoLevels() throws Exception {
		parse(three_a);
	}

	private static void parse(final File script) throws Exception {
		EolModule module = new EolModule();
		module.parse(script);
		assertEquals(new ArrayList<ParseProblem>(), module.getParseProblems());
		module.execute();
	}
}
