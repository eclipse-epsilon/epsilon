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

import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.epsilon.eol.execute.context.SingleFrame;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * Tracks all the information needed to navigate the state of a suspended
 * program. Users should call {@link #suspended()} whenever the program
 * suspends, to allow it to reset.
 */
public class SuspendedState {

	private final AtomicInteger nextReference = new AtomicInteger();
	private final BiMap<Integer, IVariableReference> references = HashBiMap.create();

	public void suspended() {
		synchronized (references) {
			nextReference.set(0);
			references.clear();
		}
	}

	public IVariableReference getReference(SingleFrame sc) {
		synchronized (references) {
			return putOrGetReference(new SingleFrameReference(sc));
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

	protected IVariableReference getValueReference(String name, Object value) {
		// TODO: structured values
		return new OpaqueValueReference(name, value);
	}

	protected IVariableReference putOrGetReference(IdentifiableReference<?> ref) {
		Integer reference = references.inverse().get(ref);
		if (reference == null) {
			reference = nextReference.incrementAndGet();
			ref.setId(reference);
			references.put(reference, ref);
		}
		return ref;
	}

}
