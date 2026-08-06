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
 * $Id: SinglePackageWithSpec.java,v 1.2 2008/08/07 12:44:20 louis Exp $
 */
package org.eclipse.epsilon.hutn.translate;

import org.eclipse.epsilon.test.util.ModelWithEolAssertions;
import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.Ast;
import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.Node;
import org.junit.BeforeClass;
import org.junit.Test;

public class SpecWithModelFile extends HutnTranslatorTest {
	
	private static ModelWithEolAssertions model;
	
	@BeforeClass
	public static void createAstModel() throws Exception {
//		final Node familyPackage = createPackage("FamilyPackage");
		
		final Node modelFileAttribute = createAttribute("file", "other.model");
		
		final Node modelClass = createClass("model");
		modelClass.getChildren().add(modelFileAttribute);
		
		final Node specPackage = createPackage("@Spec");
		specPackage.getChildren().add(modelClass);
		
		final Ast ast = initialiseAst();
		ast.getRoots().add(specPackage);
//		ast.getRoots().add(familyPackage);
		
		model = translatorTest(ast);
		model.setVariable("object", "spec.objects.first()");
	}
	
	@Test
	public void specShouldHaveCorrectModelFile() {
		model.assertEquals("other.model", "spec.modelFile");
	}
}
