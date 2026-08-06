/**
 * *******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 * ******************************************************************************
 *
 * $Id: Simple.java,v 1.2 2008/08/07 12:44:23 louis Exp $
 */
package org.eclipse.epsilon.hutn.test.acceptance.invalid;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.hutn.test.acceptance.util.InvalidHutnAcceptanceTest;
import org.junit.BeforeClass;
import org.junit.Test;

public class MixedValues extends InvalidHutnAcceptanceTest {
	
	private static List<ParseProblem> problems;
	private static ParseProblem problem;
	
	@BeforeClass
	public static void executeHutn() throws Exception {
		final String hutn = "@Spec {"                                             + "\n" +
	                        "	MetaModel \"FamiliesMetaModel\" {"                + "\n" +
	                        "		nsUri = \"families\""                         + "\n" +
	                        "	}"                                                + "\n" +
	                        "}"                                                   + "\n" +
                            "Families {"                                          + "\n" +
                            "	Family \"The Smiths\" {"                          + "\n" +
                            "		members: Person \"John\" {}, Person \"Jane\"" + "\n" +
                            "	}"                                                + "\n" +
                            "   Person \"Jane\" {}"                               + "\n" +
                            "}"; 
		
		problems = parse(hutn);
		
		if (!problems.isEmpty())
			problem = problems.get(0);
	}
	
	@Test
	public void parseProblemShouldHaveBeOnCorrectLine() {
		assertEquals(9, problem.getLine());
	}
	
	@Test
	public void hutnShouldHaveCorrectMessage() {
		assertEquals("no viable alternative at input '}'", problem.getReason());
	}
}
