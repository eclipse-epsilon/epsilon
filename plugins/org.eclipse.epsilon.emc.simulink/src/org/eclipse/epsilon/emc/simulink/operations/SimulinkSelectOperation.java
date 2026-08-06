/*********************************************************************
* Copyright (c) 2018 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.operations;

import java.util.Collection;
import java.util.List;

import org.eclipse.epsilon.emc.simulink.engine.MatlabEngine;
import org.eclipse.epsilon.emc.simulink.model.SimulinkModel;
import org.eclipse.epsilon.emc.simulink.util.collection.AbstractSimulinkCollection;
import org.eclipse.epsilon.emc.simulink.util.collection.SimulinkBlockCollection;
import org.eclipse.epsilon.emc.simulink.util.collection.SimulinkElementCollection;
import org.eclipse.epsilon.emc.simulink.util.collection.SimulinkLineCollection;
import org.eclipse.epsilon.emc.simulink.util.collection.SimulinkPortCollection;
import org.eclipse.epsilon.emc.simulink.util.collection.StateflowBlockCollection;
import org.eclipse.epsilon.eol.dom.AndOperatorExpression;
import org.eclipse.epsilon.eol.dom.BooleanLiteral;
import org.eclipse.epsilon.eol.dom.EqualsOperatorExpression;
import org.eclipse.epsilon.eol.dom.Expression;
import org.eclipse.epsilon.eol.dom.ExpressionInBrackets;
import org.eclipse.epsilon.eol.dom.IntegerLiteral;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.eclipse.epsilon.eol.dom.Parameter;
import org.eclipse.epsilon.eol.dom.PropertyCallExpression;
import org.eclipse.epsilon.eol.dom.RealLiteral;
import org.eclipse.epsilon.eol.dom.StringLiteral;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.operations.declarative.SelectOperation;

public class SimulinkSelectOperation extends SelectOperation {

	protected MatlabEngine engine;
	protected SimulinkModel model;
	protected Boolean lookUnderMasks = true;

	public SimulinkSelectOperation(SimulinkModel model){
		this.model = model;
		this.engine = model.getEngine();
	}

	@Override
	public Collection<?> execute(boolean returnOnMatch, Object target, NameExpression operationNameExpression,
			List<Parameter> iterators, Expression expression, IEolContext context) throws EolRuntimeException {
		AbstractSimulinkCollection<?, ?, ?> targetList;
		if (target instanceof AbstractSimulinkCollection 
				&& ! (target instanceof StateflowBlockCollection || target instanceof SimulinkElementCollection)) {
			try {
				//boolean isNotOperation = false;
				String exp;
				/*if (expression instanceof NotOperatorExpression) {
					isNotOperation = true;
					exp = expression(((NotOperatorExpression) expression).getFirstOperand());
				} else {
					exp = expression(expression);
				}*/
				exp = expression(expression);
				targetList = (AbstractSimulinkCollection<?,?,?>) target;
				List<?> handles = targetList.getPrimitive();
				try{
					String setup = "handles=?;";
					String cmd = String.format("find_system(handles,'SearchDepth',1,%s,%s);",
							model.getSearchPreferences().searchStatement(), 
							exp.substring(0, exp.length()-1));
					Object result = engine.evalWithSetupAndResult(setup, cmd, handles);
					/*if (isNotOperation) {
						 ((AbstractSimulinkCollection<?,?,?>) target).removePrimitive(result);
						return ((AbstractSimulinkCollection<?,?,?>) target);
					}*/
					return wrap(result, targetList);	
				} catch (Exception e) { }
			} catch (Exception e) { }
		}
		return super.execute(returnOnMatch, target, operationNameExpression, iterators, expression, context);
	}
	
	protected AbstractSimulinkCollection<?,?,?> wrap(Object result, AbstractSimulinkCollection<?,?,?> target) {
		SimulinkModel model = (SimulinkModel) target.getManager().getModel();
		if (target instanceof SimulinkBlockCollection) {
			return new SimulinkBlockCollection(result, model);
		} else if (target instanceof SimulinkPortCollection) {
			return new SimulinkPortCollection(result, model);
		} else if (target instanceof SimulinkLineCollection) {
			return new SimulinkPortCollection(result, model);
		} else {
			return new SimulinkElementCollection(result, model);
		}
	}
	
	protected String expression(Expression exp) throws Exception{
		if (exp instanceof AndOperatorExpression) {
			return 	expression(((AndOperatorExpression) exp).getFirstOperand()) 
					+ expression(((AndOperatorExpression) exp).getSecondOperand());
		}
		if (exp instanceof ExpressionInBrackets) {
			return expression(((ExpressionInBrackets) exp).getExpression());
		}
		if (exp instanceof EqualsOperatorExpression) {
			Expression firstOperand = ((EqualsOperatorExpression) exp).getFirstOperand();
			Expression secondOperand = ((EqualsOperatorExpression) exp).getSecondOperand();
			return propertyEqualsOperation(firstOperand, secondOperand);
		}
		throw new Exception("Unsupported selection format");
	}
	
	private String propertyEqualsOperation(Expression firstOperand, Expression secondOperand) throws Exception {
		String property, value; 
		if (firstOperand instanceof PropertyCallExpression) {
			property = ((PropertyCallExpression) firstOperand).getNameExpression().getName();
			value = processValue(secondOperand);
		} else if (secondOperand instanceof PropertyCallExpression){
			property = ((PropertyCallExpression) firstOperand).getNameExpression().getName();
			value = processValue(firstOperand);
		} else {
			throw new Exception("Unsupported format");
		}
		return String.format("'%s',%s,", property, value);
	}
	
	/**
	 * TODO check and handle elements of type simulinkmodel element
	 */
	protected String processValue(Expression operand) throws Exception {
		String value;
		if (operand instanceof StringLiteral) {
			value = String.format("'%s'",((StringLiteral) operand).getValue());
		} else if (operand instanceof IntegerLiteral) {
			value = String.format("'%d'",((IntegerLiteral) operand).getValue());
		} else if (operand instanceof RealLiteral) {
			value = String.format("'%f'",((RealLiteral) operand).getValue());
		} else if (operand instanceof BooleanLiteral) {
			value = String.format("'%s'",((BooleanLiteral) operand).getValue() ? "on" : "off");
		/*} else if (operand instanceof ISimulinkModelElement) {
			value = String.format("%f",((ISimulinkModelElement) operand).getHandle());*/
		} else {
			throw new Exception("Unsupported type");
		}
		return value;
	}
}
