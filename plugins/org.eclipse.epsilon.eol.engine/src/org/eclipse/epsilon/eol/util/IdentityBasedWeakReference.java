/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.util;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * Weak reference which can be used as a key of an identity-based map.
 */
public class IdentityBasedWeakReference extends WeakReference<Object> {
	private final int hashCode;

	public IdentityBasedWeakReference(Object referent, ReferenceQueue<Object> referenceQueue) {
		super(referent, referenceQueue);
		hashCode = System.identityHashCode(referent);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof IdentityBasedWeakReference)) {
			return false;
		}
		final IdentityBasedWeakReference other = (IdentityBasedWeakReference)obj;
		return hashCode == other.hashCode && this.get() == other.get();
	}

	@Override
	public int hashCode() {
		return hashCode;
	}
}
