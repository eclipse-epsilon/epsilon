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
import org.eclipse.epsilon.eol.dap.variables.maps.MapEntryReference.MapEntryTarget;
import org.eclipse.epsilon.eol.execute.context.IEolContext;

public class MapEntryReference extends IdentifiableReference<MapEntryTarget> {

	public static final String VALUE_VARIABLE_NAME = "value";
	public static final String KEY_VARIABLE_NAME = "key";

	/**
	 * Target that points at a specific key of a specific map. We don't use equality
	 * between maps as that could be potentially quite expensive to compute
	 * repeatedly.
	 */
	protected static class MapEntryTarget {
		public MapEntryTarget(Map<Object, Object> m, Object k) {
			this.map = m;
			this.key = k;
		}

		public final Map<?, ?> map;
		public final Object key;

		@Override
		public int hashCode() {
			return Objects.hash(key, System.identityHashCode(map));
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			MapEntryTarget other = (MapEntryTarget) obj;
			return Objects.equals(key, other.key) && map == other.map;
		}
	}
	
	private final String name;

	public MapEntryReference(IEolContext context, String name, Map<Object, Object> target, Object key) {
		super(context, new MapEntryTarget(target, key));
		this.name = name;
	}

	@Override
	public String getTypeName() {
		return "MapEntry";
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getValue() {
		return String.format("Map entry for key: %s", target.key);
	}

	@Override
	public List<IVariableReference> getVariables(SuspendedState state) {
		final List<IVariableReference> refs = new ArrayList<>(2);

		refs.add(state.getValueReference(context, KEY_VARIABLE_NAME, target.key));
		refs.add(state.getValueReference(context, VALUE_VARIABLE_NAME, target.map.get(target.key)));

		return refs;
	}
	
}
