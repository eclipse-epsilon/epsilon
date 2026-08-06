/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.common.module;

import org.antlr.runtime.Token;

public class Comment extends AbstractModuleElement {
	
	protected boolean multiline;
	protected String text;
	
	public Comment(Token token) {
		text = token.getText();
		multiline = text.startsWith("/*") || text.startsWith("-*");
		if (multiline) {
			text = text.substring(2, text.length() - 2);
		}
		else {
			text = text.substring(2);
		}
	}
	
	@Override
	public String toString() {
		return text;
	}
	
	public String getText() {
		return text;
	}
	
	public boolean isMultiline() {
		return multiline;
	}
}
