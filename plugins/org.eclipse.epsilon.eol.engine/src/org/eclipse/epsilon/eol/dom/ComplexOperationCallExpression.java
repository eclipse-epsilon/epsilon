/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.dom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.operations.DynamicOperation;
import org.eclipse.epsilon.eol.parse.EolParser;

/**
 * Allows for expressions which mix multiple lambdas and ordinary parameters in any order.
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class ComplexOperationCallExpression extends FeatureCallExpression {

	protected LinkedHashMap<Expression, List<Parameter>> complexParameters = new LinkedHashMap<>(4);
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		
		targetExpression = (Expression) module.createAst(cst.getFirstChild(), this);
		final AST exprAst = cst.getSecondChild();
		nameExpression = (NameExpression) module.createAst(exprAst, this);
		
		for (AST opArg : exprAst.getChildren()) {
			final List<Parameter> lambdaParams;
			final AST bodyAst;
			
			if (opArg.getType() == EolParser.LAMBDAEXPR) {
				final boolean hasParams = opArg.getFirstChild().getType() == EolParser.PARAMLIST;
				if (hasParams) {
					lambdaParams = new ArrayList<>(opArg.getFirstChild().getChildCount());
					for (AST param : opArg.getFirstChild().getChildren()) {
						lambdaParams.add((Parameter) module.createAst(param, this));
					}
				}
				else {
					lambdaParams = Collections.emptyList();
				}
				bodyAst = hasParams ? opArg.getSecondChild() : opArg.getFirstChild();
			}
			else {
				lambdaParams = null;
				bodyAst = opArg;
			}

			complexParameters.put((Expression) module.createAst(bodyAst, this), lambdaParams);
		}
	}

	@Override
	public Object execute(IEolContext context) throws EolRuntimeException {
		Object target = context.getExecutorFactory().execute(targetExpression, context);
		if (target == null && isNullSafe()) {
			return target;
		}
		return new DynamicOperation().execute(target, nameExpression, complexParameters, context);
	}
	
	public LinkedHashMap<Expression, List<Parameter>> getComplexParameters() {
		return complexParameters;
	}

	public void setComplexParameters(LinkedHashMap<Expression, List<Parameter>> complexParameters) {
		this.complexParameters = complexParameters;
	}
	
	public void accept(IEolVisitor visitor) {
		visitor.visit(this);
	}
}
