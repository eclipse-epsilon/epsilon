/*********************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.plainxml;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.epsilon.eol.dom.Expression;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.introspection.java.ObjectMethod;
import org.eclipse.epsilon.eol.execute.operations.contributors.OperationContributor;
import org.eclipse.epsilon.eol.util.ReflectionUtil;
import org.w3c.dom.Element;

public class PlainXMLOperationContributor extends OperationContributor {

	@Override
	public boolean contributesTo(Object target) {
		return target instanceof Element;
	}

	/**
	 * We can't find the method if the parameters are unevaluated
	 */
	@Override
	public ObjectMethod findContributedMethodForUnevaluatedParameters(Object target, String name,
			List<Expression> parameterExpressions, IEolContext context) {
		return null;
	}

	@Override
	public ObjectMethod findContributedMethodForEvaluatedParameters(Object target, String name, Object[] parameters,
			IEolContext context, boolean overrideContextOperationContributorRegistry) {
		return createObjectMethodFor(target, name, parameters, context, true);
	}

	private ObjectMethod createObjectMethodFor(Object target, String name, Object[] parameters, IEolContext context,
			boolean allowContravariantConversionForParameters) {
		Method method = null;
		// Maintain a cache of method names if the reflection target is this
		// so that we don't iterate through all methods every time
		if (getReflectionTarget(target) == this && cachedMethodNames == null)
			synchronized (this) {
				if (cachedMethodNames == null) {
					cachedMethodNames = ReflectionUtil.getMethodNames(this, includeInheritedMethods());
				}
			}

		if (cachedMethodNames == null || cachedMethodNames.contains(name)) {
			method = ReflectionUtil.getMethodFor(getReflectionTarget(target), name, parameters, true,
					allowContravariantConversionForParameters);
			if (method != null && !method.isAccessible()) {
				// Method is internal, try to find it in the interfaces
				method = this.searchMethodsFor(this.getMethods(target), name, parameters,
						allowContravariantConversionForParameters);
			}
		}

		if (method != null) {
			Object reflectionTarget = getReflectionTarget(target);
			ObjectMethod objectMethod = new ObjectMethod(reflectionTarget, method);

			/*
			 * If the reflection target is this contributor, then it will need to know about
			 * the actual operand for the method and the intended context.
			 */
			if (reflectionTarget == this) {
				setTarget(target);
				setContext(context);
			}
			return objectMethod;
		} else
			return null;
	}

	@Override
	protected Object getReflectionTarget(Object target) {
		return target;
	}

	/**
	 * In XML, all implementations have been moved to
	 * com.sun.org.apache.xerces.internal, thus, the methods found via reflection on
	 * the class will be not accessible (Method#canAccess == false). As a solution,
	 * we find the interfaces implemented by the Class and return the methods on
	 * those. Since the object's class can extend another class, we also need to
	 * search the superclass interfaces.
	 * 
	 * @param obj
	 * @return
	 */
	private Method[] getMethods(Object obj) {
		// Closure of all supertypes
		List<Class<?>> allSupers = superClosure(obj.getClass());
		List<Class<?>> interfaces = new ArrayList<>(Arrays.asList(obj.getClass().getInterfaces()));
		interfaces.addAll(allSupers.stream().map(sc -> Arrays.asList(sc.getInterfaces())).flatMap(Collection::stream)
				.collect(Collectors.toList()));
		List<Method> result = interfaces.stream().map(i -> this.getMethods(i)).flatMap(Collection::stream)
				.collect(Collectors.toList());
		return result.toArray(new Method[result.size()]);
	}

	private List<Class<?>> superClosure(Class<?> type) {
		Class<?> superclass = type.getSuperclass();
		List<Class<?>> allSupers = new ArrayList<>();
		if (superclass == null) {
			return allSupers;
		}
		allSupers.add(superclass);
		allSupers.addAll(superClosure(type.getSuperclass()));
		return allSupers;
	}

	private List<Method> getMethods(Class<?> intrfce) {
		return Arrays.asList(intrfce.getMethods());
	}

	private Method searchMethodsFor(Method[] methods, String methodName, Object[] parameters,
			boolean allowContravariantConversionForParameters) {
		// Antonio: according to the Java Language Specification, Sections 15.12.2.2 to
		// 15.12.2.4,
		// method resolution is done in three stages: in the first one, no autoboxing is
		// used. In
		// the second one, autoboxing (like that in our isInstance static method) is
		// used. In the
		// third one, varargs are used. We should do the same if we want to tell apart
		// remove(Object)
		// from remove(int) like Java normally would.
		for (int stage = 0; stage < 2; ++stage) {
			for (Method method : methods) {
				if (getMethodName(method).equalsIgnoreCase(methodName)) {
					Class<?>[] parameterTypes = method.getParameterTypes();
					boolean isVarargs = method.isVarArgs(),
							parametersMatch = parameterTypes.length == parameters.length || isVarargs;

					if (parametersMatch) {
						// TODO: See why parameter type checking does not work with EolSequence
						int varargIndex = method.getParameterCount() - 1;
						int endIndex = isVarargs ? varargIndex : parameterTypes.length;
						if (parameters.length < endIndex) {
							continue;
						}
						for (int j = 0; j < endIndex && parametersMatch; j++) {
							Class<?> parameterType = parameterTypes[j];
							Object parameter = parameters[j];
							if (allowContravariantConversionForParameters) {
								parametersMatch = parametersMatch && (stage == 0 ? parameterType.isInstance(parameter)
										: ReflectionUtil.isInstance(parameterType, parameter));
							} else {
								parametersMatch = parametersMatch && parameterType.equals(parameter.getClass());
							}
						}
						if (isVarargs) {
							Class<?> varargType = parameterTypes[varargIndex].getComponentType();
							for (int va = varargIndex; va < parameters.length && parametersMatch; va++) {
								Object parameter = parameters[va];
								parametersMatch = (stage == 0 ? varargType.isInstance(parameter)
										: ReflectionUtil.isInstance(varargType, parameter));
							}
						}
					}
					if (parametersMatch) {
						return method;
					}
				}
			}
		}
		return null;
	}

	private String getMethodName(Method method) {
		String methodName = method.getName();
		if (methodName.startsWith("_"))
			methodName = methodName.substring(1);
		return methodName;
	}

}
