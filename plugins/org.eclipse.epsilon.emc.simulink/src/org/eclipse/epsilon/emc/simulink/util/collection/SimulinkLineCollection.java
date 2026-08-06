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

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.epsilon.emc.simulink.model.SimulinkModel;
import org.eclipse.epsilon.emc.simulink.model.element.ISimulinkModelElement;
import org.eclipse.epsilon.emc.simulink.model.element.SimulinkLine;
import org.eclipse.epsilon.emc.simulink.operations.SimulinkCollectOperation;
import org.eclipse.epsilon.emc.simulink.operations.SimulinkSelectOperation;
import org.eclipse.epsilon.emc.simulink.util.manager.SimulinkLineManager;
import org.eclipse.epsilon.eol.execute.operations.AbstractOperation;
import org.eclipse.epsilon.eol.execute.operations.declarative.ExistsOperation;
import org.eclipse.epsilon.eol.execute.operations.declarative.FindOneOperation;
import org.eclipse.epsilon.eol.execute.operations.declarative.FindOperation;
import org.eclipse.epsilon.eol.execute.operations.declarative.ForAllOperation;
import org.eclipse.epsilon.eol.execute.operations.declarative.IAbstractOperationContributor;
import org.eclipse.epsilon.eol.execute.operations.declarative.RejectOneOperation;
import org.eclipse.epsilon.eol.execute.operations.declarative.RejectOperation;
import org.eclipse.epsilon.eol.execute.operations.declarative.SelectOneOperation;
import org.eclipse.epsilon.eol.execute.operations.declarative.SortByOperation;

public class SimulinkLineCollection extends AbstractSimulinkCollection<SimulinkLine, Double, SimulinkLineManager> implements IAbstractOperationContributor{

	public SimulinkLineCollection(Object primitive, SimulinkModel model) {
		super(primitive, new SimulinkLineManager(model));
	}
	
	@Override
	protected boolean isInstanceOf(Object object) {
		return object instanceof SimulinkLine;
	}

	@Override
	protected boolean isInstanceOfPrimitive(Object object) {
		return object instanceof Double;
	}

	@Override
	protected boolean isInstanceOfPrimitiveArray(Object object) {
		if (object instanceof Double[])
			return true;
		if (object instanceof Object[]) {
			return (Arrays.asList((Object[])object)).stream().allMatch(h -> isInstanceOfPrimitive(h));
		}
		return false;
	}
	
	@Override
	public List<ISimulinkModelElement> subList(int fromIndex, int toIndex) {
		return new SimulinkLineCollection(getPrimitive().subList(fromIndex, toIndex), getManager().getModel());
	}
	
	@Override
	public ListIterator<ISimulinkModelElement> listIterator() {
		return new SimulinkLineListIterator();
	}

	@Override
	public ListIterator<ISimulinkModelElement> listIterator(int index) {
		return new SimulinkLineListIterator(index);
	}

	@Override
	protected Iterator<ISimulinkModelElement> getInternalIterator() {
		return new SimulinkLineIterator();
	}
		
	protected class SimulinkLineIterator extends AbstractElementIterator<SimulinkLine, Double, SimulinkLineManager>{
		
		SimulinkLineIterator(){
			super(getPrimitive(), getManager());
		}
		
	}
	
	protected class SimulinkLineListIterator extends AbstractListIterator<SimulinkLine, Double, SimulinkLineManager> {
		
		SimulinkLineListIterator(){
			super(getPrimitive(), getManager());
		}
		
		SimulinkLineListIterator(int index){
			super(index, getPrimitive(), getManager());
		}
		
	}

	@Override
	public AbstractOperation getAbstractOperation(String name) {
		SimulinkModel model = getManager().getModel();
		if (model.isFindOptimisationEnabled()) {
			switch (name) {
			
			case "select":			
				return new SimulinkSelectOperation(model);
			case "collect":
				return new SimulinkCollectOperation(model.getEngine());
	
			/** Select Based */
			case "exists":
				ExistsOperation existsOperation = new ExistsOperation();
				existsOperation.setDelegateOperation(new SimulinkSelectOperation(model));
				return existsOperation;
			case "findOne":
				FindOneOperation findOneOperation = new FindOneOperation();
				findOneOperation.setDelegateOperation(new SimulinkSelectOperation(model));
				return findOneOperation;
			case "find":
				FindOperation findOperation = new FindOperation();
				findOperation.setDelegateOperation(new SimulinkSelectOperation(model));
				return findOperation;
			case "forAll":
				ForAllOperation forAllOperation = new ForAllOperation();
				forAllOperation.setDelegateOperation(new SimulinkSelectOperation(model));
				return forAllOperation;
			case "rejectOne":
				RejectOneOperation rejectOneOperation = new RejectOneOperation();
				rejectOneOperation.setDelegateOperation(new SimulinkSelectOperation(model));
				return rejectOneOperation;
			case "reject":
				RejectOperation rejectOperation = new RejectOperation();
				rejectOperation.setDelegateOperation(new SimulinkSelectOperation(model));
				return rejectOperation;
			case "selectOne":
				SelectOneOperation selectOneOperation = new SelectOneOperation();
				selectOneOperation.setDelegateOperation(new SimulinkSelectOperation(model));
				return selectOneOperation;
			
			/** Collect Based */
			case "sortBy":
				SortByOperation sortByOperation = new SortByOperation();
				sortByOperation.setDelegateOperation(new SimulinkCollectOperation(model.getEngine()));
				return sortByOperation;
			}
		} 
		return null;
	}

}
