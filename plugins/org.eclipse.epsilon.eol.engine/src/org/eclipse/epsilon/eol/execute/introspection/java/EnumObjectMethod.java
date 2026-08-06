/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.execute.introspection.java;

import java.util.Arrays;

/**
 * Wrapper to fix Bug 442540.
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class EnumObjectMethod extends ObjectMethod {

	protected Enum<?>[] enumValues;
	protected String enumName;
	
	public EnumObjectMethod(Class<? extends Enum<?>> enumClazz, String property) {
		this.object = this;
		this.enumName = property;
		try {
			this.enumValues = (Enum<?>[]) enumClazz.getMethod("values").invoke(enumClazz);
			this.method = getClass().getDeclaredMethod("getEnumValue");
		}
		catch (Exception ex) {
			// Should never happen
			ex.printStackTrace();
		}
	}
	
	protected Enum<?> getEnumValue() {
		return Arrays.stream(enumValues)
			.filter(e -> e.name().equals(enumName))
			.findAny().orElse(null);
	}
}
