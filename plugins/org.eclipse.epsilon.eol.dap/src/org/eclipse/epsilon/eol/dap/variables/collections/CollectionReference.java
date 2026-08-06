/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.eol.dap.variables.collections;

import java.util.Collection;

import org.eclipse.epsilon.eol.dap.variables.IdentifiableReference;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.types.EolCollectionType;

public abstract class CollectionReference extends IdentifiableReference<Collection<Object>> {

	public CollectionReference(IEolContext context, Collection<Object> t) {
		super(context, t);
	}

	@Override
	public String getTypeName() {
		return EolCollectionType.getTypeName(target);
	}

}