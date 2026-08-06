/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.test.acceptance.operations.template;

import static org.eclipse.epsilon.egl.util.FileUtil.NEWLINE;

import org.eclipse.epsilon.egl.test.acceptance.AcceptanceTestUtil;
import org.junit.Test;

public class TemplateOperations {
	
	@Test
	public void testTemplateOperation() throws Exception {
		final String template = "[%=main()%]"          +
		                        "[% @template"         + NEWLINE +
		                        "operation main() {%]" + NEWLINE +
		                        "foo"                  + NEWLINE + 
		                        "[%}%]";
		
		final String expected = "foo";
		
		AcceptanceTestUtil.test(template, expected);
	}
	
	@Test
	public void testTemplateOperationWithDynamicSection() throws Exception {
		final String template = "[%=main()%]"          +
		                        "[% @template"         + NEWLINE +
		                        "operation main() {%]" + NEWLINE +
		                        "foo [%='ba'+'r'%]"    + NEWLINE + 
		                        "[%}%]";
		
		final String expected = "foo bar";
		
		AcceptanceTestUtil.test(template, expected);
	}

	@Test
	public void testNonTemplateOperationWithStaticSection() throws Exception {
		final String template = "[% main(); %]"        +
		                        "[%"                   + NEWLINE +
		                        "operation main() {%]" + NEWLINE +
		                        "foo"                  + NEWLINE +
		                        "[%}%]";
		
		final String expected = "foo" + NEWLINE;
		
		AcceptanceTestUtil.test(template, expected);
	}
	
	@Test
	public void testTemplateOperationWithProtectedRegion() throws Exception {
		final String template = "[%=main() %]"                                      + 
		                        "[% @template"                                       + NEWLINE +
		                        "operation main() {%]"                               + NEWLINE +
		                        "[%=out.startPreserve('//', '', 'myRegion', true)%]" + NEWLINE +
		                        "[%=out.stopPreserve()%]" + NEWLINE +
		                        "[%}%]";
		
		final String expected = "// protected region myRegion on begin" + NEWLINE +
		                        "// protected region myRegion end";
		       
		
		AcceptanceTestUtil.test(template, expected);
	}
}
