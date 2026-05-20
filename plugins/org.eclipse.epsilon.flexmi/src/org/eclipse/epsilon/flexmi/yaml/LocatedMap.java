package org.eclipse.epsilon.flexmi.yaml;

import java.util.Map;

import org.yaml.snakeyaml.nodes.NodeTuple;

public interface LocatedMap<K, V> extends Map<K, V> {
	Object putWithLocation(K key, NodeTuple node, V value);
	NodeTuple getLocation(K key);
}
