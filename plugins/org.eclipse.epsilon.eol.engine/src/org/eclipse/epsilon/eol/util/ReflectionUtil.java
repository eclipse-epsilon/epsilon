/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.util;

import java.lang.reflect.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.exceptions.*;
import org.eclipse.epsilon.eol.execute.prettyprinting.PrettyPrinterManager;
import org.eclipse.epsilon.eol.types.EolNativeType;

public class ReflectionUtil {
	private ReflectionUtil() {}
	
	
	public static boolean hasMethods(Object obj, String methodName) {
		if (obj == null) return false;
		
		for (Method method : obj.getClass().getMethods()) {
			if (getMethodName(method).equals(methodName)) {
				return true;
			}
		}
		return false;
	}
	
	public static Set<String> getMethodNames(Object obj, boolean includeInheritedMethods) {
		if (obj == null) return new HashSet<>(0);
		
		Method[] methods = getMethods(obj, includeInheritedMethods);
		
		Set<String> methodNames = new HashSet<>(methods.length);
		for (Method method : methods) {
			methodNames.add(getMethodName(method));
		}
		return methodNames;
	}
	
	protected static String getMethodName(Method method) {
		String methodName = method.getName();
		if (methodName.startsWith("_")) methodName = methodName.substring(1);
		return methodName;
	}
	
	/**
	 * Searches for a method matching the name and criteria for the given object,
	 * including all super methods and super-interfaces recursively.
	 * 
	 * @param obj The target object to look for methods on.
	 * @param methodName The name of the method to find.
	 * @param criteria Function which limits the search scope of methods.
	 * @return A method (chosen non-deterministically) which matches the criteria.
	 * @throws EolIllegalOperationException If no method matching the criteria can be found.
	 * @throws EolIllegalOperationParametersException If the method parameters are invalid.
	 * @since 1.6
	 */
	public static Method findApplicableMethodOrThrow(Object obj, String methodName, Predicate<Method> criteria, Collection<?> parameters, ModuleElement ast, PrettyPrinterManager ppm) throws EolIllegalOperationException, EolIllegalOperationParametersException {
		final Method[] candidates = getMethodsFromPublicClassesForName(obj, methodName);
		
		Method method = Stream.of(candidates).filter(criteria).findAny().orElse(null);
		if (method == null) {
			method = searchMethodsFor(candidates, methodName, parameters.toArray(), true);
		}
		if (method == null) {
			Collector<CharSequence, ?, String> paramJoiner = Collectors.joining(", ");
			if (candidates.length > 0) {
				String expectedParams = Stream.of(candidates[0].getParameterTypes())
					.map(Class::getTypeName)
					.collect(paramJoiner);
				
				String actualParams = parameters.stream()
					.map(expr -> expr.getClass().getTypeName())
					.collect(paramJoiner);
				
				throw new EolIllegalOperationParametersException(methodName, expectedParams, actualParams, ast);
			}
			else throw new EolIllegalOperationException(obj, methodName, ast, ppm);
		}
		
		return method;
	}
	
	/**
	 * 
	 * @param clazz
	 * @return
	 * @since 1.6
	 */
	public static Class<?>[] discoverPublicClasses(Class<?> clazz) {
		List<Class<?>> interfaces = new ArrayList<>();
		discoverPublicClasses(clazz, interfaces);
		Collections.reverse(interfaces);
		return interfaces.toArray(new Class[interfaces.size()]);
	}

	/**
	 * 
	 * @param clazz
	 * @param interfaces
	 * @since 1.6
	 */
	private static void discoverPublicClasses(Class<?> clazz, List<Class<?>> interfaces) {
		if (clazz == null) return;
		if (Modifier.isPublic(clazz.getModifiers())) {
			interfaces.add(clazz);
		}
		for (Class<?> superInterface : clazz.getInterfaces()) {
			discoverPublicClasses(superInterface, interfaces);
		}
		discoverPublicClasses(clazz.getSuperclass(), interfaces);
	}
	
	/**
	 * 
	 * @param obj
	 * @param methodName
	 * @return
	 * @since 1.6
	 */
	public static Method[] getMethodsFromPublicClassesForName(Object obj, String methodName) {
		Class<?> clazz = obj instanceof EolNativeType ? ((EolNativeType) obj).getJavaClass() : obj.getClass();
		return Stream.of(discoverPublicClasses(clazz))
			//.parallel()
			.flatMap(c -> Arrays.stream(c.getMethods()))
			.filter(m -> getMethodName(m).equals(methodName))
			.toArray(Method[]::new);
	}


	private static Method[] getMethods(Object obj, boolean includeInheritedMethods) {
		Class<?> clazz = obj.getClass();
		if (includeInheritedMethods) {
			return clazz.getMethods();
		}
		else {
			return clazz.getDeclaredMethods();
		}
	}
	
	/**
	 * @param allowContravariantConversionForParameters
	 *   when false, parameters will have exactly the same class as the arguments to the returned method
	 *   when true, parameters may have a type that is more specific than the arguments to the returned method   
	 */
	public static Method getMethodFor(Object obj, String methodName, Object[] parameters, boolean includeInheritedMethods, boolean allowContravariantConversionForParameters) {
		if (obj == null)
			return null;
		
		Method instanceMethod = getInstanceMethodFor(obj, methodName, parameters, includeInheritedMethods, allowContravariantConversionForParameters);
		if (instanceMethod != null)
			return instanceMethod;
		
		Method staticMethod = getStaticMethodFor(obj, methodName, parameters, allowContravariantConversionForParameters);
		if (staticMethod != null)
			return staticMethod;
		
		return null;
	}

	private static Method getInstanceMethodFor(Object obj, String methodName, Object[] parameters, boolean includeInheritedMethods, boolean allowContravariantConversionForParameters) {
		return searchMethodsFor(getMethods(obj, includeInheritedMethods), methodName, parameters, allowContravariantConversionForParameters);
	}
	
	private static Method getStaticMethodFor(Object obj, String methodName, Object[] parameters, boolean allowContravariantConversionForParameters) {
		Method staticMethod = null;

		Class<?> javaClass = null;
		if (obj instanceof EolNativeType) {
			javaClass = ((EolNativeType) obj).getJavaClass();
		}
		if (obj instanceof Class) {
			javaClass = (Class<?>) obj;
		}
		
		if (javaClass != null) {
			staticMethod = searchMethodsFor(javaClass.getMethods(), methodName, parameters, allowContravariantConversionForParameters);
		}
		
		return staticMethod;
	}

	private static Method searchMethodsFor(Method[] methods, String methodName, Object[] parameters, boolean allowContravariantConversionForParameters) {
		// Antonio: according to the Java Language Specification, Sections 15.12.2.2 to 15.12.2.4,
		// method resolution is done in three stages: in the first one, no autoboxing is used. In
		// the second one, autoboxing (like that in our isInstance static method) is used. In the
		// third one, varargs are used. We should do the same if we want to tell apart remove(Object)
		// from remove(int) like Java normally would.
		for (int stage = 0; stage < 2; ++stage) {
			for (Method method : methods) {
				if (getMethodName(method).equalsIgnoreCase(methodName)) {
					Class<?>[] parameterTypes = method.getParameterTypes();
					boolean isVarargs = method.isVarArgs(),
							parametersMatch = parameterTypes.length == parameters.length || isVarargs;
					
					if (parametersMatch) {
						//TODO: See why parameter type checking does not work with EolSequence
						int varargIndex = method.getParameterCount() - 1;
						int endIndex = isVarargs ? varargIndex : parameterTypes.length;
						if (parameters.length < endIndex) {
							continue;
						}
						for (int j = 0; j < endIndex && parametersMatch; j++) {
							Class<?> parameterType = parameterTypes[j];
							Object parameter = parameters[j];
							if (allowContravariantConversionForParameters) {
								parametersMatch = parametersMatch && (stage == 0 ? parameterType.isInstance(parameter) : isInstance(parameterType, parameter));
							}
							else {
								parametersMatch = parametersMatch && parameterType.equals(parameter.getClass());
							}
						}
						if (isVarargs) {
							Class<?> varargType = parameterTypes[varargIndex].getComponentType();
							for (int va = varargIndex; va < parameters.length && parametersMatch; va++) {
								Object parameter = parameters[va];
								parametersMatch = (stage == 0 ? varargType.isInstance(parameter) : isInstance(varargType, parameter));
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
	
	public static Object executeMethod(Object obj, String methodName, Object... parameters) throws Throwable {
		Method method = getMethodFor(obj, methodName, parameters, true, true);
		try {
			//TODO: replace with trySetAccessible
			if (!method.isAccessible()) {
				method.setAccessible(true);
			}
			return method.invoke(obj, parameters);
		} 
		catch (InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	public static Object executeMethod(Object obj, Method method, ModuleElement ast, Object... parameters) throws EolRuntimeException {
		try {
			if (!method.isAccessible()) {
				method.setAccessible(true);
			}
			return method.invoke(obj, parameters);
		}
		catch (InvocationTargetException | IllegalAccessException | IllegalArgumentException ix) {
			Throwable cause = ix.getCause();
			if (cause == null) cause = ix;
			throw new EolInternalException(cause, ast);
		}
	}
	
	/*
	 * This method should replace the one above when we move on from Java 8.
	 * @param obj
	 * @param method
	 * @param ast
	 * @param parameters
	 * @return
	 * @throws EolRuntimeException
	 * @since 1.6
	 *
	public static Object executeMethod(Object obj, Method method, ModuleElement ast, Object... parameters) throws EolRuntimeException {
		try {
			if (method.trySetAccessible()) {
				return method.invoke(obj, parameters);
			}
			else {
				Method legal = getLegalMethod(obj, method);
				if (legal != null) {
					return legal.invoke(obj, parameters);
				}
			}
		}
		catch (InvocationTargetException | IllegalAccessException | IllegalArgumentException ix) {
			Throwable cause = ix.getCause();
			if (cause == null) cause = ix;
			throw new EolInternalException(cause, ast);
		}
			
		throw new EolIllegalOperationException(obj, method.getName(), ast, null);
	}*/
	
	/**
	 * This tries to find a method such that invoking via reflection won't be illegal in Java 9+
	 * 
	 * @param obj
	 * @param method
	 * @return
	 * @since 1.6
	 */
	public static Method getLegalMethod(Object obj, Method method) {
		for (Method m : ReflectionUtil.getMethodsFromPublicClassesForName(obj, method.getName())) {
			if (Objects.deepEquals(m.getParameterTypes(), method.getParameterTypes()) && m.getClass().isAssignableFrom(method.getClass())) {
				return m;
			}
		}
		return null;
	}
	
	/**
	 * Returns a string representation
	 * of the method
	 * @param method
	 * @return
	 */
	public static String methodToString(Method method) {
		String str = getMethodName(method);
		str += "(";
		for (int i = 0; i < method.getParameterTypes().length; i++) {
			Class<?> parameterType = method.getParameterTypes()[i];
			str += parameterType.getName();
			if (i < method.getParameterTypes().length - 1) {
				str += " ,";
			}
		}
		str += ")";
		return str;
	}
	
	/**
	 * Returns the value of a field of an object
	 * @param object
	 * @param fieldName
	 * @return
	 */
	public static Object getFieldValue(Object object, String fieldName) {
		if (object == null) return null;
		Field field = getField(object.getClass(), fieldName);
		if (field == null) return null;
		field.setAccessible(true);
		try {
			return field.get(object);
		}
		catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * Gets a field of a class using reflection
	 * by introspecting the class and its supertype(s)
	 * @param clazz
	 * @param fieldName
	 * @return
	 */
	public static Field getField(Class<?> clazz, String fieldName) {
		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getName().equals(fieldName))
				return fields[i];
		}
		
		if (clazz.getSuperclass() != Object.class)
			return getField(clazz.getSuperclass(), fieldName);
		
		return null;
	}
	
	/**
	 * Checks if the instance is an instance of clazz
	 * Necessary because in Java, int.class != Integer.class etc
	 * @param clazz
	 * @param instance
	 * @return
	 */
	public static boolean isInstance(Class<?> clazz, Object instance) {
		if (instance == null) return true;
		else if (clazz == int.class) return Integer.class.isInstance(instance);
		else if (clazz == float.class) return Float.class.isInstance(instance);
		else if (clazz == double.class) return Double.class.isInstance(instance);
		else if (clazz == boolean.class) return Boolean.class.isInstance(instance);
		else if (clazz == long.class) return Long.class.isInstance(instance);
		else if (clazz == char.class) return Character.class.isInstance(instance);
		else return clazz.isInstance(instance);
	}

	public static List<Field> getAllInheritedInstanceFields(Class<?> klazz) {
		final List<Field> fields = new ArrayList<>();
		for (Field f : klazz.getDeclaredFields()) {
			if (Modifier.isStatic(f.getModifiers())) {
				continue;
			}
			fields.add(f);
		}
		if (klazz.getSuperclass() != null) {
			fields.addAll(getAllInheritedInstanceFields(klazz.getSuperclass()));
		}
		return fields;
	}
}
