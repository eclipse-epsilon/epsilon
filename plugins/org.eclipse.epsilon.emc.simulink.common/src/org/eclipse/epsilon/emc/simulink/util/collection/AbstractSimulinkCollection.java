/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.util.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.epsilon.emc.simulink.model.element.ISimulinkModelElement;
import org.eclipse.epsilon.emc.simulink.util.MatlabEngineUtil;
import org.eclipse.epsilon.emc.simulink.util.manager.Manager;

public abstract class AbstractSimulinkCollection<E, P, M extends Manager<E, P>> implements List<ISimulinkModelElement> {

	protected List<P> primitive;
	protected M manager;

	@SuppressWarnings("unchecked")
	public AbstractSimulinkCollection(Object primitive, M manager) {
		if (primitive != null) {
			if (isInstanceOfPrimitiveArray(primitive)) {
				this.primitive = getPrimitiveFromArray(primitive);
			} else if (primitive instanceof List) {
				this.primitive = getPrimitiveFromList((List<?>)primitive);
			} else if (isInstanceOfPrimitive(primitive)) {
				this.primitive = getPrimitiveFromSingle(primitive);
			} else {
				this.primitive = new ArrayList<>();
				this.primitive.add((P)primitive);
				//new IllegalStateException("Unhandled primitive type: " + getPrimitive().getClass());
			}
		} else {
			this.primitive = new ArrayList<>();
		}
		this.manager = manager;
	}

	protected List<P> getPrimitiveFromSingle(Object primitive) {
		return (List<P>) new ArrayList<>(Arrays.asList(primitive));
	}

	protected List<P> getPrimitiveFromList(List<?> primitive) {
		return (List<P>) primitive;
	}

	protected List<P> getPrimitiveFromArray(Object primitive) {
		return MatlabEngineUtil.matlabArrayToList((P[]) primitive);
	}

	@Override
	public int size() {
		return getPrimitive().size();
	}

	@Override
	public boolean isEmpty() {
		return getPrimitive().isEmpty();
	}

	@Override
	public void clear() {
		getPrimitive().clear();
	}

	@Override
	public Iterator<ISimulinkModelElement> iterator() {
		return getInternalIterator();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ISimulinkModelElement remove(int index) {
		E element = (E) get(index);
		getPrimitive().remove(index);
		return (ISimulinkModelElement) element;
	}

	@Override
	public ISimulinkModelElement get(int index) {
		P handle = getPrimitive().get(index);
		return (ISimulinkModelElement) manager.construct(handle);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean add(ISimulinkModelElement e) {
		int size = getPrimitive().size();
		getPrimitive().add(manager.getId((E) e));
		return getPrimitive().size() == size + 1;
	}
	
	public boolean addPrimitive(Object primitive) {
		if (primitive != null) {
			if (isInstanceOfPrimitiveArray(primitive)) {
				getPrimitive().addAll(getPrimitiveFromArray(primitive));
			} else if (primitive instanceof List) {
				getPrimitive().addAll(getPrimitiveFromList((List<?>)primitive));
			} else if (isInstanceOfPrimitive(primitive)) {
				getPrimitive().addAll(getPrimitiveFromSingle(primitive));
			} else {
				getPrimitive().add((P)primitive);
			}
			return true;
		}
		return false;
	}
	
	public boolean removePrimitive(Object primitive) {
		if (primitive != null) {
			if (isInstanceOfPrimitiveArray(primitive)) {
				getPrimitive().removeAll(getPrimitiveFromArray(primitive));
			} else if (primitive instanceof List) {
				getPrimitive().removeAll(getPrimitiveFromList((List<?>)primitive));
			} else if (isInstanceOfPrimitive(primitive)) {
				getPrimitive().removeAll(getPrimitiveFromSingle(primitive));
			} else {
				getPrimitive().remove((P)primitive);
			}
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ISimulinkModelElement set(int index, ISimulinkModelElement element) {
		E previous = (E) get(index);
		getPrimitive().set(index, manager.getId((E) element));
		return (ISimulinkModelElement) previous;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void add(int index, ISimulinkModelElement element) {
		getPrimitive().add(index, manager.getId((E) element));
	}

	@SuppressWarnings("unchecked")
	@Override
	public int indexOf(Object o) {
		if (isInstanceOf(o)) {
			return getPrimitive().indexOf(manager.getId((E) o));
		} else {
			return -1;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean contains(Object o) {
		if (isInstanceOf(o)) {
			return getPrimitive().contains(manager.getId((E) o));
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(Object o) {
		if (isInstanceOf(o)) {
			return getPrimitive().remove(manager.getId((E) o));
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int lastIndexOf(Object o) {
		if (isInstanceOf(o)) {
			return getPrimitive().lastIndexOf(manager.getId((E) o));
		}
		return -1;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean addAll(Collection<? extends ISimulinkModelElement> c) {
		if (c instanceof AbstractSimulinkCollection) {
			AbstractSimulinkCollection collection = (AbstractSimulinkCollection) c;
			return getPrimitive().addAll(collection.getPrimitive());
		} else {
			int original = getPrimitive().size();
			c.stream().forEach(e -> getPrimitive().add(manager.getId((E) e)));
			return original + c.size() == getPrimitive().size();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean containsAll(Collection<?> c) {
		if (c instanceof AbstractSimulinkCollection) {
			AbstractSimulinkCollection collection = (AbstractSimulinkCollection) c;
			return getPrimitive().containsAll(collection.getPrimitive());
		} else {
			return c.parallelStream()
					.filter(e -> isInstanceOf(e))
					.map(e -> getPrimitive().contains(manager.getId((E) e)))
					.reduce(Boolean::logicalAnd)
					.orElse(false);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean addAll(int index, Collection<? extends ISimulinkModelElement> c) {
		if (c instanceof AbstractSimulinkCollection) {
			AbstractSimulinkCollection collection = (AbstractSimulinkCollection) c;
			return getPrimitive().addAll(collection.getPrimitive());
		} else {
			return c.parallelStream()
					.filter(e -> isInstanceOf(e))
					.map(e -> getPrimitive().add(manager.getId((E) e)))
					.reduce(Boolean::logicalAnd)
					.orElse(false);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean removeAll(Collection<?> c) {
		if (c instanceof AbstractSimulinkCollection) {
			AbstractSimulinkCollection collection = (AbstractSimulinkCollection) c;
			return getPrimitive().removeAll(collection.getPrimitive());
		} else {
			return c.parallelStream()
					.filter(e -> isInstanceOf(e))
					.map(e -> getPrimitive().remove(manager.getId((E) e)))
					.reduce(Boolean::logicalAnd)
					.orElse(false);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean retainAll(Collection<?> c) {
		if (c instanceof AbstractSimulinkCollection) {
			AbstractSimulinkCollection collection = (AbstractSimulinkCollection) c;
			return getPrimitive().retainAll(collection.getPrimitive());
		} else {
			List<P> collect = c.parallelStream()
					.filter(e -> isInstanceOf(e))
					.map(e -> manager.getId((E) e))
					.collect(Collectors.toList());
			return getPrimitive().retainAll(collect);
		}
	}

	@Override
	public Object[] toArray() {
		return getPrimitive().stream()
				.map(e -> getManager().construct(e))
				.toArray();
	}
	
	@Override
	public <T> T[] toArray(T[] a) {
		return getPrimitive().stream()
				.map(e -> getManager().construct(e))
				.toArray(i -> a);
	}
	
	public M getManager() {
		return this.manager;
	}

	public List<P> getPrimitive() {
		return this.primitive;
	}

	protected abstract boolean isInstanceOf(Object object);

	protected abstract boolean isInstanceOfPrimitive(Object object);

	protected abstract boolean isInstanceOfPrimitiveArray(Object object);

	protected abstract Iterator<ISimulinkModelElement> getInternalIterator();

}
