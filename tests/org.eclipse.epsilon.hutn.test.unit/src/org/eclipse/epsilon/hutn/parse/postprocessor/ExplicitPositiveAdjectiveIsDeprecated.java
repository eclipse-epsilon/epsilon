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
 * $Id: ExplicitPositiveAdjectiveIsDeprecated.java,v 1.1 2008/08/12 12:59:13 louis Exp $
 */
package org.eclipse.epsilon.hutn.parse.postprocessor;

import static org.junit.Assert.*;

import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.hutn.parse.HutnParser;
import org.junit.BeforeClass;
import org.junit.Test;

public class ExplicitPositiveAdjectiveIsDeprecated extends HutnPostProcessorTest {
	
	@BeforeClass
	public static void postprocess() {
		 postProcessorTest(buildAst(HutnParser.ADJECTIVE, "#nuclear"));
	}
	
	@Test
	public void postprocessorShouldGenerateOneProblem() {
		assertEquals(1, problems.size());
	}
	
	@Test
	public void problemShouldBeWarning() {
		assertEquals(ParseProblem.WARNING, problems.get(0).getSeverity());
	}
	
	@Test
	public void reasonShouldBeDeprecation() {
		assertEquals("Prefixing postive adjectives with the # symbol has been deprecated. Instead, use the adjective name without a prefix.", problems.get(0).getReason());
	}
}
