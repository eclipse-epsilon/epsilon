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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.eclipse.epsilon.eol.dap.variables.IVariableReference;
import org.eclipse.epsilon.eol.dap.variables.SuspendedState;
import org.eclipse.epsilon.eol.execute.context.IEolContext;

public class PerElementCollectionReference extends CollectionReference {

	private final String name;

	public PerElementCollectionReference(IEolContext context, String name, Collection<Object> collection) {
		super(context, collection);
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<IVariableReference> getVariables(SuspendedState state) {
		final List<IVariableReference> refs = new ArrayList<>(target.size());

		int i = 0;
		for (Object e : target) {
			refs.add(state.getValueReference(context, String.format("%s[%d]", name, i++), e));
		}

		return refs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(name);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PerElementCollectionReference other = (PerElementCollectionReference) obj;
		return Objects.equals(name, other.name);
	}

}
