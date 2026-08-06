/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.hutn.parse.postprocessor;

import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.eclipse.epsilon.antlr.postprocessor.AbstractAstPostProcessor;
import org.eclipse.epsilon.antlr.postprocessor.model.antlrAst.Node;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.hutn.model.hutnAntlrAst.HutnAntlrAstFactory;
import org.eclipse.epsilon.hutn.parse.HutnParser;


public class HutnPostProcessor extends AbstractAstPostProcessor {

	private final List<ParseProblem> problems;
	
	public HutnPostProcessor() {
		this.problems = null;
	}
	
	public HutnPostProcessor(List<ParseProblem> problems) {
		this.problems = problems;
	}

	@Override
	protected Node createNodeObjectFor(CommonTree ast) {
		switch (ast.getType()) {
			case HutnParser.ADJECTIVE:
				
				if (problems != null && ast.getText() != null && ast.getText().startsWith("#"))
					problems.add(new ParseProblem(ast.getLine(), ast.getCharPositionInLine(), "Prefixing postive adjectives with the # symbol has been deprecated. Instead, use the adjective name without a prefix.", ParseProblem.WARNING));
				
				return HutnAntlrAstFactory.eINSTANCE.createAdjectiveNode();
				
			case HutnParser.TEXTUAL_VALUE:
				return HutnAntlrAstFactory.eINSTANCE.createTextualValueNode();
				
			case HutnParser.NUMERIC_VALUE:
				return HutnAntlrAstFactory.eINSTANCE.createNumericValueNode();
				
			case HutnParser.NAME:
				return HutnAntlrAstFactory.eINSTANCE.createNameNode();
				
			case HutnParser.NULL:
				return HutnAntlrAstFactory.eINSTANCE.createNullNode();
				
			case HutnParser.TRUE:
				return HutnAntlrAstFactory.eINSTANCE.createTrueNode();
				
			case HutnParser.FALSE:
				return HutnAntlrAstFactory.eINSTANCE.createFalseNode();
			
			case HutnParser.REFERENCE:
				return HutnAntlrAstFactory.eINSTANCE.createReferenceNode();
				
			case HutnParser.CLS_LVL_ATTRIBUTE:
				return HutnAntlrAstFactory.eINSTANCE.createClassifierLevelAttributeNode();
			
			case HutnParser.ASSOC_INSTANCE:
				return HutnAntlrAstFactory.eINSTANCE.createAssociationInstanceNode();
				
			default:
				throw new IllegalStateException("No model element can be created from the ANTLR AST node, " + ast.toString());
		}
	}
}
