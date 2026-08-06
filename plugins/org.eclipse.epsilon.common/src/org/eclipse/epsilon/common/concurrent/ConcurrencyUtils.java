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
import java.util.concurrent.*;
import org.eclipse.epsilon.common.util.SizeCachingConcurrentQueue;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class ConcurrencyUtils {
	
	public static final Thread TOP_LEVEL_THREAD = Thread.currentThread();
	
	public static final boolean isMainThread() {
		return Thread.currentThread().getName().equals("main");
	}
	
	public static final boolean isTopLevelThread() {
		return Thread.currentThread() == TOP_LEVEL_THREAD;
	}
	
	/**
	 * The number of logical cores in the system.
	 */
	public static final int DEFAULT_PARALLELISM = Runtime.getRuntime().availableProcessors();
	
	//Should be a power of 2.
	private static final int DEFAULT_CAPACITY = 16;
	//@see https://stackoverflow.com/questions/10901752/what-is-the-significance-of-load-factor-in-hashmap
	private static final float DEFAULT_LOAD_FACTOR = 0.7f;
	
	public static final <T> Collection<T> concurrentOrderedCollection() {
		return concurrentOrderedCollection(null);
	}
	
	public static <T> Collection<T> concurrentOrderedCollection(Collection<? extends T> values) {
		return values != null ?
			new SizeCachingConcurrentQueue<>(values) :
			new SizeCachingConcurrentQueue<>();
	}
	
	public static <T> Set<T> concurrentSet() {
		return concurrentSet(-1, -1);
	}
	
	public static <T> Set<T> concurrentSet(Collection<T> initial) {
		Set<T> cs = ConcurrencyUtils.<T, Boolean>newConcurrentHashMap(
				null, initial != null ? initial.size() : -1, -1
			).keySet(Boolean.TRUE);
		if (cs != null) cs.addAll(initial);
		return cs;
	}
	
	public static <T> Set<T> concurrentSet(int initialCapacity, int parallelism) {
		return ConcurrencyUtils.<T, Boolean>newConcurrentHashMap(null, initialCapacity, parallelism).keySet(Boolean.TRUE);
	}
	
	public static <K, V> ConcurrentMap<K, V> concurrentMap() {
		return newConcurrentHashMap(null, -1, -1);
	}
	
	public static <K, V> ConcurrentMap<K, V> concurrentMap(Map<? extends K, ? extends V> initial) {
		return newConcurrentHashMap(initial, -1, -1);
	}
	
	public static <K, V> ConcurrentMap<K, V> concurrentMap(int initialCapacity, int parallelism) {
		return newConcurrentHashMap(null, initialCapacity, parallelism);
	}
	
	private static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap(Map<? extends K, ? extends V> initial, int initialCapacity, int parallelism) {
		if (initial != null) return new ConcurrentHashMap<>(initial);
		else return new ConcurrentHashMap<>(
			initialCapacity >= 0 ? initialCapacity : DEFAULT_CAPACITY,
			DEFAULT_LOAD_FACTOR,
			parallelism > 0 ? parallelism : 1+DEFAULT_PARALLELISM/2
		);
	}
	
	/**
	 * Executes the two tasks asynchronously and blocks the calling thread until both have completed.
	 * 
	 * @param t1
	 * @param t2
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public static void executeAsync(Runnable r1, Runnable r2) throws InterruptedException, ExecutionException {
		CompletableFuture.runAsync(r1)
			.thenCombine(
				CompletableFuture.runAsync(r2),
				(v1, v2) -> null
			)
			.get();
	}
	
	public static Collection<Callable<?>> runnableToCallable(Collection<? extends Runnable> runnables) {
		Collection<Callable<?>> callables = new ArrayDeque<>(runnables.size());
		for (Runnable r : runnables) {
			callables.add(runnableToCallable(r));
		}
		return callables;
	}
	
	public static Callable<?> runnableToCallable(Runnable runnable) {
		return () -> {
			runnable.run();
			return null;
		};
	}
}
