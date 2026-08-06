/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************
 *
 * $Id$
 */
package org.eclipse.epsilon.hutn.unparser.spec;

import static org.eclipse.epsilon.hutn.test.util.HutnUtil.createSpec;
import static org.junit.Assert.assertEquals;

import org.eclipse.epsilon.hutn.unparser.internal.AbstractSpecUnparserTest;
import org.junit.BeforeClass;
import org.junit.Test;

public class SpecWithOneNsUri extends AbstractSpecUnparserTest {

	@BeforeClass
	public static void setup() {
		specUnparserTest(createSpec("families"));
	}
	
	@Test
	public void hasOnePackageObject() {
		assertEquals(1, numberOfPackageObjects());
	}
	
	@Test
	public void hasSpecAsPackageObject() {
		assertEquals("@Spec {"                  +
				     "metamodel \"families\" {" +
				     "nsUri: \"families\"\n"    +
				     "}"                        +
				     "}",
				     packageObject(0));
	}
}
