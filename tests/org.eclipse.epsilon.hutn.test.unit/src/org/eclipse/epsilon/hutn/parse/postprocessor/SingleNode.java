/**
 * *******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 * ******************************************************************************
 *
 * $Id: SingleNode.java,v 1.2 2008/08/07 12:44:20 louis Exp $
 */
package org.eclipse.epsilon.hutn.parse.postprocessor;

import org.eclipse.epsilon.hutn.parse.HutnParser;
import org.eclipse.epsilon.test.util.ModelWithEolAssertions;
import org.junit.BeforeClass;
import org.junit.Test;

public class SingleNode extends HutnPostProcessorTest {
	
	private static ModelWithEolAssertions model;
	
	@BeforeClass
	public static void createAstModel() {
		 model = postProcessorTest(buildAst(HutnParser.ADJECTIVE, "foo"));
		 model.setVariable("root", "ast.roots.first()");
	}
	
	@Test
	public void modelShouldHaveOneRoot() {
		model.assertEquals(1, "ast.roots.size()");
	}
	
	@Test
	public void rootShouldBeCorrectType() {
		model.assertTrue("AdjectiveNode.isType(root)");
	}
	
	@Test
	public void rootShouldHaveCorrectText() {
		model.assertEquals("foo", "root.text");
	}
	
	@Test
	public void rootShouldHaveNoChildren() {
		model.assertEquals(0, "root.children.size()");
	}
}
