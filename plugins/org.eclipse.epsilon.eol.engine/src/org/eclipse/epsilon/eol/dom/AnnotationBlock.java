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

import java.util.ArrayList;
import java.util.List;
import org.eclipse.epsilon.common.module.AbstractModuleElement;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;

public class AnnotationBlock extends AbstractModuleElement {
	
	protected List<Annotation> annotations = new ArrayList<>(2);
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		for (AST ast : cst.getChildren()) {
			annotations.add((Annotation) module.createAst(ast, this));
		}
	}
	
	public List<Annotation> getAnnotations() {
		return annotations;
	}
	
	public void accept(IEolVisitor visitor) {
		visitor.visit(this);
	}
}
