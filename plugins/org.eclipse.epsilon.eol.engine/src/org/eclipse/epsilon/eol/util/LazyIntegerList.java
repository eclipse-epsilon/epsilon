/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class LazyIntegerList implements List<Integer> {
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		//System.out.println(new ArrayList<Object>().get(5));
		
		LazyIntegerList list = new LazyIntegerList(10, 5);
		ListIterator<Integer> li = list.listIterator();
		//System.out.println(li.hasNext());
		//System.out.println(li.next());
		//System.out.println(li.hasNext());
		
		System.err.println(list.get(10));
		
		//for (int i : list) {
		//	System.out.println(i);
		//}
		
	}
	
	protected int min, max;
	
	public LazyIntegerList(int min, int max) {
		this.min = min;
		this.max = max;
	}
	
	@Override
	public boolean add(Integer e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void add(int index, Integer element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(Collection<? extends Integer> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(int index, Collection<? extends Integer> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean contains(Object o) {
		if (!(o instanceof Integer)) return false;
		Integer i = (Integer) o;
		if (min <= max) return min <= i && i <= max;
		else return max <= i && i <= min;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object o : c) {
			if (!contains(o)) return false;
		}
		return size() > 0;
	}

	@Override
	public Integer get(int index) {
		if (index >= size()) throw new IndexOutOfBoundsException("Index: " + index + ", Size: " +  size());
		if (min <= max) return min + index;
		else return min - index;
	}

	@Override
	public int indexOf(Object o) {
		if (!(o instanceof Integer)) return -1;
		Integer i = (Integer) o;
		if (!contains(i)) return -1;
		if (min <= max) return i - min;
		else return min - i;
	}

	@Override
	public boolean isEmpty() {
		return size() > 0;
	}

	@Override
	public Iterator<Integer> iterator() {
		return listIterator();
	}

	@Override
	public int lastIndexOf(Object o) {
		return indexOf(o);
	}

	@Override
	public ListIterator<Integer> listIterator() {
		return new LazyIntegerListIterator(min, max);
	}

	@Override
	public ListIterator<Integer> listIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Integer remove(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Integer set(int index, Integer element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		return Math.abs(max - min) + 1;
	}

	@Override
	public List<Integer> subList(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray() {
		Object[] array = new Object[size()];
		int j = 0;
		for (int i : this) {
			array[j] = i;
			j++;
		}
		return array;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}
	
	public class LazyIntegerListIterator implements ListIterator<Integer> {
		
		protected int min;
		protected int max;
		protected int current;
		
		public LazyIntegerListIterator(int min, int max) {
			this.min = min;
			this.max = max;
			if (min <= max) current = min-1;
			else current = min+1;
		}
		
		@Override
		public void add(Integer arg0) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean hasNext() {
			if (min <= max) return current < max;
			else return current > max;
		}

		@Override
		public boolean hasPrevious() {
			if (min <= max) return current > min;
			else return current < max;
		}

		@Override
		public Integer next() {
			if (hasNext()) {
				if (min <= max) current = current + 1;
				else current = current - 1;
				
				return current;
			}
			else throw new IllegalStateException();
		}

		@Override
		public int nextIndex() {
			if (min <= max) return current - min + 1;
			else return max - current + 1;
		}

		@Override
		public Integer previous() {
			if (hasPrevious()) {
				if (min <= max) current = current - 1;
				else current = current + 1;
				return current;
			}
			else throw new IllegalStateException();
		}

		@Override
		public int previousIndex() {
			if (min <= max) return current - min - 1;
			else return max - current - 1;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void set(Integer arg0) {
			throw new UnsupportedOperationException();			
		}
		
	}
	
}
