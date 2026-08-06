/*********************************************************************
 * Copyright (c) 2008 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.requirement.model.element;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.epsilon.emc.simulink.engine.MatlabEngine;
import org.eclipse.epsilon.emc.simulink.exception.MatlabException;
import org.eclipse.epsilon.emc.simulink.model.element.MatlabHandleElement;
import org.eclipse.epsilon.emc.simulink.model.element.SimulinkModelElement;
import org.eclipse.epsilon.emc.simulink.requirement.model.SimulinkRequirementModel;
import org.eclipse.epsilon.emc.simulink.requirement.util.collection.SimulinkJustificationCollection;
import org.eclipse.epsilon.emc.simulink.types.HandleObject;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;

public class SimulinkJustification extends SimulinkModelElement implements ISimulinkRequirementModelElement {

	protected MatlabHandleElement justificationHandle;
	
	public SimulinkJustification(SimulinkRequirementModel model, MatlabEngine engine, SimulinkJustification justif) {
		super(model, engine);
		try {
			justificationHandle = new MatlabHandleElement(model, engine, (HandleObject) engine.fevalWithResult("add", justif.getHandle().getHandle()));
		} catch (MatlabException e) {
			e.printStackTrace();
		}
	}
	

	public SimulinkJustification(SimulinkRequirementModel model, MatlabEngine engine) {
		super(model, engine);
		try {
			justificationHandle = new MatlabHandleElement(model, engine, (HandleObject) engine.fevalWithResult("addJustification", model.getHandle().getHandle()));
		} catch (MatlabException e) {
			e.printStackTrace();
		}
	}
	
	public SimulinkJustification(SimulinkRequirementModel model, MatlabEngine engine, HandleObject id) {
		super(model, engine);
		justificationHandle = new MatlabHandleElement(model, engine, id);
	}

	@Override
	public Object getProperty(String property) throws EolRuntimeException {
		return justificationHandle.getProperty(property);
	}

	@Override
	public void setProperty(String property, Object value) throws EolRuntimeException {
		justificationHandle.setProperty(property, value);
	}

	@Override
	public Collection<String> getAllTypeNamesOf() {
		return Arrays.asList(getType());
	}

	@Override
	public boolean deleteElementInModel() throws EolRuntimeException {
		if (justificationHandle != null) {
			try {
				engine.feval("remove", justificationHandle.getHandle());
			} catch (MatlabException e) {
				return false;
			}
		}
		return true;
	}

	@Override
	public MatlabHandleElement getHandle() {
		return justificationHandle;
	}
	
	public SimulinkJustificationCollection children() {
		try {
			Object children = engine.fevalWithResult("children", justificationHandle.getHandle());
			return new SimulinkJustificationCollection(children, (SimulinkRequirementModel) model);
		} catch (MatlabException e) {
			return null; //FIXME
		} 
	}
	
	public SimulinkRequirementModel reqSet() {
		return (SimulinkRequirementModel) model;
	}
	
	public ISimulinkRequirementModelElement parent() {
		try {
			MatlabHandleElement parent = new MatlabHandleElement((SimulinkRequirementModel)model, getEngine(), (HandleObject) engine.fevalWithResult("parent", justificationHandle.getHandle()));
			if ("Justification".equals(parent.getType())){
				return new SimulinkJustification((SimulinkRequirementModel)model, engine, (HandleObject) parent.getHandle());
			} else {
				return (SimulinkRequirementModel) model;
			}
			 
		} catch (MatlabException e) {
			return null; //FIXME
		}
	}
	
}
