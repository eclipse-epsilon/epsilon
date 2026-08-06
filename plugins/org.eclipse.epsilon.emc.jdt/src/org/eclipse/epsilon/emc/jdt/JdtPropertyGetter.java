/*********************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.jdt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.epsilon.emc.jdt.objectpropertygetters.BodyDeclarationModifierGetter;
import org.eclipse.epsilon.emc.jdt.objectpropertygetters.FieldDeclarationNameGetter;
import org.eclipse.epsilon.emc.jdt.objectpropertygetters.ObjectPropertyGetter;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.introspection.java.JavaPropertyGetter;
import org.eclipse.jdt.core.dom.SimpleName;

@SuppressWarnings("rawtypes")
public class JdtPropertyGetter extends JavaPropertyGetter {
	
	protected List<ObjectPropertyGetter> objectPropertyGetters = new ArrayList<ObjectPropertyGetter>();
	protected final boolean preserveNames;

	public JdtPropertyGetter(boolean resolveBindings) {
		objectPropertyGetters.addAll(Arrays.asList(new ObjectPropertyGetter[]{
				new FieldDeclarationNameGetter(),
				new BodyDeclarationModifierGetter()
		}));
		this.preserveNames = resolveBindings;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object invoke(Object object, String property)
			throws EolRuntimeException {
		
		Object result = null;
		
		for (ObjectPropertyGetter objectPropertyGetter : objectPropertyGetters) {
			if (objectPropertyGetter.appliesTo(object, property)) {
				result = objectPropertyGetter.getValue(object, property);
				break;
			}
		}
		
		if (result == null) {
			result = super.invoke(object, property);
		}

		if (!preserveNames && result instanceof SimpleName) {
			return result.toString();
		} else {
			return result;
		}
	}
	
}
