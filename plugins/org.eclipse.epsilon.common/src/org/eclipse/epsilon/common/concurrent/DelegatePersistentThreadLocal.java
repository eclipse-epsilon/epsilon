/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.common.concurrent;

import java.util.function.Supplier;
import org.eclipse.epsilon.common.function.BaseDelegate;
import org.eclipse.epsilon.common.function.BaseDelegate.MergeMode;

/**
 * A {@link PersistentThreadLocal} which merges its values into the base (its delegate)
 * when a thread-local value is removed.
 * 
 * @author Sina Madani
 * @param <T> The type of the thread-local value.
 * @since 1.6
 */
public class DelegatePersistentThreadLocal<T extends BaseDelegate<?>> extends PersistentThreadLocal<T> {

	public DelegatePersistentThreadLocal(int numThreads, Supplier<? extends T> initialValue) {
		super(numThreads, initialValue);
	}

	public DelegatePersistentThreadLocal(Supplier<? extends T> initialValue) {
		super(initialValue);
	}
	
	public void remove(MergeMode mode) {
		if (mode != null) get().merge(mode);
		remove();
	}
	
	public void removeAll(MergeMode mode) {
		if (mode != null) getAll().forEach(t -> t.merge(mode));
		removeAll();
	}
}
