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
 * $Id: NonExistentConfigFile.java,v 1.2 2008/08/07 12:44:21 louis Exp $
 */
package org.eclipse.epsilon.hutn.translate;

import org.eclipse.epsilon.hutn.exceptions.HutnConfigFileNotFoundException;
import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.Ast;
import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.Node;
import org.junit.BeforeClass;
import org.junit.Test;

public class NonExistentConfigFile extends HutnTranslatorTest {
	
	private static Ast ast;
	
	@BeforeClass
	public static void createAstModel() throws Exception {
		final Node familyPackage = createPackage("FamilyPackage");
		
		final Node configFileAttribute = createAttribute("configFile", "NotHere.model");
		final Node nsUriAttribute      = createAttribute("nsUri", "families");
		
		final Node metaModelClass = createClass("Metamodel", "FamiliesMetaModel");
		metaModelClass.getChildren().add(nsUriAttribute);
		metaModelClass.getChildren().add(configFileAttribute);
		
		final Node specPackage = createPackage("@Spec");
		specPackage.getChildren().add(metaModelClass);
		
		ast = initialiseAst();
		ast.getRoots().add(specPackage);
		ast.getRoots().add(familyPackage);
	}
	
	@Test(expected=HutnConfigFileNotFoundException.class)
	public void configurationFileShouldNotBeFound() throws Exception {
		translatorTest(ast);
	}
}
