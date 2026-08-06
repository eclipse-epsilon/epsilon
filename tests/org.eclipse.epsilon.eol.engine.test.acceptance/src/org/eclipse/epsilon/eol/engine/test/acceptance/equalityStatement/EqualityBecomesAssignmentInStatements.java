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
package org.eclipse.epsilon.eol.engine.test.acceptance.equalityStatement;

import static org.junit.Assert.assertEquals;

import org.eclipse.epsilon.eol.EolEvaluator;
import org.junit.Test;

public class EqualityBecomesAssignmentInStatements {

	private static final EolEvaluator evaluator = new EolEvaluator();
	
	@Test
	public void assignment() {
		evaluator.execute("var a; a := 1 + 2;");
		assertEquals(3, evaluator.evaluate("a"));	
	}
	
	@Test
	public void assignmentToResultOfEquality() {
		evaluator.execute("var a; a := 1 = 1;");
		assertEquals(true, evaluator.evaluate("a"));	
	}
	
	@Test
	public void equalityActsAsAssignment() {
		evaluator.execute("var a; a = 1 + 2;");
		assertEquals(3, evaluator.evaluate("a"));	
	}
	
	@Test
	public void outerEqualityActsAsAssignment() {
		evaluator.execute("var a; a = 1 = 1;");
		assertEquals(true, evaluator.evaluate("a"));	
	}
	
	@Test
	public void otherRelationalExpressionIsUnaffected() {
		evaluator.execute("var a; a := 2 > 1;");
		assertEquals(true, evaluator.evaluate("a"));	
	}
	
	@Test
	public void otherRelationalExpressionIsUnaffectedByRewrite() {
		evaluator.execute("var a; a = 2 > 1;");
		assertEquals(true, evaluator.evaluate("a"));	
	}
}
