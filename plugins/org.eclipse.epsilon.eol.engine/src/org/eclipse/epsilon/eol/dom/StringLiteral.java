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

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;

public class StringLiteral extends LiteralExpression<String> {
	
	protected boolean doubleQuoted = false;
	
	public StringLiteral() {
		super();
	}
	
	public StringLiteral(String value) {
		super(value);
	}
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		String text = cst.getText();
		doubleQuoted = "\"".equals(text.substring(0, 1));
		text = text.substring(1, text.length() - 1);
		value = unescape(text);
	}
	
	public String unescape(String str) {
		if (str == null) {
			return null;
		}
		int size = str.length();
		StringBuffer out = new StringBuffer();
		boolean slash = false;

		for (int i = 0; i < size; i++) {
			char ch = str.charAt(i);
			if (slash) {
				slash = false;
				switch (ch) {
				case '\\':
					out.append('\\');
					break;
				case '\'':
					out.append('\'');
					break;
				case '\"':
					out.append('"');
					break;
				case 'r':
					out.append('\r');
					break;
				case 'f':
					out.append('\f');
					break;
				case 't':
					out.append('\t');
					break;
				case 'n':
					out.append('\n');
					break;
				case 'b':
					out.append('\b');
					break;
				default:
					out.append(ch);
					break;
				}
				continue;
			} else if (ch == '\\') {
				slash = true;
				continue;
			}
			out.append(ch);
		}
		if (slash) {
			out.append('\\');
		}

		return out.toString();

	}
	
	public void accept(IEolVisitor visitor) {
		visitor.visit(this);
	}
	
	public boolean isDoubleQuoted() {
		return doubleQuoted;
	}
	
	public void setDoubleQuoted(boolean doubleQuoted) {
		this.doubleQuoted = doubleQuoted;
	}
}
