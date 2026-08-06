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
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.eclipse.epsilon.eol.dap.variables.IVariableReference;
import org.eclipse.epsilon.eol.dap.variables.IdentifiableReference;
import org.eclipse.epsilon.eol.dap.variables.SuspendedState;
import org.eclipse.epsilon.eol.execute.context.IEolContext;

public class CollectionSliceReference extends IdentifiableReference<Collection<Object>>{

	private final String name;
	private final int from;
	private final int to;

	/*
	 * Only used if the sliced collection is not a List. Faster than having to skip
	 * to the start of the collection every time.
	 */
	private List<Object> internalList;

	/**
	 * Creates a reference to a slice of a collection. The slice will include all
	 * the elements in the semi-closed range {@code [from, to)}.
	 *
	 * @param name Name of the collection (the name of the slice will be
	 *             automatically computed).
	 * @param t    Target collection.
	 * @param from Starting 0-based index, included.
	 * @param to   Final 0-based index, excluded.
	 */
	public CollectionSliceReference(IEolContext context, String name, Collection<Object> t, int from, int to) {
		super(context, t);

		if (from < 0) {
			throw new IllegalArgumentException("from cannot be negative");
		}
		if (to > t.size()) {
			throw new IllegalArgumentException(String.format(
				"to cannot exceed the size of the collection: %d > %d", to, t.size()));
		}

		this.name = name;
		this.from = from;
		this.to = to;
	}

	@Override
	public String getName() {
		return String.format("%s[%d..%d]", name, from, to - 1);
	}

	@Override
	public String getValue() {
		return String.format("<slice with %d elements>", to - from);
	}

	@Override
	public List<IVariableReference> getVariables(SuspendedState state) {
		final List<IVariableReference> refs = new ArrayList<>(to - from);

		List<Object> l = null;
		if (target instanceof List) {
			l = ((List<Object>) target).subList(from, to);
		} else {
			l = getInternalList();
		}

		int i = from;
		for (Object e : l) {
			refs.add(state.getValueReference(context, String.format("%s[%d]", name, i++), e));
		}

		return refs;
	}

	protected synchronized List<Object> getInternalList() {
		if (internalList == null) {
			internalList = new ArrayList<>(to - from);

			Iterator<Object> it = target.iterator();
			int i = 0;
			while (i < from) {
				it.next();
				i++;
			}

			while (i < to) {
				internalList.add(it.next());
				i++;
			}
		}

		return internalList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(from, name, to);
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
		CollectionSliceReference other = (CollectionSliceReference) obj;
		return from == other.from && Objects.equals(name, other.name) && to == other.to;
	}
	
}
