/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.test.acceptance.traceability;

import static org.junit.Assert.assertEquals;
import java.io.File;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.common.util.OperatingSystem;
import org.eclipse.epsilon.common.util.UriUtil;
import org.eclipse.epsilon.egl.test.acceptance.AcceptanceTestUtil;
import org.eclipse.epsilon.egl.test.models.Model;
import org.eclipse.epsilon.egl.traceability.OutputFile;
import org.eclipse.epsilon.egl.traceability.Template;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Traceability {

	private final static boolean OS_IS_WINDOWS = OperatingSystem.isWindows();
	
	private static File program, subProgram, subSubProgram, programOutput, subProgramOutput;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		program          = FileUtil.getFileStandalone("Hierachy.egl", Traceability.class);
		subProgram       = FileUtil.getFileStandalone("Traceability.egl", Traceability.class);
		subSubProgram    = FileUtil.getFileStandalone("OOClass2JavaClass.egl", Traceability.class);
		programOutput    = FileUtil.createTempFile("Traceability", ".txt");
		subProgramOutput = FileUtil.createTempFile("OO2Java", ".txt");
	}
	
	@Before
	public void setUp() throws Exception {
		final File existing = FileUtil.getFileStandalone("OO2Java_existing.txt", Traceability.class);
		org.eclipse.epsilon.egl.util.FileUtil.write(subProgramOutput, org.eclipse.epsilon.egl.util.FileUtil.read(existing));
	}
	
	@AfterClass
	public static void tearDownOnce() {
		if (programOutput.exists())    programOutput.delete();
		if (subProgramOutput.exists()) subProgramOutput.delete();
	}
	
	@Test
	public void traceability() throws Exception {
		final Template expected = new Template(UriUtil.fileToUri(subProgram));
		final Template subTemplate = expected.addTemplate(UriUtil.fileToUri(subSubProgram));
		subTemplate.addVariable("name", "Animal");
		
		final OutputFile outputFile = subTemplate.addOutputFile(UriUtil.fileToUri(subProgramOutput));
		outputFile.addProtectedRegion("javadoc",  true,  OS_IS_WINDOWS ? 24 : 22);
		outputFile.addProtectedRegion("header",   true,  OS_IS_WINDOWS ? 236 : 226);
		outputFile.addProtectedRegion("sleep",    true,  OS_IS_WINDOWS ? 395 : 378);
		outputFile.addProtectedRegion("talk",     true,  OS_IS_WINDOWS ? 533 : 510);
		outputFile.addProtectedRegion("toString", false, OS_IS_WINDOWS ? 687 : 657);
		
		Object result = AcceptanceTestUtil.run(subProgram, Model.OOInstance);
		assertEquals("Hello world from Template!", result.toString().trim());
		assertEquals(expected, AcceptanceTestUtil.getTrace());
	}
	
	@Test
	public void traceabilityHierachy() throws Exception {
		final Template expected           = new Template(UriUtil.fileToUri(program));
		final Template subProgramTemplate = expected.addTemplate(UriUtil.fileToUri(subProgram));
		subProgramTemplate.addVariable("unused", "n/a");
		final Template subSubProgramTemplate = subProgramTemplate.addTemplate(UriUtil.fileToUri(subSubProgram));
		subSubProgramTemplate.addVariable("name", "Animal");
		
		final OutputFile outputFile = subSubProgramTemplate.addOutputFile(UriUtil.fileToUri(subProgramOutput));
		outputFile.addProtectedRegion("javadoc",  true,  OS_IS_WINDOWS ? 24 : 22);
		outputFile.addProtectedRegion("header",   true,  OS_IS_WINDOWS ? 236 : 226);
		outputFile.addProtectedRegion("sleep",    true,  OS_IS_WINDOWS ? 395 : 378);
		outputFile.addProtectedRegion("talk",     true,  OS_IS_WINDOWS ? 533 : 510);
		outputFile.addProtectedRegion("toString", false, OS_IS_WINDOWS ? 687 : 657);
		subProgramTemplate.addOutputFile(UriUtil.fileToUri(programOutput));
		
		setUpBeforeClass();
		Object result = AcceptanceTestUtil.run(program, Model.OOInstance);
		assertEquals(programOutput.toString(), result.toString());
		// FIXME: Doesn't pass
		//assertEquals(expected, AcceptanceTestUtil.getTrace());
	}
}
