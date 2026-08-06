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
 * $Id: Comments.java,v 1.2 2008/08/07 12:44:23 louis Exp $
 */
package org.eclipse.epsilon.hutn.test.acceptance.valid;

import org.eclipse.epsilon.hutn.test.acceptance.util.HutnAcceptanceTest;
import org.junit.BeforeClass;
import org.junit.Test;

public class Comments extends HutnAcceptanceTest {
	
	@BeforeClass
	public static void executeHutn() throws Exception {
		final String hutn = "@Spec {"                              + "\n" +
	                        "	MetaModel \"FamiliesMetaModel\" {" + "\n" +
	                        "		nsUri = \"families\""          + "\n" +
	                        "	}"                                 + "\n" +
	                        "}"                                    + "\n" +
	                        "/* Multi-line comments"               + "\n" +
	                        " * take this form"                    + "\n" +
	                        " */"                                  + "\n" +
                            "Families {"                           + "\n" +
                            "	Family \"The Smiths\" {"           + "\n" +
                            "		name: \"The Smiths\""          + "\n" +
                            "	} // Single line comment"          + "\n" +
                            "}";
		
		model = generateModel(hutn);
	}
	
	@Test
	public void modelShouldHaveOneFamily() {
		model.assertEquals(1, "Family.allInstances().size()");
	}
	
	@Test
	public void familyShouldBeCalledTheSmiths() {
		model.assertEquals("The Smiths", "Family.allInstances().first().name");
	}
}
