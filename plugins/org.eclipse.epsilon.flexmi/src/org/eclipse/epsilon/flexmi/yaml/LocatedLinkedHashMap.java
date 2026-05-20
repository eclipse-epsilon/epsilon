package org.eclipse.epsilon.flexmi.yaml;

import java.util.LinkedHashMap;
import java.util.Map;

import org.yaml.snakeyaml.nodes.NodeTuple;

/**
 * Extended version of a map which keeps track of the YAML representation
 * tree node tuple it came from (for its location).
 */
public class LocatedLinkedHashMap<K, V> extends LinkedHashMap<K, V> implements LocatedMap<K, V> {

	private static final long serialVersionUID = 1L;
	private Map<K, NodeTuple> keyToNode = new LinkedHashMap<>();

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
