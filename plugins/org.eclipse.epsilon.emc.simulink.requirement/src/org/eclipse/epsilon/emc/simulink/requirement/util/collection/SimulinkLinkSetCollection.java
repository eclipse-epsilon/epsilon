/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.requirement.util.collection;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.epsilon.emc.simulink.model.element.ISimulinkModelElement;
import org.eclipse.epsilon.emc.simulink.requirement.model.SimulinkRequirementModel;
import org.eclipse.epsilon.emc.simulink.requirement.model.element.SimulinkLinkSet;
import org.eclipse.epsilon.emc.simulink.requirement.util.manager.SimulinkLinkSetManager;
import org.eclipse.epsilon.emc.simulink.types.HandleObject;
import org.eclipse.epsilon.emc.simulink.util.collection.AbstractElementIterator;
import org.eclipse.epsilon.emc.simulink.util.collection.AbstractListIterator;
import org.eclipse.epsilon.emc.simulink.util.collection.AbstractSimulinkCollection;

public class SimulinkLinkSetCollection extends AbstractSimulinkCollection<SimulinkLinkSet, HandleObject, SimulinkLinkSetManager> {

	public SimulinkLinkSetCollection(Object primitive, SimulinkRequirementModel model) {
		super(primitive, new SimulinkLinkSetManager(model));
	}

	@Override
	protected boolean isInstanceOf(Object object) {
		return object instanceof SimulinkLinkSet;
	}

	@Override
	protected boolean isInstanceOfPrimitive(Object object) {
		return HandleObject.is(object) || object.getClass().getName().equals(HandleObject.class.getName());
	}

	@Override
	protected boolean isInstanceOfPrimitiveArray(Object object) {
		if (object instanceof Object[]) {
			return (Arrays.asList(object)).stream().allMatch(h -> HandleObject.is(h));

		}
		return false;
	}
	
	@Override
	public List<ISimulinkModelElement> subList(int fromIndex, int toIndex) {
		return new SimulinkLinkSetCollection(getPrimitive().subList(fromIndex, toIndex), getManager().getModel());
	}
	
	@Override
	public ListIterator<ISimulinkModelElement> listIterator() {
		return new SimulinkLinkSetListIterator();
	}

	@Override
	public ListIterator<ISimulinkModelElement> listIterator(int index) {
		return new SimulinkLinkSetListIterator(index);
	}

	@Override
	protected Iterator<ISimulinkModelElement> getInternalIterator() {
		return new SimulinkLinkSetIterator();
	}
		
	protected class SimulinkLinkSetIterator extends AbstractElementIterator<SimulinkLinkSet, HandleObject, SimulinkLinkSetManager>{
		
		SimulinkLinkSetIterator(){
			super(getPrimitive(), getManager());
		}
		
	}
	
	protected class SimulinkLinkSetListIterator extends AbstractListIterator<SimulinkLinkSet, HandleObject, SimulinkLinkSetManager> {
		
		SimulinkLinkSetListIterator(){
			super(getPrimitive(), getManager());
		}
		
		SimulinkLinkSetListIterator(int index){
			super(index, getPrimitive(), getManager());
		}
		
	}

}
