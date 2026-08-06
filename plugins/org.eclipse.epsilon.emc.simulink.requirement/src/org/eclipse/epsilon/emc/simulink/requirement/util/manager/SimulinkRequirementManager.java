/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.requirement.util.manager;

import org.eclipse.epsilon.emc.simulink.requirement.model.SimulinkRequirementModel;
import org.eclipse.epsilon.emc.simulink.requirement.model.element.SimulinkRequirement;
import org.eclipse.epsilon.emc.simulink.types.HandleObject;
import org.eclipse.epsilon.emc.simulink.util.manager.AbstractManager;

public class SimulinkRequirementManager extends AbstractManager<SimulinkRequirement, HandleObject> {

	public SimulinkRequirementManager(SimulinkRequirementModel model) {
		super(model);	
	}

	@Override
	public SimulinkRequirement construct(HandleObject id) {
		return new SimulinkRequirement(getModel(), getEngine(), id);
	}

	@Override
	public HandleObject getId(SimulinkRequirement from) {
		return (HandleObject) from.getHandle();
		
	}

	@Override
	public SimulinkRequirementModel getModel() {
		return (SimulinkRequirementModel) model;
	}

}
