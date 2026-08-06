/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.parse;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.parse.EpsilonTreeAdaptor;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.egl.parse.EglToken.TokenType;

public class EglParser {
	
	private final EglLexer lexer;
	private final List<ParseProblem> problems = new LinkedList<>();
	
	private final EpsilonTreeAdaptor astFactory;
	
	private ParseProblem lastError = null;
	private EglToken current; 
	
	private AST ast;
	
	
	public EglParser(EglLexer lexer, EpsilonTreeAdaptor astFactory) {
		if (lexer==null)
			throw new NullPointerException("program cannot be null");
		
		this.lexer      = lexer;
		this.astFactory = astFactory;
		
		ast = createAST(TokenType.PROGRAM, "", 1, 1);
	}
	
	public AST getAST() {
		return ast;
	}
	
	public List<ParseProblem> getParseProblems() {
		return Collections.unmodifiableList(problems);
	}
	
	public boolean parse() {
		try {
			nextToken();
			return parseProgram();
		
		} catch (EglRecognitionException e) {
			problems.add(new ParseProblem(e.getLineNumber(), e.getColumnNumber(), e.getMessage()));
			return false;
		}
	}
	
	
	private void nextToken() throws EglRecognitionException {
		current = lexer.nextToken();
	}
	
	private boolean parseProgram() throws EglRecognitionException {
		while(true) {
			if (current.getTokenType() == TokenType.EOF)
				return true;

			final AST commentedAst = parseCommented();
			
			if (commentedAst != null) {
			
				ast.addChild(commentedAst);
				
			} else {
				final AST taggedAst = parseTagged();
				
				if (taggedAst != null) {
					ast.addChild(taggedAst);
				
				} else {
				
					final AST textAst = parseText();
					
					if (textAst != null) {
						ast.addChild(textAst);
					
					} else {
						return false;
					}
				}
			}
		}
	}
	
	private AST parseText() throws EglRecognitionException {
		StringBuilder text = new StringBuilder();
		
		int line = -1;
		int col  = -1;
		
		while (current.getTokenType() == TokenType.PLAIN_TEXT) {
			if (line == -1) {
				line = current.getLine();
				col  = current.getColumn();
			}
			text.append(current.getText());
			nextToken();
		}
		
		if (text.toString().length() > 0) {
			return createAST(TokenType.PLAIN_TEXT, text.toString(), line, col);
			
		} else {
			if (current.getTokenType() == TokenType.NEW_LINE) {
				
				AST newLineAST = createAST(current); 
				nextToken();
				return newLineAST;
			
			} else {
				reportError(TokenType.PLAIN_TEXT);
				return null;
			}
		} 
	}
	
	private AST parseTagged() throws EglRecognitionException {
		final EglToken startTagToken = current; 
		
		if (current.getTokenType() != TokenType.START_TAG &&
		    current.getTokenType() != TokenType.START_OUTPUT_TAG)
			return null;
		
		nextToken();
		
		// Create a data structure to hold all of the text sections
		// that this tagged section contains
		final List<AST> textAsts = new LinkedList<>();
		
		// Parse as many text tokens as possible
		AST next = null;
		do {
			next = parseText();
			if (next != null) textAsts.add(next);
		} while(next != null && current.getTokenType() != TokenType.END_TAG && current.getTokenType() != TokenType.END_OUTDENT_TAG);
			
		// A tagged section must contain at least one text section
		if (textAsts.size() == 0) return null;
		
		// Ensure end tag is present
		if (current.getTokenType() == TokenType.END_TAG || current.getTokenType() == TokenType.END_OUTDENT_TAG) {
			final AST endTag = createAST(current);
			
			final AST taggedAst = createAST(startTagToken);
			for (AST textAst : textAsts) {
				taggedAst.addChild(textAst);
			}
			taggedAst.addChild(endTag);
			
			nextToken();
			
			return taggedAst;
		}

		return null;
	}
	
	private AST parseCommented() throws EglRecognitionException {
		final EglToken startCommentTagToken = current; 
		
		if (current.getTokenType() != TokenType.START_COMMENT_TAG &&
			current.getTokenType() != TokenType.START_MARKER_TAG)
			return null;
		
		nextToken();
		
		final StringBuilder commentContents = new StringBuilder();
		
		// Parse all tokens that between this and the end comment token
		while(current != null &&
			  current.getTokenType() != TokenType.END_COMMENT_TAG && 
			  current.getTokenType() != TokenType.EOF) {
			commentContents.append(current.getText());
			nextToken();
		}
		
		// Ensure end comment tag is present
		if (current.getTokenType() == TokenType.END_COMMENT_TAG) {
			final AST endCommentTag = createAST(current);
			final AST commentedAst = createAST(startCommentTagToken);
			
			commentedAst.addChild(createAST(TokenType.PLAIN_TEXT,
			                                commentContents.toString(),
			                                startCommentTagToken.getLine(),
			                                startCommentTagToken.getColumn() + startCommentTagToken.getText().length()));
		
			commentedAst.addChild(endCommentTag);
			
			nextToken();
			
			return commentedAst;
		}

		return null;
	}
	
	private AST createAST(EglToken t) {
		return astFactory.create(t);
	}
	
	private AST createAST(TokenType type, String text, int line, int col) {
		return astFactory.create(new EglToken(type, text, line, col));
	}

	private void reportError(TokenType expectedType) {

		if (lastError == null ||
		    lastError.getColumn() != current.getColumn() ||
		    lastError.getLine()   != current.getLine()) {
		
			lastError = new ParseProblem(current.getLine(),
			                             current.getColumn(),
			                             "Unexpected token '" + current.getTokenType() + "' " + 
			                              "(was expecting '" + expectedType + "')");
			                               	
			problems.add(lastError);
		}
	}
}
