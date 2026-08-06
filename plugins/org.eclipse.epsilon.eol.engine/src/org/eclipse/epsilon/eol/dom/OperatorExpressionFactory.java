/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.dom;

import org.eclipse.epsilon.common.parse.AST;

public class OperatorExpressionFactory {
	
	public OperatorExpression createOperatorExpression(AST ast) {
		String operator = ast.getText();
		switch (operator) {
			case "=":
				return new EqualsOperatorExpression();
			case "==":
				return new DoubleEqualsOperatorExpression();
			case "<>": case "!=":
				return new NotEqualsOperatorExpression();
			case "+":
				return new PlusOperatorExpression();
			case "-":
				return ast.getChildCount() == 2 ?
					new MinusOperatorExpression() : new NegativeOperatorExpression();
			case "*":
				return new TimesOperatorExpression();
			case "/":
				return new DivOperatorExpression();
			case "<":
				return new LessThanOperatorExpression();
			case ">":
				return new GreaterThanOperatorExpression();
			case "<=":
				return new LessEqualOperatorExpression();
			case ">=":
				return new GreaterEqualOperatorExpression();
			case "and":
				return new AndOperatorExpression();
			case "or":
				return new OrOperatorExpression();
			case "not":
				return new NotOperatorExpression();
			case "xor":
				return new XorOperatorExpression();
			case "implies":
				return new ImpliesOperatorExpression();
			case "++":
				return new PostfixOperatorExpression(true);
			case "--":
				return new PostfixOperatorExpression(false);
			case "?:":
				return new ElvisOperatorExpression();
			default:
				throw new IllegalStateException("Unknown operator: " + operator);
		}
	}
	
}
