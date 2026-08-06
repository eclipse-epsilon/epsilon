/*********************************************************************
 * Copyright (c) 2019 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.egx.engine.test.acceptance.operations;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.egl.*;
import org.junit.*;

/**
 * 
 * @since 1.6
 */
public class IncludeTests {
	
	static File fooEGX;
	static Path outputRoot;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		outputRoot = Paths.get(FileUtil.getFileStandalone("", PrintTests.class).toURI());
		// Load dependencies
		FileUtil.getFileStandalone("foo.egl", PrintTests.class);
		FileUtil.getFileStandalone("bar.egl", PrintTests.class);
		fooEGX = FileUtil.getFileStandalone("foo.egx", PrintTests.class);
	}
	
	IEgxModule module;
	
	@Test
	public void testEglInclude() throws Exception {
		module = new EgxModule(outputRoot);
		module.parse(fooEGX);
		module.execute();
	}
}
