/*******************************************************************************
 * Copyright (c) 2012-2020 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Sina Madani - extends {@link EolCollection}
 ******************************************************************************/
package org.eclipse.epsilon.eol.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import java.util.function.UnaryOperator;

@SuppressWarnings("unchecked")
public class EolSequence<T> extends EolCollection<T> implements List<T>, RandomAccess {
	
	public EolSequence() {
		super(new ArrayList<>());
	}
	
	/**
	 * 
	 * @param minSize
	 * @since 1.6
	 */
	public void ensureCapacity(int minSize) {
		if (wrapped instanceof ArrayList) {
			((ArrayList<?>) wrapped).ensureCapacity(minSize);
		}
	}
	
	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		return ((List<T>) wrapped).addAll(index, c);
	}

	@Override
	public void replaceAll(UnaryOperator<T> operator) {
		((List<T>) wrapped).replaceAll(operator);
	}

	@Override
	public void sort(Comparator<? super T> c) {
		((List<T>) wrapped).sort(c);
	}

	@Override
	public T get(int index) {
		return ((List<T>) wrapped).get(index);
	}

	@Override
	public T set(int index, T element) {
		return ((List<T>) wrapped).set(index, element);
	}

	@Override
	public void add(int index, T element) {
		((List<T>) wrapped).add(index, element);
	}

	@Override
	public T remove(int index) {
		return ((List<T>) wrapped).remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return ((List<T>) wrapped).indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return ((List<T>) wrapped).lastIndexOf(o);
	}

	@Override
	public ListIterator<T> listIterator() {
		return ((List<T>) wrapped).listIterator();
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		return ((List<T>) wrapped).listIterator(index);
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		return ((List<T>) wrapped).subList(fromIndex, toIndex);
	}
}
