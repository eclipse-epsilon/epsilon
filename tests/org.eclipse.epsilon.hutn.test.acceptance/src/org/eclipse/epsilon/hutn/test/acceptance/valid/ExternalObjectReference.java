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
 * $Id:$
 */
package org.eclipse.epsilon.hutn.test.acceptance.valid;

import org.eclipse.epsilon.hutn.test.acceptance.util.HutnAcceptanceTest;
import org.junit.BeforeClass;
import org.junit.Test;

public class ExternalObjectReference extends HutnAcceptanceTest {
	
	@BeforeClass
	public static void executeHutn() throws Exception {
		final String hutn = "@Spec {"                                                                                     +
	                        "	MetaModel \"FamiliesMetaModel\" {"                                                        +
	                        "		nsUri = \"families\""                                                                 +
	                        "	}"                                                                                        +
	                        "}"                                                                                           +
                            "Families {"                                                                                  +
                            "	Person \"John\" {"                                                                        +
                            "		sharedAccounts: Account \"" + BANK_ACCOUNTS_MODEL_URI + "#//@accounts.0\","           +
                            "                       Account \"" + BANK_ACCOUNTS_MODEL_URI + "#_swAAYJX5Ed2TbbKclPHPaA\""  +
                            "	}"                                                                                        +
                            "}";
		
		model = generateModel(hutn);
		
		model.setVariable("john", "Person.all().first()");
		
		model.setVariable("accNumber", "john.sharedAccounts.first().eClass.getEStructuralFeature('number')");
	}
	
	@Test
	public void johnShouldHaveTwoAccounts() {
		model.assertEquals(2, "john.sharedAccounts.size()");
	}
	
	
	// FIXME The contents of "john.sharedAccounts" shouldn't be dynamic objects
	
	@Test
	public void firstAccountShouldHaveCorrectNumber() {
		model.assertEquals("111111", "john.sharedAccounts.first().eGet(accNumber)");
	}
	
	@Test
	public void secondAccountShouldHaveCorrectNumber() {
		model.assertEquals("222222", "john.sharedAccounts.last().eGet(accNumber)");
	}
}
