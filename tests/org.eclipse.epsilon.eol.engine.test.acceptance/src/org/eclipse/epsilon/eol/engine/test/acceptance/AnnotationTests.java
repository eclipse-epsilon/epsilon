/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.engine.test.acceptance;

import static org.junit.Assert.assertTrue;

import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.dom.Operation;
import org.junit.Test;

public class AnnotationTests {
	
	@Test
	public void testAnnotations() throws Exception {
		
		EolModule module = new EolModule();
		module.parse(new MultilineString(
				"@foo",
				"operation bar() {}")
			.toString());
		
		Operation bar = module.getOperations().getOperation("bar");
		assertTrue(bar.hasAnnotation("foo"));
	}
	
	class MultilineString {
		
		protected String content = "";
		
		public MultilineString(String... lines) {
			for (String line : lines) {
				content = content + line + "\r\n";
			}
		}
		
		public String toString() {
			return content;
		}
	}
	
}
