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
import org.eclipse.epsilon.emc.simulink.requirement.model.element.SimulinkLinkSet;
import org.eclipse.epsilon.emc.simulink.types.HandleObject;
import org.eclipse.epsilon.emc.simulink.util.manager.AbstractManager;

public class SimulinkLinkSetManager extends AbstractManager<SimulinkLinkSet, HandleObject> {

	public SimulinkLinkSetManager(SimulinkRequirementModel model) {
		super(model);	
	}

	@Override
	public SimulinkLinkSet construct(HandleObject id) {
		return new SimulinkLinkSet(getModel(), getEngine(), id); // FIXME
	}

	@Override
	public HandleObject getId(SimulinkLinkSet from) {
		return (HandleObject) from.getHandle();
		
	}

	@Override
	public SimulinkRequirementModel getModel() {
		return (SimulinkRequirementModel) model;
	}

}
