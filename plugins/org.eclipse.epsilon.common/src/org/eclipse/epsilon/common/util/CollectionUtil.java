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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("unchecked")
public class CollectionUtil {
	
	private CollectionUtil() {}
	
	/**
	 * 
	 * @param o
	 * @return
	 * @since 1.6
	 */
	public static Iterator<?> asIterator(Object o) {
		if (o instanceof Iterator)
			return (Iterator<?>) o;
		else if (o instanceof Iterable)
			return ((Iterable<?>) o).iterator();
		else
			return Collections.singleton(o).iterator();
	}
	
	/**
	 * 
	 * @param c1
	 * @param c2
	 * @return
	 * @since 1.6
	 */
	public static boolean equalsIgnoreOrder(Collection<?> c1, Collection<?> c2) {
		if (c1 == c2) return true;
		if (c1 == null || c2 == null) return false;
		return c1.size() == c2.size() && c1.containsAll(c2);
	}
	
	/**
	 * 
	 * @param collections
	 * @return
	 * @since 1.6
	 */
	@SafeVarargs
	public static <T> ArrayList<T> composeArrayListFrom(Collection<T>... collections) {
		int size = 0;
		for (Collection<T> col : collections) {
			size += col.size();
		}
		ArrayList<T> list = new ArrayList<>(size);
		for (Collection<T> col : collections) {
			list.addAll(col);
		}
		return list;
	}
	
	/**
	 * 
	 * @param c1
	 * @param c2
	 * @param newCollection
	 * @return
	 * @since 1.6
	 */
	public static <T, C extends Collection<T>> C mergeCollectionsUnique(Collection<T> c1, Collection<T> c2, Supplier<? extends C> newCollection) {
		Stream<T> stream;
		if (c2 != null && (c1 == null || c1.isEmpty())) {
			stream = c2.stream();
		}
		else if (c1 != null && (c2 == null || c2.isEmpty())) {
			stream = c1.stream();
		}
		else if (c1 != null && c2 != null) {
			stream = Stream.concat(c1.stream(), c2.stream());
		}
		else {
			stream = Stream.empty();
		}
		return stream.distinct().collect(Collectors.toCollection(newCollection));
	}
	
	/**
	 * 
	 * @param collection
	 * @param additionalCapacity
	 * @since 1.6
	 */
	public static void addCapacityIfArrayList(Collection<?> collection, int additionalCapacity) {
		if (collection instanceof ArrayList) {
			((ArrayList<?>) collection).ensureCapacity(collection.size()+additionalCapacity);
		}
	}
	
	public static <T> Collection<T> asCollection(T o) {
		if (o instanceof Collection) {
			return (Collection<T>) o;
		}
		else {
			ArrayList<T> list = new ArrayList<>(1);
			list.add(o);
			return list;
		}
	}
	
	public static <T> List<T> asList(T o) {
		if (o instanceof List) {
			return (List<T>) o;
		}
		else {
			List<T> list = createDefaultList();
			
			if (o instanceof Collection)
				list.addAll((Collection<T>)o);
			else
				list.add(o);
			
			return list;
		}
	}
	
	public static <T> Set<T> asSet(T o) {
		if (o instanceof Set) {
			return (Set<T>) o;
		}
		else {
			Set<T> set = createDefaultSet();
			
			if (o instanceof Collection)
				set.addAll((Collection<T>)o);
			else
				set.add(o);

			return set;
		}
	}
	
	public static <T> Collection<T> flatten(Collection<T> original) {
		// First see if there are no nested collections
		// and in this case just return this
		
		boolean hasNested = false;
		for (Object o : original) {
			if (o instanceof Collection) {
				hasNested = true;
				break;
			}
		}
		
		if (!hasNested) return original;
		
		// If there are nested collections
		
		Collection<T> flattened = createDefaultList();
		for (T next : original) {
			if (next instanceof Collection){
				flattened.addAll(flatten((Collection<T>)next));
			}
			else {
				flattened.add(next);
			}
		}
		return flattened;
	}
		
	public static <T> Set<T> createDefaultSet() {
		return new LinkedHashSet<>();
	}
	
	public static <T> List<T> createDefaultList() {
		return new ArrayList<>();
	}
	
	public static <T> List<T> asList(Collection<T> c) {
		return c instanceof List ? (List<T>) c : new ArrayList<>(c);
	}
	
	public static <T> Object getFirst(Iterable<T> c) {
		Iterator<T> it = c.iterator();
		return it.hasNext() ? it.next() : null;
	}
	
	public static <T> List<T> iterate(Iterable<T> iterable) {
		List<T> filled = createDefaultList();
		Iterator<T> iterator = iterable.iterator();
		while (iterator.hasNext()) {
			filled.add(iterator.next());
		}
		return filled;
	}
	
	public static <E> E[] toArray(Collection<E> c, Class<?> arrayType) {
		if (c == null) return null;
		E[] arr = (E[]) Array.newInstance(arrayType, c.size());
		Iterator<E> it = c.iterator();
		for (int i = 0; it.hasNext(); i++) {
			arr[i] = it.next();
		}
		return arr;
	}
	
	public static String join(Iterable<?> collection, String delimiter) {
		return join(collection, delimiter, Object::toString);
	}
	
	public static String join(Iterable<?> collection, String delimiter, ElementPrinter printer) {
		final StringBuilder result = new StringBuilder();

		for (Iterator<?> iterator = collection.iterator(); iterator.hasNext();) {
			Object next = iterator.next();
			result.append(printer.print(next));
			if (iterator.hasNext()) {
				result.append(delimiter);
			}
		}
		return result.toString();
	}
	
	public static interface ElementPrinter {
		String print(Object element);
	}
}
