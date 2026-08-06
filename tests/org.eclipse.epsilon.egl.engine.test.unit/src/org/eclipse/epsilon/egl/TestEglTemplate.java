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
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.eclipse.epsilon.common.util.UriUtil;
import org.eclipse.epsilon.egl.exceptions.EglRuntimeException;
import org.eclipse.epsilon.egl.execute.context.EglContext;
import org.eclipse.epsilon.egl.execute.context.IEglContext;
import org.eclipse.epsilon.egl.merge.partition.CommentBlockPartitioner;
import org.eclipse.epsilon.egl.test.MockContext;
import org.eclipse.epsilon.egl.util.FileUtil;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestEglTemplate {

	private EglTemplate template;
	private int testNumber = 0;
	
	private static File SIMPLE, ABSENT, POPULATE, EXISTING, MERGE;
	
	@BeforeClass
	public static void setUpOnce() throws Exception {
		SIMPLE   = org.eclipse.epsilon.common.util.FileUtil.createTempFile("Simple", ".egl");
		ABSENT   = org.eclipse.epsilon.common.util.FileUtil.createTempFile("Absent", ".egl");
		POPULATE = org.eclipse.epsilon.common.util.FileUtil.createTempFile("Populate", ".egl");
		MERGE    = org.eclipse.epsilon.common.util.FileUtil.createTempFile("Merge", ".egl");
		EXISTING = org.eclipse.epsilon.common.util.FileUtil.createTempFile("Existing", ".txt");
		if (ABSENT.exists()) ABSENT.delete();
	}
	
	@AfterClass
	public static void tearDownOnce() {
		if (SIMPLE.exists())   SIMPLE.delete();
		if (ABSENT.exists())   ABSENT.delete();
		if (POPULATE.exists()) POPULATE.delete();
		if (EXISTING.exists()) EXISTING.delete();
	}
	
	@Before
	public void setUp() {
		testNumber++;
	}
	
	@Test
	public void testValid() throws Exception {
		String expected = "Hello world!" + testNumber;
		FileUtil.write(SIMPLE, "[% out.print('" + expected + "'); %]");
		
		template = new EglTemplate(UriUtil.fileToUri(SIMPLE), new MockContext());
		
		assertEquals(expected, template.process());
	}
	
	@Test
	public void testBuiltins() throws Exception  {
		FileUtil.write(SIMPLE, "[% System.out.println('' + null); %]");
		
		template = new EglTemplate(UriUtil.fileToUri(SIMPLE), new MockContext());
		
		try {
			template.process();
		} catch (EglRuntimeException e) {
			e.printStackTrace();
			fail("Processing the template should not throw an exception");
		}
	}
	
	@Test
	public void testProcessTwice() throws Exception {
		String expected = "Hello world!" + testNumber;
		FileUtil.write(SIMPLE, "[% out.print('" + expected + "'); %]");
		
		template = new EglTemplate(UriUtil.fileToUri(SIMPLE), new MockContext());
		template.process();
		
		assertEquals(expected, template.process());
	}
	
	@Test (expected = IOException.class)
	public void testAbsent() throws Exception {
		template = new EglTemplate(UriUtil.fileToUri(ABSENT), new MockContext());
		
		template.process();
	}
	
	@Test
	public void testPopulate() throws Exception {
		final String expected = "Hello world!" + testNumber;		
		FileUtil.write(POPULATE, "[% out.print(expected); %]");
		
		template = new EglTemplate(UriUtil.fileToUri(POPULATE), new MockContext());
		template.populate("expected", expected);
		
		assertEquals(expected, template.process());
	}

	
	@Test
	public void testMerge() throws Exception {
		FileUtil.write(MERGE,
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
		
		final IEglContext context = new EglContext(new EglTemplateFactory());
		context.getPartitioner().addPartitioner(new CommentBlockPartitioner("//", null));
		
		template = new EglTemplate(UriUtil.fileToUri(MERGE), context);
		
		final String actual = template.merge(FileUtil.read(EXISTING));
		
		assertEquals(expected, actual);
	}

}
