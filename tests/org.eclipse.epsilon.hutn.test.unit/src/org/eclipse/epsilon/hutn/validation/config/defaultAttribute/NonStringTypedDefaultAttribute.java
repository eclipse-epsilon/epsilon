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
 * $Id: NonStringTypedDefaultAttribute.java,v 1.2 2008/08/07 12:44:22 louis Exp $
 */
package org.eclipse.epsilon.hutn.validation.config.defaultAttribute;

import static org.junit.Assert.*;

import java.util.List;

import org.eclipse.epsilon.hutn.validation.config.HutnConfigFileValidationTest;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.junit.BeforeClass;
import org.junit.Test;

public class NonStringTypedDefaultAttribute extends HutnConfigFileValidationTest {

	private static List<ParseProblem> problems;
	
	@BeforeClass
	public static void buildModel() throws Exception {
		problems = configFileValidationTest(createConfiguration(createIdentifierRule("Family", "nuclear")));
	}
	
	@Test
	public void validatorShouldProduceOneError() {
		assertEquals(1, problems.size());
	}
	
	@Test
	public void errorMessageShouldIndicateNonExistentAttribute() {
		assertEquals("The attribute \"nuclear\" for the classifier \"Family\" does not accept String values.", problems.get(0).getReason());
	}
}
