/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.engine.test.acceptance.dom;

import static org.junit.Assert.fail;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.exceptions.EolRedefinedVariableException;
import org.junit.Test;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class VariableTests {
	
	public static Object execute(String code) throws Exception {
		EolModule module = new EolModule();
		module.parse(code);
		return module.execute();
	}
	
	/**
	 * Test for Bug 404724
	 */
	@Test
	public void testTopLevelRedeclaration() throws Exception {
		try {
			execute(
				"var a : String;" + 
				"var a : Integer;" + 
				"a = 6;" + 
				"a.println();" + 
				"var a = \"a string\";" +
				"a.println();"
			);
			fail("Expected "+EolRedefinedVariableException.class.getSimpleName());
		}
		catch (EolRedefinedVariableException ex) {
			assert "a".equals(ex.getVariableName());
		}
	}
	
	@Test
	public void testAssignmentDeclaration() throws Exception {
		try {
			execute(
				"var a : String = \"a string\";" + 
				"a.println();" + 
				"var a : Integer = 6;" +
				"a.println();"
			);
			fail("Expected "+EolRedefinedVariableException.class.getSimpleName());
		}
		catch (EolRedefinedVariableException ex) {
			assert "a".equals(ex.getVariableName());
		}
	}
	
	@Test
	public void testRedeclarationInOperation() throws Exception {
		try {
			execute(
				"f();" +
				"operation f() {" +
					"var a : String;" + 
					"var a : Integer;" + 
					"a = 6;" + 
					"a.println();" + 
					"var a = \"a string\";" +
					"a.println();" +
				"}"
			);
			fail("Expected "+EolRedefinedVariableException.class.getSimpleName());
		}
		catch (EolRedefinedVariableException ex) {
			assert "a".equals(ex.getVariableName());
		}
	}
	
	@Test
	public void test1LevelRedeclaration() throws Exception {
		execute(
			"var a : String;" +
			"a = \"a string\";" +
			"a.println();" +
			"f();" +
			"operation f() {" +
				"var a : Integer;" + 
				"a = 6;" + 
				"a.println();" +
			"}"
		);
	}
	
}
