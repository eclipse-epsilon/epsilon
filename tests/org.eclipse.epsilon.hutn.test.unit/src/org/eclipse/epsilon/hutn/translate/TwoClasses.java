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
 * $Id: TwoClasses.java,v 1.2 2008/08/07 12:44:21 louis Exp $
 */
package org.eclipse.epsilon.hutn.translate;

import org.eclipse.epsilon.test.util.ModelWithEolAssertions;
import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.Ast;
import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.Node;
import org.junit.BeforeClass;
import org.junit.Test;

public class TwoClasses extends HutnTranslatorTest {
	
	private static ModelWithEolAssertions model;
	
	@BeforeClass
	public static void createAstModel() throws Exception {
		final Node theSmithsNode = createClass("Family", "The Smiths", 2, 4);
		final Node fidoNode      = createClass("Pet",    "Fido", 10, 8);
		
		final Node packageNode = createPackage("FamilyPackage");
		packageNode.getChildren().add(theSmithsNode);
		packageNode.getChildren().add(fidoNode);
		
		final Ast ast = initialiseAst();
		ast.getRoots().add(packageNode);
		
		model = translatorTest(ast);
		model.setVariable("package", "spec.objects.first()");
		model.setVariable("class1",  "package.classObjects.first()");
		model.setVariable("class2",  "package.classObjects.last()");
	}
		
	@Test
	public void packageShouldHaveTwoClassObjects() {
		model.assertEquals(2, "package.classObjects.size()");
	}
	
	@Test
	public void firstClassShouldHaveCorrectType() {
		model.assertEquals("Family", "class1.type");
	}
	
	@Test
	public void firstClassShouldHaveCorrectLineNumber() {
		model.assertEquals(2, "class1.line");
	}
	
	@Test
	public void firstClassShouldHaveCorrectColumnNumber() {
		model.assertEquals(4, "class1.col");
	}
	
	@Test
	public void firstClassShouldHaveCorrectIdentifier() {
		model.assertEquals("The Smiths", "class1.identifier");
	}
	
	@Test
	public void secondClassShouldHaveCorrectType() {
		model.assertEquals("Pet", "class2.type");
	}
	
	@Test
	public void secondClassShouldHaveCorrectLineNumber() {
		model.assertEquals(10, "class2.line");
	}
	
	@Test
	public void secondClassShouldHaveCorrectColumnNumber() {
		model.assertEquals(8, "class2.col");
	}
	
	@Test
	public void secondClassShouldHaveCorrectIdentifier() {
		model.assertEquals("Fido", "class2.identifier");
	}
}
