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

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;

import org.eclipse.epsilon.eol.types.EolTuple;
import org.eclipse.epsilon.eol.types.EolTupleType;

public class TupleReference extends IdentifiableReference<EolTuple> {

	private final String name;

	public TupleReference(String name, EolTuple tuple) {
		super(tuple);
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getTypeName() {
		return new EolTupleType().getName();
	}

	@Override
	public List<IVariableReference> getVariables(SuspendedState state) {
		final List<IVariableReference> refs = new ArrayList<>(target.size());

		for (Entry<String, Object> entry : target.entrySet()) {
			refs.add(state.getValueReference(entry.getKey(), entry.getValue()));
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
		TupleReference other = (TupleReference) obj;
		return Objects.equals(name, other.name);
	}

}
