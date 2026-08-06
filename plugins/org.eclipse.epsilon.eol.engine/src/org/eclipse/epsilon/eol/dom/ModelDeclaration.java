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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.epsilon.common.module.AbstractModuleElement;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.util.AstUtil;
import org.eclipse.epsilon.eol.m3.Metamodel;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.parse.EolParser;

public class ModelDeclaration extends AbstractModuleElement {

	protected NameExpression nameExpression;
	protected List<NameExpression> aliasNameExpressions = new ArrayList<>();
	protected NameExpression driverNameExpression;
	protected List<ModelDeclarationParameter> modelDeclarationParameters = new ArrayList<>();
	protected Metamodel metamodel = null;
	protected IModel model;
	
	public ModelDeclaration() {}
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		nameExpression = (NameExpression) module.createAst(cst.getFirstChild(), this);
		driverNameExpression = (NameExpression) module.createAst(AstUtil.getChild(cst, EolParser.DRIVER).getFirstChild(), this);
		AST aliasesAst = AstUtil.getChild(cst, EolParser.ALIAS);
		if (aliasesAst != null) {
			for (AST aliasAst : aliasesAst.getChildren()) {
				aliasNameExpressions.add((NameExpression) module.createAst(aliasAst, this));
			}
		}
		AST parametersAst = AstUtil.getChild(cst, EolParser.MODELDECLARATIONPARAMETERS);
		if (parametersAst != null) {
			for (AST parameterAst : parametersAst.getChildren()) {
				modelDeclarationParameters.add((ModelDeclarationParameter) module.createAst(parameterAst, this));
			}
		}
	}

	public NameExpression getNameExpression() {
		return nameExpression;
	}

	public void setNameExpression(NameExpression nameExpression) {
		this.nameExpression = nameExpression;
	}
	
	public NameExpression getDriverNameExpression() {
		return driverNameExpression;
	}

	public void setDriverNameExpression(NameExpression driverNameExpression) {
		this.driverNameExpression = driverNameExpression;
	}

	public List<NameExpression> getAliasNameExpressions() {
		return aliasNameExpressions;
	}

	public List<ModelDeclarationParameter> getModelDeclarationParameters() {
		return modelDeclarationParameters;
	}
	
	public ModelDeclarationParameter getModelDeclarationParameter(String parameterName) {
		for (ModelDeclarationParameter parameter : modelDeclarationParameters) {
			if (parameter.getKey().equals(parameterName)) return parameter;
		}
		return null;
	}
	
	public List<?> getModuleElements() {
		return Collections.emptyList();
	}
	
	public Metamodel getMetamodel() {
		return metamodel;
	}
	
	public void setMetamodel(Metamodel metamodel) {
		 this.metamodel = metamodel;
	}
	
	public IModel getModel() {
		return model;
	}
	
	public void setModel(IModel model) {
		this.model = model;
	}
	
	public void accept(IEolVisitor visitor) {
		visitor.visit(this);
	}
}
