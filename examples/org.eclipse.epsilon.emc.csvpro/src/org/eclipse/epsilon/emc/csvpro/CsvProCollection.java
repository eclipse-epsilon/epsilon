/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.csvpro;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;

import org.eclipse.epsilon.eol.execute.operations.AbstractOperation;
import org.eclipse.epsilon.eol.execute.operations.declarative.IAbstractOperationContributor;

public class CsvProCollection implements Collection<Integer>, IAbstractOperationContributor {
	
	private TreeMap<String, Integer> rows;
	private String index;
	
	public CsvProCollection(String indexColumName) {
		this.index = indexColumName;
	}
	
	public CsvProCollection() {
		this.index = "";
	}

	protected void setRows(TreeMap<String, Integer> rows) {
		this.rows = rows;
	}

	@Override
	public AbstractOperation getAbstractOperation(String name) {
		if (!index.equals("") && "select".equals(name)) {
			return new CsvProCollectionSelectOperation(index);
		}
		else {
			return null;
		}
	}

	@Override
	public int size() {
		return rows.size();
	}

	@Override
	public boolean isEmpty() {
		return rows.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return rows.containsValue(o);
	}

	@Override
	public Iterator<Integer> iterator() {
		return rows.values().iterator();
	}

	@Override
	public Object[] toArray() {
		return rows.values().toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return rows.values().toArray(a);
	}

	@Override
	public boolean add(Integer e) {
		throw new UnsupportedOperationException("This collection is backed by a map so add is not supported. If "
				+ "adding another CSV Pro collection use put(key, object) if ids (keys) are unique. If not, wrap "
				+ "this collection in another one first");
	}
	
	public Integer put(String key, Integer value) {
		return rows.put(key, value);
	}

	public void putAll(Map<? extends String, ? extends Integer> map) {
		rows.putAll(map);
	}
	
	public Map<String, Integer> getMap() {
		return rows;
	}

	@Override
	public boolean remove(Object o) {
		return rows.values().remove(o);	
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return rows.values().containsAll(c);	
	}

	@Override
	public boolean addAll(Collection<? extends Integer> c) {
		throw new UnsupportedOperationException("This collection is backed by a map so add is not supported. If "
				+ "adding another CSV Pro collection use putAll(c.getMap) if ids (keys) are unique. If not, wrap "
				+ "this collection in another one first");
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return rows.values().removeAll(c);	
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return rows.values().retainAll(c);
	}

	@Override
	public void clear() {
		rows.clear();
	}

	public Integer get(Object key) {
		return rows.get(key);
	}

	public NavigableMap<String, Integer> headMap(String toKey, boolean inclusive) {
		return rows.headMap(toKey, inclusive);
	}

	public SortedMap<String, Integer> tailMap(String fromKey, boolean inclusive) {
		return rows.tailMap(fromKey, inclusive);
	}
	
	
	

}
