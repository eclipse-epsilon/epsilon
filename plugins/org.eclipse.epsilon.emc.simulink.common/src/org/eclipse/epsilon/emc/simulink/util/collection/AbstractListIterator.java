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

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.epsilon.emc.simulink.model.element.ISimulinkModelElement;
import org.eclipse.epsilon.emc.simulink.util.manager.Manager;

public class AbstractListIterator<T,I, M extends Manager<T, I>> extends AbstractElementIterator<T, I, M> implements ListIterator<ISimulinkModelElement> {
	
	ListIterator<I> listIterator;
			
	public AbstractListIterator(List<I> primitive, M manager){
		super(primitive, manager);
		listIterator = primitive.listIterator();
	}
	
	public AbstractListIterator(int index, List<I> primitive, M manager){
		super(primitive, manager);
		listIterator = primitive.listIterator(index);
	}
	
	@Override
	public Iterator<I> getIterator() {
		return listIterator;
	}

	@Override
	public boolean hasPrevious() {
		return listIterator.hasPrevious();
	}

	@Override
	public int nextIndex() {
		return listIterator.nextIndex();
	}

	@Override
	public int previousIndex() {
		return listIterator.previousIndex();
	}

	@Override
	public void remove() {
		listIterator.remove();
	}

	@Override
	public ISimulinkModelElement previous() {
		return (ISimulinkModelElement) manager.construct(listIterator.previous());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void set(ISimulinkModelElement e) {
		listIterator.set(manager.getId((T)e));
	}

	@SuppressWarnings("unchecked")
	@Override
	public void add(ISimulinkModelElement e) {
		listIterator.add(manager.getId((T) e));
	}
	
}