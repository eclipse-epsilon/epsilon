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
 * $Id: DefaultValue.java,v 1.3 2008/08/13 16:21:37 louis Exp $
 */
package org.eclipse.epsilon.hutn.test.acceptance.valid;

import org.eclipse.epsilon.hutn.test.acceptance.util.HutnAcceptanceTest;
import org.junit.BeforeClass;
import org.junit.Test;

public class DefaultValue extends HutnAcceptanceTest {
	
	@BeforeClass
	public static void executeHutn() throws Exception {
		final String hutn = "@Spec {"                                     +
	                        "	MetaModel \"FamiliesMetaModel\" {"        +
	                        "		nsUri = \"families\""                 +
	                        "		configFile = \"" + CONFIG_FILE + "\"" +
	                        "	}"                                        +
	                        "}"                                           +
                            "Families {"                                  +
                            "	Family \"The Smiths\" {"                  +
                            "	}"                                        +
                            "}";
		
		model = generateModel(hutn);
	}
	
	@Test
	public void modelShouldHaveOneFamily() {
		model.assertEquals(1, "Family.allInstances().size()");
	}
	
	@Test
	public void familyShouldHaveTwoChildren() {
		model.assertEquals(2, "Family.allInstances().first().numberOfChildren");
	}
}
