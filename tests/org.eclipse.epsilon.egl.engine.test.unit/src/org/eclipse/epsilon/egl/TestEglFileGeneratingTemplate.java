/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl;

import static org.eclipse.epsilon.egl.util.FileUtil.NEWLINE;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.eclipse.epsilon.common.util.UriUtil;
import org.eclipse.epsilon.egl.execute.context.IEglContext;
import org.eclipse.epsilon.egl.merge.partition.CommentBlockPartitioner;
import org.eclipse.epsilon.egl.test.MockContext;
import org.eclipse.epsilon.egl.util.FileUtil;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestEglFileGeneratingTemplate {
	
	private static File PROGRAM;
	private static File OUTPUT1, OUTPUT2, OUTPUT3, OUTPUT4, OUTPUT5, OUTPUT6;
	private static File EXISTING;
	private static File GENERATED;
	
	private EglFileGeneratingTemplate template;
	private int testNumber = 1;
	
	@BeforeClass
	public static void setUpOnce() throws IOException {
		PROGRAM   = org.eclipse.epsilon.common.util.FileUtil.createTempFile("Generate", ".egl");
		OUTPUT1   = org.eclipse.epsilon.common.util.FileUtil.createTempFile("Output1", ".txt");
		OUTPUT2   = org.eclipse.epsilon.common.util.FileUtil.createTempFile("Output2", ".txt");
		OUTPUT3   = org.eclipse.epsilon.common.util.FileUtil.createTempFile("Output3", ".txt");
		OUTPUT4   = org.eclipse.epsilon.common.util.FileUtil.createTempFile("Output4", ".txt");
		OUTPUT5   = org.eclipse.epsilon.common.util.FileUtil.createTempFile("Output5", ".txt");
		OUTPUT6   = org.eclipse.epsilon.common.util.FileUtil.createTempFile("Output6", ".txt");
		EXISTING  = org.eclipse.epsilon.common.util.FileUtil.createTempFile("Existing", ".txt");
		GENERATED = org.eclipse.epsilon.common.util.FileUtil.createTempFile("Generated", ".txt");
		
		if (!EXISTING.exists()) EXISTING.createNewFile();
	}
	
	@AfterClass
	public static void tearDownOnce() {
		if (PROGRAM.exists())   PROGRAM.delete();
		if (OUTPUT1.exists())   OUTPUT1.delete();
		if (OUTPUT2.exists())   OUTPUT2.delete();
		if (OUTPUT3.exists())   OUTPUT3.delete();
		if (OUTPUT4.exists())   OUTPUT4.delete();
		if (OUTPUT5.exists())   OUTPUT5.delete();
		if (OUTPUT6.exists())   OUTPUT6.delete();
		if (EXISTING.exists())  EXISTING.delete();
		if (GENERATED.exists()) GENERATED.delete();
	}
	
	@After
	public void tearDown() throws IOException {
		FileUtil.write(PROGRAM,  "");
		FileUtil.write(EXISTING, "");
		
		testNumber++;
	}
	
	@Test
	public void testStore() throws Exception {
		String expected = "Hello world!" + testNumber;
		FileUtil.write(PROGRAM, "[% out.print('" + expected + "'); %]");
		
		template = new EglFileGeneratingTemplate(UriUtil.fileToUri(PROGRAM), new MockContext(), UriUtil.fileToUri(PROGRAM.getParentFile()));
		
		template.process();
		template.generate(OUTPUT1.getName(), true, false);
		
		assertEquals(expected, FileUtil.read(OUTPUT1));
	}
	
	@Test
	public void testStoreAbsolute() throws Exception {
		String expected = "Hello world!" + testNumber;
		FileUtil.write(PROGRAM, "[% out.print('" + expected + "'); %]");
		
		template = new EglFileGeneratingTemplate(UriUtil.fileToUri(PROGRAM), new MockContext(), UriUtil.fileToUri(PROGRAM.getParentFile()));
		
		template.process();
		template.generate(OUTPUT1.getAbsolutePath(), true, false);
		
		assertEquals(expected, FileUtil.read(OUTPUT1));
	}
	
	@Test
	public void testStoreWithOverwrite() throws Exception {
		String expected = "Hello world!" + testNumber;
		FileUtil.write(PROGRAM, "[% out.print('" + expected + "'); %]");
		template = new EglFileGeneratingTemplate(UriUtil.fileToUri(PROGRAM), new MockContext(), UriUtil.fileToUri(PROGRAM.getParentFile()));
		
		template.process();
		template.generate(OUTPUT2.getName(), true, false);
		
		assertEquals(expected, FileUtil.read(OUTPUT2));
	}
	
	
	@Test
	public void testStoreWithoutProcess() throws Exception {
		String expected = "Hello world!" + testNumber;
		FileUtil.write(PROGRAM, "[% out.print('" + expected + "'); %]");
		template = new EglFileGeneratingTemplate(UriUtil.fileToUri(PROGRAM), new MockContext(), UriUtil.fileToUri(PROGRAM.getParentFile()));
		
		template.generate(OUTPUT3.getName(), true, false);
		
		assertEquals(expected, FileUtil.read(OUTPUT3));
	}
	
	
	@Test
	public void testStoreOverExisting() throws Exception {
		String contents = "Hello world!" + testNumber;
		FileUtil.write(PROGRAM, "[% out.print('" + contents + "'); %]");
		template = new EglFileGeneratingTemplate(UriUtil.fileToUri(PROGRAM), new MockContext(), UriUtil.fileToUri(PROGRAM.getParentFile()));
		
		template.process();
		template.generate(EXISTING.getName(), false, false);
		
		assertEquals("", FileUtil.read(EXISTING));
	}
	
	@Test
	public void testStoreOverExistingWithOverwrite() throws Exception {
		String expected = "Hello world!" + testNumber;
		FileUtil.write(PROGRAM, "[% out.print('" + expected + "'); %]");
		template = new EglFileGeneratingTemplate(UriUtil.fileToUri(PROGRAM), new MockContext(), UriUtil.fileToUri(PROGRAM.getParentFile()));
		
		template.process();
		template.generate(EXISTING.getName(), true, false);
		
		assertEquals(expected, FileUtil.read(EXISTING));
	}
	
	
	@Test
	public void testGenerate() throws Exception {
		FileUtil.write(PROGRAM, 
		               "This text will appear before the protected region" + NEWLINE +
		               "// protected region test on begin"                 + NEWLINE +
		               "// This default value will not be present"         + NEWLINE + 
		               "// protected region test end"                      + NEWLINE +
		               "This text will appear after the protected region");
		
		FileUtil.write(EXISTING,
		               "This text will not be preserved"   + NEWLINE +
		               "// protected region test on begin" + NEWLINE +
		               "// This region will be preserved"  + NEWLINE + 
		               "// protected region test end"      + NEWLINE +
		               "Also this text will not be preserved");
		
		final String expected = "This text will appear before the protected region" + NEWLINE +
                                "// protected region test on begin"                 + NEWLINE +
                                "// This region will be preserved"                  + NEWLINE + 
                                "// protected region test end"                      + NEWLINE +
                                "This text will appear after the protected region";
		
		final IEglContext context = new MockContext();
		context.getPartitioner().addPartitioner(new CommentBlockPartitioner("//", null));
		
		template = new EglFileGeneratingTemplate(UriUtil.fileToUri(PROGRAM), context, UriUtil.fileToUri(PROGRAM.getParentFile()));
		template.generate(EXISTING.getName());
		
		assertEquals(expected, FileUtil.read(EXISTING));
	}
	
	@Test
	public void testGenerateNoExisting() throws Exception {
		final String expected = "This text will appear before the protected region" + NEWLINE +
                                "// protected region test on begin"                 + NEWLINE +
                                "// This region will be preserved"                  + NEWLINE + 
                                "// protected region test end"                      + NEWLINE +
                                "This text will appear after the protected region";
		
		FileUtil.write(PROGRAM, expected);
		
		
		final IEglContext context = new MockContext();
		context.getPartitioner().addPartitioner(new CommentBlockPartitioner("//", null));
		
		template = new EglFileGeneratingTemplate(UriUtil.fileToUri(PROGRAM), context, UriUtil.fileToUri(PROGRAM.getParentFile()));
		template.generate(OUTPUT4.getName());
		
		assertEquals(expected, FileUtil.read(OUTPUT4));
	}
	
	@Test
	public void testDoesNotOverwriteWhenMergedContentsAreUnchanged() throws Exception {
		final String existing = "This text will appear before the protected region" + NEWLINE +
		                        "// protected region test on begin"                 + NEWLINE +
		                        "// This region will be preserved"                  + NEWLINE + 
		                        "// protected region test end"                      + NEWLINE +
		                        "This text will appear after the protected region";
		
		FileUtil.write(OUTPUT5, existing);
		FileUtil.write(PROGRAM, existing);
		
		final MockContext context = new MockContext();
		
		template = new EglFileGeneratingTemplate(UriUtil.fileToUri(PROGRAM), context, UriUtil.fileToUri(PROGRAM.getParentFile()));
		template.generate(OUTPUT5.getName());
		
		assertEquals(1, context.getStatusMessages().size());
		assertEquals("Content unchanged for " + OUTPUT5.getAbsolutePath(), context.getStatusMessages().get(0).getMessage());
	}
	
	@Test
	public void testDoesNotOverwriteWhenUnprotectedContentsAreUnchanged() throws Exception {
		FileUtil.write(OUTPUT6, "Existing text");
		FileUtil.write(PROGRAM, "Existing text");
		
		final MockContext context = new MockContext();
		
		template = new EglFileGeneratingTemplate(UriUtil.fileToUri(PROGRAM), context, UriUtil.fileToUri(PROGRAM.getParentFile()));
		template.generate(OUTPUT6.getName(), true, false);
		
		assertEquals(1, context.getStatusMessages().size());
		assertEquals("Content unchanged for " + OUTPUT6.getAbsolutePath(), context.getStatusMessages().get(0).getMessage());
	}
}

