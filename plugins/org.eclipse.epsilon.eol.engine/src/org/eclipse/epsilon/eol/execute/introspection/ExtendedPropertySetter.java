/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.introspection;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;

public class ExtendedPropertySetter extends AbstractPropertySetter {
	

	@Override
	public void invoke(Object target, String property, Object value, IEolContext context) throws EolRuntimeException {
		
		context.getExtendedProperties().setPropertyValue(target, property.substring(1), value);
		
		/*
		Map<String, Object> extendedProperties = context.getExtendedProperties().get(object);
		if (extendedProperties == null) {
			extendedProperties = new HashMap<>();
			context.getExtendedProperties().put(object, extendedProperties);
		}
		extendedProperties.put(this.property.substring(1), value);*/
	}
}
