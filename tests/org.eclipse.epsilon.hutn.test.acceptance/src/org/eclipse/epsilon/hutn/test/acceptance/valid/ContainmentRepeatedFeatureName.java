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
 * $Id: Containment.java,v 1.2 2008/08/07 12:44:23 louis Exp $
 */
package org.eclipse.epsilon.hutn.test.acceptance.valid;

import org.eclipse.epsilon.hutn.test.acceptance.util.HutnAcceptanceTest;
import org.junit.BeforeClass;
import org.junit.Test;

public class ContainmentRepeatedFeatureName extends HutnAcceptanceTest {
	
	@BeforeClass
	public static void executeHutn() throws Exception {
		final String hutn = "@Spec {"                                  +
	                        "	MetaModel \"FamiliesMetaModel\" {"     +
	                        "		nsUri = \"families\""              +
	                        "	}"                                     +
	                        "}"                                        +
                            "Families {"                               +
                            "	Family \"The Smiths\" {"               +
                            "		members: Person \"John\" {"        +
                            "			name: \"John\""                +
                            "		}"                                 +
                            "		members: Person \"Gill\" {"        +
                            "			name: \"Gill\""                +
                            "		}"                                 +
                            "	}"                                     +
                            "}";
		
		model = generateModel(hutn);
		
		model.setVariable("theSmiths", "Family.allInstances().first()");
	}
	
	@Test
	public void modelShouldHaveOneFamily() {
		model.assertEquals(1, "Family.allInstances().size()");
	}
	
	@Test
	public void familyShouldContainTwoMembers() {
		model.assertEquals(2, "theSmiths.members.size()");
	}
	
	@Test
	public void familyShouldContainMemberCalledJohn() {
		model.assertTrue("theSmiths.members.exists(p:Person | p.name = 'John')");
	}
	
	@Test
	public void familyShouldContainMemberCalledGill() {
		model.assertTrue("theSmiths.members.exists(p:Person | p.name = 'Gill')");
	}
}
