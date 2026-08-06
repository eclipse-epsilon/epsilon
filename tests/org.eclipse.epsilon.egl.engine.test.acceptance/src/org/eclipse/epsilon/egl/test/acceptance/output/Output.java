/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.test.acceptance.output;

import static org.eclipse.epsilon.egl.util.FileUtil.NEWLINE;

import java.io.File;

import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.egl.test.acceptance.AcceptanceTestUtil;
import org.junit.BeforeClass;
import org.junit.Test;

public class Output {

	private static File process;
	private static File hierachy;
	
	@BeforeClass
	public static void setUpOnce() throws Exception {
		process  = FileUtil.getFileStandalone("Process.egl",  Output.class);
		hierachy = FileUtil.getFileStandalone("Hierachy.egl", Output.class);
		// Create imported files
		FileUtil.getFileStandalone("SubTemplate1.egl", Output.class);
		FileUtil.getFileStandalone("SubTemplate2.egl", Output.class);
		FileUtil.getFileStandalone("SubTemplate3.egl", Output.class);
		FileUtil.getFileStandalone("HierachySubTemplate.egl", Output.class);
		FileUtil.getFileStandalone("HierachySubSubTemplate.egl", Output.class);
	}
	
	@Test
	public void testProcess() throws Exception {
		final String expected = "Preprocess"                     + NEWLINE +
		                        "Hello world from SubTemplate1!" + NEWLINE +
		                        "Postprocess"                    + NEWLINE +
		                        "Preprocess"                     + NEWLINE +
		                        "Hello world from SubTemplate2!" + NEWLINE +
		                        "Postprocess"                    + NEWLINE +
		                        "Preprocess"                     + NEWLINE +
		                        "Hello world from SubTemplate3!" + NEWLINE +
		                        "Postprocess"                    + NEWLINE;
		
		AcceptanceTestUtil.test(process, expected);
	}
	
	@Test
	public void testProcessHierachy() throws Exception {
		final String expected = "Hello world from Template!"         + NEWLINE +
		                        "Hello world from SubTemplate!"      + NEWLINE +
		                        "Hello world from SubSubTemplate!"   + NEWLINE +
		                        "Goodbye world from SubSubTemplate!" + NEWLINE +
		                        "Goodbye world from SubTemplate!"    + NEWLINE +
		                        "Goodbye world from Template!";
		
		AcceptanceTestUtil.test(hierachy, expected);
	}
}
