package org.eclipse.epsilon.common.parse;

import org.antlr.runtime.CharStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;

// Custom superclass for Epsilon's lexers 
// See https://www.antlr3.org/pipermail/antlr-interest/2007-September/023602.html
public abstract class Lexer extends org.antlr.runtime.Lexer {

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
		EpsilonParseProblemManager.INSTANCE.reportException(e.line, e.charPositionInLine, getErrorMessage(e, getTokenNames()));
	}
	
}
