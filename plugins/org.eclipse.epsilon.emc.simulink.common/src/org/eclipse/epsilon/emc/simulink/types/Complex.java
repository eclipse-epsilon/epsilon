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

import org.eclipse.epsilon.emc.simulink.engine.MatlabClassLoader;
import org.eclipse.epsilon.emc.simulink.engine.MatlabEngine;
import org.eclipse.epsilon.emc.simulink.util.ReflectionLocalUtil;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;

public class Complex extends AbstractType {

	private static final String COMPLEX_MATLAB_CLASS = "com.mathworks.matlab.types.Complex";
	private static Class<?> complex_class;

	public static boolean is(Object obj) {
		return (getMatlabClass() == null) ? false : getMatlabClass().isInstance(obj);
	}

	private static Class<?> getMatlabClass() {
		if (complex_class == null) {
			try {
				complex_class = MatlabClassLoader.getInstance().loadMatlabClass(COMPLEX_MATLAB_CLASS);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return complex_class;
	}

	public Complex(Object complex) {
		super();
		if (is(complex)) {
			this.object = complex;
		}
	}
	
	public Complex(MatlabEngine engine) {
		this();
		this.engine = engine;
	}
	
	public Object getHandle() {
		return object;
	}

	public Complex(Double real, Double imag) {
		super();
		try {
			Constructor<?> constructor = getMatlabClass().getConstructor(new Class<?>[] {Double.TYPE, Double.TYPE});
			object = constructor.newInstance(real, imag);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			System.out.println("Problem retrieving constructor of the complex type");
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			System.out.println("Problem instantiating the complex type");
			e.printStackTrace();
		}
	}
	
	public Complex(Double real, Integer imag) {
		this(real,imag.doubleValue());
	}
	
	public Complex(Integer real, Double imag) {
		this(real.doubleValue(),imag);
	}
	public Complex(Integer real, Integer imag) {
		this(real.doubleValue(),imag.doubleValue());
	}

	public Complex() {
		this(0,0);

	}

	public Double getReal() {
		try {
			return (Double) ReflectionLocalUtil.getFieldValue(object, "real");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setReal(Double real) throws Exception {
		ReflectionLocalUtil.setFieldValue(object, "real", real);
	}
	
	public void setReal(Integer real) throws Exception {
		setReal(real.doubleValue());
	}

	public Double getImag()  {
		try {
			return (Double) ReflectionLocalUtil.getFieldValue(object, "imag");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setImag(Double imag) throws Exception {
		ReflectionLocalUtil.setFieldValue(object, "imag", imag);
	}
	
	public void setImag(Integer imag) throws Exception {
		setImag(imag.doubleValue());
	}
	
	@Override
	public String toString() {
		Double imag = getImag();
		return this.getReal().toString() +(imag>0 ? " +" : " " ) + imag.toString() + "i";
	}

	@Override
	public Object getProperty(String property) throws EolRuntimeException {
		try {
			switch (property) {
			case "real":
				return getReal();
			case "imag":
				return getImag();
			default:
				break;
			}
		} catch (Exception e) {
			throw new EolRuntimeException(e);
		}
		return null;
	}

	@Override
	public void setProperty(String property, Object value) throws EolRuntimeException {
		if (value instanceof Double || value instanceof Integer) {
			try {
				switch (property) {
				case "real":
					setReal((Double) value);
				case "imag":
					setImag((Double) value);
				default:
					break;
				}
			} catch (Exception e) {
				throw new EolRuntimeException(e);
			}
		}
	}

	@Override
	protected void init() {}

	@Override
	protected Object getObject() {
		return object;
	}

}
