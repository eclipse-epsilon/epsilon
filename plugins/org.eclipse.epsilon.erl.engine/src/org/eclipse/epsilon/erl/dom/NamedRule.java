/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.erl.dom;

import java.util.Objects;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.eol.dom.AnnotatableModuleElement;
import org.eclipse.epsilon.eol.dom.NameExpression;

public abstract class NamedRule extends AnnotatableModuleElement {
	
	protected NameExpression nameExpression;

	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		nameExpression = (NameExpression) module.createAst(getNameAst(cst), this);
	}
	
	protected AST getNameAst(AST cst) {
		return cst.getFirstChild();
	}
	
	public String getName() { 
		if (nameExpression != null) {
			return nameExpression.getName();
		}
		return "";
	}
	
	public NameExpression getNameExpression() {
		return nameExpression;
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
	/**
	 * @since 1.6
	 */
	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), toString());
	}
	
	/**
	 * @since 1.6
	 */
	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (!super.equals(other)) return false;
		
		return Objects.equals(this.toString(), other.toString());
	}
}
