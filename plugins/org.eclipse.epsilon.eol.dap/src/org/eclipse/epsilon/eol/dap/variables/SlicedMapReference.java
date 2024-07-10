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
import java.util.Map;

import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.types.EolMapType;

public class SlicedMapReference extends IdentifiableReference<Map<Object, Object>> {

	private final int sliceSize;
	private final String name;

	public SlicedMapReference(IEolContext context, String name, Map<Object, Object> m, int sliceSize) {
		super(context, m);
		this.sliceSize = sliceSize;
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
	public String getValue() {
		return String.format("<sliced map of %d elements>", target.size());
	}

	@Override
	public List<IVariableReference> getVariables(SuspendedState state) {
		final List<IVariableReference> refs = new ArrayList<>(target.size() / sliceSize + 1);
		for (int from = 0; from < target.size(); from += sliceSize) {
			final int to = Math.min(target.size(), from + sliceSize);
			refs.add(state.putOrGetReference(new MapSliceReference(context, name, target, from, to)));
		}
		return refs;
	}

}
