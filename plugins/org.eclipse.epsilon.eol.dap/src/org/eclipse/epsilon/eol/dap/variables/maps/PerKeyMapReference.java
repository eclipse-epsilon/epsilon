/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.eol.dap.variables.maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.epsilon.eol.dap.variables.IVariableReference;
import org.eclipse.epsilon.eol.dap.variables.IdentifiableReference;
import org.eclipse.epsilon.eol.dap.variables.SuspendedState;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.types.EolMapType;

public class PerKeyMapReference extends IdentifiableReference<Map<Object, Object>> {

	private final String name;

	public PerKeyMapReference(IEolContext context, String name, Map<Object, Object> m) {
		super(context, m);
		this.name = name;
	}

	@Override
	public String getTypeName() {
		return new EolMapType().getName();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<IVariableReference> getVariables(SuspendedState state) {
		final List<IVariableReference> refs = new ArrayList<>(target.size());

		int i = 0;
		for (Object key : target.keySet()) {
			final String entryName = String.format("%s[%d]", name, i++);
			refs.add(state.putOrGetReference(new MapEntryReference(context, entryName, target, key)));
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
		PerKeyMapReference other = (PerKeyMapReference) obj;
		return Objects.equals(name, other.name);
	}

}
