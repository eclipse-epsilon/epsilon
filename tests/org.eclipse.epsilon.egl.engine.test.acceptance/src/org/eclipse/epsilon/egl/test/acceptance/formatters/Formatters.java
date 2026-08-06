/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.test.acceptance.formatters;

import java.io.File;

import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.egl.test.acceptance.AcceptanceTestUtil;
import org.junit.BeforeClass;
import org.junit.Test;

public class Formatters {
	
	/*
	 * TODO
	 *   - formatter group: ordered bag of formatters for default / template-specific
	 */
	
	private static File DefaultFormatter, DefaultFormatterExpected;
	private static File FormatterGroup, FormatterGroupExpected;
	private static File TemplateSpecificFormatter, TemplateSpecificFormatterExpected;
	private static File TemplateSpecificFormatterGroup, TemplateSpecificFormatterGroupExpected;
	
	@BeforeClass
	public static void setUpOnce() throws Exception {
		DefaultFormatter         = FileUtil.getFileStandalone("DefaultFormatter.egl", Formatters.class);
		DefaultFormatterExpected = FileUtil.getFileStandalone("DefaultFormatter.txt", Formatters.class);
		
		FormatterGroup         = FileUtil.getFileStandalone("DefaultFormatterGroup.egl", Formatters.class);
		FormatterGroupExpected = FileUtil.getFileStandalone("DefaultFormatterGroup.txt", Formatters.class);
		
		TemplateSpecificFormatter         = FileUtil.getFileStandalone("TemplateSpecificFormatter.egl", Formatters.class);
		TemplateSpecificFormatterExpected = FileUtil.getFileStandalone("TemplateSpecificFormatter.txt", Formatters.class);
		
		TemplateSpecificFormatterGroup         = FileUtil.getFileStandalone("TemplateSpecificFormatterGroup.egl", Formatters.class);
		TemplateSpecificFormatterGroupExpected = FileUtil.getFileStandalone("TemplateSpecificFormatterGroup.txt", Formatters.class);
		
		// Load imported files
		FileUtil.getFileStandalone("FirstSubtemplate.egl", Formatters.class);
		FileUtil.getFileStandalone("SecondSubtemplate.egl", Formatters.class);
	}
	
	@Test
	public void defaultFormatterIsAppliedForAllTemplates() throws Exception {
		AcceptanceTestUtil.test(DefaultFormatter, DefaultFormatterExpected);
	}
	
	@Test
	public void allFormattersInAGroupAreApplied() throws Exception {
		AcceptanceTestUtil.test(FormatterGroup, FormatterGroupExpected);
	}
	
	@Test
	public void templateSpecificFormatterIsPreferredToDefaultFormatter() throws Exception {
		AcceptanceTestUtil.test(TemplateSpecificFormatter, TemplateSpecificFormatterExpected);
	}
	
	@Test
	public void allTemplateSpecificFormattersAreApplied() throws Exception {
		AcceptanceTestUtil.test(TemplateSpecificFormatterGroup, TemplateSpecificFormatterGroupExpected);
	}
}
