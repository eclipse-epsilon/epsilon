/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.dictionary.model.data;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.epsilon.emc.simulink.dictionary.model.SimulinkDictionaryModel;
import org.eclipse.epsilon.emc.simulink.dictionary.model.element.ISimulinkDictionaryModelElement;
import org.eclipse.epsilon.emc.simulink.engine.MatlabEngine;
import org.eclipse.epsilon.emc.simulink.exception.MatlabException;
import org.eclipse.epsilon.emc.simulink.model.element.MatlabHandleElement;
import org.eclipse.epsilon.emc.simulink.model.element.SimulinkModelElement;
import org.eclipse.epsilon.emc.simulink.types.HandleObject;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;

public class SimulinkDataType extends SimulinkModelElement implements ISimulinkDictionaryModelElement {
	
	protected MatlabHandleElement dataType;
	protected String type;
	protected Map<String, Object> properties = new HashMap<>();

	// Create a new entry
	public SimulinkDataType(SimulinkDictionaryModel model, MatlabEngine engine, String type) {
		super(model, engine);
		if (!type.startsWith("Simulink.")) {
			throw new IllegalStateException("Wrong type " + type);
		}
		try {
			dataType = new MatlabHandleElement(model, engine, (HandleObject) engine.fevalWithResult(type));
			this.type = type;
		} catch (MatlabException e) {
			throw new IllegalStateException();
		} 
	}

	// Create from handle
	public SimulinkDataType(SimulinkDictionaryModel model, MatlabEngine engine, HandleObject entryHandle) {
		super(model, engine);
		dataType = new MatlabHandleElement(model, engine, entryHandle);
		try {
			type = (String) engine.fevalWithResult("class", entryHandle);
		} catch (MatlabException e) {
			throw new IllegalStateException();
		}
	}

	@Override
	public Object getProperty(String property) throws EolRuntimeException {
		return dataType.getProperty(property);	
	}


	@Override
	public void setProperty(String property, Object value) throws EolRuntimeException {
		dataType.setProperty(property, value);
	}

	@Override
	public boolean deleteElementInModel() throws EolRuntimeException {
		try {
			engine.feval("deleteEntry", dataType.getHandle());
			return true;
		} catch (MatlabException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Collection<String> getAllTypeNamesOf() {
		return Arrays.asList("Entry");
	}

	@Override
	public String getType() {
		return type;
	}
	
	@Override
	public Object getHandle() {
		return dataType.getHandle();
	}

}
