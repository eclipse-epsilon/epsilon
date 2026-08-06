/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.dom;

import org.eclipse.epsilon.common.module.AbstractModuleElement;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.types.EolAnyType;
import org.eclipse.epsilon.eol.types.EolType;

public class Parameter extends AbstractModuleElement {
	
	protected NameExpression nameExpression;
	protected TypeExpression typeExpression;
	protected EolType type;
	
	public Parameter() {}
	
	public Parameter(NameExpression nameExpression) {
		this.nameExpression = nameExpression;
	}
	
	public Parameter(NameExpression nameExpression, TypeExpression typeExpression) {
		this(nameExpression);
		this.typeExpression = typeExpression;
	}
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		this.nameExpression = (NameExpression) module.createAst(cst.getFirstChild(), this);
		this.typeExpression = (TypeExpression) module.createAst(cst.getSecondChild(), this);
	}

	public TypeExpression getTypeExpression() {
		return typeExpression;
	}
	
	public void setTypeExpression(TypeExpression typeExpression) {
		this.typeExpression = typeExpression;
	}
	
	public NameExpression getNameExpression() {
		return nameExpression;
	}
	
	public void setNameExpression(NameExpression nameExpression) {
		this.nameExpression = nameExpression;
	}
	
	public String getName() {
		return nameExpression.getName();
	}

	public String getTypeName() {
		if (typeExpression == null) {
			return "Any";
		}
		else {
			return typeExpression.getName();
		}
	}
	
	@Override
	public String toString() {
		return getName() + " : " + getTypeName();
	}
	
	public EolType getType(IEolContext context) throws EolRuntimeException {
		if (type == null) {
			if (typeExpression != null) {
				type = (EolType) context.getExecutorFactory().execute(typeExpression,context);
			}
			else {
				type = EolAnyType.Instance;
			}
		}
		return type;
	}

	/**
	 * Clear any cached information. This is useful for running EUnit, which has to rerun
	 * the same EOL script with different sets of models without reparsing it.
	 */
	public void clearCache() {
		type = null;
	}
	
	public boolean isExplicitlyTyped() {
		return typeExpression != null;
	}
	
	public void accept(IEolVisitor visitor) {
		visitor.visit(this);
	}
}
