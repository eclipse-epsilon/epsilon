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
import org.eclipse.epsilon.emc.simulink.requirement.model.element.SimulinkJustification;
import org.eclipse.epsilon.emc.simulink.types.HandleObject;
import org.eclipse.epsilon.emc.simulink.util.manager.AbstractManager;

public class SimulinkJustificationManager extends AbstractManager<SimulinkJustification, HandleObject> {

	public SimulinkJustificationManager(SimulinkRequirementModel model) {
		super(model);	
	}

	@Override
	public SimulinkJustification construct(HandleObject id) {
		return new SimulinkJustification(getModel(), getEngine(), id); // FIXME
	}

	@Override
	public HandleObject getId(SimulinkJustification from) {
		return (HandleObject) from.getHandle().getHandle();
		
	}

	@Override
	public SimulinkRequirementModel getModel() {
		return (SimulinkRequirementModel) model;
	}

}
