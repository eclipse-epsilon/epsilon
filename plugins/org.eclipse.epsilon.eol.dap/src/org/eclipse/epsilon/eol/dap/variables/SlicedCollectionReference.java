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
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class SlicedCollectionReference extends CollectionReference {

	private final String name;
	private final int sliceSize;
	
	public SlicedCollectionReference(String name, Collection<Object> t, int sliceSize) {
		super(t);

		this.name = name;
		this.sliceSize = sliceSize;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getValue() {
		return String.format("<sliced %s of %d elements>", target.getClass().getSimpleName(), target.size());
	}

	@Override
	public List<IVariableReference> getVariables(SuspendedState state) {
		final List<IVariableReference> refs = new ArrayList<>(target.size() / sliceSize + 1);
		for (int from = 0; from < target.size(); from += sliceSize) {
			final int to = Math.min(target.size(), from + sliceSize);
			refs.add(state.putOrGetReference(new CollectionSliceReference(name, target, from, to)));
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
		SlicedCollectionReference other = (SlicedCollectionReference) obj;
		return Objects.equals(name, other.name);
	}
	
}
