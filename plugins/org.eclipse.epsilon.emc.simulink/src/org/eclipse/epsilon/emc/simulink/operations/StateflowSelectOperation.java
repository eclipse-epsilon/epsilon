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

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.epsilon.emc.simulink.engine.MatlabEngine;
import org.eclipse.epsilon.emc.simulink.model.SimulinkModel;
import org.eclipse.epsilon.emc.simulink.util.StateflowUtil;
import org.eclipse.epsilon.emc.simulink.util.collection.StateflowBlockCollection;
import org.eclipse.epsilon.eol.dom.AndOperatorExpression;
import org.eclipse.epsilon.eol.dom.BooleanLiteral;
import org.eclipse.epsilon.eol.dom.EqualsOperatorExpression;
import org.eclipse.epsilon.eol.dom.Expression;
import org.eclipse.epsilon.eol.dom.ExpressionInBrackets;
import org.eclipse.epsilon.eol.dom.IntegerLiteral;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.eclipse.epsilon.eol.dom.NotEqualsOperatorExpression;
import org.eclipse.epsilon.eol.dom.NotOperatorExpression;
import org.eclipse.epsilon.eol.dom.OperationCallExpression;
import org.eclipse.epsilon.eol.dom.OrOperatorExpression;
import org.eclipse.epsilon.eol.dom.Parameter;
import org.eclipse.epsilon.eol.dom.PropertyCallExpression;
import org.eclipse.epsilon.eol.dom.RealLiteral;
import org.eclipse.epsilon.eol.dom.StringLiteral;
import org.eclipse.epsilon.eol.dom.XorOperatorExpression;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.operations.declarative.SelectOperation;

public class StateflowSelectOperation extends SelectOperation {

	protected MatlabEngine engine;

	public StateflowSelectOperation(MatlabEngine engine){
		this.engine = engine;
	}
	
	@Override
	public Collection<?> execute(boolean returnOnMatch, Object target, NameExpression operationNameExpression,
			List<Parameter> iterators, Expression expression, IEolContext context) throws EolRuntimeException {
		StateflowBlockCollection targetList;
		if (target instanceof StateflowBlockCollection) {
			try {
				String exp = expression(expression);
				
				targetList = (StateflowBlockCollection) target;
				List<?> handles = targetList.getPrimitive();
				String cellArray = handles.stream()
					.map( e -> ((Double)e).intValue() + "")
					.collect(Collectors.joining(";","{","}"));
				
				SimulinkModel model = targetList.getManager().getModel(); 
				try {
					StateflowUtil.modelHandleAsM(model);
					String setup = "handles=cell2mat(arrayfun(@(a) m.find('Id',a),cell2mat(?), 'UniformOutput', false));";
					engine.eval(setup, cellArray);
					String cmd = String.format("get(handles.find(%s'-depth',0),'Id');", exp);
					Object result = engine.evalWithResult(cmd);
					if (result instanceof Collection) {
						result = engine.evalWithResult("cell2mat(result);");
					}
					return new StateflowBlockCollection(result, model);
			
				} catch (Exception e) {}
			} catch (Exception e) {}
		}
		return super.execute(returnOnMatch, target, operationNameExpression, iterators, expression, context);
	}
	
	
	protected String expression(Expression exp) throws Exception{
		if (exp instanceof AndOperatorExpression) {
			return 	expression(((AndOperatorExpression) exp).getFirstOperand()) 
					+ "'-and'," 
					+ expression(((AndOperatorExpression) exp).getSecondOperand());
		}
		if (exp instanceof OrOperatorExpression) {
			return 	expression(((OrOperatorExpression) exp).getFirstOperand()) 
					+ "'-or'," 
					+ expression(((OrOperatorExpression) exp).getSecondOperand());
		}
		if (exp instanceof XorOperatorExpression) {
			return 	expression(((XorOperatorExpression) exp).getFirstOperand()) 
					+ "'-xor'," 
					+ expression(((XorOperatorExpression) exp).getSecondOperand());
		}
		if (exp instanceof ExpressionInBrackets) {
			return expression(((ExpressionInBrackets) exp).getExpression());
		}
		if (exp instanceof EqualsOperatorExpression) {
			Expression firstOperand = ((EqualsOperatorExpression) exp).getFirstOperand();
			Expression secondOperand = ((EqualsOperatorExpression) exp).getSecondOperand();
			return propertyEqualsOperation(firstOperand, secondOperand);
		}
		if (exp instanceof NotEqualsOperatorExpression) {
			Expression firstOperand = ((NotEqualsOperatorExpression) exp).getFirstOperand();
			Expression secondOperand = ((NotEqualsOperatorExpression) exp).getSecondOperand();
			return "'-not'," + propertyEqualsOperation(firstOperand, secondOperand);
		}
		if (exp instanceof NotOperatorExpression) {
			Expression operand = ((NotOperatorExpression) exp).getFirstOperand();
			if (operand instanceof ExpressionInBrackets) {
				Expression brakets = ((ExpressionInBrackets) operand).getExpression();
				if (brakets instanceof EqualsOperatorExpression) {
					return expression(operand);					
				}
			} else if (operand instanceof EqualsOperatorExpression) {
				return "'-not'," + expression(operand);
			} else if (operand instanceof NotEqualsOperatorExpression) {
				return expression(operand);
			}
			throw new Exception("Unsupported type");			
		}
		if (exp instanceof OperationCallExpression) {
			String property, value, append, prepend;
			append = prepend = "";
			switch ((((OperationCallExpression) exp).getName())) {
			case "matches":
				break;
			case "startsWith":
				prepend = "^";
				break;
			case "endsWith":
				append = "$";
				break;	
			default:
				throw new Exception("(Unsupported operation)");
			}
			Expression target = ((OperationCallExpression) exp).getTargetExpression();
			List<Expression> parameterExpressions = ((OperationCallExpression) exp).getParameterExpressions();
			if (target instanceof PropertyCallExpression) {				
				property = ((PropertyCallExpression) target).getNameExpression().getName();
			} else {
				throw new Exception("Unsupported format");
			}
			if (parameterExpressions.size() ==1) {				
				value = processValue(parameterExpressions.get(0), prepend, append);
			} else {
				throw new Exception("Unsupported format");
			}
			return String.format("'-regexp','%s',%s,", property,value);
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

	protected String processValue(Expression operand) throws Exception {
		return processValue(operand, "", "");
	}
	
	/**
	 * TODO check and handle elements of type simulinkmodel element
	 */
	protected String processValue(Expression operand, String prepend, String append) throws Exception {
		String value;
		if (operand instanceof StringLiteral) {
			value = String.format("'%s%s%s'",prepend,((StringLiteral) operand).getValue(), append);
		} else if (operand instanceof IntegerLiteral) {
			value = String.format("%d",((IntegerLiteral) operand).getValue());
		} else if (operand instanceof RealLiteral) {
			value = String.format("%f",((RealLiteral) operand).getValue());
		} else if (operand instanceof BooleanLiteral) {
			value = String.format("%d",((BooleanLiteral) operand).getValue() ? 1 : 0);
		/*} else if (operand instanceof ISimulinkModelElement) {
			value = String.format("%f",((ISimulinkModelElement) operand).getHandle());*/
		} else {
			throw new Exception("Unsupported type");
		}
		return value;
	}
	
	
	protected String expression(Expression exp1, Expression exp2) throws Exception{
		return expression(exp1) + expression(exp2);
	}
}
