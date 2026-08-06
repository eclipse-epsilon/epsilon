/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.model.element;

import org.eclipse.epsilon.emc.simulink.engine.MatlabEngine;
import org.eclipse.epsilon.emc.simulink.model.IGenericSimulinkModel;
import org.eclipse.epsilon.eol.models.IModel;

public abstract class SimulinkModelElement implements ISimulinkModelElement {

	protected IGenericSimulinkModel model = null;
	protected MatlabEngine engine = null;
	protected String type = null;
	protected String superType = null;


	public SimulinkModelElement(IGenericSimulinkModel model, MatlabEngine engine) {
		this.model = model;
		this.engine = engine;
	}
		
	@Override
	public IModel getOwningModel() {
		return this.model;
	}
	
	public MatlabEngine getEngine() {
		return this.engine;
	}
	
	@Override
	public String getType() {
		return this.type;
	}
	
	public String getSuperType() {
		return superType;
	}

}
