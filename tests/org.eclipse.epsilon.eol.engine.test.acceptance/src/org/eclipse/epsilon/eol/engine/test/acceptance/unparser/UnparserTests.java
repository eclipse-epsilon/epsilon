/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.engine.test.acceptance.unparser;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.parse.EolUnparser;

public abstract class UnparserTests {

	public abstract EolModule createModule();
	public abstract EolUnparser createUnparser();
	
	protected void test(Class<?> clazz, String... fileNames) throws Exception {
		for (String fileName : fileNames) {
			test(fileName, clazz);
		}
	}
	
	protected void test(String fileName, Class<?> clazz) throws Exception {
		EolModule module = createModule();
		File file = FileUtil.getFileStandalone(fileName, clazz);
		module.parse(file);
	
		String fileContents = stripCommentsAndEmptyLines(file);
		String unparserOutput = stripCommentsAndEmptyLines(createUnparser().unparse(module));
		
		if (!removeWhitespace(fileContents).equals(removeWhitespace(unparserOutput))) {
			assertEquals(fileContents, unparserOutput);
		}
	}
	
	protected String removeWhitespace(String str) {
		return str.replaceAll("\\s+","");
	}
	
	protected String stripCommentsAndEmptyLines(File file) throws Exception {
		List<String> lines = Files.readAllLines(file.toPath());
		return stripCommentsAndEmptyLines(lines);
	}
	
	protected String stripCommentsAndEmptyLines(String string) {
		return stripCommentsAndEmptyLines(Arrays.asList(string.split(System.lineSeparator())));
	}
	
	protected String stripCommentsAndEmptyLines(List<String> lines) {
		StringBuffer buffer = new StringBuffer();
		for (String line : lines) {
			if (!line.trim().isEmpty() && !line.trim().startsWith("//")) {
				buffer.append(line);
				buffer.append(System.lineSeparator());
			}
		}
		return buffer.toString();
	}
	
}
