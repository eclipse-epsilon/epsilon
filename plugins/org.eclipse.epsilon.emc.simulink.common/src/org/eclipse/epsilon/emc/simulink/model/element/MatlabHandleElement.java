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

import java.util.Collection;

import org.eclipse.epsilon.emc.simulink.engine.MatlabEngine;
import org.eclipse.epsilon.emc.simulink.exception.MatlabException;
import org.eclipse.epsilon.emc.simulink.model.IGenericSimulinkModel;
import org.eclipse.epsilon.emc.simulink.types.HandleObject;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;

public class MatlabHandleElement extends SimulinkModelElement {

	HandleObject handle;

	public MatlabHandleElement(IGenericSimulinkModel model, MatlabEngine engine, HandleObject handle) {
		super(model, engine);
		this.handle = handle;
	}

	@Override
	public boolean deleteElementInModel() throws EolRuntimeException {
		return false;
	}

	@Override
	public Object getProperty(String property) throws EolRuntimeException {
		try {
			setHandleInMatlabWorkspace();
			return engine.evalWithResult("handle." + property + ";");
		} catch (MatlabException e) {
			throw e.toEolRuntimeException();
		}
	}

	@Override
	public void setProperty(String property, Object value) throws EolRuntimeException {
		try {
			setHandleInMatlabWorkspace();
			String escaped = "?";
			if (value instanceof ISimulinkModelElement) {
				ISimulinkModelElement element = (ISimulinkModelElement) value;
				value = element.getHandle();
			} else {
				escaped = "'" + escaped + "'";
			}
			String cmd = "handle." + property + "=" + escaped + ";";
			engine.eval(cmd, value);

		} catch (MatlabException e) {
			throw e.toEolRuntimeException();
		}
	}

	@Override
	public Object getHandle() {
		return  handle.getHandleObject();
	}

	public void setHandleInMatlabWorkspace() throws MatlabException {
		// This is necessary to get properties of MATLAB objects
		// set the value of the handle to the variable "handle" in the base workspace
		engine.feval(0, "assignin", "base", "handle", getHandle());
	}
	
	@Override
	public String toString() {
		return handle.getHandleObject().getClass().toString();
	}

	@Override
	public Collection<String> getAllTypeNamesOf() {
		// TODO Auto-generated method stub
		return null;
	}

}
