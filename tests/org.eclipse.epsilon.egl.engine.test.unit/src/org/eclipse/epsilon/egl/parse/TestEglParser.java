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

import static org.eclipse.epsilon.egl.util.FileUtil.NEWLINE;
import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;
import org.eclipse.epsilon.egl.parse.EglToken.TokenType;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.parse.EpsilonTreeAdaptor;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.junit.Test;

public class TestEglParser {
	
	private final EpsilonTreeAdaptor astFactory = new EpsilonTreeAdaptor((File)null);
	
	private void walkAst(AST ast, EglToken... tokens) {
		
		AST current = ast;
		AST parent  = null;
		
		for (EglToken token : tokens) {
			assertTrue(current != null);
//			System.out.println(token + " | " + current);
			
			assertEquals(token.getTokenType(), TokenType.typeOf(current.getType()));
			assertEquals(token.getText(),   current.getText());
			assertEquals(token.getLine(),   current.getLine());
			assertEquals(token.getColumn(), current.getColumn());
			
			if (current.getFirstChild() != null) {
				parent  = current;
				current = current.getFirstChild();
				
			} else if (current.getNextSibling() != null) {
				current = current.getNextSibling();
			
			} else if (parent != null && parent.getNextSibling() != null) {
				current = parent.getNextSibling();
			
			} else {
				current = null;
			}
		}
	}
	
	
	@Test
	public void testEmptyProgram() {
		final EglLexer   lexer = new EglLexer("");
		final EglParser parser = new EglParser(lexer, astFactory);
		
		assertTrue(parser.parse());
		
		walkAst(parser.getAST(), new EglToken(TokenType.PROGRAM, "", 1, 1));
		
		assertEquals(0, parser.getParseProblems().size());
	}
	
	@Test
	public void testTextOnly() {
		final EglLexer   lexer = new EglLexer("abc");
		final EglParser parser = new EglParser(lexer, astFactory);
		
		assertTrue(parser.parse());
		
		walkAst(parser.getAST(), new EglToken(TokenType.PROGRAM, "", 1, 1),
		                         new EglToken(TokenType.PLAIN_TEXT, "abc", 1, 1));
		
		assertEquals(0, parser.getParseProblems().size());
	}
	
	@Test
	public void testTaggedOnly() {
		final EglLexer   lexer = new EglLexer("[%abc%]");
		final EglParser parser = new EglParser(lexer, astFactory);
		
		assertTrue(parser.parse());
		
		walkAst(parser.getAST(), new EglToken(TokenType.PROGRAM, "", 1, 1),
		                         new EglToken(TokenType.START_TAG, "[%", 1, 1),
		                         new EglToken(TokenType.PLAIN_TEXT, "abc", 1, 3),
		                         new EglToken(TokenType.END_TAG, "%]", 1, 6));
		
		assertEquals(0, parser.getParseProblems().size());
	}
	
	@Test
	public void testOutputTaggedOnly() {
		final EglLexer   lexer = new EglLexer("[%=abc%]");
		final EglParser parser = new EglParser(lexer, astFactory);
		
		assertTrue(parser.parse());
		
		walkAst(parser.getAST(), new EglToken(TokenType.PROGRAM, "", 1, 1),
				                 new EglToken(TokenType.START_OUTPUT_TAG, "[%=", 1, 1),
				                 new EglToken(TokenType.PLAIN_TEXT, "abc", 1, 4),
				                 new EglToken(TokenType.END_TAG, "%]", 1, 7));
		
		assertEquals(0, parser.getParseProblems().size());
	}
	
	@Test
	public void testTextThenTagged() {
		final EglLexer   lexer = new EglLexer("abc[%=def%]");
		final EglParser parser = new EglParser(lexer, astFactory);
		
		assertTrue(parser.parse());
		
		walkAst(parser.getAST(), new EglToken(TokenType.PROGRAM, "", 1, 1),
		                         new EglToken(TokenType.PLAIN_TEXT, "abc", 1, 1),
		                         new EglToken(TokenType.START_OUTPUT_TAG, "[%=", 1, 4),
		                         new EglToken(TokenType.PLAIN_TEXT, "def", 1, 7),
		                         new EglToken(TokenType.END_TAG, "%]", 1, 10));
		
		assertEquals(0, parser.getParseProblems().size());
	}
	
	@Test
	public void testTaggedThenText() {
		final EglLexer   lexer = new EglLexer("[%=abc%]def");
		final EglParser parser = new EglParser(lexer, astFactory);
		
		assertTrue(parser.parse());
		
		walkAst(parser.getAST(), new EglToken(TokenType.PROGRAM, "", 1, 1),
		                         new EglToken(TokenType.START_OUTPUT_TAG, "[%=", 1, 1),
		                         new EglToken(TokenType.PLAIN_TEXT, "abc", 1, 4),
		                         new EglToken(TokenType.END_TAG, "%]", 1, 7),
		                         new EglToken(TokenType.PLAIN_TEXT, "def", 1, 9));
		
		assertEquals(0, parser.getParseProblems().size());
	}
	
	@Test
	public void testEmptyComment() {
		final EglLexer   lexer = new EglLexer("[**]");
		final EglParser parser = new EglParser(lexer, astFactory);
		
		assertTrue(parser.parse());
		
		walkAst(parser.getAST(), new EglToken(TokenType.PROGRAM, "", 1, 1),
		                         new EglToken(TokenType.START_COMMENT_TAG, "[*", 1, 1),
		                         new EglToken(TokenType.PLAIN_TEXT, "", 1, 3),
		                         new EglToken(TokenType.END_COMMENT_TAG, "*]", 1, 3));
		
		assertEquals(0, parser.getParseProblems().size());
	}
	
	@Test
	public void testComment() {
		//                                     0          
		//                                     123456789
		final EglLexer   lexer = new EglLexer("[* foo *]");
		final EglParser parser = new EglParser(lexer, astFactory);
		
		assertTrue(parser.parse());
		
		walkAst(parser.getAST(), new EglToken(TokenType.PROGRAM, "", 1, 1),
		                         new EglToken(TokenType.START_COMMENT_TAG, "[*", 1, 1),
		                         new EglToken(TokenType.PLAIN_TEXT, " foo ", 1, 3),
		                         new EglToken(TokenType.END_COMMENT_TAG, "*]", 1, 8));
		
		assertEquals(0, parser.getParseProblems().size());
	}
	
	@Test
	public void testMultiSectionComment() {
		//                                     0        1         2         3         4         5         
		//                                     12345678901234567890123456789012345678901234567890123456789
		final EglLexer   lexer = new EglLexer("[* [% for (i in Sequence{1..5}) { %] i is [%=i%] [% } %] *]");
		final EglParser parser = new EglParser(lexer, astFactory);
		
		assertTrue(parser.parse());
		
		walkAst(parser.getAST(), new EglToken(TokenType.PROGRAM, "", 1, 1),
		                         new EglToken(TokenType.START_COMMENT_TAG, "[*", 1, 1),
		                         new EglToken(TokenType.PLAIN_TEXT, " [% for (i in Sequence{1..5}) { %] i is [%=i%] [% } %] ", 1, 3),
		                         new EglToken(TokenType.END_COMMENT_TAG, "*]", 1, 58));
		
		assertEquals(0, parser.getParseProblems().size());
	}
	
	@Test
	public void testEmptyMarker() {
		final EglLexer   lexer = new EglLexer("[*-*]");
		final EglParser parser = new EglParser(lexer, astFactory);
		
		assertTrue(parser.parse());
		
		walkAst(parser.getAST(), new EglToken(TokenType.PROGRAM, "", 1, 1),
		                         new EglToken(TokenType.START_MARKER_TAG, "[*-", 1, 1),
		                         new EglToken(TokenType.PLAIN_TEXT, "", 1, 4),
		                         new EglToken(TokenType.END_COMMENT_TAG, "*]", 1, 4));
		
		assertEquals(0, parser.getParseProblems().size());
	}
	
	@Test
	public void testMarker() {
		//                                     0          
		//                                     123456789
		final EglLexer   lexer = new EglLexer("[*- foo *]");
		final EglParser parser = new EglParser(lexer, astFactory);
		
		assertTrue(parser.parse());
		
		walkAst(parser.getAST(), new EglToken(TokenType.PROGRAM, "", 1, 1),
		                         new EglToken(TokenType.START_MARKER_TAG, "[*-", 1, 1),
		                         new EglToken(TokenType.PLAIN_TEXT, " foo ", 1, 4),
		                         new EglToken(TokenType.END_COMMENT_TAG, "*]", 1, 9));
		
		assertEquals(0, parser.getParseProblems().size());
	}
	
	@Test
	public void testMissingTextInTags() {
		final EglLexer   lexer = new EglLexer("[%%]");
		final EglParser parser = new EglParser(lexer, astFactory);
		
		assertFalse(parser.parse());
		
		assertEquals(1, parser.getParseProblems().size());
		
		ParseProblem e = parser.getParseProblems().get(0);
		
//		System.out.println(e);
		
		assertEquals(1, e.getLine());
		assertEquals(3, e.getColumn());
	}
	
	@Test
	public void testMissingStartTag() {
		final EglLexer   lexer = new EglLexer("abc%]");
		final EglParser parser = new EglParser(lexer, astFactory);
		
		assertFalse(parser.parse());
		
		assertEquals(1, parser.getParseProblems().size());
		
		ParseProblem e = parser.getParseProblems().get(0);
		
//		System.out.println(e);
		
		assertEquals(1, e.getLine());
		assertEquals(4, e.getColumn());
	}
	
	@Test
	public void testMissingEndTag() {
		final EglLexer   lexer = new EglLexer("[%abc");
		final EglParser parser = new EglParser(lexer, astFactory);
		
		assertFalse(parser.parse());
		
		assertEquals(1, parser.getParseProblems().size());
		
		ParseProblem e = parser.getParseProblems().get(0);
		
//		System.out.println(e);
		
		assertEquals(1, e.getLine());
		assertEquals(6, e.getColumn());
	}
	
	@Test
	public void testMissingStartCommentTag() {
		final EglLexer   lexer = new EglLexer("abc*]");
		final EglParser parser = new EglParser(lexer, astFactory);
		
		assertFalse(parser.parse());
		
		assertEquals(1, parser.getParseProblems().size());
		
		ParseProblem e = parser.getParseProblems().get(0);
		
//		System.out.println(e);
		
		assertEquals(1, e.getLine());
		assertEquals(4, e.getColumn());
	}
	
	@Test
	public void testMissingEndCommentTag() {
		final EglLexer   lexer = new EglLexer("[*abc");
		final EglParser parser = new EglParser(lexer, astFactory);
		
		assertFalse(parser.parse());
		
		assertEquals(1, parser.getParseProblems().size());
		
		ParseProblem e = parser.getParseProblems().get(0);
		
//		System.out.println(e);
		
		assertEquals(1, e.getLine());
		assertEquals(6, e.getColumn());
	}
	
	@Test
	public void testDoubleStartTag() {
		//                               0        1
		//                               1234567890123456
		final EglLexer   lexer = new EglLexer("[% abc [% def %]");
		final EglParser parser = new EglParser(lexer, astFactory);
		
		assertFalse(parser.parse());
		
		assertEquals(1, parser.getParseProblems().size());
		
		ParseProblem e = parser.getParseProblems().get(0);
		
//		System.out.println(e);
		
		assertEquals(1, e.getLine());
		assertEquals(8, e.getColumn());
	}
	
	
	@Test
	public void testDoubleEndTag() {
		//                                     0        1
		//                                     1234567890123456
		final EglLexer   lexer = new EglLexer("[% abc %] def %]");
		final EglParser parser = new EglParser(lexer, astFactory);
		
		assertFalse(parser.parse());
		
		assertEquals(1, parser.getParseProblems().size());
		
		ParseProblem e = parser.getParseProblems().get(0);
		
//		System.out.println(e);
		
		assertEquals(1, e.getLine());
		assertEquals(15, e.getColumn());
	}
	
	@Test
	public void testMultiline() {
		//                      0        1         2         3
		//                      12345678901234567890123456789012
		final String program = "[% for(i in Sequence(1..10) { %]" + NEWLINE +
		                       "i is [%=i%]" + NEWLINE +
		                       "[% } %]";
		final EglLexer lexer = new EglLexer(program);
		final EglParser parser = new EglParser(lexer, astFactory);
		
		assertTrue(parser.parse());
		
		walkAst(parser.getAST(), new EglToken(TokenType.PROGRAM, "", 1, 1),
				
		                         new EglToken(TokenType.START_TAG, "[%", 1, 1),
		                         new EglToken(TokenType.PLAIN_TEXT, " for(i in Sequence(1..10) { ", 1, 3),
		                         new EglToken(TokenType.END_TAG, "%]", 1, 31), 
		                         new EglToken(TokenType.NEW_LINE, NEWLINE, 1, 33),
		                         
		                         new EglToken(TokenType.PLAIN_TEXT, "i is ", 2, 1),
		                         
		                         new EglToken(TokenType.START_OUTPUT_TAG, "[%=", 2, 6),
		                         new EglToken(TokenType.PLAIN_TEXT, "i", 2, 9),
		                         new EglToken(TokenType.END_TAG, "%]", 2, 10),
		                         new EglToken(TokenType.NEW_LINE, NEWLINE, 2, 12),
		                         
		                         new EglToken(TokenType.START_TAG, "[%", 3, 1),
		                         new EglToken(TokenType.PLAIN_TEXT, " } ", 3, 3),
		                         new EglToken(TokenType.END_TAG, "%]", 3, 6));
		
		assertEquals(0, parser.getParseProblems().size());
	}
	
	
	@Test
	public void testMultiLineTaggedText() throws EglRecognitionException, IOException {
		//                               1         2         3         4
		//                      123456789012345678901234567890123456789012345678
		final String program = "[%for (i in Sequence{1..10})" + NEWLINE + 
                               "  var y : String;  %] [%var x : Integer := i+2%]";
		
		final EglLexer   lexer = new EglLexer(program);
		final EglParser parser = new EglParser(lexer, astFactory);
		
		assertTrue(parser.parse());

		
		walkAst(parser.getAST(), new EglToken(TokenType.PROGRAM, "",                                    1, 1),
		                         new EglToken(TokenType.START_TAG,        "[%",                         1, 1),
				                 new EglToken(TokenType.PLAIN_TEXT,       "for (i in Sequence{1..10})", 1, 3),
				                 new EglToken(TokenType.NEW_LINE,         NEWLINE,                      1, 29),
				                 
		                         new EglToken(TokenType.PLAIN_TEXT,       "  var y : String;  ",        2, 1),
		                         new EglToken(TokenType.END_TAG,          "%]",                         2, 20),
		                         
		                         new EglToken(TokenType.PLAIN_TEXT,       " ",                          2, 22),
		                         
		                         new EglToken(TokenType.START_TAG,        "[%",                         2, 23),
		                         new EglToken(TokenType.PLAIN_TEXT,       "var x : Integer := i+2",     2, 25),
		                         new EglToken(TokenType.END_TAG,          "%]",                         2, 47));
		
		assertEquals(0, parser.getParseProblems().size());
	}
}
