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
 * $Id: TestHutnLexer.java,v 1.3 2008/08/08 14:58:03 louis Exp $
 */
package org.eclipse.epsilon.hutn.parse;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.junit.Test;

public class TestHutnLexer {

	private class ErrorRecordingHutnLexer extends HutnLexer {

		private final List<RecognitionException> problems = new LinkedList<>();
		
		public ErrorRecordingHutnLexer(CharStream input) {
			super(input);
		}
		
		@Override
		public void reportError(RecognitionException e) {
			problems.add(e);
			super.reportError(e);
		}
		
		public List<RecognitionException> getRecognitionProblems() {
			return Collections.unmodifiableList(problems);
		}
	}


	private void validNumberTest(String number) throws IOException {
		final ErrorRecordingHutnLexer lexer = new ErrorRecordingHutnLexer(new ANTLRReaderStream(new StringReader(number)));
		
		assertEquals(0, lexer.getRecognitionProblems().size());
		
		final Token actual = lexer.nextToken();
		
		assertEquals(HutnParser.NUMERIC_VALUE, actual.getType());
		assertEquals(number, actual.getText());
	}
	
	private void invalidNumberTest(String number) throws IOException {
		final ErrorRecordingHutnLexer lexer = new ErrorRecordingHutnLexer(new ANTLRReaderStream(new StringReader(number)));
		lexer.nextToken();
		
		assertEquals(1, lexer.getRecognitionProblems().size());
	}
	
	@Test
	public void testSingleDigit() throws IOException {
		validNumberTest("1");
	}
	
	@Test
	public void testNegativeNumber() throws IOException {
		validNumberTest("-1");
	}
	
	@Test
	public void testExplicitPositiveNumber() throws IOException {
		validNumberTest("+4");
	}
	
	@Test
	public void testDecimalNumber() throws IOException {
		validNumberTest("-15.56739");
	}
	
	@Test
	public void testNoDecimalDigits() throws IOException {
		invalidNumberTest("1.");
	}

	@Test
	public void testScientificNotation() throws IOException {
		validNumberTest("-15.56739e5");
	}
}
