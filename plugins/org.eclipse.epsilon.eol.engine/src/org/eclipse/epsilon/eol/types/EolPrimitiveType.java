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

import java.util.List;

import org.eclipse.epsilon.eol.exceptions.EolIllegalOperationParametersException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;

public class EolPrimitiveType extends EolType {

	private Class<?> clazz;
	private String name;
	
	public static final EolPrimitiveType
		Integer = new EolPrimitiveType(Integer.class, "Integer"),
		String = new EolPrimitiveType(String.class, "String"),
		Boolean = new EolPrimitiveType(Boolean.class, "Boolean"),
		Real = new EolPrimitiveType(Float.class, "Real");
	
	private EolPrimitiveType(Class<?> clazz, String name) {
		this.clazz = clazz;
		this.name = name;
	}

	@Override
	public Object createInstance(List<Object> parameters) throws EolRuntimeException {
		throw new EolIllegalOperationParametersException("createInstance");
	}
	
	@Override
	public boolean isType(Object o) {
		if (o == null) return false;
		else if ((this == EolPrimitiveType.Integer) && (o instanceof Integer || o instanceof Long)) {
			return true;
		}
		else if ((this == EolPrimitiveType.Real) && (o instanceof Double || o instanceof Float)) {
			return true;
		}
		else if ((this == EolPrimitiveType.String) && (o instanceof String)) {
			return true;
		}
		else if ((this == EolPrimitiveType.Boolean) && (o instanceof Boolean)) {
			return true;
		}
		return false;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	@Override
	public boolean isKind(Object o) {
		if (this == EolPrimitiveType.Integer) return Integer.isType(o);
		else if (this == EolPrimitiveType.Real) return Real.isType(o) || Integer.isType(o);
		else if (this == EolPrimitiveType.String) return String.isType(o);
		else if (this == EolPrimitiveType.Boolean) return Boolean.isType(o);
		else return false;
	}

	@Override
	public Object createInstance() {
		if (this == Integer) return 0;
		else if (this == Real) return 0.0f;
		else if (this == String) return "";
		else if (this == Boolean) return false;
		else return null;
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
