/*******************************************************************************
 * Copyright (c) 2008-2020 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Antonio Garcia-Dominguez - add type parameters
 *     Sina Madani - implements interface rather than extends
 ******************************************************************************/
package org.eclipse.epsilon.eol.types;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class EolMap<K, V> implements Map<K, V> {
	
	protected Map<K, V> wrapped;
	
	/**
	 * 
	 * @param mapImpl
	 * @since 2.2
	 */
	protected EolMap(Map<K, V> mapImpl) {
		this.wrapped = mapImpl;
	}
	
	public EolMap() {
		this(new LinkedHashMap<K, V>());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Map)) return false;
		return o instanceof EolMap ?
			this.getClass() == o.getClass() && this.wrapped.equals(((EolMap<?,?>)o).wrapped) :
			o.equals(wrapped);
	}

	@Override
	public int hashCode() {
		return Objects.hash(wrapped, getClass().getName());
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName()+" "+wrapped.toString();
	}
	
	@Override
	public int size() {
		return wrapped.size();
	}

	@Override
	public boolean isEmpty() {
		return wrapped.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return wrapped.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return wrapped.containsValue(value);
	}

	@Override
	public V get(Object key) {
		return wrapped.get(key);
	}

	@Override
	public V put(K key, V value) {
		return wrapped.put(key, value);
	}

	@Override
	public V remove(Object key) {
		return wrapped.remove(key);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		wrapped.putAll(m);
	}

	@Override
	public void clear() {
		wrapped.clear();
	}

	@Override
	public Set<K> keySet() {
		return wrapped.keySet();
	}

	@Override
	public Collection<V> values() {
		return wrapped.values();
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return wrapped.entrySet();
	}

	@Override
	public V getOrDefault(Object key, V defaultValue) {
		return wrapped.getOrDefault(key, defaultValue);
	}

	@Override
	public void forEach(BiConsumer<? super K, ? super V> action) {
		wrapped.forEach(action);
	}

	@Override
	public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
		wrapped.replaceAll(function);
	}

	@Override
	public V putIfAbsent(K key, V value) {
		return wrapped.putIfAbsent(key, value);
	}

	@Override
	public boolean remove(Object key, Object value) {
		return wrapped.remove(key, value);
	}

	@Override
	public boolean replace(K key, V oldValue, V newValue) {
		return wrapped.replace(key, oldValue, newValue);
	}

	@Override
	public V replace(K key, V value) {
		return wrapped.replace(key, value);
	}

	@Override
	public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
		return wrapped.computeIfAbsent(key, mappingFunction);
	}

	@Override
	public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		return wrapped.computeIfPresent(key, remappingFunction);
	}

	@Override
	public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		return wrapped.compute(key, remappingFunction);
	}

	@Override
	public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
		return wrapped.merge(key, value, remappingFunction);
	}
}
