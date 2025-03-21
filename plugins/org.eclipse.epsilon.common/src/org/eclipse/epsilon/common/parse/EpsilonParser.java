/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.parse;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import org.antlr.runtime.Parser;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.TreeAdaptor;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.common.util.ReflectionUtil;

public abstract class EpsilonParser extends Parser {
	
	private boolean printErrors = false;
	protected List<ParseProblem> parseProblems = new ArrayList<ParseProblem>();
	protected static WeakHashMap<TokenStream, WeakReference<EpsilonParser>> tokenStreamParsers = new WeakHashMap<TokenStream, WeakReference<EpsilonParser>>();
	protected EpsilonParser delegator = null;
	
	public EpsilonParser(TokenStream tokenstream) {
		super(tokenstream);
		configureDelegator(tokenstream);
	}

	public EpsilonParser(TokenStream tokenstream, RecognizerSharedState recognizersharedstate) {
		super(tokenstream, recognizersharedstate);
		configureDelegator(tokenstream);
	}
	
	protected void configureDelegator(TokenStream tokenStream) {
		// A parser can spawn several delegates on the same token stream
		// The first parser to process a token stream is recorded in tokenStreamParsers 
		// so that all delegates can report parse problems to it (their delegator)
		
		if (!tokenStreamParsers.containsKey(tokenStream)) {
			tokenStreamParsers.put(tokenStream, new WeakReference<EpsilonParser>(this));
			delegator = this;
		}
		else {
			WeakReference<EpsilonParser> ref = tokenStreamParsers.get(tokenStream);
			EpsilonParser parser = ref.get();
			if (parser != null) {
				delegator = parser;
			}
			else {
				delegator = this;
			}
		}
	}
	
	public abstract TreeAdaptor getTreeAdaptor();
	
	public abstract void setTreeAdaptor(TreeAdaptor adaptor);
	
	protected boolean isSupertype(Class<?> parent, Class<?> child) {
		if (parent == child) {
			return true;
		}
		else if (child.getSuperclass() != null) {
			return isSupertype(parent, child.getSuperclass());
		}
		else {
			return false;
		}
	}
	
	public void setDeepTreeAdaptor(TreeAdaptor adaptor) {
		
		setTreeAdaptor(adaptor);
		
		for (Field f : this.getClass().getFields()) {
			try {
				if (isSupertype(EpsilonParser.class, f.getType())) {
					Object value = ReflectionUtil.getFieldValue(this, f.getName());
					EpsilonParser delegate = (EpsilonParser) value;
					if (delegate.getTreeAdaptor() != adaptor) {
						delegate.setDeepTreeAdaptor(adaptor);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void prepareForGUnit() {
		printErrors = true;
	}
	
	@Override
	public void displayRecognitionError(String[] tokenNames, RecognitionException re) {
		reportException(re.line, re.charPositionInLine, getErrorMessage(re, getTokenNames()));
		
		if (printErrors) {
			super.displayRecognitionError(tokenNames, re);
		}
	}
	
	public void reportException(int line, int column, String reason) {
		// Filter out duplicate problems
		if (!delegator.getParseProblems().stream().anyMatch(
				problem -> problem.getColumn() == column && 
				problem.getLine() == line && 
				problem.getReason().equals(reason))) {
			
			ParseProblem problem = new ParseProblem();
			problem.setLine(line);
			problem.setColumn(column);
			problem.setReason(reason);
			delegator.getParseProblems().add(problem);
		}
	}
	
	public List<ParseProblem> getParseProblems() {
		return parseProblems;
	}
}
