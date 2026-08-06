/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.dt.widgets;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.epsilon.egl.dt.widgets.ListListener.ChangeType;

class ListModel<T> {

	private final List<ListListener> changeListeners = new LinkedList<>();
	private final List<T> orderedList = new LinkedList<>();
	
	public void replaceAllWith(Collection<T> items) {
		orderedList.clear();
		orderedList.addAll(items);
	}
	
	public Collection<T> getItems() {
		return orderedList;
	}
	
	public void add(T item) {
		orderedList.add(item);
		notifyChangeListeners(ChangeType.ADDED, item, orderedList.size()-1);
	}
	
	public void remove(int... selectedIndices) {
		for (int selectedIndex : new Selection(selectedIndices).indicesInDescendingOrder()) {
			remove(selectedIndex);
		}
	}
	
	private void remove(int index) {
		final Object removedItem = orderedList.remove(index);
		notifyChangeListeners(ChangeType.REMOVED, removedItem, index);
	}
	
	public void moveUp(int... selectedIndices) {
		for (int selectedIndex : new Selection(selectedIndices).excludingTop().indicesInAscendingOrder()) {
			moveUp(selectedIndex);
		}
	}

	private void moveUp(int selectedIndex) {
		final T item = orderedList.remove(selectedIndex);
		final int newIndex = selectedIndex-1;
		
		orderedList.add(newIndex, item);
		notifyChangeListeners(ChangeType.MOVED_UP, item, newIndex);
	}
	
	public void moveDown(int... selectedIndices) {
		for (int selectedIndex : new Selection(selectedIndices).excludingBottom(orderedList.size()-1).indicesInDescendingOrder()) {
			moveDown(selectedIndex);
		}
	}
	
	private void moveDown(int selectedIndex) {
		final T item = orderedList.remove(selectedIndex);
		final int newIndex = selectedIndex+1;
		
		orderedList.add(newIndex, item);
		notifyChangeListeners(ChangeType.MOVED_DOWN, item, newIndex);
	}
	
	private void notifyChangeListeners(ChangeType changeType, Object item, int index) {
		for (ListListener changeListener : changeListeners) {
			changeListener.changed(changeType, item, index);
		}
	}

	public void addChangeListener(ListListener listener) {
		changeListeners.add(listener);
	}
	
	public void removeChangeListener(ListListener listener) {
		changeListeners.remove(listener);
	}
	
	
	private static class Selection {
		
		private final List<Integer> indices = new LinkedList<>();
		
		public Selection(int... indices) {
			for (int index : indices) {
				this.indices.add(index);
			}
		}
		
		private Selection(Collection<Integer> indices) {
			this.indices.addAll(indices);
		}
		
		public Iterable<Integer> indicesInAscendingOrder() {
			return Collections.unmodifiableCollection(indices);
		}
		
		public Iterable<Integer> indicesInDescendingOrder() {
			final LinkedList<Integer> sortedIndices = new LinkedList<>(indices);
			Collections.sort(sortedIndices, Collections.reverseOrder());
			return Collections.unmodifiableCollection(sortedIndices);
		}
		
		public Selection excludingTop() {
			return excludingSequenceAtStart(0, 1, indicesInAscendingOrder());
		}
		
		public Selection excludingBottom(int lastIndex) {
			return excludingSequenceAtStart(lastIndex, -1, indicesInDescendingOrder());
		}

		private Selection excludingSequenceAtStart(int startOfSequence, int step, Iterable<Integer> orderedIndices) {
			final List<Integer> indicesWithoutSequence = new LinkedList<>();
			int nextInSequence = startOfSequence;
			
			for (int index : orderedIndices) {
				if (index == nextInSequence) {
					nextInSequence += step;
					
				} else {
					indicesWithoutSequence.add(index);
				}
			}
			
			return new Selection(indicesWithoutSequence);
		}
	}
}
