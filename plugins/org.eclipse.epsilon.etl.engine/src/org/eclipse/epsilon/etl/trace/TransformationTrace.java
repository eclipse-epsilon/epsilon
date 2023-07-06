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

import org.eclipse.epsilon.etl.dom.TransformationRule;

public class TransformationTrace {
	
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

	/**
	 * Unmodifiable view of the transformations from a given source.
	 */
	private class SourceTransformationsCollection extends AbstractCollection<Transformation> {
		private final Object source;
		
		public SourceTransformationsCollection(Object source) {
			this.source = source;
		}

		@Override
		public Iterator<Transformation> iterator() {
			Map<TransformationRule, List<Transformation>> ruleMap = transformations.get(source);
			if (ruleMap == null) {
				return Collections.emptyIterator();
			}

			return new Iterator<Transformation>() {
				final Iterator<List<Transformation>> itList = ruleMap.values().iterator();
				Iterator<Transformation> itTransformation;
				Transformation next;

				@Override
				public boolean hasNext() {
					if (next == null) {
						while (itTransformation == null || !itTransformation.hasNext()) {
							if (itList.hasNext()) {
								itTransformation = itList.next().iterator();
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
			};
		}

		@Override
		public int size() {
			Map<TransformationRule, List<Transformation>> ruleMap = transformations.get(source);
			if (ruleMap == null) {
				return 0;
			}

			int size = 0;
			for (List<Transformation> l : ruleMap.values()) {
				size += l.size();
			}
			return size;
		}
	}

	/**
	 * Unmodifiable flat view of all the transformations in this trace.
	 */
	private class TransformationsCollection extends AbstractCollection<Transformation> {
		@Override
		public Iterator<Transformation> iterator() {
			return new Iterator<Transformation>() {
				final Iterator<Map<TransformationRule, List<Transformation>>> itObjects = transformations.values().iterator();
				Iterator<List<Transformation>> itTransformationLists;
				Iterator<Transformation> itTransformation;
				Transformation next;

				@Override
				public boolean hasNext() {
					if (next == null) {
						while (itTransformation == null || !itTransformation.hasNext()) {
							while (itTransformationLists == null || !itTransformationLists.hasNext()) {
								if (itObjects.hasNext()) {
									itTransformationLists = itObjects.next().values().iterator();
								} else {
									// No more objects left - we're done
									itTransformationLists = null;
									itTransformation = null;
									return false;
								}
							}

							itTransformation = itTransformationLists.next().iterator();
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
			};
		}

		@Override
		public int size() {
			int size = 0;
			for (Map<TransformationRule, List<Transformation>> objectMap : transformations.values()) {
				for (List<Transformation> txList : objectMap.values()) {
					size += txList.size();
				}
			}
			return size;
		}
	}

	final Map<Object, Map<TransformationRule, List<Transformation>>> transformations;
	final boolean isConcurrent;
	
	public TransformationTrace() {
		this(false);
	}
	
	public TransformationTrace(boolean concurrent) {
		this.isConcurrent = concurrent;
		transformations = createIdentityMap();
	}

	private <K, V> Map<K, V> createIdentityMap() {
		return isConcurrent ? Collections.synchronizedMap(new LinkedHashMap<>()) : new LinkedHashMap<>();
	}

	private void syncOn(Object syncTarget, Runnable r) {
		if (isConcurrent) {
			synchronized (syncTarget) {
				r.run();
			}
		} else {
			r.run();
		}
	}

	public void add(Object source, Collection<Object> targets, TransformationRule rule) {
		Transformation transformation = new Transformation(source, targets);
		transformation.setRule(rule);

		List<Transformation> sourceRuleTransformations = transformations
			.computeIfAbsent(source, (k) -> createIdentityMap())
			.computeIfAbsent(rule, (k) -> new ArrayList<>());

		syncOn(sourceRuleTransformations, () -> sourceRuleTransformations.add(transformation));
	}
	
	public Collection<Transformation> getTransformations() {
		return new TransformationsCollection();
	}
	
	public Collection<Transformation> getTransformations(Object source) {
		return new SourceTransformationsCollection(source);
	}

	/**
	 * Returns an unmodifiable view of the transformation targets for a given source and rule name.
	 */
	public Collection<?> getTransformationTargets(Object source, TransformationRule rule) {
		Map<TransformationRule, List<Transformation>> sourceMap = transformations.get(source);
		if (sourceMap == null) {
			return Collections.emptyList();
		}

		List<Transformation> txList = sourceMap.get(rule);
		if (txList == null) {
			return Collections.emptyList();
		}

		return new TransformationTargetsCollection(txList);
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
		Map<TransformationRule, List<Transformation>> sourceMap = transformations.get(source);
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
		for (Entry<Object, Map<TransformationRule, List<Transformation>>> entry : transformations.entrySet()) {
			if (entry.getValue().containsKey(rule)) {
				return true;
			}
		};
		return false;
	}
}
