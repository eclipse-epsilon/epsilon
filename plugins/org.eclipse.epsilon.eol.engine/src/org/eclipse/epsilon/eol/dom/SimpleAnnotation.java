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
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;

public class SimpleAnnotation extends Annotation {
	
	protected String value;
	
	public SimpleAnnotation() {}
	
	public SimpleAnnotation(String value) {
		setValue(value);
	}
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		String text = cst.getText().substring(1).trim();
		if (text.indexOf(" ") > -1) {
			String[] parts = text.split(" ");
			name = parts[0];
			value = parts[1];
		}
		else {
			name = text;
		}
	}
		
	@Override
	public boolean hasValue() {
		return getValue() != null;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String getValue(IEolContext context) throws EolRuntimeException {
		return getValue();
	}
	
	public void accept(IEolVisitor visitor) {
		visitor.visit(this);
	}
}
