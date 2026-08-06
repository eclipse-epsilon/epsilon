/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.types;

import java.util.List;

import org.eclipse.epsilon.egl.EglTemplate;
import org.eclipse.epsilon.eol.exceptions.EolIllegalOperationParametersException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.types.EolType;

public class EglComplexType extends EolType {

	private Class<?> clazz;
	private String name;
	
	public static EglComplexType Template = new EglComplexType(EglTemplate.class, "Template");
	
	private EglComplexType(Class<?> clazz, String name) {
		this.clazz = clazz;
		this.name = name;
	}
	
	@Override
	public boolean isType(Object o) {
		if (o == null) return true;
		return o.getClass() == clazz;
	}

	@Override
	public boolean isKind(Object o) {
		return clazz.isInstance(o);
	}
	
	@Override
	public Object createInstance() {
		try {
			return clazz.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Object createInstance(List<Object> parameters) throws EolRuntimeException {
		throw new EolIllegalOperationParametersException("createInstance");
	}
	
	@Override
	public String getName() {
		return name;
	}
}
