/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.operations.contributors;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.eclipse.epsilon.common.util.CollectionUtil;
import org.eclipse.epsilon.eol.types.*;
import org.eclipse.epsilon.eol.types.concurrent.*;

public class IterableOperationContributor extends OperationContributor {

	public IterableOperationContributor() {}
	
	public IterableOperationContributor(Iterable<?> target) {
		setTarget(target);
	}

	@Override
	protected Iterable<?> getTarget() {
		return (Iterable<?>) super.getTarget();
	}
	
	@SuppressWarnings("unchecked")
	protected Iterable<Object> getIterable() {
		return (Iterable<Object>) getTarget();
	}
	
	protected boolean isCollection() {
		return getTarget() instanceof Collection;
	}

	@SuppressWarnings("unchecked")
	protected Collection<Object> getCollection() {
		return (Collection<Object>) getTarget();
	}

	protected boolean isList() {
		return getTarget() instanceof List;
	}
	
	protected List<?> getList() {
		return (List<?>) getTarget();
	}
	
	protected boolean isSet() {
		return getTarget() instanceof Set;
	}
	
	protected Set<?> getSet() {
		return (Set<?>) getTarget();
	}
	
	@Override
	public boolean contributesTo(Object target) {
		return target instanceof Iterable;
	}
	
	public Object random() {
		if (isEmpty()) return null;
		final int size = size();
		return nth(size > 0 ? new Random().nextInt(size) : 0);
	}

	public int size() {
		if (isCollection()) {
			return getCollection().size();
		}
		else {
			int size = 0;
			for (Iterator<?> it = getTarget().iterator(); it.hasNext(); ++size) {
				it.next();
			}
			return size;
		}
	}

	public Object at(int index) {
		if (isList()) {
			return getList().get(index);
		}
		else {
			int i = 0;
			for (Object next : getTarget()) {
				if (i++ == index) {
					return next;
				}
			}
			return null;
		}
	}
	
	public Object removeAt(int index) {
		if (isList()) {
			return getList().remove(index);
		}
		else {
			Object toRemove = null;
			Iterator<?> it = getTarget().iterator();
			int i;
			for (i = 0; it.hasNext() && i <= index; ++i) {
				toRemove = it.next();
			}
			if (toRemove != null && i == index + 1) {
				it.remove();
			}
			return toRemove;
		}
	}

	/**
	 * 
	 * @return
	 * @since 2.1
	 */
	public EolConcurrentBag<Object> asConcurrentBag() {
		EolConcurrentBag<Object> copy = new EolConcurrentBag<>();
		copy(getIterable(), copy);
		return copy;
	}
	
	/**
	 * 
	 * @return
	 * @since 2.1
	 */
	public EolConcurrentSet<Object> asConcurrentSet() {
		EolConcurrentSet<Object> copy = new EolConcurrentSet<>();
		copy(getIterable(), copy);
		return copy;
	}
	
	public EolSequence<Object> asSequence() {
		EolSequence<Object> copy = new EolSequence<>();
		copy(getIterable(), copy);
		return copy;
	}
	
	public EolSet<Object> asSet() {
		EolSet<Object> copy = new EolSet<>();
		copy(getIterable(), copy);
		return copy;
	}
	
	public EolBag<Object> asBag() {
		EolBag<Object> copy = new EolBag<>();
		copy(getIterable(), copy);
		return copy;
	}
	
	public EolOrderedSet<Object> asOrderedSet() {
		EolOrderedSet<Object> copy = new EolOrderedSet<>();
		copy(getIterable(), copy);
		return copy;
	}
	
	public Number sum() {
		return parallelStream()
			.filter(Number.class::isInstance)
			.map(Number.class::cast)
			.reduce(0, (i, sum) -> NumberUtil.add(sum, i));
	}
	
	public Number product() {
		if (isEmpty()) return 0.0f;
		
		return parallelStream()
			.filter(Number.class::isInstance)
			.map(Number.class::cast)
			.reduce(1, (product, i) -> NumberUtil.multiply(product, i));
	}
	
	public boolean isEmpty() {
		Object target = getTarget();
		if (target instanceof Collection<?>) {
			return ((Collection<?>) target).isEmpty();
		}
		return !getTarget().iterator().hasNext();
	}

	public boolean notEmpty() {
		return !isEmpty();
	}
	
	protected <T> void copy(Iterable<T> source, Collection<T> target) {
		for (
			Iterator<T> it = source.iterator();
			it.hasNext();
			target.add(it.next())
		);
	}
	
	/**
	 * 
	 * @return
	 * @since 2.1
	 */
	public Stream<?> stream() {
		return stream(false);
	}
	
	/**
	 * 
	 * @return
	 * @since 2.1
	 */
	public Stream<?> parallelStream() {
		return stream(true);
	}
	
	/**
	 * 
	 * @param parallel
	 * @return
	 * @since 2.1
	 */
	protected Stream<?> stream(boolean parallel) {
		Iterable<?> target = getTarget();
		if (target instanceof Collection) {
			Collection<?> col = ((Collection<?>) target);
			return parallel ? col.parallelStream() : col.stream();
		}
		else {
			return StreamSupport.stream(target.spliterator(), parallel);
		}
	}

	@Override
	public Collection<?> clone() {
		// May require its own contributor
		if (isCollection()) {
			return EolCollectionType.clone(getCollection());
		}
		return null;
	}

	public boolean includes(Object key) {
		if (isCollection()) {
			return getCollection().contains(key);
		}
		else {
			Iterator<?> it = getTarget().iterator();
			while (it.hasNext()) {
				if (Objects.equals(it.next(), key)) {
					return true;
				}
			}
			return false;
		}
	}
	
	public boolean excludes(Object o) {
		return !includes(o);
	}
	
	public boolean includesAll(Collection<?> col) {
		for (Object item : col) {
			if (excludes(item)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean excludesAll(Collection<?> col) {
		for (Object item : col) {
			if (includes(item)) {
				return false;
			}
		}
		return true;
	}
	
	public int count(Object o) {
		return stream()
			.filter(item -> Objects.equals(item, o))
			.mapToInt(item -> 1).sum();
	}

	/**
	 * 
	 * @param type
	 * @return
	 * @since 2.1
	 */
	public Collection<Object> selectByKind(EolType type) {
		if (type == null) {
			type = EolNoType.Instance;
		}
		return stream()
			.filter(type::isKind)
			.collect(Collectors.toCollection(this::createCollection));
	}
	
	/**
	 * 
	 * @param type
	 * @return
	 * @since 2.1
	 */
	public Collection<Object> selectByType(EolType type) {
		if (type == null) {
			type = EolNoType.Instance;
		}
		return stream()
			.filter(type::isType)
			.collect(Collectors.toCollection(this::createCollection));
	}
	
	public Collection<Object> includingAll(Collection<?> col) {
		Collection<Object> result = createCollection();
		addAll(getTarget(), result);
		addAll(col, result);
		return result;
	}

	public Collection<Object> including(Object o) {
		Collection<Object> result = createCollection();
		addAll(getTarget(), result);
		result.add(o);
		return result;
	}

	/**
	 * TODO : See this
	 * @return
	 */
	public Collection<Object> flatten() {
		Collection<Object> col;
		if (isCollection()) {
			col = getCollection();
		}
		else {
			col = createCollection();
			addAll(getTarget(), col);
		}
		return CollectionUtil.flatten(col);
	}

	public Collection<Object> excluding(Object o) {
		Collection<Object> excluding = createCollection();
		addAll(getTarget(), excluding);
		while (excluding.contains(o)) {
			excluding.remove(o);
		}
		return excluding;
	}

	public Collection<Object> excludingAll(Collection<?> col) {
		Collection<Object> difference = createCollection();
		for (Object next : getTarget()) {
			if (!col.contains(next)) {
				difference.add(next);
			}
		}
		/*
		 * The normal way would be to use the removeAll method like following
		 * but the MDR IndexSetWrapper does not support it.
		 */
		return difference;
	}
	
	public Collection<Object> first(int number) {
		Iterator<?> it = getTarget().iterator();
		ArrayList<Object> result = new ArrayList<>(number);
		for (int i = 0; it.hasNext() && i++ < number; result.add(it.next()));
		return result;
	}
	
	public Object first() {
		return nth(0);
	}
	
	public Object second() {
		return nth(1);
	}
	
	public Object third() {
		return nth(2);
	}
	
	public Object fourth() {
		return nth(3);
	}
	
	public Object last() {
		if (isCollection()) {
			return nth(getCollection().size() - 1);
		}
		else {
			Object o = null;
			for (Iterator<?> it = getTarget().iterator(); it.hasNext(); o = it.next());
			return o;
		}
	}
	
	public int indexOf(Object o) {
		if (isList()) {
			return getList().indexOf(o);
		}
		else {
			int counter = 0;
			for (Object item : getTarget()) {
				if (Objects.equals(item, o)) {
					return counter;
				}
				counter++;
			}
			return -1;
		}
	}
	
	private Object nth(int index) {
		if (isEmpty()) return null;
		return at(index);
	}
	
	public String concat() {
		return concat("");
	}
	
	public String concat(String delimiter) {
		return CollectionUtil.join(getTarget(), delimiter,
			element -> Objects.toString(element, "")
		);
	}
	
	public Number max() {
		return max(0);
	}
	
	public Number max(Number default_) {
		Number max = null;
		for (Object next : getTarget()) {
			if (next instanceof Number) {
				Number nextNumber = (Number) next;
				if (max == null || NumberUtil.greaterThan(nextNumber, max)) {
					max = nextNumber;
				}
			}
		}
		if (max == null) {
			max = default_;
		}
		return max;
	}
	
	public Number min() {
		return min(0);
	}
	
	public Number min(Number default_) {
		Number min = null;
		for (Object next : getTarget()) {
			if (next instanceof Number) {
				Number nextNumber = (Number) next;
				if (min == null || NumberUtil.lessThan(nextNumber, min)) {
					min = nextNumber;
				}
			}
		}
		if (min == null) {
			min = default_;
		}
		return min;
	}
	
	public Collection<Object> invert() {
		EolSequence<Object> sequence = new EolSequence<>();
		for (Object o : getTarget()) {
			sequence.add(0, o);
		}
		return sequence;
	}

	public Collection<Object> createCollection() {
		if (isCollection()) {
			return EolCollectionType.createSameType(getCollection());
		}
		else {
			return new EolSequence<>();
		}
	}
	
	public Set<Set<Object>> powerset() {	
		List<Object> originalSet = asSequence();
		Set<Set<Object>> sets = new HashSet<>();
		
	    if (originalSet.isEmpty()) {
	    	sets.add(new HashSet<>());
	    	return sets;
	    }
	    
	    Object head = originalSet.get(0);
	    Set<Object> rest = new HashSet<>(originalSet.subList(1, originalSet.size())); 
	    try (IterableOperationContributor restOC = new IterableOperationContributor(rest)) {
		    for (Set<Object> set : restOC.powerset()) {
		    	Set<Object> newSet = new HashSet<>();
		    	newSet.add(head);
		    	newSet.addAll(set);
		    	sets.add(newSet);
		    	sets.add(set);
		    }		
	    }
	    return sets;
	}
	
	private static void addAll(final Iterable<?> elements, Collection<Object> target) {
		if (elements instanceof Collection) {
			target.addAll((Collection<?>)elements);
		}
		else {
			for (Object o : elements) {
				target.add(o);
			}
		}
	}
}
