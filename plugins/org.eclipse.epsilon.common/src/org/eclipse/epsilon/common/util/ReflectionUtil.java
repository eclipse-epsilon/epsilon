/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

public class ReflectionUtil {
	
	public static Object invokeMethodSafe(Object source, String method, Collection<Object> arguments) {
		try {
			return invokeMethod(source, method, arguments);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Object invokeMethod(Object source, String method, Collection<Object> arguments) throws Exception {
		Collection<Class<?>> argumentClasses = new ArrayList<>();
		for (Object argument : arguments) {
			argumentClasses.add(argument.getClass());
		}
		//Method m = source.getClass().getMethod(method, argumentClasses);
		
		if (source == null) return null;
		
		for (Method m : source.getClass().getMethods()) {
			if (m.getName().equalsIgnoreCase(method)) {
				m.setAccessible(true);
				return m.invoke(source, arguments.toArray());
			}
		}
		return null;
	}
	
	public static Object getFieldValue(Object source, String field) throws Exception{
		for (Field f : source.getClass().getFields()) {
			f.setAccessible(true);
			if (f.getName().equalsIgnoreCase(field)) {
				return f.get(source);
			}
		}
		return null;
	}
}
