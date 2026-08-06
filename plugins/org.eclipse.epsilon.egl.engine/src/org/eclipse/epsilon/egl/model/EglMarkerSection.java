/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.egl.model;

import org.eclipse.epsilon.common.module.AbstractModuleElement;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;

public class EglMarkerSection extends AbstractModuleElement {
	
	protected String text;
	
	@Override
	public void build(AST cst, IModule module) {
		text = cst.getFirstChild().getText();
	}
	
	@Override
	public String toString() {
		return text;
	}
	
}
