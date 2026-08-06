/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.dictionary.util.collection;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.epsilon.emc.simulink.dictionary.model.SimulinkDictionaryModel;
import org.eclipse.epsilon.emc.simulink.dictionary.model.data.SimulinkDataType;
import org.eclipse.epsilon.emc.simulink.dictionary.util.manager.SimulinkDataTypeManager;
import org.eclipse.epsilon.emc.simulink.model.element.ISimulinkModelElement;
import org.eclipse.epsilon.emc.simulink.types.HandleObject;
import org.eclipse.epsilon.emc.simulink.util.collection.AbstractElementIterator;
import org.eclipse.epsilon.emc.simulink.util.collection.AbstractListIterator;
import org.eclipse.epsilon.emc.simulink.util.collection.AbstractSimulinkCollection;

public class SimulinkDataTypeCollection extends AbstractSimulinkCollection<SimulinkDataType, HandleObject, SimulinkDataTypeManager> {

	public SimulinkDataTypeCollection(Object primitive, SimulinkDictionaryModel model) {
		super(primitive, new SimulinkDataTypeManager(model));
	}

	@Override
	protected boolean isInstanceOf(Object object) {
		return object instanceof SimulinkDataType;
	}

	@Override
	protected boolean isInstanceOfPrimitive(Object object) {
		return HandleObject.is(object);
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
		return new SimulinkDataTypeCollection(getPrimitive().subList(fromIndex, toIndex), getManager().getModel());
	}
	
	@Override
	public ListIterator<ISimulinkModelElement> listIterator() {
		return new SimulinkDataTypeListIterator();
	}

	@Override
	public ListIterator<ISimulinkModelElement> listIterator(int index) {
		return new SimulinkDataTypeListIterator(index);
	}

	@Override
	protected Iterator<ISimulinkModelElement> getInternalIterator() {
		return new SimulinkDataTypeIterator();
	}
		
	public class SimulinkDataTypeIterator extends AbstractElementIterator<SimulinkDataType, HandleObject, SimulinkDataTypeManager>{
		
		public SimulinkDataTypeIterator(){
			super(getPrimitive(), getManager());
		}
		
	}
	
	public class SimulinkDataTypeListIterator extends AbstractListIterator<SimulinkDataType, HandleObject, SimulinkDataTypeManager> {
		
		public SimulinkDataTypeListIterator(){
			super(getPrimitive(), getManager());
		}
		
		public SimulinkDataTypeListIterator(int index){
			super(index, getPrimitive(), getManager());
		}
		
	}

}
