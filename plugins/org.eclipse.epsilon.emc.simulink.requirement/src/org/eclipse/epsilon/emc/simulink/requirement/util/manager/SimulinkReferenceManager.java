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
import org.eclipse.epsilon.emc.simulink.requirement.model.element.SimulinkReference;
import org.eclipse.epsilon.emc.simulink.types.HandleObject;
import org.eclipse.epsilon.emc.simulink.util.manager.AbstractManager;

public class SimulinkReferenceManager extends AbstractManager<SimulinkReference, HandleObject> {

	public SimulinkReferenceManager(SimulinkRequirementModel model) {
		super(model);	
	}

	@Override
	public SimulinkReference construct(HandleObject id) {
		return new SimulinkReference(getModel(), getEngine(), id); // FIXME
	}

	@Override
	public HandleObject getId(SimulinkReference from) {
		return (HandleObject) from.getHandle().getHandle();
		
	}

	@Override
	public SimulinkRequirementModel getModel() {
		return (SimulinkRequirementModel) model;
	}

}
