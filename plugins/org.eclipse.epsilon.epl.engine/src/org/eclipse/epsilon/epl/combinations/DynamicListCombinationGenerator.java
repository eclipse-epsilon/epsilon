/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.epl.combinations;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.epsilon.epl.dom.NoMatch;

public class DynamicListCombinationGenerator<T> implements CombinationGenerator<T> {
	
	protected int n;
	protected List<T> list;
	protected ListCombinationGenerator<T> listCombinationGenerator;
	protected ArrayList<CombinationGeneratorListener<T>> listeners = new ArrayList<>(2);
	protected boolean producedValidCombination = false;
	protected State state = State.NORMAL;
	protected Boolean optional = null;
	
	public DynamicListCombinationGenerator(List<T> list, int n) {
		init(list, n);
	}
	
	protected void init(List<T> list, int n) {
		this.list = list;
		this.n = n;
		
		if (list instanceof DynamicList<?>) {
			((DynamicList<T>) list).addListener(list_ -> createCombinationGenerator());
		}
		else {
			createCombinationGenerator();
		}
	}
	
	public void addListener(CombinationGeneratorListener<T> listener) {
		listeners.add(listener);
	}
	
	public void removeListener(CombinationGeneratorListener<T> listener) {
		listeners.remove(listener);
	}

	protected void createCombinationGenerator() {
		listCombinationGenerator = new ListCombinationGenerator<>(list, n);		
	}
	
	@Override
	public void reset() {
		if (listCombinationGenerator != null) listCombinationGenerator.reset();
		if (list instanceof DynamicList<?>) ((DynamicList<T>) list).reset();
		for (CombinationGeneratorListener<T> listener : listeners) {
			listener.reset();
		}
		producedValidCombination = false;
		state = State.NORMAL;
		optional = null;
	}
	
	@Override
	public boolean hasNext() {
		if (state != State.NORMAL && isOptional()) {
			if (state == State.CAN_RETURN_OPTIONAL) return true;
			if (state == State.HAS_RETURNED_OPTIONAL) return false;
		}
		
		nudgeList();
		boolean hasMore = listCombinationGenerator.hasNext();
		if (!hasMore && !producedValidCombination && isOptional()) {
			state = State.CAN_RETURN_OPTIONAL;
			hasMore = true;
		}
		
		return hasMore;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> next() {
		if (state != State.NORMAL && isOptional()) {
			if (state == State.HAS_RETURNED_OPTIONAL) return null;
			
			if (state == State.CAN_RETURN_OPTIONAL) {
				ArrayList<T> optional = new ArrayList<>(n);
				for (int i = 0; i < n; i++) {
					optional.add((T) NoMatch.INSTANCE);
				}
				state = State.HAS_RETURNED_OPTIONAL;
				return optional;
			}
		}
		
		nudgeList();
		List<T> next = listCombinationGenerator.next();
		
		for (CombinationGeneratorListener<T> listener : listeners) {
			listener.generated(next);
		}
		
		return next;
	}
	
	/**
	 * Nudge the list so that it loads
	 * its contents if it's dynamic
	 */
	protected void nudgeList() {
		list.size();
	}

	protected boolean checkOptional() {
		return false;
	}
	
	@Override
	public void producedValidCombination() {
		this.producedValidCombination = true;
	}
	
	public final boolean isOptional() {
		if (optional == null) {
			optional = checkOptional();
		}
		return optional;
	}
	
	enum State {
		NORMAL,
		CAN_RETURN_OPTIONAL,
		HAS_RETURNED_OPTIONAL
	}
}
