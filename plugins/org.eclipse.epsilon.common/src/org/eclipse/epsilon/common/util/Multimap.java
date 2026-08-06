/*******************************************************************************
 * Copyright (c) 2010-2018 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 *     Sina Madani - concurrency support, refactoring, additional methods
 ******************************************************************************/
package org.eclipse.epsilon.common.util;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;
import org.eclipse.epsilon.common.concurrent.ConcurrencyUtils;

/**
 * A {@linkplain Map} which allows each to be associated with multiple values.
 * @param <K> The key type.
 * @param <V> The type of values.
 * @since 1.6 implements Map interface, additional utility methods
 */
public class Multimap<K, V> implements Map<K, Collection<V>> {
	
	protected final Map<K, Collection<V>> storage;
	protected final boolean isConcurrent, isReadOptimized;
	
	/**
	 * Creates a non-concurrent Multimap.
	 */
	public Multimap() {
		this(false, false);
	}
	
	/**
	 * 
	 * @param concurrent Whether this implementation should be thread-safe.
	 * 
	 * @since 1.6
	 */
	public Multimap(boolean concurrent) {
		this(concurrent, false);
	}
	
	/**
	 * 
	 * @param concurrent Whether this implementation should be thread-safe.
	 * 
	 * @param readOnly Whether this Multimap (i.e. both the underlying Map itself and
	 * collections associated with keys) will be immutable or rarely written to.
	 * 
	 * @since 1.6
	 */
	public Multimap(boolean concurrent, boolean readOnly) {
		this(concurrent, readOnly, null);
	}
	
	/**
	 * 
	 * @param concurrent Whether this implementation should be thread-safe.
	 * 
	 * @param readOnly Whether this Multimap (i.e. both the underlying Map itself and
	 * collections associated with keys) will be immutable or rarely written to.
	 * 
	 * @param other The Multimap to copy from.
	 * 
	 * @since 1.6
	 */
	public Multimap(boolean concurrent, boolean readOnly, Map<K, ? extends Collection<V>> other) {
		this.isConcurrent = concurrent;
		this.isReadOptimized = readOnly;
		this.storage = newMapDelegate(other != null ? other : null);
	}
	
	/**
	 * Copy constructor.
	 * @param other The Multimap to copy from.
	 * @throws NullPointerException If the Multimap is null.
	 */
	public Multimap(Multimap<K, V> other) {
		this(Objects.requireNonNull(other).isConcurrent, other.isReadOptimized, other.storage);
	}
	
	/**
	 * Convenience method for creating a new underlying Multimap.
	 * 
	 * @param initial The elements to add to the multimap.
	 * @since 1.6
	 * @see #newMapDelegate(Map)
	 */
	protected final Map<K, Collection<V>> newMapDelegate() {
		return newMapDelegate(null);
	}
	
	/**
	 * Convenience method for creating a new underlying Multimap.
	 * 
	 * @param initial The elements to add to the multimap.
	 * @return A suitable Map based on the thread-safety of this Multimap.
	 */
	protected Map<K, Collection<V>> newMapDelegate(Map<? extends K, ? extends Collection<V>> initial) {
		return isConcurrent ? ConcurrencyUtils.concurrentMap(initial) :
			initial != null ? new HashMap<>(initial) : new HashMap<>();
	}
	
	/**
	 * Convenience method for creating new values for entries.
	 * @return A suitable collection based on the thread-safety of this Multimap.
	 * @see #newCollection(Collection)
	 * @since 1.6
	 */
	protected final Collection<V> newCollection() {
		return newCollection(null);
	}
	
	/**
	 * Convenience method for creating new values for entries.
	 * @param values The items to add to the returned collection.
	 * @return A suitable collection based on the thread-safety of this Multimap.
	 * @since 1.6
	 */
	protected Collection<V> newCollection(Collection<? extends V> values) {
		if (isReadOptimized) {
			return values != null ? new CopyOnWriteArrayList<>(values) : new CopyOnWriteArrayList<>();
		}
		else return isConcurrent ?
			ConcurrencyUtils.concurrentOrderedCollection(values) :
			values != null ? new ArrayList<>(values) : new LinkedList<>();
	} 
	
	/**
	 * Returns all values associated with the key. The returned collection is immutable.
	 * @param key
	 * @return The associated values, or an empty collection if the key was not present.
	 */
	@Override
	public Collection<V> get(Object key) {
		Collection<V> values = storage.get(key);
		return values != null ?
			Collections.unmodifiableCollection(values) : Collections.emptyList();
	}
	
	/**
	 * Returns all values associated with the key. The returned collection is the collection used
	 * internally to associate the key with its values.
	 * 
	 * @param key
	 * @return A collection of the associated values, or <code>null</code> if no mapping was present.
	 * @since 1.6
	 */
	public Collection<V> getMutable(Object key) {
		return storage.get(key);
	}

	/**
	 * Adds the given value to the associations for the key.
	 * @param key
	 * @param value
	 * @return Whether a mapping existed for the key.
	 */
	public boolean put(K key, V value) {
		Collection<V> values = storage.get(key);
		boolean wasPresent = true;
		
		if (values == null) {
			wasPresent = false;
			storage.put(key, values = newCollection());
		}
		
		values.add(value);
		return wasPresent;
	}

	/**
	 * Associates the value with the key if the key already exists.
	 * Note that this operation will succeed even if the key has
	 * no values associated with it; only if the key is present.
	 * 
	 * @param key
	 * @param value
	 * @return Whether the key was present and value was added.
	 * @since 1.6
	 */
	public boolean putIfPresent(Object key, V value) {
		Collection<V> values = storage.get(key);
		return values != null && values.add(value);
	}
	
	/**
	 * Adds all of the specified values to the collection associated with
	 * the key if a mapping for the key exists in this Multimap.
	 * @param key
	 * @param values The additional values to associate with the key.
	 * @return Whether the key was present and the values were added.
	 * @since 1.6
	 */
	public boolean putAllIfPresent(K key, Collection<V> values) {
		return putAll(key, values, false, false, true);
	}
	
	/**
	 * Removes the specified value associated with the given key.
	 * @param key
	 * @param value
	 * @return Whether the key was present and the associated collection of values was changed.
	 */
	@Override
	public boolean remove(Object key, Object value) {
		Collection<V> col = storage.get(key);
		return col != null && col.remove(value);
	}

	/**
	 * Removes all values associated with the key, leaving the key itself in place,
	 * by calling {@link Collection#clear()} if the mapping is present.
	 * @param key
	 * @return Whether the key was present and the collection changed as a result.
	 * @since 1.6
	 */
	public boolean removeAll(Object key) {
		Collection<V> values = storage.get(key);
		boolean changed = false;
		if (values != null) {
			changed = !values.isEmpty();
			values.clear();
		}
		return changed;
	}
	
	/**
	 * Removes all of the specified values associated with the key. Note that
	 * if the collection contains all values associated with the key, the key itself
	 * will not be removed.
	 * 
	 * @param key
	 * @param values The values to remove.
	 * @return Whether the key was present and the associated collection of
	 * values was changed as a result of this call.
	 * @since 1.6
	 */
	public boolean removeAll(Object key, Collection<V> values) {
		Collection<V> oldValues = storage.get(key);
		return oldValues != null && oldValues.removeAll(values);
	}
	
	/**
	 * Deletes the specified key from this map.
	 * @param key
	 * @return The values associated with the key, or <code>null</code> if no mapping was present.
	 */
	@Override
	public Collection<V> remove(Object key) {
		return storage.remove(key);
	}
	
	/**
	 * Removes all mappings.
	 */
	@Override
	public void clear() {
		storage.clear();
	}

	/**
	 * 
	 * @param key
	 * @return Whether the given key has one or more values associated with it.
	 */
	@Override
	public boolean containsKey(Object key) {
		Collection<V> col = storage.get(key);
		return col != null && !col.isEmpty();
	}
	
	/**
	 *
	 * @param key
	 * @return Whether the key is present in they keySet.
	 * @since 1.6
	 */
	public boolean hasKey(K key) {
		return storage.containsKey(key);
	}
	
	/**
	 * Associates all of the specified values for the given key. Previously associated
	 * values will not be removed.
	 * @param key
	 * @param values The additional values to associate with the key.
	 * @return Whether the key was present.
	 * @since 1.6 Values are wrapped into the appropriate collection, and existing values are appended to rather than replaced.
	 */
	public final boolean putAll(K key, Collection<V> values) {
		return putAll(key, values, true);
	}
	
	/**
	 * Associates all of the specified values for the given key. Previously associated
	 * values will not be removed.
	 * @param key
	 * @param values The additional values to associate with the key.
	 * @param wrap Whether to wrap the provided values into a new collection.
	 * @return Whether the key was present.
	 * @since 1.6
	 */
	public boolean putAll(K key, Collection<V> values, boolean wrap) {
		boolean wasPresent = storage.containsKey(key) && storage.get(key).addAll(values);
		if (!wasPresent) {
			put(key, values, wrap);
		}
		return wasPresent;
	}
	
	/**
	 * Replaces the values associated with the key with the values from
	 * the specified collection. The Collection associated with the key itself
	 * will not be replaced; only its values will be.
	 * Note that unlike putAll, a new entry will not be created if a mapping for
	 * the key doesn't exist.
	 * 
	 * @param key
	 * @param values The collection of values to copy from.
	 * @return Whether the collection was changed as a result of 
	 * @since 1.6
	 */
	public boolean replaceValues(K key, Collection<V> values) {
		return putAll(key, values, false, true, true);
	}
	
	/**
	 * Associates the key with the given values, overwriting the entry if
	 * the key is present. Note that the associated collection will not
	 * be the same object as the parameter.
	 * 
	 * @param key
	 * @param values The values to copy from.
	 * @param wrap Whether to wrap the provided values into a new collection.
	 * @return The previously associated values, or <code>null</code> if no
	 * mapping was present.
	 * @since 1.6
	 */
	public Collection<V> put(K key, Collection<V> values, boolean wrap) {
		return storage.put(key, wrap || values == null ? newCollection(values) : values);
	}
	
	@Override
	public Collection<V> put(K key, Collection<V> values) {
		return put(key, values, true);
	}
	
	/**
	 * Replaces the entry in this Multimap with the specified values.
	 * @param key
	 * @param values The values to associate with the key.
	 * @param create Creates a new entry with the values if one doesn't exist.
	 * @param replace Whether to append or replace existing values.
	 * @param wrap Whether to wrap the values into a known data structure.
	 * @since 1.6
	 * @return Whether the operation had an effect on this Multimap.
	 */
	protected boolean putAll(K key, Collection<V> values, boolean create, boolean replace, boolean wrap) {
		Collection<V> existingValues = storage.get(key);
		final boolean containsKey = existingValues != null;
		
		if (!containsKey && !create) {
			return false;
		}
		else if ((containsKey && replace) || (!containsKey && create)) {
			storage.put(key, wrap ? newCollection(values) : values);
			return true;
		}
		else if (containsKey && !replace) {
			return existingValues.addAll(values);
		}
		else {
			return false;
		}
	}
	
	/**
	 * 
	 * @return
	 * @since 1.6
	 */
	public boolean isThreadSafe() {
		return isConcurrent;
	}
	
	/**
	 * Streams the values for the given key.
	 * @param key
	 * @return The stream of values for the key, or an empty stream if the key is not present.
	 * @since 1.6
	 */
	public Stream<V> stream(Object key) {
		Collection<V> colForKey = storage.get(key);
		return colForKey != null ? colForKey.stream() : Stream.empty();
	}
	
	/**
	 * 
	 * @return A flattened Stream of all values in this Multimap.
	 * @since 1.6
	 */
	public Stream<V> streamAll() {
		return storage.values().stream().flatMap(Collection::stream);
	}
	
	/**
	 * 
	 * @return An immutable KeySetView of this Multimap.
	 * @since 1.6
	 */
	@Override
	public Set<Map.Entry<K, Collection<V>>> entrySet() {
		return Collections.unmodifiableSet(storage.entrySet());
	}
	
	/**
	 * 
	 * @return The number of keys.
	 * @since 1.6
	 */
	@Override
	public int size() {
		return storage.size();
	}
	
	/**
	 * Returns the number of values associated with the key.
	 * 
	 * @param key
	 * @return The number of associated values, or <code>-1</code> if
	 * the key was not present.
	 * @since 1.6
	 */
	public int size(K key) {
		Collection<V> values = storage.get(key);
		return values != null ? values.size() : -1;
	}
	
	/**
	 * @since 1.6
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof Multimap)) return false;
		
		Multimap<?, ?> other = (Multimap<?, ?>) obj;
		
		return this.storage.equals(other.storage);
	}
	
	/**
	 * @since 1.6
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(storage);
	}
	
	@Override
	public String toString() {
		return storage.toString();
	}

	/**
	 * @since 1.6
	 */
	@Override
	public boolean isEmpty() {
		return storage.isEmpty();
	}

	/**
	 * @since 1.6
	 */
	@Override
	public boolean containsValue(Object value) {
		return storage.containsValue(value) ||
			streamAll()
			.anyMatch(v -> Objects.equals(v, value));
	}

	/**
	 * @since 1.6
	 */
	@Override
	public void putAll(Map<? extends K, ? extends Collection<V>> m) {
		storage.putAll(m);
	}

	/**
	 * @since 1.6
	 */
	@Override
	public Set<K> keySet() {
		return storage.keySet();
	}

	/**
	 * @since 1.6
	 */
	@Override
	public Collection<Collection<V>> values() {
		return storage.values();
	}
}
