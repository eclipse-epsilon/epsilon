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
 * $Id: DuplicateIdentifiers.java,v 1.1 2008/08/07 14:51:11 louis Exp $
 */

package org.eclipse.epsilon.hutn.validation.model;

import static org.eclipse.epsilon.hutn.test.util.HutnUtil.createClassObject;
import static org.eclipse.epsilon.hutn.test.util.HutnUtil.createPackageObject;
import static org.eclipse.epsilon.hutn.test.util.HutnUtil.createSpec;
import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class DuplicateIdentifiers extends HutnModelValidationTest {

	@BeforeClass
	public static void validateModel() throws Exception {
		problems = modelValidationTest(createSpec("families", createPackageObject(createClassObject("Fido", "Pet"),
		                                                                          createClassObject("Fido", "Person"))));
	}
	
	@Test
	public void validationShouldReportTwoProblems() {
		assertEquals(2, problems.size());
	}
	
	@Test
	public void firstReasonShouldBeDuplicateIdentifier() {
		assertEquals("Duplicate identifier: Fido", problems.get(0).getReason());
	}
	
	@Test
	public void secondReasonShouldBeDuplicateIdentifier() {
		assertEquals("Duplicate identifier: Fido", problems.get(1).getReason());
	}
}
