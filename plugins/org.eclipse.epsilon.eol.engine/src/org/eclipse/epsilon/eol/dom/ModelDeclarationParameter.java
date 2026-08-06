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

import org.eclipse.epsilon.common.module.AbstractModuleElement;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;

public class ModelDeclarationParameter extends AbstractModuleElement {
	
	protected String key;
	protected String value;
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		this.key = cst.getFirstChild().getText();
		this.value = cst.getSecondChild().getText();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public void accept(IEolVisitor visitor) {
		visitor.visit(this);
	}
	
}
