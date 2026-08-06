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


public class RealLiteral extends LiteralExpression<Number> {
	
	protected boolean doublePrecision = false;
	private String text = null;
	
	public RealLiteral() {}
	
	public RealLiteral(Number value) {
		setValue(value);
	}
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		setText(cst.getText());
	}
	
	public void setText(String text) {
		this.text = text;
		
		if (text.endsWith("f")) {
			text = text.substring(0, text.length() - 1);
			doublePrecision = false;
		}
		else if (text.endsWith("d")) {
			text = text.substring(0, text.length() - 1);
			doublePrecision = true;		
		}
		else {
			doublePrecision = false;			
		}
		
		if (doublePrecision) {
			value = Double.valueOf(text);
		}
		else {
			value = Float.valueOf(text);
		}
	}
	
	@Override
	public void setValue(Number value) {
		super.setValue(value);
		if (value instanceof Double) this.text = value + "d";
		else this.text = value + "";
	}
	
	public String getText() {
		return text;
	}
	
	public boolean isDoublePrecision() {
		return doublePrecision;
	}
	
	public void accept(IEolVisitor visitor) {
		visitor.visit(this);
	}
	
	
}
