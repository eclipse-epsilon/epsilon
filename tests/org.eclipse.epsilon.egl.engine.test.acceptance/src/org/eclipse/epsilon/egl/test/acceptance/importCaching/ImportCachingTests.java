/*******************************************************************************
 * Copyright (c) 2024 Antonio García-Domínguez.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Antonio García-Domínguez - initial API and implementation
******************************************************************************/
package org.eclipse.epsilon.egl.test.acceptance.importCaching;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.io.File;
import java.net.URI;
import java.util.List;

import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.egl.EglTemplate;
import org.eclipse.epsilon.egl.EglTemplateFactory;
import org.eclipse.epsilon.egl.EgxModule;
import org.eclipse.epsilon.egl.exceptions.EglRuntimeException;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.dom.Import;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests that ensure that import caching works as intended.
 */
public class ImportCachingTests {

	private static File mainEGX;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Class<?> relClass = ImportCachingTests.class;

		// Load dependencies
		mainEGX = FileUtil.getFileStandalone("main.egx", relClass);
		FileUtil.getFileStandalone("first.egl", relClass);
		FileUtil.getFileStandalone("second.egl", relClass);
		FileUtil.getFileStandalone("utils.eol", relClass);
	}

	@Test
	public void reusedEOL() throws Exception {
		EgxModule module = new EgxModule();
		module.parse(mainEGX);

		IEolModule firstImported;
		{
			EglTemplate firstTemplate = loadTemplate(module, "first.egl");
			List<Import> firstImports = firstTemplate.getModule().getImports();
			assertEquals(1, firstImports.size());
			firstImported = (IEolModule) firstImports.get(0).getImportedModule();
		}

		IEolModule secondImported;
		{
			EglTemplate secondTemplate = loadTemplate(module, "second.egl");
			List<Import> secondImports = secondTemplate.getModule().getImports();
			assertEquals(1, secondImports.size());
			secondImported = (IEolModule) secondImports.get(0).getImportedModule();
		}

		assertSame("Both EGL templates should reuse the same EOL module instance",
			firstImported, secondImported);
	}

	private EglTemplate loadTemplate(EgxModule module, String templateName) throws EglRuntimeException {
		EglTemplateFactory templateFactory = module.getContext().getTemplateFactory();
		URI templateUri = templateFactory.resolveTemplate(templateName);
		EglTemplate eglTemplate = templateFactory.load(templateUri);
		return eglTemplate;
	}

}
