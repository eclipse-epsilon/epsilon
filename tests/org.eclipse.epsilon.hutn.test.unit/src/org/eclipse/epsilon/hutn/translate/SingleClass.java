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
 * $Id: SingleClass.java,v 1.2 2008/08/07 12:44:21 louis Exp $
 */
package org.eclipse.epsilon.hutn.translate;

import org.eclipse.epsilon.test.util.ModelWithEolAssertions;
import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.Ast;
import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.Node;
import org.junit.BeforeClass;
import org.junit.Test;

public class SingleClass extends HutnTranslatorTest {
	
	private static ModelWithEolAssertions model;
	
	@BeforeClass
	public static void createAstModel() throws Exception {
		final Node classNode = createClass("Family", "The Smiths", 2, 4);
		
		final Node packageNode = createPackage("FamilyPackage");
		packageNode.getChildren().add(classNode);
		
		final Ast ast = initialiseAst();
		ast.getRoots().add(packageNode);
		
		model = translatorTest(ast);
		model.setVariable("package", "spec.objects.first()");
		model.setVariable("class",   "package.classObjects.first()");
	}
		
	@Test
	public void packageShouldHaveOneClassObject() {
		model.assertEquals(1, "package.classObjects.size()");
	}
	
	@Test
	public void classShouldHaveCorrectType() {
		model.assertEquals("Family", "class.type");
	}
	
	@Test
	public void classShouldHaveCorrectLineNumber() {
		model.assertEquals(2, "class.line");
	}
	
	@Test
	public void classShouldHaveCorrectColumnNumber() {
		model.assertEquals(4, "class.col");
	}
	
	@Test
	public void classShouldHaveCorrectIdentifier() {
		model.assertEquals("The Smiths", "class.identifier");
	}
}
