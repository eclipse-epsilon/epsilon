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

import java.util.*;
import org.eclipse.epsilon.common.function.ExceptionHandler;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;

public abstract class DynamicList<T> implements List<T> {

	private List<T> values;
	private boolean reset = true, resetable = false;
	private ExceptionHandler<EolRuntimeException> exceptionHandler;
	private ArrayList<DynamicListListener<T>> listeners = new ArrayList<>(2);
	
	protected abstract List<T> getValues() throws EolRuntimeException;
	
	final void check() {
		if (reset || values == null) {
			try {
				values = getValues();
				reset = false;
				for (DynamicListListener<T> listener : listeners) {
					listener.valuesChanged(this);
				}
			}
			catch (EolRuntimeException ex) {
				if (exceptionHandler != null) {
					exceptionHandler.handleException(ex);
				}
			}
		}
	}
	
	public void addListener(DynamicListListener<T> listener) {
		listeners.add(listener);
	}
	
	public boolean removeListener(DynamicListListener<T> listener) {
		return listeners.remove(listener);
	}
	
	public ExceptionHandler<EolRuntimeException> getExceptionHandler() {
		return exceptionHandler;
	}
	
	public void setExceptionHandler(ExceptionHandler<EolRuntimeException> exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}
	
	public void reset() {
		if (resetable) this.reset = true;
	}
	
	public boolean isResetable() {
		return resetable;
	}
	
	public void setResetable(boolean resetable) {
		this.resetable = resetable;
	}
	
	@Override
	public boolean add(T arg0) {
		check();
		return values.add(arg0);
	}

	@Override
	public void add(int arg0, T arg1) {
		check();
		values.add(arg0, arg1);
	}

	@Override
	public boolean addAll(Collection<? extends T> arg0) {
		check();
		return values.addAll(arg0);
	}

	@Override
	public boolean addAll(int arg0, Collection<? extends T> arg1) {
		check();
		return values.addAll(arg0, arg1);
	}

	@Override
	public void clear() {
		check();
		values.clear();
	}

	@Override
	public boolean contains(Object arg0) {
		check();
		return values.contains(arg0);
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		check();
		return values.containsAll(arg0);
	}

	@Override
	public T get(int arg0) {
		check();
		return values.get(arg0);
	}

	@Override
	public int indexOf(Object arg0) {
		check();
		return values.indexOf(arg0);
	}

	@Override
	public boolean isEmpty() {
		check();
		return values.isEmpty();
	}

	@Override
	public Iterator<T> iterator() {
		check();
		return values.iterator();
	}

	@Override
	public int lastIndexOf(Object arg0) {
		check();
		return values.lastIndexOf(arg0);
	}

	@Override
	public ListIterator<T> listIterator() {
		check();
		return values.listIterator();
	}

	@Override
	public ListIterator<T> listIterator(int arg0) {
		check();
		return values.listIterator(arg0);
	}

	@Override
	public boolean remove(Object arg0) {
		check();
		return values.remove(arg0);
	}

	@Override
	public T remove(int arg0) {
		check();
		return values.remove(arg0);
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		check();
		return values.removeAll(arg0);
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		check();
		return values.retainAll(arg0);
	}

	@Override
	public T set(int arg0, T arg1) {
		check();
		return values.set(arg0, arg1);
	}

	@Override
	public int size() {
		check();
		return values.size();
	}

	@Override
	public List<T> subList(int arg0, int arg1) {
		check();
		return values.subList(arg0, arg1);
	}

	@Override
	public Object[] toArray() {
		check();
		return values.toArray();
	}

	@Override
	public <A> A[] toArray(A[] arg0) {
		check();
		return values.toArray(arg0);
	}
}
