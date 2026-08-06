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
 * $Id: SingleClassWithAdjectives.java,v 1.2 2008/08/07 12:44:21 louis Exp $
 */
package org.eclipse.epsilon.hutn.translate;

import org.eclipse.epsilon.test.util.ModelWithEolAssertions;
import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.Ast;
import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.Node;
import org.junit.BeforeClass;
import org.junit.Test;

public class SingleClassWithAdjectives extends HutnTranslatorTest {
	
	private static ModelWithEolAssertions model;
	
	@BeforeClass
	public static void createAstModel() throws Exception {
		final Node classNode = createClass("Family", "The Smiths", "#nuclear", "~migrant");
		
		final Node packageNode = createPackage("FamilyPackage");
		packageNode.getChildren().add(classNode);
		
		final Ast ast = initialiseAst();
		ast.getRoots().add(packageNode);
		
		model = translatorTest(ast);
		model.setVariable("package", "spec.objects.first()");
		model.setVariable("class",   "package.classObjects.first()");
		
		model.setVariable("slot1", "class.slots.at(0)");
		model.setVariable("slot2", "class.slots.at(1)");
	}
	
	@Test
	public void firstSlotShouldHaveCorrectFeatureName() {
		model.assertEquals("nuclear", "slot1.feature");
	}
	
	@Test
	public void firstSlotShouldHaveSingleValue() {
		model.assertEquals(1, "slot1.values.size()");
	}
	
	@Test
	public void firstSlotShouldHaveCorrectValue() {
		model.assertEquals(true, "slot1.values.first()");
	}
	
	@Test
	public void secondSlotShouldHaveCorrectFeatureName() {
		model.assertEquals("migrant", "slot2.feature");
	}
	
	@Test
	public void secondSlotShouldHaveSingleValue() {
		model.assertEquals(1, "slot2.values.size()");
	}
	
	@Test
	public void secondSlotShouldHaveCorrectValue() {
		model.assertEquals(false, "slot2.values.first()");
	}
}
