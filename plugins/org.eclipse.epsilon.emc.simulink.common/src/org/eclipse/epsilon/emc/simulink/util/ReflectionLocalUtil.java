/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.eclipse.epsilon.common.util.ReflectionUtil;

public class ReflectionLocalUtil {

	public static Boolean setFieldValue(Object source, String field, Object value) throws Exception {
		for (Field f : source.getClass().getFields()) {
			f.setAccessible(true);
			if (f.getName().equalsIgnoreCase(field)) {
				f.set(source, value);
				return true;
			}
		}
		throw new Exception("Invalid property");
	}

	public static Object getFieldValue(Object source, String field) throws Exception {
		return ReflectionUtil.getFieldValue(source, field);
	}

	public static void explore(String className) throws ClassNotFoundException {
		System.out.println("Loading methods for " + className);
		Class<?> c = Class.forName(className);
		Method[] m = c.getDeclaredMethods();
		for (int i = 0; i < m.length; i++) {
			m[i].setAccessible(true);
			System.out.println(m[i].toString());
		}
		Class<?>[] inte = c.getInterfaces();
		for (int i = 0; i < inte.length; i++) {
			System.out.println(inte[i].toString());
		}
		Class<?> s = c.getSuperclass();
		if (s!= null) {
			System.out.println(s.getName());
		}
		Field[] f = c.getDeclaredFields();
		for (int i = 0; i < f.length; i++) {
			System.out.println(f[i].toString());
		}
		System.out.println("done");

	}
}
