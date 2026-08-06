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
 * $Id: NodeTypes.java,v 1.5 2008/08/14 12:37:27 louis Exp $
 */
package org.eclipse.epsilon.hutn.parse.postprocessor;

import org.eclipse.epsilon.hutn.parse.HutnParser;
import org.eclipse.epsilon.test.util.ModelWithEolAssertions;
import org.junit.Test;

public class NodeTypes extends HutnPostProcessorTest {
	
	private static ModelWithEolAssertions model;
	
	@Test
	public void typeShouldBeAdjective() {
		 model = postProcessorTest(buildAst(HutnParser.ADJECTIVE));
		 model.assertTrue("AdjectiveNode.isType(ast.roots.first())");
	}
	
	@Test
	public void typeShouldBeTextualValue() {
		 model = postProcessorTest(buildAst(HutnParser.TEXTUAL_VALUE));
		 model.assertTrue("TextualValueNode.isType(ast.roots.first())");
	}
	
	@Test
	public void typeShouldBeNumericValue() {
		 model = postProcessorTest(buildAst(HutnParser.NUMERIC_VALUE));
		 model.assertTrue("NumericValueNode.isType(ast.roots.first())");
	}
	
	@Test
	public void typeShouldBeName() {
		 model = postProcessorTest(buildAst(HutnParser.NAME));
		 model.assertTrue("NameNode.isType(ast.roots.first())");
	}
	
	@Test
	public void typeShouldBeNull() {
		 model = postProcessorTest(buildAst(HutnParser.NULL));
		 model.assertTrue("NullNode.isType(ast.roots.first())");
	}
	
	@Test
	public void typeShouldBeTrue() {
		 model = postProcessorTest(buildAst(HutnParser.TRUE));
		 model.assertTrue("TrueNode.isType(ast.roots.first())");
	}
	
	@Test
	public void typeShouldBeFalse() {
		 model = postProcessorTest(buildAst(HutnParser.FALSE));
		 model.assertTrue("FalseNode.isType(ast.roots.first())");
	}
	
	@Test
	public void typeShouldBeClassifierLevelAttribute() {
		 model = postProcessorTest(buildAst(HutnParser.CLS_LVL_ATTRIBUTE));
		 model.assertTrue("ClassifierLevelAttributeNode.isType(ast.roots.first())");
	}
	
	@Test
	public void typeShouldBeAssocInstance() {
		 model = postProcessorTest(buildAst(HutnParser.ASSOC_INSTANCE));
		 model.assertTrue("AssociationInstanceNode.isType(ast.roots.first())");
	}
}
