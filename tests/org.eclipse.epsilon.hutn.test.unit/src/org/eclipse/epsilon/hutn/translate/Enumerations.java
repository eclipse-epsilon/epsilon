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
 * $Id: Enumerations.java,v 1.1 2008/08/08 17:19:01 louis Exp $
 */
package org.eclipse.epsilon.hutn.translate;

import org.eclipse.epsilon.test.util.ModelWithEolAssertions;
import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.Ast;
import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.Node;
import org.junit.BeforeClass;
import org.junit.Test;

public class Enumerations extends HutnTranslatorTest {
	
	private static ModelWithEolAssertions model;
	
	@BeforeClass
	public static void createAstModel() throws Exception {
		final Node enumerationAttribute  = createEnumerationAttribute("breed", "labrador");
		
		final Node classNode = createClass("Family", "The Smiths");
		classNode.getChildren().add(enumerationAttribute);
		
		final Node packageNode = createPackage("FamilyPackage");
		packageNode.getChildren().add(classNode);
		
		final Ast ast = initialiseAst();
		ast.getRoots().add(packageNode);
		
		model = translatorTest(ast);
		model.setVariable("package", "spec.objects.first()");
		model.setVariable("class",   "package.classObjects.first()");
		
		model.setVariable("slot", "class.slots.at(0)");
	}
	
	@Test
	public void packageShouldContainOneClassObject() {
		model.assertEquals(1, "package.classObjects.size()");
	}
	
	@Test
	public void slotShouldHaveCorrectFeatureName() {
		model.assertEquals("breed", "slot.feature");
	}
	
	@Test
	public void slotShouldHaveOneValue() {
		model.assertEquals(1, "slot.values.size()");
	}
	
	@Test
	public void slotShouldHaveCorrectValue() {
		model.assertEquals("labrador", "slot.values.first");
	}
}
