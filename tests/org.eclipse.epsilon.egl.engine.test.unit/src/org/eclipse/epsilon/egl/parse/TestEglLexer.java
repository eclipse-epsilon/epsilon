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

import static org.junit.Assert.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.epsilon.egl.parse.EglToken.TokenType;
import org.eclipse.epsilon.common.util.FileUtil;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.eclipse.epsilon.egl.util.FileUtil.NEWLINE;

public class TestEglLexer {
	
	private static File TEST_FILE;
	
	@BeforeClass
	public static void setUpOnce() throws IOException {
		// FIXME We want a temp file
		//TEST_FILE = FileUtil.getFile("Test.txt", TestEglLexer.class);
		TEST_FILE = FileUtil.createTempFile("Test.txt");
		
		final String program = "[% for(i in Sequence(1..10) { %]" + NEWLINE +
		                       "i is [%=i%]" + NEWLINE +
		                       "[% } %]";
		
		org.eclipse.epsilon.egl.util.FileUtil.write(TEST_FILE, program);
	}
	
	@AfterClass
	public static void tearDownOnce() throws IOException {
		if (TEST_FILE != null && TEST_FILE.exists()) TEST_FILE.delete();
	}
	
	@Test
	public void testStartTag() throws EglRecognitionException {
		final EglLexer l = new EglLexer("[%");
		
		EglToken expected = new EglToken(TokenType.START_TAG, "[%", 1, 1);
		EglToken actual = l.nextToken();
		
		assertTrue(expected.equals(actual));
	}
	
	
	@Test
	public void testStartOutputTag() throws EglRecognitionException {
		final EglLexer l = new EglLexer("[%=");
		
		EglToken expected = new EglToken(TokenType.START_OUTPUT_TAG, "[%=", 1, 1);
		EglToken actual = l.nextToken();
		
		assertTrue(expected.equals(actual));
	}
	
	@Test
	public void testEndTag() throws EglRecognitionException {
		final EglLexer l = new EglLexer("%]");
		
		EglToken expected = new EglToken(TokenType.END_TAG, "%]", 1, 1);
		EglToken actual = l.nextToken();
		
		assertTrue(expected.equals(actual));
	}
	
	@Test
	public void testStartCommentTag() throws EglRecognitionException {
		final EglLexer l = new EglLexer("[*");
		
		EglToken expected = new EglToken(TokenType.START_COMMENT_TAG, "[*", 1, 1);
		EglToken actual = l.nextToken();
		
		assertTrue(expected.equals(actual));
	}
	
	@Test
	public void testStartMarkerTag() throws EglRecognitionException {
		final EglLexer l = new EglLexer("[*-");
		
		EglToken expected = new EglToken(TokenType.START_MARKER_TAG, "[*-", 1, 1);
		EglToken actual = l.nextToken();
		
		assertTrue(expected.equals(actual));
	}
	
	@Test
	public void testEndCommentTag() throws EglRecognitionException {
		final EglLexer l = new EglLexer("*]");
		
		EglToken expected = new EglToken(TokenType.END_COMMENT_TAG, "*]", 1, 1);
		EglToken actual = l.nextToken();
		
		assertTrue(expected.equals(actual));
	}
	
	@Test
	public void testPlainText() throws EglRecognitionException {
		final String program = "ab[c%d]";
		final EglLexer l = new EglLexer(program);
		
		EglToken expected = new EglToken(TokenType.PLAIN_TEXT, program, 1, 1);
		EglToken actual = l.nextToken();
		
		assertTrue(expected.equals(actual));
	}
	
	@Test
	public void testNewLine() throws EglRecognitionException {
		final String program = NEWLINE;
		final EglLexer l = new EglLexer(program);
		
		EglToken expected = new EglToken(TokenType.NEW_LINE, program, 1, 1);
		EglToken actual = l.nextToken();
		
		assertTrue(expected.equals(actual));
	}

	@Test
	public void testEOF() throws EglRecognitionException {
		final String program = "";
		final EglLexer l = new EglLexer(program);
		
		EglToken expected = new EglToken(TokenType.EOF, program, 1, 1);
		EglToken actual = l.nextToken();
		
		assertTrue(expected.equals(actual));
	}
	
	
	@Test
	public void testMultipleTokens() throws EglRecognitionException {
		//                      0        1         2         3
		//                      12345678901234567890123456789012
		final String program = "plaintext[% eol %] moreplaintext";
		final EglLexer l = new EglLexer(program);
		
		List<EglToken> expectedTokens = new LinkedList<>();
		expectedTokens.add(new EglToken(TokenType.PLAIN_TEXT, "plaintext",      1, 1));
		expectedTokens.add(new EglToken(TokenType.START_TAG,  "[%",             1, 10));
		expectedTokens.add(new EglToken(TokenType.PLAIN_TEXT, " eol ",          1, 12));
		expectedTokens.add(new EglToken(TokenType.END_TAG,    "%]",             1, 17));
		expectedTokens.add(new EglToken(TokenType.PLAIN_TEXT, " moreplaintext", 1, 19));
		expectedTokens.add(new EglToken(TokenType.EOF,        "",               1, 33));
		
		for (EglToken expected : expectedTokens) {
			final EglToken actual = l.nextToken();
			assertTrue(expected.equals(actual));
		}
	}
	
	
	@Test
	public void testMultipleLines() throws EglRecognitionException {
		//                      0        1         2         3
		//                      12345678901234567890123456789012
		final String program = "[% for(i in Sequence(1..10) { %]" + NEWLINE +
		                       "i is [%=i%]" + NEWLINE +
		                       "[% } %]";
		final EglLexer l = new EglLexer(program);
		
		List<EglToken> expectedTokens = new LinkedList<>();
		expectedTokens.add(new EglToken(TokenType.START_TAG,        "[%",                           1, 1));
		expectedTokens.add(new EglToken(TokenType.PLAIN_TEXT,       " for(i in Sequence(1..10) { ", 1, 3));
		expectedTokens.add(new EglToken(TokenType.END_TAG,          "%]",                           1, 31));
		expectedTokens.add(new EglToken(TokenType.NEW_LINE,         NEWLINE,                        1, 33));
		expectedTokens.add(new EglToken(TokenType.PLAIN_TEXT,       "i is ",                        2, 1));
		expectedTokens.add(new EglToken(TokenType.START_OUTPUT_TAG, "[%=",                          2, 6));
		expectedTokens.add(new EglToken(TokenType.PLAIN_TEXT,       "i",                            2, 9));
		expectedTokens.add(new EglToken(TokenType.END_TAG,          "%]",                           2, 10));
		expectedTokens.add(new EglToken(TokenType.NEW_LINE,         NEWLINE,                        2, 12));
		expectedTokens.add(new EglToken(TokenType.START_TAG,        "[%",                           3, 1));
		expectedTokens.add(new EglToken(TokenType.PLAIN_TEXT,       " } ",                          3, 3));
		expectedTokens.add(new EglToken(TokenType.END_TAG,          "%]",                           3, 6));
		expectedTokens.add(new EglToken(TokenType.EOF,              "",                             3, 8));
		
		for (EglToken expected : expectedTokens) {
			final EglToken actual = l.nextToken();
			assertTrue(expected.equals(actual));
		}
	}
	
	
	@Test
	public void testMultipleLinesReader() throws EglRecognitionException, IOException {
		//                      0        1         2         3
		//                      12345678901234567890123456789012
		final String program = "[% for(i in Sequence(1..10) { %]" + NEWLINE +
		                       "i is [%=i%]" + NEWLINE +
		                       "[% } %]";
		final EglLexer l = new EglLexer(new StringReader(program));
		
		List<EglToken> expectedTokens = new LinkedList<>();
		expectedTokens.add(new EglToken(TokenType.START_TAG,        "[%",                           1, 1));
		expectedTokens.add(new EglToken(TokenType.PLAIN_TEXT,       " for(i in Sequence(1..10) { ", 1, 3));
		expectedTokens.add(new EglToken(TokenType.END_TAG,          "%]",                           1, 31));
		expectedTokens.add(new EglToken(TokenType.NEW_LINE,         NEWLINE,                        1, 33));
		expectedTokens.add(new EglToken(TokenType.PLAIN_TEXT,       "i is ",                        2, 1));
		expectedTokens.add(new EglToken(TokenType.START_OUTPUT_TAG, "[%=",                          2, 6));
		expectedTokens.add(new EglToken(TokenType.PLAIN_TEXT,       "i",                            2, 9));
		expectedTokens.add(new EglToken(TokenType.END_TAG,          "%]",                           2, 10));
		expectedTokens.add(new EglToken(TokenType.NEW_LINE,         NEWLINE,                        2, 12));
		expectedTokens.add(new EglToken(TokenType.START_TAG,        "[%",                           3, 1));
		expectedTokens.add(new EglToken(TokenType.PLAIN_TEXT,       " } ",                          3, 3));
		expectedTokens.add(new EglToken(TokenType.END_TAG,          "%]",                           3, 6));
		expectedTokens.add(new EglToken(TokenType.EOF,              "",                             3, 8));
		
		for (EglToken expected : expectedTokens) {
			final EglToken actual = l.nextToken();
			assertTrue(expected.equals(actual));
		}
	}
	
	
	@Test
	public void testMultipleLinesInputStream() throws EglRecognitionException, IOException {
		final EglLexer l = new EglLexer(new FileInputStream(TEST_FILE));
		
		List<EglToken> expectedTokens = new LinkedList<>();
		expectedTokens.add(new EglToken(TokenType.START_TAG,        "[%",                           1, 1));
		expectedTokens.add(new EglToken(TokenType.PLAIN_TEXT,       " for(i in Sequence(1..10) { ", 1, 3));
		expectedTokens.add(new EglToken(TokenType.END_TAG,          "%]",                           1, 31));
		expectedTokens.add(new EglToken(TokenType.NEW_LINE,         NEWLINE,                        1, 33));
		expectedTokens.add(new EglToken(TokenType.PLAIN_TEXT,       "i is ",                        2, 1));
		expectedTokens.add(new EglToken(TokenType.START_OUTPUT_TAG, "[%=",                          2, 6));
		expectedTokens.add(new EglToken(TokenType.PLAIN_TEXT,       "i",                            2, 9));
		expectedTokens.add(new EglToken(TokenType.END_TAG,          "%]",                           2, 10));
		expectedTokens.add(new EglToken(TokenType.NEW_LINE,         NEWLINE,                        2, 12));
		expectedTokens.add(new EglToken(TokenType.START_TAG,        "[%",                           3, 1));
		expectedTokens.add(new EglToken(TokenType.PLAIN_TEXT,       " } ",                          3, 3));
		expectedTokens.add(new EglToken(TokenType.END_TAG,          "%]",                           3, 6));
		expectedTokens.add(new EglToken(TokenType.EOF,              "",                             3, 8));
		
		for (EglToken expected : expectedTokens) {
			final EglToken actual = l.nextToken();
			assertTrue(expected.equals(actual));
		}
	}
	
	
	
	@Test
	public void testMultiLineTaggedText() throws EglRecognitionException, IOException {
		//                               1         2         3         4
		//                      123456789012345678901234567890123456789012345678
		final String program = "[%for (i in Sequence{1..10})" + NEWLINE + 
                               "  var y : String;  %] [%var x : Integer := i+2%]";
		
		final EglLexer l = new EglLexer(program);
		
		List<EglToken> expectedTokens = new LinkedList<>();
		expectedTokens.add(new EglToken(TokenType.START_TAG,        "[%",                         1, 1));
		expectedTokens.add(new EglToken(TokenType.PLAIN_TEXT,       "for (i in Sequence{1..10})", 1, 3));
		expectedTokens.add(new EglToken(TokenType.NEW_LINE,         NEWLINE,                      1, 29));
		expectedTokens.add(new EglToken(TokenType.PLAIN_TEXT,       "  var y : String;  ",        2, 1));
		expectedTokens.add(new EglToken(TokenType.END_TAG,          "%]",                         2, 20));
		expectedTokens.add(new EglToken(TokenType.PLAIN_TEXT,       " ",                          2, 22));
		expectedTokens.add(new EglToken(TokenType.START_TAG,        "[%",                         2, 23));
		expectedTokens.add(new EglToken(TokenType.PLAIN_TEXT,       "var x : Integer := i+2",     2, 25));
		expectedTokens.add(new EglToken(TokenType.END_TAG,          "%]",                         2, 47));
		expectedTokens.add(new EglToken(TokenType.EOF,              "",                           2, 49));
		
		for (EglToken expected : expectedTokens) {
			final EglToken actual = l.nextToken();
			assertTrue(expected.equals(actual));
		}
	}
}
