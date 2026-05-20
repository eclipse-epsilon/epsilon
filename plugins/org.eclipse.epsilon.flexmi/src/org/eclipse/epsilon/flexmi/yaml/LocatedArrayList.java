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

import java.util.ArrayList;
import java.util.List;

import org.yaml.snakeyaml.nodes.Node;

/**
 * Subclass of an ArrayList which keeps track of the YAML representation tree
 * node that each value came from.
 *
 * Note: this class is only meant to be used during the loading of a YAML
 * document. It assumes the only changes made to the list are to add elements
 * during the loading process.
 */
public class LocatedArrayList<E> extends ArrayList<E> implements LocatedList<E> {

	private static final long serialVersionUID = 1L;

	private List<Node> locations = new ArrayList<>(); 

	public LocatedArrayList() {
		super();
	}

	public LocatedArrayList(int initialCapacity) {
		super(initialCapacity);
	}

	@Override
	public Object addWithLocation(Node n, E value) {
		locations.add(n);
		return add(value);
	}

	@Override
	public Node getLocation(int i) {
		return locations.get(i);
	}

	@Override
	public E remove(int index) {
		locations.remove(index);
		return super.remove(index);
	}

	@Override
	public void clear() {
		locations.clear();
		super.clear();
	}
}
