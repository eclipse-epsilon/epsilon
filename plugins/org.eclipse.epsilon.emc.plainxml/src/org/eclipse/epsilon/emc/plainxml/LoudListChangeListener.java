/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.plainxml;

public interface LoudListChangeListener<E> extends LoudListListener<E> {
	
	void listChanged(LoudList<E> list);
	
	@Override
	default void objectAdded(LoudList<E> list, E o) {
		listChanged(list);
	}

	@Override
	default void objectAdded(LoudList<E> list, E o, int index) {
		listChanged(list);
	}

	@Override
	default void objectRemoved(LoudList<E> list, E o) {
		listChanged(list);
	}

	@Override
	default void objectRemoved(LoudList<E> list, E o, int index) {
		listChanged(list);
	}
}
