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
 * $Id: HutnPostProcessorTest.java,v 1.3 2008/08/12 12:59:13 louis Exp $
 */
package org.eclipse.epsilon.hutn.parse.postprocessor;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.CommonToken;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.emc.emf.AbstractEmfModel;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.test.util.ModelWithEolAssertions;

public abstract class HutnPostProcessorTest {
	
	protected final static List<ParseProblem> problems = new LinkedList<>();
	
	protected static ModelWithEolAssertions postProcessorTest(AST ast) {
		problems.clear();
		
		final AbstractEmfModel astModel = new InMemoryEmfModel(new HutnPostProcessor(problems).process(ast).eResource());
		final ModelWithEolAssertions model = new ModelWithEolAssertions(astModel);
		
		model.assertEquals(1, "Ast.allInstances().size()");
		model.setVariable("ast", "Ast.allInstances().first()");
		
		return model;
	}
	
	protected static AST buildAst(int type) {
		return buildAst(type, null);
	}
	
	protected static AST buildAst(int type, String text) {
		final AST ast = new AST(new CommonToken(type, text), (URI)null);
		
//		ast.token.setType(type);
//		if (text != null) ast.token.setText(text);
		
		return ast;
	}
}
