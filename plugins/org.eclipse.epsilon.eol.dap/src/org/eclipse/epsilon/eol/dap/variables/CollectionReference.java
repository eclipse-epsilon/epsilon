/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.eol.dap.variables;

import java.util.Collection;

import org.eclipse.epsilon.eol.types.EolCollectionType;

public abstract class CollectionReference extends IdentifiableReference<Collection<Object>> {

	public CollectionReference(Collection<Object> t) {
		super(t);
	}

	@Override
	public String getTypeName() {
		return EolCollectionType.getTypeName(target);
	}

}