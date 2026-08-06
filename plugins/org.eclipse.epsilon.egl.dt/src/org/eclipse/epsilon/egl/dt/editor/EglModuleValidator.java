/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.egl.dt.editor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.eclipse.epsilon.common.dt.editor.AbstractModuleValidator;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.module.ModuleMarker;
import org.eclipse.epsilon.common.module.ModuleMarker.Severity;
import org.eclipse.epsilon.egl.IEglModule;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.dom.Expression;
import org.eclipse.epsilon.eol.dom.ExpressionStatement;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.eclipse.epsilon.eol.dom.OperationCallExpression;
import org.eclipse.epsilon.eol.dom.Statement;
import org.eclipse.epsilon.eol.dom.StringLiteral;

public class EglModuleValidator extends AbstractModuleValidator {
	
	@Override
	public List<ModuleMarker> validate(IModule module) {
		if (!appliesTo(module)) return Collections.emptyList();
		
		ArrayList<ModuleMarker> markers = new ArrayList<>();
		IEolModule eglModule = ((IEglModule) module).getCurrentTemplate().getModule();
		
		if (!eglModule.getDeclaredOperations().isEmpty()) {
			
			boolean looseStatements = false;
			
			// Fix for bug #393988
			for (Statement statement : eglModule.getPostOperationStatements()) {
				if (!isEmptyPrintStatement(statement)) {
					looseStatements = true; break;
				}
			}
			
			if (looseStatements) {
				markers.add(new ModuleMarker(null, eglModule.getOperations().get(0).getRegion(), 
						"All loose statements and textual content after the first operation will be ignored at runtime.", 
						Severity.Warning));
			}
			
		}
		
		return markers;
		
	}
	
	protected boolean appliesTo(IModule module) {
		return module instanceof IEglModule;
	}
	
	protected boolean isEmptyPrintStatement(ModuleElement ast) {
		// We're looking for out.prinx("<whitespace only here>") 
		if (ast instanceof ExpressionStatement) {
			Expression expression = ((ExpressionStatement) ast).getExpression();
			if (expression instanceof OperationCallExpression) {
				OperationCallExpression operationCallExpression = (OperationCallExpression) expression;
				if (operationCallExpression.getTargetExpression() instanceof NameExpression) {
					NameExpression nameExpression = (NameExpression) operationCallExpression.getTargetExpression();
					if (operationCallExpression.getParameterExpressions().size() == 1 && operationCallExpression.getParameterExpressions().get(0) instanceof StringLiteral) {
						String parameterValue = ((StringLiteral) operationCallExpression.getParameterExpressions().get(0)).getValue();
						return "out".equals(nameExpression.getName()) && "prinx".equals(operationCallExpression.getName())
								&& (parameterValue.trim().equals("\\n") || parameterValue.trim().isEmpty());
					}
				}
			}
		}
		
		return false;
	}

}
