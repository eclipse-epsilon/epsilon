/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************
 *
 * $Id$
 */
package org.eclipse.epsilon.hutn.parse.spec;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;
import java.util.LinkedList;

import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.hutn.model.hutn.HutnFactory;
import org.eclipse.epsilon.hutn.model.hutn.Spec;

public class HutnPreamble {

	// TODO - should this be an epsilon module?
	
	private final Collection<ParseProblem> problems = new LinkedList<>();
	private Token next;
	
	public Spec process(String hutn) {
		try {
			return transform(parse(hutn));
	
		} catch (RecognitionException ex){
			addParseProblem(ex.getMessage(), ex.line, ex.charPositionInLine);
		
		} catch (Throwable ex) {
			addParseProblem("mismatched input: '" + next.getText() + "'", next.getLine(), next.getCharPositionInLine());
		}
				
		return emptySpec();
	}
	

	private CommonTree parse(String hutn) throws IOException, RecognitionException {
		final HutnSpecLexer  lexer  = new HutnSpecLexer(new ANTLRReaderStream(new StringReader(hutn)));
		final HutnSpecParser parser = new HutnSpecParser(new CommonTokenStream(lexer));
		
		next = parser.input.LT(1);
		
		return (CommonTree)parser.preamble().getTree();
	}

	private Spec transform(CommonTree ast) {
		return new HutnSpecAstToSpec(ast).transformPreamble();
	}

	private Spec emptySpec() {
		return HutnFactory.eINSTANCE.createSpec();
	}

	private void addParseProblem(String reason, int line, int column) {
		problems.add(new ParseProblem(line, column, reason));
	}
}
