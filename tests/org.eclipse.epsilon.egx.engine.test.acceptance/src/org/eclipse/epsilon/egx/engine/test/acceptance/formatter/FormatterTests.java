/*********************************************************************
 * Copyright (c) 2022 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.egx.engine.test.acceptance.formatter;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.egl.EgxModule;
import org.eclipse.epsilon.egl.IEgxModule;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * @since 2.5
 */
public class FormatterTests {
	static File xmlEGX;
	static Path outputRoot;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		outputRoot = Paths.get(FileUtil.getFileStandalone("", FormatterTests.class).toURI());
		// Load dependencies
		FileUtil.getFileStandalone("xml.egl", FormatterTests.class);
		xmlEGX = FileUtil.getFileStandalone("xml.egx", FormatterTests.class);
	}
	
	IEgxModule module;
	
	@Test
	public void testFormatter() throws Exception {
		module = new EgxModule(outputRoot);
		module.parse(xmlEGX);
		module.execute();

		List<String> lines = Files.lines(outputRoot.resolve("output.xml")).collect(Collectors.toList());
		assertEquals(3, lines.size());
		assertEquals("\t<hello world/>", lines.get(1));
	}
}
