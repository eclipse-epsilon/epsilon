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
import org.eclipse.epsilon.emc.simulink.model.element.ISimulinkModelElement;
import org.eclipse.epsilon.emc.simulink.model.element.MatlabHandleElement;
import org.eclipse.epsilon.emc.simulink.model.element.SimulinkModelElement;
import org.eclipse.epsilon.emc.simulink.requirement.model.SimulinkRequirementModel;
import org.eclipse.epsilon.emc.simulink.requirement.util.collection.SimulinkRequirementCollection;
import org.eclipse.epsilon.emc.simulink.types.HandleObject;
import org.eclipse.epsilon.emc.simulink.types.Struct;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;

public class SimulinkRequirement extends SimulinkModelElement implements ISimulinkRequirementModelElement{

	protected MatlabHandleElement requirementHandle;
	
	public SimulinkRequirement(SimulinkRequirementModel model, MatlabEngine engine, SimulinkRequirement requirement) {
		super(model, engine);
		try {
			requirementHandle = new MatlabHandleElement(model, engine, (HandleObject) engine.fevalWithResult("add", requirement.getHandle()));
		} catch (MatlabException e) {
			e.printStackTrace();
		}
	}
	
	public SimulinkRequirement(SimulinkRequirementModel model, MatlabEngine engine, SimulinkJustification justification) {
		super(model, engine);
		try {
			requirementHandle = new MatlabHandleElement(model, engine, (HandleObject) engine.fevalWithResult("add", justification.getHandle().getHandle()));
		} catch (MatlabException e) {
			e.printStackTrace();
		}
	}

	// Model Handle is a ReqSet
	public SimulinkRequirement(SimulinkRequirementModel model, MatlabEngine engine) {
		super(model, engine);
		try {
			requirementHandle = new MatlabHandleElement(model, engine, (HandleObject) engine.fevalWithResult("add", model.getHandle().getHandle()));
		} catch (MatlabException e) {
			e.printStackTrace();
		}
	}
	
	public SimulinkRequirement(SimulinkRequirementModel model, MatlabEngine engine, String subtype) {
		super(model, engine);
		try {
			requirementHandle = new MatlabHandleElement(model, engine, (HandleObject) engine.fevalWithResult("add", model.getHandle().getHandle(), "ReqType", subtype));
		} catch (MatlabException e) {
			e.printStackTrace();
		}
	}
	
	public SimulinkRequirement(SimulinkRequirementModel model, MatlabEngine engine, HandleObject id) {
		super(model, engine);
		requirementHandle = new MatlabHandleElement(model, engine, id);
	}
	
	//getAttribute Get requirement custom attributes
	@Override
	public Object getProperty(String property) throws EolRuntimeException {
		return requirementHandle.getProperty(property);
	}
	
	//setAttribute Set requirement custom attributes
	@Override
	public void setProperty(String property, Object value) throws EolRuntimeException {
		requirementHandle.setProperty(property, value);
	}

	@Override
	public Collection<String> getAllTypeNamesOf() {
		return Arrays.asList("Requirement", getType());
	}

	@Override
	public boolean deleteElementInModel() throws EolRuntimeException {
		if (requirementHandle != null) {				
			try {
				engine.feval("remove", requirementHandle.getHandle());
			} catch (MatlabException e) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Object getHandle() {
		return requirementHandle.getHandle();
	}
	
	@Override
	public String getType() {
		try {
			return (String) getProperty("Type");
		} catch (EolRuntimeException e) {
			return "Requirement";
		}
	}

	public SimulinkRequirementCollection children() {
		try {
			Object collection = engine.fevalWithResult("children", getHandle());
			return new SimulinkRequirementCollection(collection, (SimulinkRequirementModel) getOwningModel());
		} catch (MatlabException e) {
			return new SimulinkRequirementCollection(null, (SimulinkRequirementModel) getOwningModel());
		}
	}
	
	public SimulinkRequirement add() {
		try {
			HandleObject id = (HandleObject) engine.fevalWithResult("add", getHandle());
			return new SimulinkRequirement((SimulinkRequirementModel) getOwningModel(), engine, id);
		} catch (MatlabException e) {
			return null;
		}
	}
	
	public SimulinkRequirement parent() {
		try {
			HandleObject id = (HandleObject) engine.fevalWithResult("parent", getHandle());
			if (id != null) {				
				return new SimulinkRequirement((SimulinkRequirementModel) getOwningModel(), engine, id);
			} else {
				return null;
			}
		} catch (MatlabException e) {
			return null;
		}
	}
	
	public Struct getImplementationStatus() throws MatlabException {
		return (Struct) engine.fevalWithResult("getImplementationStatus", getHandle());
	}
	
	public SimulinkLink justifyImplementation(SimulinkJustification justification){
		return justify(justification, "Implementation");
	}
	
	public SimulinkLink justifyVerification(SimulinkJustification justification){
		return justify(justification, "Verification");
	}
	
	public SimulinkLink justify(SimulinkJustification justification, String kind){
		try {
			HandleObject id = (HandleObject) engine.fevalWithResult("justify" + kind, getHandle(), justification.getHandle().getHandle());
			return new SimulinkLink((SimulinkRequirementModel) getOwningModel(), engine, id);
		} catch (MatlabException e) {
			return null;
		}
	}
	
	public SimulinkLink linkTo(ISimulinkModelElement element){
		try {
			HandleObject id = (HandleObject) engine.fevalWithResult("slreq.createLink", getHandle(), element.getHandle());
			return new SimulinkLink((SimulinkRequirementModel) getOwningModel(), engine, id);
		} catch (MatlabException e) {
			return null;
		}
	}
	
	public SimulinkLink linkFrom(ISimulinkModelElement element){
		try {
			HandleObject id = (HandleObject) engine.fevalWithResult("slreq.createLink", element.getHandle(), getHandle());
			return new SimulinkLink((SimulinkRequirementModel) getOwningModel(), engine, id);
		} catch (MatlabException e) {
			return null;
		}
	}
	
	/**
	reqSet - Return parent requirements set
	*/
	
}
