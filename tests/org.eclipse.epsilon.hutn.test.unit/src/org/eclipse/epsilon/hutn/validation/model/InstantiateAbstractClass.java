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
 * $Id: InstantiateAbstractClass.java,v 1.1 2008/08/12 08:59:04 louis Exp $
 */

package org.eclipse.epsilon.hutn.validation.model;

import static org.eclipse.epsilon.hutn.test.util.HutnUtil.createClassObject;
import static org.eclipse.epsilon.hutn.test.util.HutnUtil.createPackageObject;
import static org.eclipse.epsilon.hutn.test.util.HutnUtil.createSpec;
import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class InstantiateAbstractClass extends HutnModelValidationTest {

	@BeforeClass
	public static void validateModel() throws Exception {
		problems = modelValidationTest(createSpec("families", createPackageObject(createClassObject("John Doe", "NamedElement"))));
	}
	
	@Test
	public void validationShouldReportTwoProblems() {
		assertEquals(1, problems.size());
	}
	
	@Test
	public void firstReasonShouldBeIllegalInstantiation() {
		assertEquals("Cannot instantiate the abstract class: NamedElement", problems.get(0).getReason());
	}
}
