/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.epl.dom;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.epsilon.common.module.AbstractModuleElement;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.util.AstUtil;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.eclipse.epsilon.eol.dom.TypeExpression;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.types.EolModelElementType;
import org.eclipse.epsilon.eol.types.EolType;
import org.eclipse.epsilon.epl.parse.EplParser;

public class Role extends AbstractModuleElement {
	
	protected List<String> names;
	protected TypeExpression typeExpression;
	protected Domain domain;
	protected EolType type;
	protected ExecutableBlock<Boolean> guard, optionalAst, activeAst;
	protected Cardinality cardinality = new Cardinality(1, 1);
	protected boolean negative;
	protected Boolean isActiveCache;
	
	@SuppressWarnings("unchecked")
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		
		List<AST> nameAsts = AstUtil.getChildren(cst, EplParser.NAME);
		names = new ArrayList<>(nameAsts.size());
		for (AST nameAst : nameAsts) {
			names.add(nameAst.getText());
		}
		
		typeExpression = (TypeExpression) module.createAst(AstUtil.getChild(cst, EplParser.TYPE), this);
		
		AST domainAst = AstUtil.getChild(cst, EplParser.DOMAIN);
		if (domainAst != null) {
			domain = (Domain) module.createAst(domainAst, this);
			domain.setRole(this);
		}
		
		AST guardAst = AstUtil.getChild(cst, EplParser.GUARD);
		if (guardAst != null) {
			guard = (ExecutableBlock<Boolean>) module.createAst(guardAst, this);
		}
		
		negative = AstUtil.getChild(cst, EplParser.NO) != null;
		
		AST cardinalityAst = AstUtil.getChild(cst, EplParser.CARDINALITY);
		if (cardinalityAst != null) {
			cardinality = (Cardinality) module.createAst(cardinalityAst, this);
		}
		
		AST optionalAst = AstUtil.getChild(cst, EplParser.OPTIONAL);
		if (optionalAst != null) {
			this.optionalAst = (ExecutableBlock<Boolean>) module.createAst(optionalAst, this);
		}
		
		AST activeAst = AstUtil.getChild(cst, EplParser.ACTIVE);
		if (activeAst != null) {
			this.activeAst = (ExecutableBlock<Boolean>) module.createAst(activeAst, this);
		}
		else isActiveCache = true;
	}
	
	public Cardinality getCardinality() {
		return cardinality;
	}
	
	public boolean hasActiveAst() {
		return activeAst != null;
	}
	
	public boolean isActive(IEolContext context) throws EolRuntimeException {
		return isActive(context, false);
	}
	
	public boolean isActive(IEolContext context, boolean forceRecompute) throws EolRuntimeException {
		if (hasActiveAst() && (forceRecompute || isActiveCache == null)) {
			isActiveCache = (boolean) context.getExecutorFactory().execute(activeAst, context);
		}
		return isActiveCache;
	}
	
	public boolean isOptional(IEolContext context) throws EolRuntimeException {
		return optionalAst != null && (boolean) context.getExecutorFactory().execute(optionalAst, context);
	}
	
	public boolean isNegative() {
		return negative;
	}
	
	public List<String> getNames() {
		return names;
	}
	
	public Domain getDomain() {
		return domain;
	}
	
	public ExecutableBlock<Boolean> getGuard() {
		return guard;
	}
	
	public EolType getType(IEolContext context) throws EolRuntimeException {
		if (type == null) {
			type = (EolType) context.getExecutorFactory().execute(typeExpression, context);
			if (!(type instanceof EolModelElementType))
				throw new EolRuntimeException(type.getName() + " is not a model element type", typeExpression);
		}
		return type;
	}
}
