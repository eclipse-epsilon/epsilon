/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.flexmi.test;

import org.eclipse.epsilon.common.util.FileUtil;
import org.junit.Before;
import org.junit.Test;

public class IncludeTests extends FlexmiTests {
	
	@Before
	public void setup()  throws Exception {
		super.setup();
		FileUtil.getFileStandalone("models/include/valid-included.flexmi", FlexmiTests.class);
		FileUtil.getFileStandalone("models/include/datatypes.flexmi", FlexmiTests.class);
	}
	
	@Test
	public void testNoWarnings() throws Exception {
		assertNoWarnings("include/valid-main.flexmi");
	}
	
	@Test
	public void testFlexmiRootNode() throws Exception {
		assertEval("EPackage.all.first().eClassifiers.size()", 2, "include/include-datatypes.flexmi");
	}
	
	
	@Test
	public void testESuperType() throws Exception {
		assertEval("EClass.all.first().eSuperTypes.first().name", "c2", "include/valid-main.flexmi");
	}	
}
