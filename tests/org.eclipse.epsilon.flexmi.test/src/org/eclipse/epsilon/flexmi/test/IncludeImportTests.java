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

public class IncludeImportTests extends FlexmiTests {
	
	@Before
	public void setup()  throws Exception {
		super.setup();
		FileUtil.getFileStandalone("models/include-import/valid-included.flexmi", FlexmiTests.class);	
	}
	
	@Test
	public void testUniqueC1() throws Exception {
		assertEval("EClass.all.selectOne(c|c.name='C3').eSuperTypes.first() == EClass.all.selectOne(c|c.name='C1')", 
				true, "include-import/valid-main.flexmi");
	}
	
}
