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

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.constructor.ConstructorException;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.SequenceNode;
import org.yaml.snakeyaml.nodes.Tag;

/**
 * Subclass of the default SnakeYAML constructor which uses
 * extended lists and maps that track the YAML representation
 * tree node that each value came from.
 *
 * TODO: support tracking locations of sets? These would not
 * have much use in the Flexmi YAML flavour, however.
 */
public class LocatedSafeConstructor extends SafeConstructor {

	protected static class LocatedSafeConstructorException extends ConstructorException {
		private static final long serialVersionUID = 1L;

		protected LocatedSafeConstructorException(String context, Mark contextMark, String problem, Mark problemMark) {
			super(context, contextMark, problem, problemMark);
		}

		public LocatedSafeConstructorException(String context, Mark contextMark, String problem, Mark problemMark,
				Throwable cause) {
			super(context, contextMark, problem, problemMark, cause);
		}
	}

	public LocatedSafeConstructor(LoaderOptions loaderOptions) {
		super(loaderOptions);
		this.yamlConstructors.put(Tag.MAP, new ConstructYamlMap());
	}

	@Override
	protected Map<Object, Object> createDefaultMap(int initSize) {
		return new LocatedLinkedHashMap<>(initSize);
	}

	@Override
	protected List<Object> createDefaultList(int initSize) {
		return new LocatedArrayList<>(initSize);
	}

	/*
	 * We override this bit to change the put() call to putWithLocation(),
	 * but otherwise keep the code as is.
	 *
	 * TODO: decide if we actually need per-key locations (if not, we can
	 * simplify this method).
	 */
	@Override
	protected void constructMapping2ndStep(MappingNode node, Map<Object, Object> mapping) {
		// New - record the location of the mapping
		LocatedMap<Object, Object> lm = ((LocatedMap<Object, Object>) mapping);
		lm.setLocation(node);

		// From SafeConstructor - needed to support merge nodes
		flattenMapping(node);

		// From BaseConstructor - change put to putWithLocation
		List<NodeTuple> nodeValue = node.getValue();
		for (NodeTuple tuple : nodeValue) {
			Node keyNode = tuple.getKeyNode();
			Node valueNode = tuple.getValueNode();
			Object key = constructObject(keyNode);
			if (key != null) {
				try {
					key.hashCode();// check circular dependencies
				} catch (Exception e) {
					throw new LocatedSafeConstructorException(
						"while constructing a mapping", node.getStartMark(),
						"found unacceptable key " + key, tuple.getKeyNode().getStartMark(), e);
				}
			}

			Object value = constructObject(valueNode);
			if (keyNode.isTwoStepsConstruction()) {
				if (loadingConfig.getAllowRecursiveKeys()) {
					postponeMapFilling(mapping, key, value);
				} else {
					throw new YAMLException(
							"Recursive key for mapping is detected but it is not configured to be allowed.");
				}
			} else {
				// This is the one line we wanted to change
				lm.putWithLocation(key, tuple, value);
			}
		}
	}

	@Override
	protected void constructSequenceStep2(SequenceNode node, Collection<Object> collection) {
		LocatedList<Object> located = (LocatedList<Object>) collection;
		located.setLocation(node);
		for (Node child : node.getValue()) {
			located.addWithLocation(child, constructObject(child));
		}
	}

}
