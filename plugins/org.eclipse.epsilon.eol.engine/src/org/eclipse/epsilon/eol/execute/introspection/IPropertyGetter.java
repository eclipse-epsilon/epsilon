/*******************************************************************************
 * Copyright (c) 2008-2020 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Antonio Garcia-Dominguez - add isPropertySet
 *     Sina Madani - stateless refactoring
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.introspection;

import org.eclipse.epsilon.eol.exceptions.EolIllegalPropertyException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;

public interface IPropertyGetter {
	
	/**
	 * Invokes a property on a model element.
	 * 
	 * @param object The model element.
	 * @param property The property name of the model element.
	 * @param context The execution context.
	 * @return The value of the requested property.
	 * @throws EolIllegalPropertyException If the property could not be found.
	 * @throws EolRuntimeException If an exception occurs when querying the model for the property.
	 * @since 1.6
	 */
	Object invoke(Object object, String property, IEolContext context) throws EolRuntimeException;
	
	/**
	 * Convenience method for checking whether a property exists.
	 * 
	 * @param object The model element.
	 * @param property The property name of the model element.
	 * @param ast Where this is being called from.
	 * @param context The execution context.
	 * @return <code>true</code> if the property exists, <code>false</code> otherwise.
	 * @since 1.6
	 */
	default boolean hasProperty(Object object, String property, IEolContext context) {
		try {
			invoke(object, property, context);
			return true;
		}
		catch (EolRuntimeException ex) {
			return false;
		}
	}
	
	// BACKWARDS COMPATIBILITY
	
	/**
	 * This method is provided for backwards compatibility and convenience.
	 * Implementations should override the {@link #invoke(Object, String, IEolContext)} method.
	 * Callers should prefer the {@link #invoke(Object, String, IEolContext)} method where possible.
	 * 
	 * @param object
	 * @param property
	 * @return
	 * @throws EolRuntimeException
	 */
	default Object invoke(Object object, String property) throws EolRuntimeException {
		return invoke(object, property, null);
	}
	
	/**
	 * This method is provided for backwards compatibility and convenience.
	 * Implementations should override the {@link #hasProperty(Object, String, IEolContext)} method.
	 * Callers should prefer the {@link #hasProperty(Object, String, IEolContext)} method where possible.
	 * 
	 * @param object
	 * @param property
	 * @return
	 */
	default Object hasProperty(Object object, String property) {
		return hasProperty(object, property, null);
	}
}
