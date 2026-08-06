/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.parse;

import java.lang.reflect.Field;
import java.util.HashMap;

public class StaticFieldNameResolver {
	
	HashMap<Integer, String> map = new HashMap<>();
	
	public StaticFieldNameResolver(Class<?> clazz) {
		
		for (Field f : clazz.getFields()) {
			if (f.getType() == int.class) {
				try {
					map.put((Integer) f.get(null), f.getName());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public String getField(Integer value) {
		return map.get(value);
	}
}
