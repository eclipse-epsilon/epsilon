/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.types;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.eclipse.epsilon.eol.dom.StringLiteral;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.EolTypeNotFoundException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;

public class EolNativeType extends EolAnyType {
	
	protected IEolContext context;
	protected String clazz;
	protected IToolNativeTypeDelegate delegate;
	
	/**
	 * 
	 * @param actualClass
	 * @param context
	 * @since 1.6
	 */
	public EolNativeType(Class<?> actualClass, IEolContext context) {
		this.clazz = actualClass.getCanonicalName();
		this.context = context;
		for (IToolNativeTypeDelegate delegate : context.getNativeTypeDelegates()) {
			if (delegate.knowsAbout(this.clazz)) {
				this.delegate = delegate;
				return;
			}
		}
	}
	
	public EolNativeType(StringLiteral classAst, IEolContext context) throws EolTypeNotFoundException {
		for (IToolNativeTypeDelegate delegate : context.getNativeTypeDelegates()) {
			if (delegate.knowsAbout(classAst.getValue())) {
				this.clazz = classAst.getValue();
				this.delegate = delegate;
				this.context = context;
				return;
			}
		}
		throw new EolTypeNotFoundException(classAst.getValue(), classAst);
	}

	@Override
	public Object createInstance() throws EolRuntimeException {
		return delegate.createInstance(clazz, Collections.emptyList(), context);
	}
	
	@Override
	public Object createInstance(List<Object> parameters) throws EolRuntimeException {
		return delegate.createInstance(clazz, parameters, context);
	}
	
	public Class<?> getJavaClass() {
		try {
			return ClassLoader.getSystemClassLoader().loadClass(clazz);
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
	
	@Override
	public String getName() {
		return "Native (" + clazz + ")";
	}

	@Override
	public boolean isKind(Object o) {
		if (o != null) {
			Class<?> cls = o.getClass();
			boolean found = false;
			while (cls != null && !found) {
				if (cls.getCanonicalName().equalsIgnoreCase(clazz)) {
					return true;
				}
				else {
					cls = cls.getSuperclass();
				}
			}
		}
		return false;
	}

	@Override
	public boolean isType(Object o) {
		return o != null && o.getClass().getCanonicalName().equalsIgnoreCase(clazz);
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(clazz);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof EolNativeType) {
			return ((EolNativeType) o).clazz.equalsIgnoreCase(this.clazz);
		}
		return false;
	}
	
}
