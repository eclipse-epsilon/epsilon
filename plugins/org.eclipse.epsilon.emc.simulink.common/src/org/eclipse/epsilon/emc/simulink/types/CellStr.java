/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.types;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.epsilon.emc.simulink.engine.MatlabClassLoader;
import org.eclipse.epsilon.emc.simulink.engine.MatlabEngine;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;

public class CellStr extends AbstractType {

	private static final String CELL_STR_MATLAB_CLASS = "com.mathworks.matlab.types.CellStr";

	private static Class<?> cell_str_class;

	protected Method getStringArrayMethod;
	protected Method equalsMethod;
	
	public static boolean is(Object obj) {
		return (getMatlabClass() == null) ? false : getMatlabClass().isInstance(obj);  
	}
	
	protected static Class<?> getMatlabClass() {
		if (cell_str_class == null) {
			try {
				cell_str_class = MatlabClassLoader.getInstance().loadMatlabClass(CELL_STR_MATLAB_CLASS);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return cell_str_class;
	}
	
	public CellStr(Object cellStr) {
		super();
		if (is(cellStr)) {
			this.object = cellStr;
			init();
		}
	}
	
	public CellStr(MatlabEngine engine) {
		super(engine);
	}
	
	public CellStr(String[] stringArray) {
		try {
			Constructor<?> constructor = getMatlabClass().getConstructor(String[].class);
			object = constructor.newInstance((Object)stringArray);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			System.out.println("Problem retrieving constructor of the complex type");
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			System.out.println("Problem instantiating the complex type");
			e.printStackTrace();
		}		
	}
	
	public CellStr(String[][] stringArray) {
		try {
			Constructor<?> constructor = getMatlabClass().getConstructor(String[][].class);
			object = constructor.newInstance((Object) stringArray);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			System.out.println("Problem retrieving constructor of the complex type");
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			System.out.println("Problem instantiating the complex type");
			e.printStackTrace();
		}		
	}
	
	@Override
	protected void init() {
		Class<?> clazz = getMatlabClass();
		try {
			getStringArrayMethod = getStringArrayMethod == null ? clazz.getDeclaredMethod("getStringArray") : null;
			equalsMethod = equalsMethod == null ? clazz.getDeclaredMethod("equals", Object.class) : null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Object getStringArray() {
		try {
			return (Integer) getStringArrayMethod.invoke(object);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e.getMessage());
		}
	}
	
	@Override
	public boolean equals(Object object) {
		try {
			return (Boolean) equalsMethod.invoke(this.object, object);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e.getMessage());
		}
	}

	@Override
	public Object getProperty(String property) throws EolRuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProperty(String property, Object value) throws EolRuntimeException {
		// TODO Auto-generated method stub

	}
	
	@Override
	protected Object getObject() {
		return object;
	}

}
