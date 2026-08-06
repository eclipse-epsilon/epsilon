/*********************************************************************
 * Copyright (c) 2008 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.operations;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.epsilon.emc.simulink.engine.MatlabEngine;
import org.eclipse.epsilon.emc.simulink.util.StateflowUtil;
import org.eclipse.epsilon.emc.simulink.util.collection.StateflowBlockCollection;
import org.eclipse.epsilon.eol.dom.Expression;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.eclipse.epsilon.eol.dom.Parameter;
import org.eclipse.epsilon.eol.dom.PropertyCallExpression;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.operations.declarative.CollectOperation;

public class StateflowCollectOperation extends CollectOperation {

	protected MatlabEngine engine;

	public StateflowCollectOperation(MatlabEngine engine){
		this.engine = engine;
	}
	@Override
	public Collection<?> execute(Object target, NameExpression operationNameExpression, List<Parameter> iterators,
			List<Expression> expressions, IEolContext context) throws EolRuntimeException {
		
		StateflowBlockCollection targetList;
		if (target instanceof StateflowBlockCollection) {			
			targetList = (StateflowBlockCollection) target;
			
			List<?> handles = targetList.getPrimitive();
			String cellArray = handles.stream()
				.map(e -> ((Double)e).intValue() + "")
				.collect(Collectors.joining(";","{","}"));
			
			if (areExpressionsValid(expressions)) {
				PropertyCallExpression expression = (PropertyCallExpression) expressions.get(0);
				try{
					StateflowUtil.modelHandleAsM(targetList.getManager().getModel());
					String setup = "handles=arrayfun(@(a) m.find('Id',a),cell2mat(?), 'UniformOutput', false);";
					String eval = "get(cell2mat(handles),'?');";
					Object result= engine.evalWithSetupAndResult(setup, eval, cellArray, expression.getName());
					if (result instanceof Collection) {						
						return (Collection<?>) result;
					} else {
						return Arrays.asList(result);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}		
		return super.execute(target, operationNameExpression, iterators, expressions, context);
	}
	
	protected boolean areExpressionsValid(List<Expression> expressions) {
		return expressions.size() == 1 && expressions.get(0) instanceof PropertyCallExpression;
	}
}
