/*********************************************************************
 * Copyright (c) 2019 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.common.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * A {@link ConcurrentLinkedQueue} wrapper which supports <code>null</code> values
 * using a constant wrapper in place of null elements. This implementation caches
 * the size and updates it when structurally modified to avoid the issue outlined
 * in <a href="https://www.javaspecialists.eu/archive/Issue261.html">Java Specialists Issue 261</a>.
 * 
 * @author Sina Madani
 * @since 1.6
 * @param <E>
 */
@SuppressWarnings("unchecked")
public class SizeCachingConcurrentQueue<E> extends ConcurrentLinkedQueue<E> {

	private static final long serialVersionUID = 5903966144644716311L;
	
	protected static final Object NULL = new Object();
	
	protected static final Object replaceWithNull(Object o) {
		return o == null ? NULL : o;
	}
	
	public static final <T> T convertToNull(Object o) {
		return o == NULL ? null : (T) o;
	}
	
	protected final AtomicInteger size;
	
	public SizeCachingConcurrentQueue() {
		size = new AtomicInteger();
	}
	
	public SizeCachingConcurrentQueue(Collection<? extends E> initial) {
		super(initial);
		size = new AtomicInteger(initial.size());
	}
	
	@Override
	public E peek() {
		return convertToNull(super.peek());
	}
	
	@Override
	public E poll() {
		E res = convertToNull(super.poll());
		size.decrementAndGet();
		return res;
	}
	
	// Compiler-pleasing magic
	protected final Consumer<? extends E> offerSuper = super::offer;
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean offer(E e) {
		((Consumer) offerSuper).accept(replaceWithNull(e));
		return size.incrementAndGet() > 0;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean res = false;
		if (c != null && c != this) {
			Collection filtered = c.stream()
				.map(SizeCachingConcurrentQueue::replaceWithNull)
				.collect(Collectors.toList());
			
			res = super.addAll(filtered);
			size.getAndAdd(filtered.size());
		}
		return res;
	}
	
	@Override
	public boolean remove(Object o) {
		if (super.remove(replaceWithNull(o))) {
			size.decrementAndGet();
			return true;
		}
		else return false;
	}
	
	@Override
	public int size() {
		return size.get();
	}
	
	@Override
	public Iterator<E> iterator() {
		return new InternalIter(super.iterator());
	}
	
	private final class InternalIter implements Iterator<E> {
		Iterator<E> delegate;
		
		InternalIter(Iterator<E> delegate) {
			this.delegate = delegate;
		}
		
        @Override
        public boolean hasNext() {
        	return delegate.hasNext();
        }
        
        @Override
        public E next() {
        	return (E) convertToNull(delegate.next());
        }
        
        @Override
        public void remove() {
        	delegate.remove();
        	size.decrementAndGet();
        }
    }
	
	@Override
	public boolean contains(Object o) {
		return super.contains(replaceWithNull(o));
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof SizeCachingConcurrentQueue)) return false;
		Collection<?> that = (Collection<?>) o;
		if (this.size() != that.size()) return false;
		
		for (
			Iterator<?> thisIter = this.iterator(), thatIter = that.iterator();
			thisIter.hasNext() && thatIter.hasNext();
		) {
			if (!Objects.equals(thisIter.next(), thatIter.next()))
				return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(size);
	}
}
