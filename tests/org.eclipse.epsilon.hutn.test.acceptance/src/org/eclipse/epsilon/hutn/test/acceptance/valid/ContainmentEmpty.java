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
 * $Id: ContainmentEmpty.java,v 1.1 2008/08/14 13:04:24 louis Exp $
 */
package org.eclipse.epsilon.hutn.test.acceptance.valid;

import org.eclipse.epsilon.hutn.test.acceptance.util.HutnAcceptanceTest;
import org.junit.BeforeClass;
import org.junit.Test;

public class ContainmentEmpty extends HutnAcceptanceTest {
	
	@BeforeClass
	public static void executeHutn() throws Exception {
		final String hutn = "@Spec {"                                  +
	                        "	MetaModel \"FamiliesMetaModel\" {"     +
	                        "		nsUri = \"families\""              +
	                        "	}"                                     +
	                        "}"                                        +
                            "Families {"                               +
                            "	Family \"The Smiths\" {"               +
                            "		members: Person \"John\" {}"       +
                            "	}"                                     +
                            "}";
		
		model = generateModel(hutn);
//		model = generateModel(hutn, org.epsilon.test.TestUtil.getPath("Debug.model", SingleFamilyWithPets.class).replaceFirst("bin", "src"));
		
		model.setVariable("theSmiths", "Family.allInstances().first()");
		model.setVariable("john",      "Person.allInstances().first()");
	}
	
	@Test
	public void modelShouldHaveOneFamily() {
		model.assertEquals(1, "Family.allInstances().size()");
	}
	
	@Test
	public void familyShouldContainOneMember() {
		model.assertEquals(1, "theSmiths.members.size()");
	}
	
	@Test
	public void familyShouldContainJohn() {
		model.assertTrue("theSmiths.members.includes(john)");
	}
}
