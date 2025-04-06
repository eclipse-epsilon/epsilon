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

import static org.junit.Assert.*;
import org.eclipse.epsilon.common.util.FileUtil;
import org.junit.Before;
import org.junit.Test;

public class ImportTests extends FlexmiTests {
	
	@Before
	public void setup()  throws Exception {
		super.setup();
		FileUtil.getFileStandalone("models/include/valid-included.flexmi", FlexmiTests.class);
		FileUtil.getFileStandalone("models/import/circular1.flexmi", FlexmiTests.class);
		FileUtil.getFileStandalone("models/import/circular2.flexmi", FlexmiTests.class);
	}
	
	@Test
	public void testNoWarnings() throws Exception {
		assertNoWarnings("include/valid-main.flexmi");
	}

	@Test
	public void testNoWarningsWithTrailing() throws Exception {
		FileUtil.getFileStandalone("models/import/valid-imported.flexmi", FlexmiTests.class);
		assertNoWarnings("import/valid-main-nsuri_trailing_space.flexmi");
	}
	
	@Test
	public void testESuperType() throws Exception {
		assertEval("EClass.all.first().eSuperTypes.first().name", "c2", "include/valid-main.flexmi");
	}
	
	@Test
	public void testImportEcore() throws Exception {
		assertEval("EAttribute.all.selectOne(a|a.name='a').eType.name", "EString", "include/import-ecore.flexmi");
	}
	
	@Test
	public void testCircularImport() throws Exception {
		assertEval("EClass.all.first().eSuperTypes.first().name", "C2", "import/circular1.flexmi");
		assertEval("EClass.all.second().eSuperTypes.first().name", "C1", "import/circular1.flexmi");
		assertEval("EClass.all.first().eSuperTypes.first().name", "C1", "import/circular2.flexmi");
		assertEval("EClass.all.second().eSuperTypes.first().name", "C2", "import/circular2.flexmi");
	}
	
	//@Test
	public void testUnresolvedReferenceWarning() throws Exception {
		// FIXME What do we really want for an invalid file? non-existent or corrupted?
		//assertEquals(loadResource("import/invalid-main.flexmi").getWarnings().size(), 1);
	}
}
