/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.execute.context;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.epsilon.eol.util.Cache;

public class ExtendedProperties {
	
	public static void main(String[] args) {
		ExtendedProperties e = new ExtendedProperties();
		e.setPropertyValue(1, "foo", "bar");
		System.err.println(e.getPropertyValue(2, "foo"));
	}
	
	private final Cache<Object, Map<String, Object>> cache = new Cache<>();
	
	public Object getPropertyValue(Object o, String property) {
		return getPropertyValues(o).get(property);
	}
	
	public void setPropertyValue(Object o, String property, Object value) {
		getPropertyValues(o, true).put(property, value);
	}
	
	public Map<String, Object> getPropertyValues(Object o) {
		return getPropertyValues(o, false);
	}
	
	protected Map<String, Object> getPropertyValues(Object o, boolean create) {
		Map<String, Object> propertyValues = cache.get(o);
		
		if (propertyValues == null) {
			propertyValues = new HashMap<>(4);
			if (create) {
				cache.put(o, propertyValues);
			}
		}
		
		return propertyValues;
	}
	
	public void clear() {
		cache.dispose();
	}
}
