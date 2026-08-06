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

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.Collectors;

import org.eclipse.epsilon.emc.simulink.engine.MatlabEngine;
import org.eclipse.epsilon.emc.simulink.exception.MatlabException;
import org.eclipse.epsilon.emc.simulink.model.SimulinkModel;
import org.eclipse.epsilon.emc.simulink.model.TypeHelper.Kind;
import org.eclipse.epsilon.emc.simulink.util.SimulinkUtil;
import org.eclipse.epsilon.emc.simulink.util.StateflowUtil;
import org.eclipse.epsilon.emc.simulink.util.collection.StateflowBlockCollection;
import org.eclipse.epsilon.eol.exceptions.EolIllegalPropertyException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;

public class StateflowBlock extends SimulinkModelElement {
	
	/** CONSTANTS */

	//public static final String STATEFLOW_LIB = "sflib/";
	private static final String SIMPLE_TYPE = "class(?);";
	private static final String DELETE_SF = "?.delete;";

	/** FIELDS */

	private Double id;
	private HashMap<String, Object> properties = new HashMap<>();

	/** CONSTRUCTORS */

	// From StateflowBlock Parent
	public StateflowBlock(SimulinkModel model, MatlabEngine engine, String type, StateflowBlock parent) throws MatlabException, EolRuntimeException {
		super(model, engine);
		setType(type);
		buildFromStateflow(parent);
	}

	/** PARENT-LESS CONSTRUCTORS */

	public StateflowBlock(SimulinkModel model, MatlabEngine engine, String type) throws MatlabException {
		super(model, engine);
		setType(type);
	}
	
	/** FROM ID */

	// From known StateflowBlock Id
	public StateflowBlock(SimulinkModel model, MatlabEngine engine, Double stateflowId) throws MatlabException {
		super(model, engine);
		this.id = stateflowId;
		setType();
	}

	// From known StateflowBlock Id as Integer
	public StateflowBlock(SimulinkModel model, MatlabEngine engine, Integer stateflowId) throws MatlabException {
		this(model, engine, stateflowId.doubleValue());
	}

	// From known StateflowBlock Id
	public StateflowBlock(SimulinkModel model, MatlabEngine engine, String type, Double stateflowId) throws MatlabException {
		super(model, engine);
		this.id = stateflowId;
		setType();
	}

	// From known StateflowBlock Id as Integer
	public StateflowBlock(SimulinkModel model, MatlabEngine engine, String type, Integer stateflowId) throws MatlabException {
		this(model, engine, type, stateflowId.doubleValue());
	}

	/** TYPE */

	private void setType(String type) throws MatlabException {
		if (type != null && type.startsWith(SimulinkModel.STATEFLOW)) {
			this.type = type;
		} else {
			setType();
		}
	}
	
	private void setType() throws MatlabException {
		if (this.id != null) {
			String handle = StateflowUtil.getBlockHandleFromId(((SimulinkModel)model), engine, this.id);
			this.type = SimulinkModel.STATEFLOW + "." + (String) engine.evalWithResult(SIMPLE_TYPE, handle);
		}
	}
	
	@Override
	public String getType() { // RETURNS E.G. "STATE"
		return this.type;	
	}

	/** PROPERTIES **/

	private boolean isUnset() {
		return id == null;
	}

	@Override
	public Object getProperty(String property) throws EolIllegalPropertyException{ 
		if (isUnset()) {
			return properties.get(property.toLowerCase());
		} else {
			if (property.equalsIgnoreCase("parent") || property.equalsIgnoreCase("up")) {
				try {
					return getParent();
				} catch (EolRuntimeException e) {
					throw new EolIllegalPropertyException(this, property, null, null);
				}
			} else {
				String h;
				try {
					h = StateflowUtil.getBlockHandle(this);
					return engine.evalWithResult("?.?;", h, property);
				} catch (MatlabException e) {
					throw new EolIllegalPropertyException(this, property, null, null);
				}
			}
		}
	}

	@Override
	public void setProperty(String property, Object value) throws EolIllegalPropertyException { 
		try {
			if (property.equalsIgnoreCase("parent") || property.equalsIgnoreCase("up")) {
				if (value instanceof StateflowBlock) {
					try {
						setParent((StateflowBlock)value);
					} catch (EolRuntimeException e) {
						// e.printStackTrace();
						throw new EolIllegalPropertyException(this, property, null, null);
					}
				} else {
					throw new EolIllegalPropertyException(this, property, null, null);
				}
			} else {
				if (isUnset()) {
					properties.put(property.toLowerCase(), value);
				} else {
					String escaped = "?";

					String randomHandle = "";
					if (value instanceof StateflowBlock) {
						randomHandle = StateflowUtil.getBlockHandle(((StateflowBlock) value));
						value = randomHandle;
					}
					if (value instanceof String && !value.equals(randomHandle)) {
						String valueStr = (String) value;
						if 	( !( 
								( valueStr.startsWith("'") && valueStr.endsWith("'") ) ||
								( valueStr.startsWith("[") && valueStr.endsWith("]") ) 
							)) 
						{
							escaped = "'" + escaped + "'";
						}
					}
					if (value instanceof Collection) {
						value = "[" + ((Collection<?>) value).stream().map(String::valueOf).collect(Collectors.joining(",")) + "]";
					}
					escaped = escaped.replace("?", String.valueOf(value));
					String cmd = "?.? = " + escaped + ";";
					String h = StateflowUtil.getBlockHandle(this);
					engine.eval(cmd, h, property);					
				}
			}
		} catch (MatlabException e) {
			//e.printStackTrace();
			throw new EolIllegalPropertyException(this, property, null, null);
		}
	}

	/** IDENTIFIER */

	public Double getId() { 
		if (this.id == null) { 
			//System.err.println("ID WAS NULL");
		}
		return this.id;
	}
	
	public String getPath() {
		if (this.id != null) {
			try {
				String handle = StateflowUtil.getBlockHandleFromId(((SimulinkModel)model), engine, id);
				return (String) engine.evalWithResult("?.path;", handle);
			} catch (MatlabException e) {}
		}
		return null;
	}

	// PARENT CHILDREN

	public ISimulinkModelElement getParent() throws EolRuntimeException {
		try {
			String h = StateflowUtil.getBlockHandle(this);
			try {
				Double parentId = (Double) engine.evalWithResult("?.up.id;",h);
				return new StateflowBlock(((SimulinkModel)model), engine, parentId);
			} catch (MatlabException e) {
				String path = (String) engine.evalWithResult("?.path;",h);
				return new SimulinkBlock(path, ((SimulinkModel)model), engine);
			}
		} catch (MatlabException e1) {
			throw new EolRuntimeException(e1.getMessage());
		}
	}

	public void setParent(StateflowBlock parent) throws EolRuntimeException, MatlabException {
		if (!this.isUnset()) {
			changeParent(parent);
		} else {
			buildFromStateflow(parent);
			applyProperties();
		}
	}

	private void applyProperties() throws EolIllegalPropertyException {
		Iterator<String> keys = properties.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			setProperty(key, properties.get(key));
		}
		properties.clear();
	}

	private void changeParent(StateflowBlock parent) throws EolRuntimeException { // TODO
		throw new EolRuntimeException("Changing parent is not yet implemented");
		/*try {
			String positionProperty = "Position";
			Object position = getProperty(positionProperty);
			Object newParentPosition = parent.getProperty(positionProperty);
			Object newPosition = newParentPosition;
			// setProperty(positionProperty, newPosition);
		} catch (MatlabException e) {
			e.printStackTrace();
		}*/
	}

	/** BUILDER */

	private void buildFromStateflow(StateflowBlock parent) throws EolRuntimeException {
		if (!parent.isUnset()) {
			try {
				String parentHandle = StateflowUtil.getBlockHandle(parent);
				engine.eval("block = ?(?);", getType(), parentHandle);
				this.id = (Double) engine.evalWithResult("block.id;");	
			} catch (MatlabException e) {
				throw new EolRuntimeException(e.getMessage());
			}			
		} else {
			throw new EolRuntimeException("Parent was not a valid entity");
		}
	}

	public StateflowBlockCollection getChildren() {
		try {
			String handle = StateflowUtil.getBlockHandle(this);
			Object children = this.engine.evalWithSetupAndResult("list = ?.find('-depth',1); list = setdiff(list, ?);", "get(list,'Id');", handle, handle);
			return new StateflowBlockCollection(children, ((SimulinkModel)model));			
		} catch (MatlabException e) {
			return new StateflowBlockCollection(null, ((SimulinkModel)model));
		}
	}

	public StateflowBlock inspect() { 
		try {
			String h = StateflowUtil.getBlockHandle(this);
			engine.eval("get(?);", h);
		} catch (MatlabException e) {
		}
		return this;
	}

	@Override
	public boolean equals(Object other) { 
		return (other instanceof StateflowBlock) && ((StateflowBlock) other).getId().equals(this.getId());
	}

	@Override
	public String toString() {
		return getType() + "[ id = " + getId() + ", path = " + getPath() + " ]";
	}

	@Override
	public Collection<String> getAllTypeNamesOf() {
		return Arrays.asList(Kind.STATEFLOW.getKind(), getType());
	}

	@Override
	public boolean deleteElementInModel() throws EolRuntimeException { 
		try {
			engine.eval(DELETE_SF, StateflowUtil.getBlockHandle(this));
			engine.flush();
			return true;
		} catch (MatlabException e) {
			return false;
		}
	}
	
	public StateflowBlock add(StateflowBlock block) throws EolRuntimeException { 
		try {
			block.setParent(this);
		} catch (MatlabException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public SimulinkBlock asSimulink() throws EolRuntimeException {
		Double h = SimulinkUtil.getHandle(this.getPath(), this.engine);
		if (h >= 0) 
			return new SimulinkBlock(((SimulinkModel)model), engine, h);	
		throw new EolRuntimeException("This block does not have a simulink Nature");
	}

	@Override
	public Double getHandle() {
		return getId();
	}
	
	public Boolean isCommented() throws EolRuntimeException{
		try {
			String handle = StateflowUtil.getBlockHandleFromId(((SimulinkModel)model), engine, id);
			return (Boolean) engine.evalWithResult("?.isCommented;", handle);	
		}catch (Exception e) {
			throw new EolRuntimeException(e.getMessage());
		}
	}
}
