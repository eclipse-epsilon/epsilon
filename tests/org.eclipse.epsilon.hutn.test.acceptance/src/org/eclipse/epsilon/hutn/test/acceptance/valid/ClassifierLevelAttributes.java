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
 * $Id: ClassifierLevelAttributes.java,v 1.2 2008/08/13 15:42:13 louis Exp $
 */
package org.eclipse.epsilon.hutn.test.acceptance.valid;

import org.eclipse.epsilon.hutn.test.acceptance.util.HutnAcceptanceTest;
import org.junit.BeforeClass;
import org.junit.Test;

public class ClassifierLevelAttributes extends HutnAcceptanceTest {
	
	@BeforeClass
	public static void executeHutn() throws Exception {
		final String hutn = "@Spec {"                              +
	                        "	MetaModel \"FamiliesMetaModel\" {" +
	                        "		nsUri = \"families\""          +
	                        "	}"                                 +
	                        "}"                                    +
                            "Families {"                           +
                            "	Family.nuclear = true;"            +
                            "	Family.numberOfChildren = 2;"      +
                            ""                                     +
                            "	~nuclear Family \"The Smiths\" {"  +
                            "		name: \"The Smiths\""          +
                            "	}"                                 +
                            ""                                     +
                            "	Family \"The Does\" {"             +
                            "		name: \"The Does\""            +
                            "		numberOfChildren: 5"           +
                            "	}"                                 +
                            "}";
		
		model = generateModel(hutn);
		
		model.setVariable("theSmiths", "Family.allInstances().selectOne(f|f.name = 'The Smiths')");
		model.setVariable("theDoes",   "Family.allInstances().selectOne(f|f.name = 'The Does')");
	}
	
	@Test
	public void modelShouldHaveTwoFamilies() {
		model.assertEquals(2, "Family.allInstances().size()");
	}
	
	@Test
	public void theSmithsShouldNotBeNuclear() {
		model.assertFalse("theSmiths.nuclear");
	}
	
	@Test
	public void theSmithsShouldHaveTwoChildren() {
		model.assertEquals(2, "theSmiths.numberOfChildren");
	}
	
	@Test
	public void theDoesShouldBeNuclear() {
		model.assertTrue("theDoes.nuclear");
	}
	
	@Test
	public void theDoesShouldHaveFiveChildren() {
		model.assertEquals(5, "theDoes.numberOfChildren");
	}
}
