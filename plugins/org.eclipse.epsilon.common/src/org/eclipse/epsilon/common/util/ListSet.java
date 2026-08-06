/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ListSet<E> implements Set<E> {
	
	protected ArrayList<E> storage = new ArrayList<>();

	@Override
	public boolean add(E e) {
		if (contains(e)) {
			return false;
		}
		else {
			return storage.add(e);
		}
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean result = c.size() > 0;
		for (E e : c) {
			result = result & add(e);
		}
		return result;
	}

	@Override
	public void clear() {
		storage.clear();
	}

	@Override
	public boolean contains(Object o) {
		for (E e : storage) {
			if (e == o || e.equals(o)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		boolean result = c.size() > 0;
		Iterator<E> it = storage.iterator();
		while (it.hasNext()) {
			E e = it.next();
			result = result & contains(e);
		}
		return result;
	}

	@Override
	public boolean isEmpty() {
		return storage.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		return storage.iterator();
	}

	@Override
	public boolean remove(Object o) {
		return storage.remove(o);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return storage.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return storage.retainAll(c);
	}

	@Override
	public int size() {
		return storage.size();
	}

	@Override
	public Object[] toArray() {
		return storage.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return storage.toArray(a);
	}
	
	public List<E> getList() {
		return storage;
	}
}
