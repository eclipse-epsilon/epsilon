/*******************************************************************************
 * Copyright (c) 2022-2023 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Sina Madani - initial API and implementation
 *     Antonio Garcia-Dominguez - complete implementation and add tests/docs
 ******************************************************************************/
package org.eclipse.epsilon.emc.json;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.introspection.java.JavaPropertyGetter;

public class JsonPropertyGetter extends JavaPropertyGetter {

	/*
	 * <p>This prefix allows us to refer to properties which may have been shadowed
	 * by the methods of the JsonObject class. For instance, imagine we parse this
	 * JSON:</p>
	 *
	 * <pre>
	 * { "keySet": [1, 2, 3] }
	 * </pre>
	 *
	 * <p>In this case, we wouldn't be able to use {@code Model.root.keySet} to refer to
	 * the {@code [1, 2, 3]} value, because it would be shadowed by the keySet() method
	 * in JSONObject which was inherited from Map. We would instead use {@code Model.root.p_keySet}
	 * in this case.</p>  
	 */
	private static final String PROPERTY_PREFIX = "p_";

	@Override
	public Object invoke(Object object, String property, IEolContext context) throws EolRuntimeException {
		JsonModelObject jsonObject = (JsonModelObject) object;

		if (jsonObject.containsKey(property)) {
			return jsonObject.get(property);
		}
		if (property.startsWith(PROPERTY_PREFIX)) {
			String unprefixedProperty = property.substring(PROPERTY_PREFIX.length());
			if (jsonObject.containsKey(unprefixedProperty)) {
				return jsonObject.get(unprefixedProperty);
			}
		}

		return super.invoke(object, property, context);
	}
	
}
