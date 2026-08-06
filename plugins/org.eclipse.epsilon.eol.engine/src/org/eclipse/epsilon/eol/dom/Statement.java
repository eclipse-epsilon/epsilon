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

import org.eclipse.epsilon.common.module.ModuleElement;

public abstract class Statement extends AbstractExecutableModuleElement {
	
	protected StatementBlock toStatementBlock(ModuleElement element) {
		if (element instanceof StatementBlock) {
			return (StatementBlock) element;
		}
		else if (element instanceof Statement) {
			return new StatementBlock((Statement) element);
		}
		else if (element instanceof Expression) {
			ExpressionStatement expressionStatement = new ExpressionStatement((Expression) element);
			expressionStatement.setParent(this);
			return new StatementBlock(expressionStatement);
		}
		else throw new IllegalArgumentException(element + " was expected to be a StatementBlock, Statement or Expression but instead it is " + element);
	}
	
	public abstract void accept(IEolVisitor visitor);
	
}
