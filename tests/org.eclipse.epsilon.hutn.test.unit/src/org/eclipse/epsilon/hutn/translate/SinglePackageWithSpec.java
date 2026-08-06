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

public class SinglePackageWithSpec extends HutnTranslatorTest {
	
	private static ModelWithEolAssertions model;
	
	@BeforeClass
	public static void createAstModel() throws Exception {
		final Node familyPackage = createPackage("FamilyPackage");
		
		final Node nsUriAttribute = createAttribute("nsUri", "families");
		
		final Node metaModelClass = createClass("Metamodel", "FamiliesMetaModel");
		metaModelClass.getChildren().add(nsUriAttribute);
		
		final Node specPackage = createPackage("@Spec");
		specPackage.getChildren().add(metaModelClass);
		
		final Ast ast = initialiseAst();
		ast.getRoots().add(specPackage);
		ast.getRoots().add(familyPackage);
		
		model = translatorTest(ast);
		model.setVariable("object", "spec.objects.first()");
	}
	
	@Test
	public void specShouldHaveOneNsUri() {
		model.assertEquals(1, "spec.nsUris.size()");
	}
	
	@Test
	public void nsUriShouldHaveCorrectValue() {
		model.assertEquals("families", "spec.nsUris.first().value");
	}
	
	@Test
	public void specShouldHaveNoModelFile() {
		model.assertUndefined("spec.modelFile");
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
}
