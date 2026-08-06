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
 * $Id: Simple.java,v 1.3 2008/10/09 11:54:05 louis Exp $
 */
package org.eclipse.epsilon.hutn.test.acceptance.valid.uml;

import org.eclipse.uml2.uml.UMLPackage;
import org.junit.BeforeClass;
import org.junit.Test;

public class Simple extends HutnUmlAcceptanceTest {
	
	@BeforeClass
	public static void executeHutn() throws Exception {
		final String hutn = "@Spec {"                                                      + 
		                    "  MetaModel \"uml\" {"                                        +
		                    "    nsUri = \"" + UMLPackage.eNS_URI + "\"" +
		                    "  }"                                                          +
		                    "}"                                                            +
		                    ""                                                             +
		                    "Packages {"                                                   +
		                    "  Package \"Pk\" {"                                           +
		                    "    name: \"package\""                                        +
		                    "    ownedType: Class \"C\""                                   +
		                    "  }"                                                          +
		                    "  isLeaf isAbstract isActive Class \"C\" { name: \"class\" }" +
		                    "}";

		
		model = generateModel(hutn);
		
		model.setVariable("pkg", "Package.allInstances().first()");
		model.setVariable("cls", "Class.allInstances().first()");
	}
	
	@Test
	public void modelShouldHaveOnePackage() {
		model.assertEquals(1, "Package.allInstances().size()");
	}
	
	@Test
	public void packageShouldBeCalledPackage() {
		model.assertEquals("package", "pkg.name");
	}
	
	@Test
	public void classShouldBeCalledClass() {
		model.assertEquals("class", "cls.name");
	}
	
	@Test
	public void packageShouldHaveOneOwnedType() {
		model.assertEquals(1, "pkg.ownedType.size()");
	}
	
	@Test
	public void packageShouldContainClass() {
		model.assertTrue("pkg.ownedType.includes(cls)");
	}
}
