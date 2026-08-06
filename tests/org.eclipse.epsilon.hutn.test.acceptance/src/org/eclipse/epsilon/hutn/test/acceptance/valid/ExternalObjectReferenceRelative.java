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

import java.io.File;

import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.hutn.test.acceptance.util.HutnAcceptanceTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ExternalObjectReferenceRelative extends HutnAcceptanceTest {
	
	private static File hutnSource;
	
	@BeforeClass
	public static void executeHutn() throws Exception {
		// FIXME We want a temp file
		//hutnSource = FileUtil.getFile("temp.hutn", ExternalObjectReferenceRelative.class);
		hutnSource = FileUtil.createTempFile("temp.hutn");
		final String hutn = "@Spec {"                                                                  +
	                        "	MetaModel \"FamiliesMetaModel\" {"                                     +
	                        "		nsUri = \"families\""                                              +
	                        "	}"                                                                     +
	                        "}"                                                                        +
                            "Families {"                                                               +
                            "	Person \"John\" {"                                                     +
                            "		sharedAccounts: Account \"Nearby.model#//@accounts.0\","           +
                            "                       Account \"Nearby.model#_swAAYJX5Ed2TbbKclPHPaA\""  +
                            "	}"                                                                     +
                            "}";
		
		FileUtil.setFileContents(hutn, hutnSource);
		
		// Load nearby model
		FileUtil.getFileStandalone("Nearby.model", ExternalObjectReferenceRelative.class);
		model = generateModel(hutnSource);
		model.setVariable("john", "Person.all().first()");
		model.setVariable("accNumber", "john.sharedAccounts.first().eClass.getEStructuralFeature('number')");
	}
	
	@AfterClass
	public static void removeHutnSource() {
		if (hutnSource.exists())
			hutnSource.delete();
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
