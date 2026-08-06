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

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Convenience class for storing collections of thread-local data.
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class ThreadLocalBatchData<D> extends PersistentThreadLocal<List<D>> {
	
	public ThreadLocalBatchData() {
		this(0);
	}
	
	public ThreadLocalBatchData(int numThreads) {
		super(numThreads, ArrayList::new);
	}
	
	public static <K, V> ThreadLocalBatchData<Entry<? extends K, ? extends V>> ofEntry() {
		return new ThreadLocalBatchData<>();
	}
	
	public static <K, V> ThreadLocalBatchData<Entry<? extends K, ? extends V>> ofEntry(int parallelism) {
		return new ThreadLocalBatchData<>(parallelism);
	}
	
	public static ThreadLocalBatchData<?> ofAny(int parallelism) {
		return new ThreadLocalBatchData<>(parallelism);
	}
	
	public static ThreadLocalBatchData<?> ofAny() {
		return new ThreadLocalBatchData<>();
	}

	public Collection<D> getBatch() {
		return getAll().stream()
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
	}
	
	public void addElement(D value) {
		getAndThen(data -> data.add(value));
	}
	
	public void removeElement(D value) {
		getAndThen(data -> data.remove(value));
	}
	
	public void updateElement(D oldValue, D newValue) {
		getAndThen(data -> data.replaceAll(value -> Objects.equals(value, oldValue) ? newValue : value));
	}
	
	protected void getAndThen(Consumer<List<D>> dataConsumer) {
		List<D> data = get();
		if (data != null)
			dataConsumer.accept(data);
	}
}
