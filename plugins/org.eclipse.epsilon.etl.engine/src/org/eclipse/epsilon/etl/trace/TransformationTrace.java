/*******************************************************************************
 * Copyright (c) 2008-2023 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Antonio Garcia-Dominguez - use map->map->list data structure
 *                                and computed collection views
 ******************************************************************************/
package org.eclipse.epsilon.etl.trace;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

import org.eclipse.epsilon.etl.dom.TransformationRule;

import com.google.common.collect.Iterators;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;
import com.google.common.collect.Tables;

public class TransformationTrace {
	
	private class TransformationListIterator implements Iterator<Transformation> {
		private final Iterator<List<Transformation>> itLists;
		private Iterator<Transformation> itTransformation;
		private Transformation next;

		private TransformationListIterator(Iterator<List<Transformation>> itLists) {
			this.itLists = itLists;
		}

		@Override
		public boolean hasNext() {
			if (next == null) {
				while (itTransformation == null || !itTransformation.hasNext()) {
					if (itLists.hasNext()) {
						itTransformation = itLists.next().iterator();
					} else {
						itTransformation = null;
						return false;
					}
				}
				next = itTransformation.next();
			}
			return next != null;
		}

		@Override
		public Transformation next() {
			if (!hasNext()) throw new NoSuchElementException();
			Transformation ret = next;
			next = null;
			return ret;
		}
	}

	/**
	 * Unmodifiable view of the targets for a given transformation.
	 */
	private class TransformationTargetsCollection extends AbstractCollection<Object> {
		private final List<Transformation> txList;

		public TransformationTargetsCollection(List<Transformation> txList) {
			this.txList = txList;
		}

		@Override
		public Iterator<Object> iterator() {
			return new Iterator<Object>() {
				final Iterator<Transformation> itTransformation = txList.iterator();
				Iterator<Object> itTarget;
				Object next;

				@Override
				public boolean hasNext() {
					if (next == null) {
						while (itTarget == null || !itTarget.hasNext()) {
							if (itTransformation.hasNext()) {
								itTarget = itTransformation.next().getTargets().iterator();
							} else {
								itTarget = null;
								return false;
							}
						}
						next = itTarget.next();
					}
					return next != null;
				}

				@Override
				public Object next() {
					if (!hasNext()) throw new NoSuchElementException();
					Object ret = next;
					next = null;
					return ret;
				}
			};
		}

		@Override
		public int size() {
			return txList.size();
		}
	}

	private final Table<Object, TransformationRule, List<Transformation>> transformations;
	private final boolean isConcurrent;
	
	public TransformationTrace() {
		this(false);
	}
	
	public TransformationTrace(boolean concurrent) {
		this.isConcurrent = concurrent;
		transformations = Tables.newCustomTable(createIdentityMap(), this::createIdentityMap);
	}

	private <K, V> Map<K, V> createIdentityMap() {
		return isConcurrent ? Collections.synchronizedMap(new LinkedHashMap<>()) : new LinkedHashMap<>();
	}

	private <T> T syncOn(Object syncTarget, Supplier<T> r) {
		if (isConcurrent) {
			synchronized (syncTarget) {
				return r.get();
			}
		} else {
			return r.get();
		}
	}

	public void add(Object source, Collection<Object> targets, TransformationRule rule) {
		Transformation transformation = new Transformation(source, targets);
		transformation.setRule(rule);

		List<Transformation> sourceRuleTransformations = syncOn(transformations, () -> {
			List<Transformation> l = transformations.get(source, rule);
			if (l == null) {
				l = new ArrayList<>();
				transformations.put(source, rule, l);
			}
			return l;
		});

		syncOn(sourceRuleTransformations,
			() -> sourceRuleTransformations.add(transformation));
	}

	public Collection<Transformation> getTransformations() {
		return new AbstractCollection<Transformation>() {
			@Override
			public Iterator<Transformation> iterator() {
				final Iterator<Cell<Object, TransformationRule, List<Transformation>>> itCells = transformations.cellSet().iterator();
				final Iterator<List<Transformation>> itLists = Iterators.transform(itCells, (cell) -> cell.getValue());
				return new TransformationListIterator(itLists);
			}

			@Override
			public int size() {
				int size = 0;
				for (Cell<Object, TransformationRule, List<Transformation>> cell : transformations.cellSet()) {
					size += cell.getValue().size();
				}
				return size;
			}
		};
	}

	public Collection<Transformation> getTransformations(Object source) {
		return new AbstractCollection<Transformation>() {
			@Override
			public Iterator<Transformation> iterator() {
				return new TransformationListIterator(transformations.row(source).values().iterator());
			}

			@Override
			public int size() {
				int size = 0;
				for (List<Transformation> txList : transformations.row(source).values()) {
					size += txList.size();
				}
				return size;
			}
		};
	}

	/**
	 * Returns an unmodifiable view of the transformation targets for a given source and rule name.
	 */
	public Collection<?> getTransformationTargets(Object source, TransformationRule rule) {
		List<Transformation> txList = transformations.get(source, rule);
		return txList == null ? Collections.emptyList() : new TransformationTargetsCollection(txList);
	}

	/**
	 * Retrieves the transformation targets of a source by rule name. Only kept for
	 * backwards compatibility.
	 *
	 * @deprecated Use {@link #getTransformationTargets(Object, TransformationRule)}
	 *             instead, for better performance.
	 */
	@Deprecated
	public Collection<?> getTransformationTargets(Object source, String rule) {
		Map<TransformationRule, List<Transformation>> sourceMap = transformations.row(source);
		if (sourceMap == null) {
			return Collections.emptyList();
		}

		for (Entry<TransformationRule, List<Transformation>> entry : sourceMap.entrySet()) {
			if (entry.getKey().getName().equals(rule)) {
				return new TransformationTargetsCollection(entry.getValue());
			}
		}

		return Collections.emptyList();
	}

	public boolean containsTransformedBy(TransformationRule rule) {
		return transformations.columnKeySet().contains(rule);
	}
}
