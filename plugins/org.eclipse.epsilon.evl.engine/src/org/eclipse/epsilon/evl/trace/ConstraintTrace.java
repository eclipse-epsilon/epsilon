/*******************************************************************************
 * Copyright (c) 2008-2020 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Antonio Garcia-Dominguez - use collection interfaces and consider
 *                                optimised execution
 *     Sina Madani - Concurrency support and optimisation
 ******************************************************************************/
package org.eclipse.epsilon.evl.trace;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import org.eclipse.epsilon.common.concurrent.ConcurrencyUtils;
import org.eclipse.epsilon.evl.dom.Constraint;

public class ConstraintTrace implements Iterable<ConstraintTraceItem> {

	protected final Set<Constraint> storageOptimised;
	// Map of constraint trace items per object
	protected final Map<Object, Set<ConstraintTraceItem>> objectItemsMap;
	protected boolean concurrent = false;
	
	public ConstraintTrace() {
		this(false);
	}
	
	/**
	 * @since 1.6
	 * @param concurrent
	 */
	public ConstraintTrace(boolean concurrent) {
		this.concurrent = concurrent;
		storageOptimised = concurrent ? ConcurrencyUtils.concurrentSet() : new LinkedHashSet<>();
		objectItemsMap = concurrent ? ConcurrencyUtils.concurrentMap(): new LinkedHashMap<Object, Set<ConstraintTraceItem>>();
	}
	
	/**
	 * 
	 * @param others
	 * @since 1.6
	 */
	public void addAll(Collection<? extends ConstraintTrace> others) {
		for (ConstraintTrace ct : others) {
			storageOptimised.addAll(ct.storageOptimised);
			for (ConstraintTraceItem cti : ct.getItems()) {
				addChecked(cti.getConstraint(), cti.getInstance(), cti.getResult());
			}
		}
	}
	
	/**
	 * @param others
	 * @since 1.6
	 */
	public void addAll(ConstraintTrace... others) {
		addAll(Arrays.asList(others));
	}
	
	/**
	 * Indicates that this constraint was checked in an optimised manner, so
	 * we only have the trace items for the invalid items and anything else
	 * is considered checked and valid.
	 */
	public void addCheckedOptimised(Constraint constraint) {
		storageOptimised.add(constraint);
	}

	public synchronized void addChecked(Constraint constraint, Object object, boolean result) {
		ConstraintTraceItem cti = new ConstraintTraceItem(object, constraint, result);
		Set<ConstraintTraceItem> items = objectItemsMap.get(object);
		if (items == null) {
			items = concurrent ? ConcurrencyUtils.concurrentSet() : new LinkedHashSet<>();
			objectItemsMap.put(object, items);
		}
		items.add(cti);
	}

	public boolean isChecked(Constraint constraint, Object instance) {
		
		if (storageOptimised.contains(constraint)) return true;
		
		Set<ConstraintTraceItem> items = objectItemsMap.get(instance);
		
		if (items == null) return false;
		else return items.stream().anyMatch(cti -> cti.equals(constraint, instance));
	}

	public boolean isSatisfied(Constraint constraint, Object instance) {
		if (storageOptimised.contains(constraint)) return true;
		ConstraintTraceItem cti = new ConstraintTraceItem(instance, constraint, true);
		
		Set<ConstraintTraceItem> items = objectItemsMap.get(instance);
		
		if (items == null) return false;
		else return items.stream().anyMatch(cti::equals);
	}

	/**
	 * @since 1.6
	 */
	public void clear() {
		if (storageOptimised != null) storageOptimised.clear();
		if (objectItemsMap != null) objectItemsMap.clear();
	}
	
	public Set<ConstraintTraceItem> getItems() {
		Set<ConstraintTraceItem> items = new LinkedHashSet<>();
		for (Set<ConstraintTraceItem> objectItems : objectItemsMap.values()) {
			items.addAll(objectItems);
		}
		return items;
	}
	
	/**
	 * 
	 * @return
	 * @since 1.6
	 */
	public Stream<ConstraintTraceItem> stream() {
		return getItems().stream();
	}
	
	@Override
	public Iterator<ConstraintTraceItem> iterator() {
		return getItems().iterator();
	}
	
	/**
	 * @since 1.6
	 */
	@Override
	public int hashCode() {
		return Objects.hash(storageOptimised, objectItemsMap);
	}
	
	/**
	 * @since 1.6
	 */
	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (!(other instanceof ConstraintTrace))
			return false;
		
		ConstraintTrace ct = (ConstraintTrace) other;
		return
			Objects.equals(this.storageOptimised, ct.storageOptimised) &&
			Objects.equals(this.objectItemsMap, ct.objectItemsMap);
	}
}
