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

import org.eclipse.epsilon.emc.simulink.exception.MatlabRuntimeException;
import org.eclipse.epsilon.emc.simulink.model.SimulinkModel;
import org.eclipse.epsilon.emc.simulink.model.element.SimulinkPort;

public class SimulinkPortManager extends AbstractManager<SimulinkPort, Double> {
	
	public SimulinkPortManager(SimulinkModel model){
		super(model);
	}
	
	@Override
	public SimulinkPort construct(Double id) {
		try {
			return new SimulinkPort(getModel(), getEngine(), id);
		} catch (MatlabRuntimeException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Double getId(SimulinkPort from) {
		return (Double) from.getHandle();
	}	
	
	@Override
	public SimulinkModel getModel() {
		return (SimulinkModel) model;
	}
	
}