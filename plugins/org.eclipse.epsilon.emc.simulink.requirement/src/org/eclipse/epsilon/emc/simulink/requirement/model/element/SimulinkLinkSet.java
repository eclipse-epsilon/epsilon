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
import org.eclipse.epsilon.emc.simulink.types.HandleObject;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;

/** 
save Save link set
sources Get link sources
 */
public class SimulinkLinkSet extends SimulinkModelElement implements ISimulinkRequirementModelElement {

	/*public SimulinkLinkSet(AbstractSimulinkModel model) {
		//myLinkset2 = slreq.load(model.getFile());
		//myLinkset2.Description = 'Link set for the fuel system'
		//myLinkset2.Description <- getter?
	}*/
	
	protected MatlabHandleElement linkSetHandle;
	
	public SimulinkLinkSet(SimulinkRequirementModel model, MatlabEngine engine) {
		super(model, engine);
	}
	
	public SimulinkLinkSet(SimulinkRequirementModel model, MatlabEngine engine, String artifact) {
		super(model, engine);
		HandleObject id;
		try {
			id = (HandleObject) engine.fevalWithResult("add", model.getHandle().getHandle(), "Artifact", artifact);
			linkSetHandle = new MatlabHandleElement(model, engine, id);
		} catch (MatlabException e) {
			e.printStackTrace(); //FIXME
		}
	}
	
	public void setArtifact(String artifact) {
		try {
			linkSetHandle = new MatlabHandleElement(model, engine, (HandleObject) engine.fevalWithResult("slreq.load", artifact));
		} catch (MatlabException e) {
			e.printStackTrace();
		}
	}
	
	public SimulinkLinkSet(SimulinkRequirementModel model, MatlabEngine engine, HandleObject id) {
		super(model, engine);
		linkSetHandle = new MatlabHandleElement(model, engine, id);
	}

	@Override
	public Object getProperty(String property) throws EolRuntimeException {
		return linkSetHandle.getProperty(property);
	}

	@Override
	public void setProperty(String property, Object value) throws EolRuntimeException {
		linkSetHandle.setProperty(property, value);
	}

	@Override
	public Collection<String> getAllTypeNamesOf() {
		return Arrays.asList(getType());
	}

	@Override
	public boolean deleteElementInModel() throws EolRuntimeException {
		throw new IllegalAccessError("Cannot remove LinkSet");
	}
	
	@Override
	public Object getHandle() {
		return linkSetHandle.getHandle();
	}
	
	@Override
	public String getType() {
		return "LinkSet";
	}
}
