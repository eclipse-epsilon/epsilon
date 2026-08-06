/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.function;

import java.util.Iterator;
import java.util.List;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.dom.Expression;
import org.eclipse.epsilon.eol.dom.Parameter;
import org.eclipse.epsilon.eol.exceptions.EolIllegalOperationException;
import org.eclipse.epsilon.eol.exceptions.EolIllegalReturnException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.concurrent.EolContextParallel;

/**
 * Utility class for converting EOL lambdas to Java lambdas.
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class EolLambdaFactory { 
	
	public static Object resolveFor(Class<?> clazz, List<Parameter> iteratorParams, Expression lambdaExpr,
			ModuleElement ast, IEolContext context) throws EolIllegalOperationException {
	
		return resolveFor(clazz.getSimpleName(), iteratorParams, lambdaExpr, ast, context);
	}
	
	/**
	 * Validation of parameters is assumed to have already been performed in DynamicOperation or by the caller.
	 * @param clazzName
	 * @param iteratorParams
	 * @param lambdaExprs
	 * @param context
	 * @return
	 * @throws EolIllegalOperationException 
	 */
	public static Object resolveFor(String clazzName, List<Parameter> iteratorParams, Expression lambdaExpr,
			ModuleElement ast, IEolContext context) throws EolIllegalOperationException {
		
		clazzName = clazzName.toLowerCase().replace("checkedeol", "");
		if (clazzName.startsWith("get")) {
			clazzName = clazzName.substring(3);
		}
		
		switch (clazzName) {
			case "runnable":// case "statement":
				return (CheckedEolRunnable) () -> executeExpression(context, lambdaExpr, null, lambdaExpr, iteratorParams);
			case "consumer":// case "setter":
				return (CheckedEolConsumer<?>) t -> executeExpression(context, ast, null, lambdaExpr, iteratorParams, t);
			case "supplier":// case "getter":
				return (CheckedEolSupplier<?>) () -> executeExpression(context, ast, null, lambdaExpr, iteratorParams);
			case "function": case "func": case "fun":// case "mapper":
				return (CheckedEolFunction<?, ?>) t -> executeExpression(context, ast, null, lambdaExpr, iteratorParams, t);
			case "predicate":// case "filter":
				return (CheckedEolPredicate<?>) t -> executeExpression(context, ast, Boolean.class, lambdaExpr, iteratorParams, t);
			case "unary": case "unaryoperator":// case "uoperator": case "unaryop":
				return (CheckedEolUnaryOperator<?>) t -> executeExpression(context, ast, t != null ? t.getClass() : null, lambdaExpr, iteratorParams, t);
			case "biconsumer":// case "binaryconsumer": case "bisetter": case "binarysetter":
				return (CheckedEolBiConsumer<?, ?>) (t, u) -> executeExpression(context, ast, null, lambdaExpr, iteratorParams, t, u);
			case "bifunction":// case "bifunc": case "bifun": case "binaryfunction": case "binaryfunc": case "binaryfun": case "bimappper": case "binarymapper":
				return (CheckedEolBiFunction<?, ?, ?>) (t, u) -> executeExpression(context, ast, null, lambdaExpr, iteratorParams, t, u);
			case "bipredicate":// case "binarypredicate": case "bifilter": case "binaryfilter":
				return (CheckedEolBiPredicate<?, ?>) (t, u) -> executeExpression(context, ast, Boolean.class, lambdaExpr, iteratorParams, t, u);
			case "binaryoperator":// case "bioperator": case "binary": case "binaryop":
				return (CheckedEolBinaryOperator<?>) (t, u) -> executeExpression(context, ast, t != null ? t.getClass() : null, lambdaExpr, iteratorParams, t, u);
			default:
				throw new EolIllegalOperationException(EolLambdaFactory.class, clazzName, ast, context.getPrettyPrinterManager());
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <R> R executeExpression(IEolContext context, ModuleElement ast,
			Class<R> expectedReturnType, Expression expression, List<Parameter> params,
			Object... paramValues) throws EolRuntimeException {

		assert context != null && (
				(params == null || params.isEmpty()) &&
				(paramValues == null || paramValues.length == 0) ||
			params.size() >= paramValues.length
		);
		
		if (context instanceof EolContextParallel) {
			context = ((EolContextParallel) context).getShadow();
		}
		
		FrameStack scope = context.getFrameStack();
		scope.enterLocal(FrameType.UNPROTECTED, expression);
		
		if (params != null && paramValues != null) {
			Iterator<Parameter> paramsIter = params.iterator();
			for (Object value : paramValues) {
				scope.put(paramsIter.next().getName(), value);
			}
		}
		
		Object result = context.getExecutorFactory().execute(expression, context);
		scope.leaveLocal(expression);
		
		try {
			return (R) result;
		}
		catch (ClassCastException ccx) {
			throw new EolIllegalReturnException(expectedReturnType.getName(), result, ast, context);
		}
	}
}
