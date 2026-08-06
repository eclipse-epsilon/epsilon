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
 * $Id: SinglePackage.java,v 1.2 2008/08/07 12:44:21 louis Exp $
 */
package org.eclipse.epsilon.hutn.translate;

import org.eclipse.epsilon.test.util.ModelWithEolAssertions;
import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.Ast;
import org.junit.BeforeClass;
import org.junit.Test;

public class SinglePackage extends HutnTranslatorTest {
	
	private static ModelWithEolAssertions model;
	
	@BeforeClass
	public static void createAstModel() throws Exception {
		final Ast ast = initialiseAst();
		ast.getRoots().add(createPackage("FamilyPackage", 2, 4));
		
		model = translatorTest(ast);
		model.setVariable("object", "spec.objects.first()");
	}
	
	@Test
	public void specShouldHaveNoNsUris() {
		model.assertEquals(0, "spec.nsUris.size()");
	}
	
	@Test
	public void specShouldHaveOneObject() {
		model.assertEquals(1, "spec.objects.size()");
	}
	
	@Test
	public void objectShouldBePackage() {
		model.assertTrue("PackageObject.isType(object)");
	}
	
	@Test
	public void objectShouldHaveCorrectType() {
		model.assertEquals("FamilyPackage", "object.type");
	}
	
	@Test
	public void objectShouldHaveCorrectLineNumber() {
		model.assertEquals(2, "object.line");
	}
	
	@Test
	public void objectShouldHaveCorrectColumnNumber() {
		model.assertEquals(4, "object.col");
	}
	
	@Test
	public void objectShouldHaveNoClassObjects() {
		model.assertEquals(0, "object.classObjects.size()");
	}
}
