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
 * $Id: NodeWithChild.java,v 1.2 2008/08/07 12:44:20 louis Exp $
 */
package org.eclipse.epsilon.hutn.parse.postprocessor;

import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.hutn.parse.HutnParser;
import org.eclipse.epsilon.test.util.ModelWithEolAssertions;
import org.junit.BeforeClass;
import org.junit.Test;


public class NodeWithChild extends HutnPostProcessorTest {
	private static ModelWithEolAssertions model;
	
	@BeforeClass
	public static void createAstModel() {
		final AST root  = buildAst(HutnParser.NAME, "family");
		final AST child = buildAst(HutnParser.TEXTUAL_VALUE, "\"The Smiths\"");
		
		root.setFirstChild(child);
		
		 model = postProcessorTest(root);
		 model.setVariable("root",  "ast.roots.first()");
		 model.setVariable("child", "root.children.first()");
	}
	
	@Test
	public void modelShouldHaveOneRoot() {
		model.assertEquals(1, "ast.roots.size()");
	}
	
	@Test
	public void rootShouldHaveOneChild() {
		model.assertEquals(1, "root.children.size()");
	}
	
	@Test
	public void childShouldBeCorrectType() {
		model.assertTrue("TextualValueNode.isType(child)");
	}
	
	@Test
	public void childShouldHaveCorrectText() {
		model.assertEquals("\"The Smiths\"", "child.text");
	}
	
	@Test
	public void childShouldHaveNoChildren() {
		model.assertEquals(0, "child.children.size()");
	}
}
