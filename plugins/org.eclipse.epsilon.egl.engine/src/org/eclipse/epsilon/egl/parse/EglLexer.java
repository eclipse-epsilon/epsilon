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

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import org.eclipse.epsilon.egl.parse.EglToken.TokenType;

public class EglLexer {

	private String program;
	private int col = 1;
	private int line = 1;	
	private int index = 0;
	
	public EglLexer(InputStream is) throws IOException {
		final StringBuilder program = new StringBuilder();
		int next = is.read();
		
		while (next != -1) {
			program.append(((char)next));
			
			next = is.read();
		}
		
		this.program = program.toString();
	}
	
	public EglLexer(Reader reader) throws IOException {
		final StringBuilder program = new StringBuilder();
		
		for (int next = reader.read(); next != -1; next = reader.read()) {
			program.append(((char)next));
		}
		
		this.program = program.toString();
	}
	
	public EglLexer(String program) {
		if (program == null)
			throw new NullPointerException();
		
		this.program = program;
	}
	
	public EglToken nextToken() throws EglRecognitionException {
		if (program.isEmpty()) {
			return tokenise(TokenType.EOF, "");
		}
		else if (program.startsWith("\r\n")) {
			return tokenise(TokenType.NEW_LINE, "\r\n");
		}
		else if (program.startsWith("\n")) {
			return tokenise(TokenType.NEW_LINE, "\n");
		}
		else if (program.startsWith("[*-")) {
			return tokenise(TokenType.START_MARKER_TAG, "[*-");
		}
		else if (program.startsWith("[*")) {
			return tokenise(TokenType.START_COMMENT_TAG, "[*");
		}
		else if (program.startsWith("*]")) {
			return tokenise(TokenType.END_COMMENT_TAG, "*]");
		}
		else if (program.startsWith("[%=")) {
			return tokenise(TokenType.START_OUTPUT_TAG, "[%=");
		}
		else if (program.startsWith("[%")) {
			return tokenise(TokenType.START_TAG, "[%");
		}
		else if (program.startsWith("-%]")) {
			return tokenise(TokenType.END_OUTDENT_TAG, "-%]");
		}
		else if (program.startsWith("%]")) {
			return tokenise(TokenType.END_TAG, "%]");
		}
		else {
			for (index = 0; index < program.length(); index++) {
				// Check if any other token is next
				if (programMatches("\n") || programMatches("\r\n") ||
					programMatches("[%") || programMatches("%]") || programMatches("-%]")   ||
					programMatches("[*") || programMatches("*]")) {
					
					return tokenise(TokenType.PLAIN_TEXT, program.substring(0, index));
				}
			}
			
			return tokenise(TokenType.PLAIN_TEXT, program);
		}
	}
	
	private static String unescape(String text) {
		String result = new String(text);
		
		result = result.replaceAll("\\\\\\[%", "\\[%");
		result = result.replaceAll("\\\\%\\]", "%\\]");
		
		// Replace all double slashes with single slashes
		// Java's Pattern class makes this messy as it uses
		// backslash as an escape character. Coupling this
		// with Java already using backslash as an escape
		// sequence, results in 4 slashes equating to 1 slash!
		//result = result.replaceAll("\\\\\\\\", "\\\\");
		
		return result;
	}
	
	private void newLine() {
		line++;
		col = 1;
	}
	
	private EglToken tokenise(TokenType type, String text) {
		EglToken t;
		
		if (type == TokenType.PLAIN_TEXT)
			t = new EglToken(type, unescape(text), line, col);
		else
			t = new EglToken(type, text, line, col);
		
		program = program.substring(text.length());
		col += text.length();
		
		if (type==TokenType.NEW_LINE) newLine();
		
		return t;
	}
	
	
	private boolean programMatches(String toMatch) {
		// Use the field called index by default
		return programMatches(toMatch, index);
	} 
	
	private boolean programMatches(String toMatch, int index) {
		if (index < 0)
			throw new IllegalStateException("index is less than zero");
		
		if (index + toMatch.length() > program.length())
			return false;
		
		return program.substring(index, index + toMatch.length()).equals(toMatch);
	}
}
