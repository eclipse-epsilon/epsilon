/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.output;

import java.io.File;
import java.io.IOException;
import org.eclipse.epsilon.common.util.FileUtil;
import org.junit.*;
import static org.junit.Assert.*;
import static org.eclipse.epsilon.egl.util.FileUtil.NEWLINE;

public class TestWriter {
	
	private static File VALID_PATH;
	private static File READ_ONLY_PATH;
	
	private int testNumber = 0;
	
	@BeforeClass
	public static void setUpOnce() throws Exception {	
		// FIXME What we actually want is some temp paths
		//VALID_PATH     = FileUtil.getFile("Valid.txt", TestWriter.class);
		//READ_ONLY_PATH = FileUtil.getFile("ReadOnly.txt", TestWriter.class);
		VALID_PATH     = FileUtil.createTempFile("Valid.txt");
		READ_ONLY_PATH = FileUtil.createTempFile("ReadOnly.txt");
		
		// READ_ONLY_PATH.createNewFile();
		READ_ONLY_PATH.setReadOnly();
	}
	
	@AfterClass
	public static void tearDownOnce() throws IOException {
		if (VALID_PATH.exists())     VALID_PATH.delete();
		if (READ_ONLY_PATH.exists()) READ_ONLY_PATH.delete();
		
	}
	
	@Before
	public void setUp() {
		testNumber++;
	}
	
	@Test
	public void testWriteValid() throws IOException {
		final String contents = "Hello" + NEWLINE + "World!" + NEWLINE + testNumber;
		org.eclipse.epsilon.egl.util.FileUtil.write(VALID_PATH, contents);
		
		assertEquals(contents, org.eclipse.epsilon.egl.util.FileUtil.read(VALID_PATH));
	}
	
	@Test (expected=IOException.class)
	public void testWriteReadOnly() throws IOException {
		final String contents = "Hello" + NEWLINE + "World!" + NEWLINE + testNumber;
		org.eclipse.epsilon.egl.util.FileUtil.write(READ_ONLY_PATH, contents);
	}
}
