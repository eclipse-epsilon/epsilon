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
 * $Id: ClassifierLevelAttributePrecedence.java,v 1.2 2008/08/13 16:21:37 louis Exp $
 */
package org.eclipse.epsilon.hutn.test.acceptance.valid;

import org.eclipse.epsilon.hutn.test.acceptance.util.HutnAcceptanceTest;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests that the DefaultValueRules, specified in a HUTN configuration file,
 * are correctly overridden by any classifier level attributes in the HUTN
 * source.
 * 
 * In this test case, the configuration file specifies default value rules 
 * for the averageAge and numberOfChildren attributes on the Family class;
 * while the HUTN source also specifies a classifier level value for the
 * Family.averageAge attribute.
 * 
 * @author louis
 */
public class ClassifierLevelAttributePrecedence extends HutnAcceptanceTest {
	
	@BeforeClass
	public static void executeHutn() throws Exception {
		final String hutn = "@Spec {"                                     +
	                        "	MetaModel \"FamiliesMetaModel\" {"        +
	                        "		nsUri = \"families\""                 +
	                        "		configFile = \"" + CONFIG_FILE + "\"" +
	                        "	}"                                        +
	                        "}"                                           +
                            "Families {"                                  +
                            "	Family.averageAge = 28.3;"                +
                            ""                                            +
                            "	Family \"The Smiths\" {}"         		  +
                            "}";
		
		model = generateModel(hutn);
		
		model.setVariable("theSmiths", "Family.allInstances().first()");
	}
	
	@Test
	public void modelShouldHaveOneFamily() {
		model.assertEquals(1, "Family.allInstances().size()");
	}
	
	@Test
	public void theSmithsShouldHaveClassifierLevelAttributeValue() {
		model.assertEquals(28.3, "theSmiths.averageAge");
	}
	
	@Test
	public void theSmithsShouldHaveDefaultValue() {
		model.assertEquals(2, "theSmiths.numberOfChildren");
	}
}
