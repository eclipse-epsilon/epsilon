/*******************************************************************************
 * Copyright (c) 2024 Antonio García-Domínguez.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Antonio García-Domínguez - initial API and implementation
******************************************************************************/
package org.eclipse.epsilon.eol.engine.test.acceptance.importCaching;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.io.File;

import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.IEolModule;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests that ensure that import caching works as intended.
 */
public class ImportCachingTests {

	private static File mainEOL;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Class<?> relClass = ImportCachingTests.class;

		// Load dependencies
		mainEOL = FileUtil.getFileStandalone("main.eol", relClass);
		FileUtil.getFileStandalone("b.eol", relClass);
		FileUtil.getFileStandalone("c.eol", relClass);
	}

	@Test
	public void reusedEOL() throws Exception {
		EolModule module = new EolModule();
		module.parse(mainEOL);

		assertEquals("main.eol should import 2 modules", 2, module.getImports().size());
		IEolModule bModule = (IEolModule) module.getImports().get(0).getImportedModule();
		IEolModule cModule = (IEolModule) module.getImports().get(1).getImportedModule();
		assertEquals("First import in main.eol should be b.eol", "b.eol", bModule.getFile().getName());
		assertEquals("Second import in main.eol should be c.eol", "c.eol", cModule.getFile().getName());

		assertEquals("b.eol should import 1 module", 1, bModule.getImports().size());
		assertSame("main.eol and b.eol should reuse the same instance of c.eol",
			cModule, bModule.getImports().get(0).getImportedModule());
	}

}
