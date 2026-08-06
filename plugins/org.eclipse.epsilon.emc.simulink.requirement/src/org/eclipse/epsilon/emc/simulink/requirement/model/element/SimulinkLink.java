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
import org.eclipse.epsilon.emc.simulink.types.HandleObject;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;

public class SimulinkLink extends SimulinkModelElement implements ISimulinkRequirementModelElement {

	protected MatlabHandleElement linkHandle;

	public SimulinkLink(SimulinkRequirementModel model, MatlabEngine engine, ISimulinkModelElement source, ISimulinkModelElement target) throws EolRuntimeException {
		super(model, engine);
		try {
			HandleObject handle = (HandleObject) engine.fevalWithResult("slreq.createLink", source.getHandle(), target.getHandle());
			linkHandle = new MatlabHandleElement(model, engine, handle); 
		} catch (MatlabException e) {
			throw new EolRuntimeException(e);
		}
	}
	
	public SimulinkLink(SimulinkRequirementModel model, MatlabEngine engine) {
		super(model, engine);
	}
	
	public SimulinkLink(SimulinkRequirementModel model, MatlabEngine engine, HandleObject id) {
		super(model, engine);
		linkHandle = new MatlabHandleElement(model, engine, id);
	}

	@Override
	public Object getProperty(String property) throws EolRuntimeException {
		return linkHandle.getProperty(property);
	}

	@Override
	public void setProperty(String property, Object value) throws EolRuntimeException {
		linkHandle.setProperty(property, value);
	}

	@Override
	public Collection<String> getAllTypeNamesOf() {
		return Arrays.asList(getType(), "Link");
	}

	@Override
	public boolean deleteElementInModel() throws EolRuntimeException {
		if (linkHandle != null) {				
			try {
				engine.feval("remove", linkHandle.getHandle());
			} catch (MatlabException e) {
				return false;
			}
		}
		return true;
	}

	@Override
	public MatlabHandleElement getHandle() {
		return linkHandle;
	}
	
	@Override
	public String getType() {
		try {
			return (String) getProperty("Type");
		} catch (EolRuntimeException e) {
			return "Link";
		}
	}
	
	public Boolean isResolved() throws EolRuntimeException {
		return isResolved("");
	}
	
	public Boolean isResolvedSource() throws EolRuntimeException {
		return isResolved("Source");
	}
	
	public Boolean isResolvedDestination() throws EolRuntimeException {
		return isResolved("Destination");
	}
	
	public Boolean isResolved(String location) throws EolRuntimeException {
		try {
			return ((Boolean) engine.fevalWithResult("isResolved" + location, linkHandle.getHandle())) ;
		} catch (MatlabException e) {
			throw new EolRuntimeException(e);
		}
	}
	
	Object source, destination;
	
	public SimulinkLink setSource(Object source) throws EolRuntimeException {
		if (linkHandle == null) {
			try {
				this.source = source;
				if (source instanceof ISimulinkModelElement) {
					source = ((ISimulinkModelElement) source).getHandle();
				}
				engine.feval("setSource", linkHandle, source);
				return this;
			} catch (MatlabException e) {
				throw new EolRuntimeException(e);
			}
		} else {
			throw new EolRuntimeException("Source already set");
		}
	}
	
	public Object getSource() throws EolRuntimeException {
		if (linkHandle == null) {
			return source;
		} else {
			try {
				return engine.fevalWithResult("source", linkHandle.getHandle());
			} catch (MatlabException e) {
				throw new EolRuntimeException(e);
			}
		}
	}
	
	public SimulinkLink setDestination(Object destination) throws EolRuntimeException {
		if (linkHandle == null) {
			try {
				this.destination = destination;
				if (destination instanceof ISimulinkModelElement) {
					destination = ((ISimulinkModelElement) destination).getHandle();
				}
				engine.feval("setDestination", linkHandle, destination);
				return this;
			} catch (MatlabException e) {
				throw new EolRuntimeException(e);
			}
		} else {
			throw new EolRuntimeException("Target already set");
		}
	}
	
	public Object getDestination() throws EolRuntimeException {
		if (linkHandle == null) {
			return destination;
		} else {
			try {
				return engine.fevalWithResult("destination", linkHandle.getHandle());
			} catch (MatlabException e) {
				throw new EolRuntimeException(e);
			}
		}
	}
	
	/**
	linkSet Return parent link set
	 */
	
}
