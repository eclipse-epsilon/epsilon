package org.eclipse.epsilon.common.parse;

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.CharStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;

// Custom superclass for Epsilon's lexers 
// See https://www.antlr3.org/pipermail/antlr-interest/2007-September/023602.html
public abstract class Lexer extends org.antlr.runtime.Lexer {
	
	List<ParseProblem> parseProblems = new ArrayList<>();
	
	public Lexer() {
		super();
	}

	public Lexer(CharStream input, RecognizerSharedState state) {
		super(input, state);
	}

	public Lexer(CharStream input) {
		super(input);
	}
	
	public void displayRecognitionError(String[] tokenNames, RecognitionException e) {
		reportException(e.line, e.charPositionInLine, getErrorMessage(e, getTokenNames()));
	}
	
	public void reportException(int line, int column, String reason) {
		ParseProblem problem = new ParseProblem();
		problem.setLine(line);
		problem.setColumn(column);
		problem.setReason(reason);
		parseProblems.add(problem);
	}
	
	public List<ParseProblem> getParseProblems() {
		return parseProblems;
	}
}
