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
 * $Id: TwoPackages.java,v 1.2 2008/08/07 12:44:21 louis Exp $
 */
package org.eclipse.epsilon.hutn.translate;

import org.eclipse.epsilon.test.util.ModelWithEolAssertions;
import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.Ast;
import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.Node;
import org.junit.BeforeClass;
import org.junit.Test;

public class TwoPackages extends HutnTranslatorTest {
	
	private static ModelWithEolAssertions model;
	
	@BeforeClass
	public static void createAstModel() throws Exception {
		final Node secondPackage = createPackage("PetsPackage");
		final Node firstPackage  = createPackage("FamilyPackage");
		
		final Ast ast = initialiseAst();
		ast.getRoots().add(firstPackage);
		ast.getRoots().add(secondPackage);
		
		model = translatorTest(ast);
		model.setVariable("object1", "spec.objects.at(0)");
		model.setVariable("object2", "spec.objects.at(1)");
	}
	
	@Test
	public void specShouldHaveNoNsUris() {
		model.assertEquals(0, "spec.nsUris.size()");
	}
	
	@Test
	public void specShouldHaveTwoObjects() {
		model.assertEquals(2, "spec.objects.size()");
	}
	
	@Test
	public void firstObjectShouldBePackage() {
		model.assertTrue("PackageObject.isType(object1)");
	}
	
	@Test
	public void firstObjectShouldHaveCorrectType() {
		model.assertEquals("FamilyPackage", "object1.type");
	}
	
	@Test
	public void secondObjectShouldBePackage() {
		model.assertTrue("PackageObject.isType(object2)");
	}
	
	@Test
	public void secondObjectShouldHaveCorrectType() {
		model.assertEquals("PetsPackage", "object2.type");
	}
}
