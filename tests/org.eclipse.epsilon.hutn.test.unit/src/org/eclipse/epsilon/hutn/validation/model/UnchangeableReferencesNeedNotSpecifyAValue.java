/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************
 *
 * $Id: RequiredValueNotSpecified.java,v 1.1 2008/10/09 11:54:05 louis Exp $
 */
package org.eclipse.epsilon.hutn.validation.model;

import static org.eclipse.epsilon.hutn.test.util.HutnUtil.createClassObject;
import static org.eclipse.epsilon.hutn.test.util.HutnUtil.createPackageObject;
import static org.eclipse.epsilon.hutn.test.util.HutnUtil.createSpec;
import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * The dogs reference of the Family class requires a value (its lowerbound is 1), but
 * it is not changeable, so no value should be specified in HUTN.
 * 
 * @author louis
 */
public class UnchangeableReferencesNeedNotSpecifyAValue extends HutnModelValidationTest {

	@BeforeClass
	public static void validateModel() throws Exception {
		problems = modelValidationTest(createSpec("families", createPackageObject(createClassObject("The Smiths", "Family"))));
	}
	
	@Test
	public void validationShouldReportNoProblems() {
		assertEquals(0, problems.size());
	}
}
