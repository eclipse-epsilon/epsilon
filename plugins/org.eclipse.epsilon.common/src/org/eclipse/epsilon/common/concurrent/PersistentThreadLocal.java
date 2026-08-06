/*********************************************************************
 * Copyright (c) 2017 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.common.concurrent;

import java.util.*;
import java.util.function.Supplier;

/**
 * Thread-local storage which retains values for all threads.
 * 
 * @see https://dzone.com/articles/how-threadlocal-implemented
 * @see https://stackoverflow.com/questions/2795447/is-there-no-way-to-iterate-over-or-copy-all-the-values-of-a-java-threadlocal
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class PersistentThreadLocal<T> extends ThreadLocal<T> {

	protected final Map<Thread, T> allValues;
	protected final Supplier<? extends T> valueGetter;
	
	public PersistentThreadLocal(Supplier<? extends T> initialValue) {
		this(0, initialValue);
	}
	
	public PersistentThreadLocal(int numThreads, Supplier<? extends T> initialValue) {
		numThreads = numThreads > 0 ? numThreads : ConcurrencyUtils.DEFAULT_PARALLELISM;
		//Don't use a WeakHashMap because we want to persist the values!
		allValues = ConcurrencyUtils.concurrentMap(numThreads, numThreads);
		valueGetter = initialValue;
	}
	
	@Override
	protected T initialValue() {
		T value = valueGetter != null ? valueGetter.get() : super.initialValue();
		allValues.put(Thread.currentThread(), value);
		return value;
	}
	
	@Override
	public void set(T value) {
		super.set(value);
		allValues.put(Thread.currentThread(), value);
	}

	@Override
	public void remove() {
		super.remove();
		allValues.remove(Thread.currentThread());
	}
	
	public Collection<T> getAll() {
		return allValues.values();
	}
	
	public int size() {
		return allValues.size();
	}
	
	public void removeAll() {
		allValues.clear();
		remove();
	}
}
