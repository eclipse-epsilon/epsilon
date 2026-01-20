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

import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.Modifier;

public class BodyDeclarationModifierGetter extends ObjectPropertyGetter<BodyDeclaration, Boolean> {

	public BodyDeclarationModifierGetter() {
		super(BodyDeclaration.class, "public", "static", "abstract", "protected", "private", "final");
	}
	
	@Override
	public Boolean getValue(BodyDeclaration bodyDeclaration, String property) {
		
		for (Object modifier : bodyDeclaration.modifiers()) {
			if (modifier instanceof Modifier) {
				if (property.equals(((Modifier) modifier).getKeyword().toString())) {
					return true;
				}
			}
		}
		return false;
	}

}
