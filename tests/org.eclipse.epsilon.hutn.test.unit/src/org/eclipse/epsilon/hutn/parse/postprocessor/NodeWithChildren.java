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
 * $Id: NodeWithChildren.java,v 1.2 2008/08/07 12:44:20 louis Exp $
 */
package org.eclipse.epsilon.hutn.parse.postprocessor;

import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.hutn.parse.HutnParser;
import org.eclipse.epsilon.test.util.ModelWithEolAssertions;
import org.junit.BeforeClass;
import org.junit.Test;


public class NodeWithChildren extends HutnPostProcessorTest {
	private static ModelWithEolAssertions model;
	
	@BeforeClass
	public static void createAstModel() {
		final AST root   = buildAst(HutnParser.NAME, "family");
		final AST child1 = buildAst(HutnParser.ADJECTIVE, "~nuclear");
		final AST child2 = buildAst(HutnParser.ADJECTIVE, "#migrant");
		final AST child3 = buildAst(HutnParser.TEXTUAL_VALUE, "\"The Smiths\"");
		
		root.addChild(child1);
		root.addChild(child2);
		root.addChild(child3);
		
		 model = postProcessorTest(root);
		 model.setVariable("root",  "ast.roots.first()");
		 model.setVariable("child1", "root.children.at(0)");
		 model.setVariable("child2", "root.children.at(1)");
		 model.setVariable("child3", "root.children.at(2)");
	}
	
	@Test
	public void modelShouldHaveOneRoot() {
		model.assertEquals(1, "ast.roots.size()");
	}
	
	@Test
	public void rootShouldHaveThreeChildren() {
		model.assertEquals(3, "root.children.size()");
	}
	
	@Test
	public void child1ShouldHaveCorrectText() {
		model.assertEquals("~nuclear", "child1.text");
	}
	
	@Test
	public void child2ShouldHaveCorrectText() {
		model.assertEquals("#migrant", "child2.text");
	}
	
	@Test
	public void child3ShouldHaveCorrectText() {
		model.assertEquals("\"The Smiths\"", "child3.text");
	}
}
