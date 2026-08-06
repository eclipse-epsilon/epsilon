/*******************************************************************************
 * Copyright (c) 2008-2020 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Sina Madani - stateless refactoring
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.introspection;

import java.util.Map;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;

public interface IPropertySetter {
	
	/**
	 * Sets a property on a given object to the specified value.
	 * 
	 * @param target The model element to update.
	 * @param property The name of the property of the model element.
	 * @param value The new value of the property.
	 * @param context The execution context.
	 * @throws EolRuntimeException If anything goes wrong in updating the value.
	 * @since 1.6
	 */
	void invoke(Object target, String property, Object value, IEolContext context) throws EolRuntimeException;
	
	/**
	 * This method is provided only for convenience. Implementations should override the
	 * {@link #invoke(Object, String, Object, IEolContext)} method instead.
	 * 
	 * @param target
	 * @param property
	 * @param value
	 * @throws EolRuntimeException
	 */
	default void invoke(Object target, String property, Object value) throws EolRuntimeException {
		invoke(target, property, value, null);
	}
	
	/**
	 * Supports batch property setting through 
	 * e.g. new Person(name="John", surname="Brown")
	 * @param target
	 * @param properties
	 * @since 2.5
	 */
	default void invoke(Object target, Map<String, Object> properties, IEolContext context) throws EolRuntimeException {
		for (String property : properties.keySet()) {
			invoke(target, property, properties.get(property), context);
		}
	}
	
}
