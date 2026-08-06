/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.execute.operations;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.Arrays;
import java.util.Collection;
import org.eclipse.epsilon.common.util.StringUtil;
import org.eclipse.epsilon.eol.dom.Expression;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.eclipse.epsilon.eol.dom.Parameter;
import org.eclipse.epsilon.eol.exceptions.EolIllegalOperationException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.introspection.java.ObjectMethod;
import org.eclipse.epsilon.eol.function.EolLambdaFactory;
import org.eclipse.epsilon.eol.types.EolNoType;
import org.eclipse.epsilon.eol.util.ReflectionUtil;

/**
 * Provides a bridge between EOL first-order syntax and Java lambdas
 * (i.e. {@linkplain FunctionalInterface}s).
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class DynamicOperation extends AbstractOperation {
	
	@Override
	public Object execute(Object target, NameExpression operationNameExpression, List<Parameter> iterators, List<Expression> expressions, IEolContext context) throws EolRuntimeException {
		assert expressions != null && !expressions.isEmpty();
		
		LinkedHashMap<Expression, List<Parameter>> lambdas = new LinkedHashMap<>(expressions.size(), 1f);
		for (Expression expression : expressions) {
			lambdas.put(expression, iterators);
		}
		
		return execute(target, operationNameExpression, lambdas, context);
	}
	
	public Object execute(Object target, NameExpression operationNameExpression, LinkedHashMap<Expression, List<Parameter>> lambdas, IEolContext context) throws EolRuntimeException {
		final Iterator<Map.Entry<Expression, List<Parameter>>> entriesIter = lambdas.entrySet().iterator();
		final Collection<Expression> expressions = lambdas.keySet();
		final String methodName = operationNameExpression.getName();
		
		if (target instanceof EolNoType || target instanceof EolLambdaFactory) {
			final Map.Entry<Expression, List<Parameter>> first = entriesIter.next();
			return EolLambdaFactory.resolveFor(methodName, first.getValue(), first.getKey(), operationNameExpression, context);
		}
		
		// Look for a matching method with FunctionalInterface parameter(s)
		Predicate<Method> criteria = method ->
			Modifier.isPublic(method.getModifiers()) &&
			method.getParameterCount() == expressions.size() &&
			Arrays.stream(method.getParameterTypes())
				.filter(Class::isInterface)
				.allMatch(intf -> Arrays.stream(intf.getAnnotations())
						.anyMatch(a -> a.annotationType().equals(FunctionalInterface.class)) ||
					Arrays.stream(intf.getMethods())
						.filter(targetParamMethod -> Modifier.isAbstract(targetParamMethod.getModifiers()))
						.count() == 1
				);
		
		final Method resolvedMethod = ReflectionUtil.findApplicableMethodOrThrow(
			target, methodName, criteria, expressions, operationNameExpression, context.getPrettyPrinterManager()
		);
		
		final int candidateParamCount = resolvedMethod.getParameterCount();
		final Object[] candidateParameterValues = new Object[candidateParamCount];
		final java.lang.reflect.Parameter[] candidateParameterTypes = resolvedMethod.getParameters();
		
		for (int i = 0; i < candidateParamCount && entriesIter.hasNext(); i++) {
			final Class<?> targetType = candidateParameterTypes[i].getType();
			final Map.Entry<Expression, List<Parameter>> complexExpr = entriesIter.next();
			final Expression rawExpr = complexExpr.getKey();
			
			if (!targetType.isInterface() || complexExpr.getValue() == null) {
				candidateParameterValues[i] = rawExpr.execute(context);
				continue;
			}
			
			final List<Parameter> iteratorParams = normalizeParameters(complexExpr.getValue());
			
			try {
				// First try to use the CheckedEol version of known functional interfaces
				candidateParameterValues[i] = EolLambdaFactory.resolveFor(targetType, iteratorParams, rawExpr, operationNameExpression, context);
			}
			catch (EolIllegalOperationException eox) {
				final IEolContext fContext = context;
				// Failing that, try to implement the interface
				candidateParameterValues[i] = Proxy.newProxyInstance(
					targetType.getClassLoader(),
					new Class[]{targetType},
					(proxy, method, args) ->
						EolLambdaFactory.executeExpression(fContext, operationNameExpression, null, rawExpr,  iteratorParams, args)
				);
			}
		}
		
		// Finally, call the method with the resolved parameters
		try (ObjectMethod om = new ObjectMethod(target, resolvedMethod)) {
			return om.execute(operationNameExpression, context, candidateParameterValues);
		}
	}
	
	static List<Parameter> normalizeParameters(List<Parameter> iterators) {
		if (iterators != null && iterators.size() == 1 && StringUtil.isOneOf(iterators.get(0).getName(), "null", "_"))
			return Collections.emptyList();
		else
			return iterators;
	}
	
}
