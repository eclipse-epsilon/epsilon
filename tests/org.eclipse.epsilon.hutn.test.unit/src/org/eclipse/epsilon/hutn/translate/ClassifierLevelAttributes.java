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
 * $Id: ClassifierLevelAttributes.java,v 1.1 2008/08/13 15:12:40 louis Exp $
 */
package org.eclipse.epsilon.hutn.translate;

import org.eclipse.epsilon.test.util.ModelWithEolAssertions;
import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.Ast;
import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.Node;
import org.junit.BeforeClass;
import org.junit.Test;

public class ClassifierLevelAttributes extends HutnTranslatorTest {
	
	private static ModelWithEolAssertions model;
	
	@BeforeClass
	public static void createAstModel() throws Exception {
		final Node cla1 = createClassifierLevelAttribute("Family", "numberOfChildren", 2);
		final Node cla2 = createClassifierLevelAttribute("Family", "address", "123 Main Street", "32 Fulford Road");
		
		final Node classNode = createClass("Family", "The Smiths");
		
		final Node packageNode = createPackage("FamilyPackage");
		packageNode.getChildren().add(cla1);
		packageNode.getChildren().add(classNode);
		packageNode.getChildren().add(cla2);
		
		final Ast ast = initialiseAst();
		ast.getRoots().add(packageNode);
		
		model = translatorTest(ast);
		model.setVariable("package",   "spec.objects.first()");
		model.setVariable("class",     "package.classObjects.first()");
		
		model.setVariable("slot1", "class.slots.at(0)");
		model.setVariable("slot2", "class.slots.at(1)");
	}
	
	@Test
	public void packageShouldContainOneObject() {
		model.assertEquals(1, "package.classObjects.size()");
	}
	
	@Test
	public void firstSlotShouldHaveCorrectFeatureName() {
		model.assertEquals("numberOfChildren", "slot1.feature");
	}
	
	@Test
	public void firstSlotShouldHaveSingleValue() {
		model.assertEquals(1, "slot1.values.size()");
	}
	
	@Test
	public void firstSlotShouldHaveCorrectValue() {
		model.assertEquals(2, "slot1.values.first()");
	}
	
	@Test
	public void secondSlotShouldHaveCorrectFeatureName() {
		model.assertEquals("address", "slot2.feature");
	}
	
	@Test
	public void secondSlotShouldHaveTwoValues() {
		model.assertEquals(2, "slot2.values.size()");
	}
	
	@Test
	public void secondSlotShouldHaveCorrectFirstValue() {
		model.assertEquals("123 Main Street", "slot2.values.first()");
	}
	
	@Test
	public void secondSlotShouldHaveCorrectSecondValue() {
		model.assertEquals("32 Fulford Road", "slot2.values.last()");
	}
}
