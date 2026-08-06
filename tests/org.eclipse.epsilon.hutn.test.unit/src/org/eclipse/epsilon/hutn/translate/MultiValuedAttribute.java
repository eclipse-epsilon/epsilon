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
 * $Id: MultiValuedAttribute.java,v 1.2 2008/08/07 12:44:21 louis Exp $
 */
package org.eclipse.epsilon.hutn.translate;

import org.eclipse.epsilon.test.util.ModelWithEolAssertions;
import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.Ast;
import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.Node;
import org.junit.BeforeClass;
import org.junit.Test;

public class MultiValuedAttribute extends HutnTranslatorTest {
	
	private static ModelWithEolAssertions model;
	
	@BeforeClass
	public static void createAstModel() throws Exception {
		final Node stringAttribute = createAttribute("name", "The Smiths", "The Does", "The Bloggs");
		
		final Node classNode = createClass("Family", "The Smiths");
		classNode.getChildren().add(stringAttribute);
		
		final Node packageNode = createPackage("FamilyPackage");
		packageNode.getChildren().add(classNode);
		
		final Ast ast = initialiseAst();
		ast.getRoots().add(packageNode);
		
		model = translatorTest(ast);
		model.setVariable("package", "spec.objects.first()");
		model.setVariable("class",   "package.classObjects.first()");
		
		model.setVariable("slot", "class.slots.first()");
	}
	
	@Test
	public void slotShouldHaveThreeValues() {
		model.assertEquals(3, "slot.values.size()");
	}
	
	@Test
	public void slotShouldHaveCorrectFirstValue() {
		model.assertEquals("The Smiths", "slot.values.at(0)");
	}
	
	@Test
	public void slotShouldHaveCorrectSecondValue() {
		model.assertEquals("The Does", "slot.values.at(1)");
	}
	
	@Test
	public void slotShouldHaveCorrectThirdValue() {
		model.assertEquals("The Bloggs", "slot.values.at(2)");
	}
}
