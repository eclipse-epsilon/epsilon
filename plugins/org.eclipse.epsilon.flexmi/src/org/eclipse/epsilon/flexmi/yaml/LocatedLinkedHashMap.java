/*********************************************************************
 * Copyright (c) 2026 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 **********************************************************************/
package org.eclipse.epsilon.flexmi.yaml;

import java.util.LinkedHashMap;
import java.util.Map;

import org.yaml.snakeyaml.nodes.NodeTuple;

/**
 * Extended version of a map which keeps track of the YAML representation
 * tree node tuple it came from (for its location).
 *
 * Note: this class is only meant to be used during the loading of a YAML
 * document. It assumes the only changes made to the map are to put keys
 * during the loading process.
 */
public class LocatedLinkedHashMap<K, V> extends LinkedHashMap<K, V> implements LocatedMap<K, V> {

	private static final long serialVersionUID = 1L;
	private Map<K, NodeTuple> keyToNode = new LinkedHashMap<>();

	public LocatedLinkedHashMap() {
		super();
	}

	public LocatedLinkedHashMap(int initialCapacity) {
		super(initialCapacity);
	}

	@Override
	public Object putWithLocation(K key, NodeTuple node, V value) {
		keyToNode.put(key, node);
		return put(key, value);
	}

	@Override
	public NodeTuple getLocation(K key) {
		return keyToNode.get(key);
	}

	@Override
	public void clear() {
		keyToNode.clear();
		super.clear();
	}

	@Override
	public V remove(Object o) {
		keyToNode.remove(o);
		return super.remove(o);
	}

}
