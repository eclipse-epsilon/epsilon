/*******************************************************************************
 * Copyright (c) 2025 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Sam Harris - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.test.acceptance.parse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Collection;

import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.egl.test.acceptance.AcceptanceTestUtil;
import org.eclipse.epsilon.egl.util.FileUtil;
import org.junit.Test;

public class ParseProblemTests {

	@Test
	public void parseProblemReportsCorrectPosition() throws Exception {
		final String program = "[*Generate foo*]" + FileUtil.NEWLINE
							+ "<h1>[%='foo'[%='foo'%]</h1>";
		
		AcceptanceTestUtil.parse(program);
		Collection<ParseProblem> problems = AcceptanceTestUtil.getParseProblems();
		
		assertFalse(problems.isEmpty());
		
		ParseProblem problem = problems.iterator().next();
		
		assertEquals(2, problem.getLine());
		assertEquals(13, problem.getColumn());
	}
	
	@Test
	public void parseProblemReportsCorrectPositionEOF() throws Exception {
		final String program = "[*Generate foo*]" + FileUtil.NEWLINE
							+ "<h1>[%='foo'%</h1>";
		
		AcceptanceTestUtil.parse(program);
		Collection<ParseProblem> problems = AcceptanceTestUtil.getParseProblems();
		
		assertFalse(problems.isEmpty());
		
		ParseProblem problem = problems.iterator().next();
		
		assertEquals(2, problem.getLine());
		assertEquals(19, problem.getColumn());
	}
}
