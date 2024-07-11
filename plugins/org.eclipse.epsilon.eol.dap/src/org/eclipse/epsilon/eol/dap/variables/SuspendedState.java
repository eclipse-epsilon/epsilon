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
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.epsilon.eol.dap.variables.collections.PerElementCollectionReference;
import org.eclipse.epsilon.eol.dap.variables.collections.SlicedCollectionReference;
import org.eclipse.epsilon.eol.dap.variables.maps.PerKeyMapReference;
import org.eclipse.epsilon.eol.dap.variables.maps.SlicedMapReference;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.SingleFrame;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.models.IReflectiveModel;
import org.eclipse.epsilon.eol.types.EolTuple;
import org.eclipse.epsilon.eol.types.EolType;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * Tracks all the information needed to navigate the state of a suspended
 * program. Users should call {@link #suspended()} whenever the program
 * suspends, to allow it to reset.
 */
public class SuspendedState {

	public static final int LARGE_COLLECTION_THRESHOLD = 200;
	public static final int SLICE_SIZE = LARGE_COLLECTION_THRESHOLD / 2;

	private final AtomicInteger nextReference = new AtomicInteger();
	private final BiMap<Integer, IVariableReference> references = HashBiMap.create();

	public void suspended() {
		synchronized (references) {
			nextReference.set(0);
			references.clear();
		}
	}

	public IVariableReference getReference(IEolContext context, SingleFrame sc) {
		synchronized (references) {
			return putOrGetReference(new SingleFrameReference(context, sc));
		}
	}

	public IVariableReference getReference(int id) {
		synchronized (references) {
			return references.get(id);
		}
	}

	public Integer getReferenceId(IVariableReference r) {
		synchronized (references) {
			return references.inverse().get(r);
		}
	}

	@SuppressWarnings("unchecked")
	public IVariableReference getValueReference(IEolContext context, String name, Object value) {
		if (value == null) {
			return new OpaqueValueReference(context, name, value);
		}

		IModel model = context.getModelRepository().getOwningModel(value);
		if (model instanceof IReflectiveModel) {
			IReflectiveModel rModel = (IReflectiveModel) model;
			return putOrGetReference(new ModelElementReference(context, rModel, name, value));
		}

		if (value instanceof Collection) {
			Collection<Object> c = (Collection<Object>) value;

			IdentifiableReference<?> ref;
			if (c.size() >= LARGE_COLLECTION_THRESHOLD) {
				ref = new SlicedCollectionReference(context, name, c, SLICE_SIZE);
			} else {
				ref = new PerElementCollectionReference(context, name, c);
			}
			return putOrGetReference(ref);
		}

		if (value instanceof EolTuple) {
			return putOrGetReference(new TupleReference(context, name, (EolTuple) value));
		}

		if (value instanceof Map) {
			IdentifiableReference<?> ref;
			Map<Object, Object> m = (Map<Object, Object>) value;
			if (m.size() >= LARGE_COLLECTION_THRESHOLD) {
				ref = new SlicedMapReference(context, name, m, SLICE_SIZE);
			} else {
				ref = new PerKeyMapReference(context, name, m);
			}
			return putOrGetReference(ref);
		}

		for (EolType t : IdentifiableReference.PREDEFINED_TYPES) {
			if (t.isKind(value)) {
				return new OpaqueValueReference(context, name, value);
			}
		}

		return putOrGetReference(new JavaObjectReference(context, name, value));
	}

	public IVariableReference putOrGetReference(IdentifiableReference<?> ref) {
		synchronized (references) {
			/*
			 * If we do not have an ID for this reference, generate one.
			 *
			 * Otherwise, reuse the ID.
			 */
			Integer reference = references.inverse().get(ref);
			if (reference == null) {
				reference = nextReference.incrementAndGet();
				references.put(reference, ref);
			}
			ref.setId(reference);
		}

		return ref;
	}

}
