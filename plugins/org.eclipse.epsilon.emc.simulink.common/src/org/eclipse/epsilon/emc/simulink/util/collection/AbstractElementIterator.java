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

import org.eclipse.epsilon.emc.simulink.model.element.ISimulinkModelElement;
import org.eclipse.epsilon.emc.simulink.util.manager.Manager;

public abstract class AbstractElementIterator<T, I, M extends Manager<T, I>> implements Iterator<ISimulinkModelElement> {
	protected Iterator<I> iterator; 		
	protected M manager ;
	
	public AbstractElementIterator(List<I> primitive, M manager) {
		this.iterator = primitive.iterator();
		this.manager = manager;
	}

	@Override
	public synchronized boolean hasNext() {
		return getIterator().hasNext();
	}

	@Override
	public synchronized ISimulinkModelElement next() {
		return (ISimulinkModelElement) manager.construct(getIterator().next());
	}
	
	protected Iterator<I> getIterator() {
		return iterator;
	}
	
}