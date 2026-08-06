/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.test.acceptance.output.lineNumbers;

import static org.eclipse.epsilon.egl.util.FileUtil.NEWLINE;

import java.io.File;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.egl.test.acceptance.AcceptanceTestUtil;
import org.junit.Test;

public class CurrentLineNumber {
	
	@Test
	public void testLineNumber() throws Exception {
		final String template = "These "                           + NEWLINE +
		                        "are "                             + NEWLINE +
		                        "lines "                           + NEWLINE +
		                        "[%=out.getCurrentLineNumber()%] " + NEWLINE + 
		                        "and "                             + NEWLINE +
		                        "[%=out.getCurrentLineNumber()%].";                
		
		final String expected = "These are lines 4 and 6.".replaceAll(" ", " \n");
		
		AcceptanceTestUtil.test(template, expected);
	}
	
	@Test
	public void testLineNumberCountsLinesGeneratedBySubtemplate() throws Exception {
		final String expected = "Parent 1" + NEWLINE +
		                        "Child 1"  + NEWLINE +
		                        "Child 2"  + NEWLINE +
		                        "Child 3"  + NEWLINE +
		                        "Child 4"  + NEWLINE +
		                        "Parent 6";
		
		AcceptanceTestUtil.test(getParentTemplate(), expected);
	}
	
	
	private static File getParentTemplate() throws Exception {
		// Load imported file
		FileUtil.getFileStandalone("Child.egl",  CurrentLineNumber.class);
		return FileUtil.getFileStandalone("Parent.egl",  CurrentLineNumber.class);
	}

}
