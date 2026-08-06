/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.dictionary.util.manager;

import org.eclipse.epsilon.emc.simulink.dictionary.model.SimulinkDictionaryModel;
import org.eclipse.epsilon.emc.simulink.dictionary.model.data.SimulinkDataType;
import org.eclipse.epsilon.emc.simulink.types.HandleObject;
import org.eclipse.epsilon.emc.simulink.util.manager.AbstractManager;

public class SimulinkDataTypeManager extends AbstractManager<SimulinkDataType, HandleObject> {

	public SimulinkDataTypeManager(SimulinkDictionaryModel model) {
		super(model);	
	}

	@Override
	public SimulinkDataType construct(HandleObject id) {
		return new SimulinkDataType(getModel(), getEngine(), id);
	}

	@Override
	public HandleObject getId(SimulinkDataType from) {
		return (HandleObject) from.getHandle();
	}

	@Override
	public SimulinkDictionaryModel getModel() {
		return (SimulinkDictionaryModel) model;
	}

}
