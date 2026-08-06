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
 * $Id: InvalidConfigFile.java,v 1.2 2008/08/07 12:44:24 louis Exp $
 */
package org.eclipse.epsilon.hutn.test.acceptance.invalid;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.eclipse.epsilon.hutn.test.acceptance.util.InvalidHutnAcceptanceTest;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.junit.BeforeClass;
import org.junit.Test;

public class InvalidConfigFile extends InvalidHutnAcceptanceTest {

	private static final String CONFIG_FILE = "../org.eclipse.epsilon.hutn.test.dependencies.model/models/org/eclipse/epsilon/hutn/test/models/InvalidFamiliesConfig.model";
	
	private static List<ParseProblem> problems;
	private static ParseProblem problem;
	
	@BeforeClass
	public static void executeHutn() throws Exception {
		final String hutn = "@Spec {"                                     + "\n" +
	                        "	MetaModel \"FamiliesMetaModel\" {"        + "\n" + 
	                        "		nsUri = \"families\""                 + "\n" +
	                        "		configFile = \"" + CONFIG_FILE + "\"" + "\n" +
	                        "	}"                                        + "\n" +
	                        "}"                                           + "\n" +
                            "Families {"                                  + "\n" +
                            "	Family \"The Smiths\" {"                  + "\n" +
                            "		name: \"The Smiths\""                 + "\n" +
                            "	}"                                        + "\n" +
                            "}";
		
		problems = parse(hutn);
		
		if (!problems.isEmpty())
			problem = problems.get(0);
	}
	
	@Test
	public void hutnShouldHaveOneParseProblem() {
		assertEquals(1, problems.size());
	}
	
	@Test
	public void parseProblemShouldHaveBeOnCorrectLine() {
		assertEquals(4, problem.getLine());
	}
	
	@Test
	public void hutnShouldHaveCorrectMessage() {
		assertEquals("The attribute \"address\" for the classifier \"Family\" accepts multiple values.", problem.getReason());
	}
}
