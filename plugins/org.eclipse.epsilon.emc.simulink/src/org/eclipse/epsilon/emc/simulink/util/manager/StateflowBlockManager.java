/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.util.manager;

import org.eclipse.epsilon.emc.simulink.exception.MatlabException;
import org.eclipse.epsilon.emc.simulink.model.SimulinkModel;
import org.eclipse.epsilon.emc.simulink.model.element.StateflowBlock;

public class StateflowBlockManager extends AbstractManager<StateflowBlock, Double> {
	
	public StateflowBlockManager(SimulinkModel model){
		super(model);
	}
	
	@Override
	public StateflowBlock construct(Double id) {
		try {
			return new StateflowBlock(getModel(), getEngine(), id);
		} catch (MatlabException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Double getId(StateflowBlock from) {
		return from.getId();
	}	
	
	@Override
	public SimulinkModel getModel() {
		return (SimulinkModel) model;
	}
}