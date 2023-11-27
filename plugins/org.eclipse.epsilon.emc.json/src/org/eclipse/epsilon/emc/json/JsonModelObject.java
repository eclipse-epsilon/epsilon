/*******************************************************************************
 * Copyright (c) 2023 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.json;

import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.collections.collection.UnmodifiableCollection;
import org.apache.commons.collections.set.UnmodifiableSet;
import org.json.simple.JSONObject;

/**
 * Thin wrapper over a {@link JSONObject} which adds the concept of a container to its
 * values.
 */
public class JsonModelObject implements Contained, Map<String, Object>, HasCreatorModel {

	private final JsonModel creatorModel;

	private Set<Object> containers = Collections.newSetFromMap(new IdentityHashMap<>());

	// Underlying map is the same, but we can intercept all calls
	private JSONObject object = new JSONObject();

	public JsonModelObject() {
		this.creatorModel = null;
	}

	public JsonModelObject(JsonModel creatorModel) {
		this.creatorModel = creatorModel;
	}

	@Override
	public JsonModel getCreatorModel() {
		return creatorModel;
	}

	@Override
	public Set<Object> getContainers() {
		return containers;
	}

	@Override
	public int size() {
		return object.size();
	}

	@Override
	public boolean isEmpty() {
		return object.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return object.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return object.containsKey(value);
	}

	@Override
	public Object get(Object key) {
		return object.get(key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object put(String key, Object value) {
		Object ret = object.put(key, value);

		if (value instanceof Contained) {
			((Contained) value).addContainer(this);
		}
		if (ret instanceof Contained) {
			((Contained) ret).removeContainer(this);
		}

		return ret;
	}

	@Override
	public Object remove(Object key) {
		Object ret = object.remove(key);
		if (ret instanceof Contained) {
			((Contained) ret).removeContainer(this);
		}
		return ret;
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {
		for (Entry<? extends String, ? extends Object> e : m.entrySet()) {
			put(e.getKey(), e.getValue());
		}
	}

	@Override
	public void clear() {
		for (Object e : object.values()) {
			if (e instanceof Contained) {
				((Contained) e).removeContainer(this);
			}
		}
		object.clear();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> keySet() {
		/*
		 * NOTE: we use the Apache Commons Collections to avoid issues in Java 9+ with the
		 * fact that the usual Collections.unmodifiable* methods have issues around reflection.
		 */
		return UnmodifiableSet.decorate(object.keySet());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Object> values() {
		// See comment above about use of Apache Commons
		return UnmodifiableCollection.decorate(object.values());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Entry<String, Object>> entrySet() {
		// See comment above about use of Apache Commons
		return UnmodifiableSet.decorate(object.entrySet());
	}

	@Override
	public int hashCode() {
		return Objects.hash(object);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;

		return object.equals(obj);
	}

	@Override
	public String toString() {
		return "JsonModelObject [object=" + object + ", containers=" + containers + "]";
	}

}
