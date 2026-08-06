/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.dictionary.model.element;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.epsilon.emc.simulink.dictionary.model.SimulinkDictionaryModel;
import org.eclipse.epsilon.emc.simulink.engine.MatlabEngine;
import org.eclipse.epsilon.emc.simulink.exception.MatlabException;
import org.eclipse.epsilon.emc.simulink.model.element.ISimulinkModelElement;
import org.eclipse.epsilon.emc.simulink.model.element.MatlabHandleElement;
import org.eclipse.epsilon.emc.simulink.model.element.SimulinkModelElement;
import org.eclipse.epsilon.emc.simulink.types.HandleObject;
import org.eclipse.epsilon.eol.exceptions.EolIllegalPropertyException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;

public class SimulinkEntry extends SimulinkModelElement implements ISimulinkDictionaryModelElement {

	protected MatlabHandleElement entry;
	protected SimulinkSection section;
	protected String entryType;
	
	protected Map<String, Object> properties = new HashMap<>();

	public SimulinkEntry(SimulinkDictionaryModel model, MatlabEngine engine, SectionEnum sectionType) {
		super(model, engine);
		section = sectionType.getFromModel(model);
		entryType = sectionType.getEpsilonEntryName();
	}

	// Create a new entry
	public SimulinkEntry(SimulinkDictionaryModel model, MatlabEngine engine, SimulinkSection section) {
		super(model, engine);
		this.section = section;
	}

	// Create from handle
	public SimulinkEntry(SimulinkDictionaryModel model, MatlabEngine engine, HandleObject entryHandle) {
		super(model, engine);
		entry = new MatlabHandleElement(model, engine, entryHandle);
	}
	
	public SimulinkSection getSection() {
		return section;
	}
	
	public String getEntryType() {
		if (entryType == null) {
			try {
				entryType = SectionEnum.fromMatlab((String) getSection().getProperty("Name")).getEpsilonEntryName();
			} catch (EolRuntimeException e) {
				return "";
			} 
		}
		return entryType;
	}

	@Override
	public Object getProperty(String property) throws EolRuntimeException {
		if (entry != null) {
			if ("Value".equals(property)) {
				try {
					return engine.fevalWithResult("getValue", entry.getHandle());
				} catch (MatlabException e) {
					if (e.isUnsupportedTypeException()) {
						return null;
					} else {						
						throw new EolIllegalPropertyException(this, property, null, null);
					}
				}
			} else {				
				return entry.getProperty(property);
			}
		} else {
			if (properties.containsKey(property)) {
				return properties.get(property);
			} else {
				throw new EolIllegalPropertyException(this, property, null, null);
			}
		}
	}

	@Override
	public void setProperty(String property, Object value) throws EolRuntimeException {
		if (entry != null)
			if (property.equals("Value")) {
				
				if (value instanceof HandleObject) {
					value = ((HandleObject) value).getHandleObject();
				} else if (value instanceof ISimulinkModelElement) {
					value = ((ISimulinkModelElement) value).getHandle();
					if (value instanceof MatlabHandleElement) {
						value = ((MatlabHandleElement)value).getHandle();
					}
				}
				try {
					engine.feval(0, "setValue", entry.getHandle(), value);
				} catch (MatlabException e) {
					throw new EolIllegalPropertyException(this, property, null, null);
				} 
			} else {
				entry.setProperty(property, value);
			}
		else {
			properties.put(property, value);
			if (properties.containsKey("Name") && properties.containsKey("Value")) {
				try {
					HandleObject entryObject = (HandleObject) engine.fevalWithResult("addEntry",
							getSection().getHandle(), 
							properties.get("Name"),
							properties.get("Value"));
					entry = new MatlabHandleElement(model, engine, entryObject);
					
					properties.remove("Name");
					properties.remove("Value");
					for (Map.Entry<String, Object> p : properties.entrySet()) {
						setProperty(p.getKey(), p.getValue());
					}
					properties = null;
				} catch (MatlabException e) {
					throw new EolIllegalPropertyException(this, property, null, null);
				}
			}
		}
	}

	@Override
	public boolean deleteElementInModel() throws EolRuntimeException {
		if (entry!= null) {			
			try {
				engine.fevalWithResult(0,"deleteEntry", entry.getHandle());
			} catch (MatlabException e) {
				throw new EolRuntimeException(e.getMessage());
			}
		}
		return true;
	}

	@Override
	public Collection<String> getAllTypeNamesOf() {
		return Arrays.asList("Entry", getEntryType());
	}

	@Override
	public String getType() {
		return "Entry";
	}

	@Override
	public Object getHandle() {
		return entry;
	}

}
