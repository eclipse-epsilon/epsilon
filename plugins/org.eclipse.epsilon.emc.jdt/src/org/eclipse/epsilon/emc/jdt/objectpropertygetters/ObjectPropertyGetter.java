/*********************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.jdt.objectpropertygetters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ObjectPropertyGetter<T, R> {
	
	protected Class<?> clazz;
	protected List<String> properties = new ArrayList<String>();
	
	public ObjectPropertyGetter(Class<?> clazz, String... properties) {
		this.clazz = clazz;
		this.properties.addAll(Arrays.asList(properties));
	}
	
	public boolean appliesTo(Object object, String property) {
		return properties.contains(property) && clazz.isInstance(object);
	}
	
	public abstract R getValue(T object, String property);
	
}
