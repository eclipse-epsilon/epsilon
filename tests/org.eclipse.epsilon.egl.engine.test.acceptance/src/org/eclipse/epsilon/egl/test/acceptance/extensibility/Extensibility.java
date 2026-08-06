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
package org.eclipse.epsilon.egl.test.acceptance.extensibility;

import static org.junit.Assert.assertEquals;
import java.io.File;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.egl.test.acceptance.AcceptanceTestUtil;
import org.eclipse.epsilon.egl.test.models.Model;
import org.junit.BeforeClass;
import org.junit.Test;

public class Extensibility {

	private static File Driver;
	
	@BeforeClass
	public static void setUpOnce() throws Exception {
		// Load imported files
		FileUtil.getFileStandalone("GenerateClasses.egl", Extensibility.class);
		FileUtil.getFileStandalone("GenerateFields.egl", Extensibility.class);
		Driver = FileUtil.getFileStandalone("Driver.egl", Extensibility.class);
	}
	
	@Test
	public void testValid() throws Exception {
		AcceptanceTestUtil.run(initialiseFactory(), Driver, Model.OOInstance);
		
		final File generatedFile = FileUtil.getFileStandalone("extensibility/Pet.java", Extensibility.class);
		assertEquals(2, CountingTemplate.countFor(generatedFile));
	}

	private static CountingTemplateFactory initialiseFactory() throws Exception {
		final CountingTemplateFactory factory = new CountingTemplateFactory();
		//factory.setOutputRoot(FileUtil.getDirectoryOf(Extensibility.class).getAbsolutePath());
		factory.setOutputRoot(FileUtil.createTempDir("extensibility").getAbsolutePath());
		return factory;
	}
}
