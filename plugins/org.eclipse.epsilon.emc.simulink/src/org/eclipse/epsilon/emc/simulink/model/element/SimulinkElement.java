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

import org.eclipse.epsilon.common.util.StringUtil;
import org.eclipse.epsilon.emc.simulink.engine.MatlabEngine;
import org.eclipse.epsilon.emc.simulink.exception.MatlabException;
import org.eclipse.epsilon.emc.simulink.exception.MatlabRuntimeException;
import org.eclipse.epsilon.emc.simulink.model.SimulinkModel;
import org.eclipse.epsilon.emc.simulink.model.TypeHelper;
import org.eclipse.epsilon.emc.simulink.model.TypeHelper.Kind;
import org.eclipse.epsilon.emc.simulink.util.SimulinkUtil;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;

public abstract class SimulinkElement extends SimulinkModelElement {

	protected static final String ADD_BLOCK_MAKE_NAME_UNIQUE_ON = "add_block('?', '?', 'MakeNameUnique', 'on');";
	protected static final String HANDLE = "handle = ?;";
	protected static final String GET_SIMULINK_KIND = "get_param(handle, 'Type');";
	protected static final String GET_SIMULINK_TYPE = "get_param(handle, '%sType');";
	protected static final String GET_FULL_NAME = "getfullname(?);";
	protected static final String GET_HANDLE_PROPERTY = "get_param(handle, '?');";

	protected Double handle = null;
	
	// Used when creating a new block on a given path
	public SimulinkElement(SimulinkModel model, MatlabEngine engine, String type, String destPath) throws MatlabRuntimeException {
		super(model, engine);
		try {
			String parentSysPath;
			
			// If the path to the parent (sub)system is not provided, create the block
			// at the top level of the Simulink model. This should be consistent with
			// the behaviour exhibited when re-parenting blocks
			if (destPath != null && !destPath.isEmpty()) {
				parentSysPath = destPath;
			} else {
				parentSysPath = model.getSimulinkModelName();
			}
			
			this.handle = (Double) engine.evalWithResult(ADD_BLOCK_MAKE_NAME_UNIQUE_ON, type,
					parentSysPath + "/" + SimulinkUtil.getSimpleTypeName(type));
		} catch (Exception e) {
			e.printStackTrace();
			throw new MatlabRuntimeException("Unable to create block element");
		}
		try {
			setType();
		} catch (MatlabException e) {
			throw new MatlabRuntimeException("Unable to set up the type");
		}
	}

	// Used when creating blocks
	public SimulinkElement(SimulinkModel model, MatlabEngine engine, String type) throws MatlabRuntimeException {
		super(model, engine);
		try {
			String path = SimulinkUtil.getTypePathInModel(model, type);
			this.handle = (Double) engine.evalWithResult(ADD_BLOCK_MAKE_NAME_UNIQUE_ON, type, path);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MatlabRuntimeException("Unable to create block element");
		}
		try {
			setType();
		} catch (MatlabException e) {
			throw new MatlabRuntimeException("Unable to set up the type");
		}
	}
	
	// Used when retrieving blocks with find_system
	public SimulinkElement(SimulinkModel model, MatlabEngine engine, Double handle) throws MatlabRuntimeException{
		super(model, engine);
		if (handle == -1 ) {
			throw new MatlabRuntimeException("Bad handle -1");
		} else {			
			this.handle = handle;
		}
		try {
			setType();
		} catch (MatlabException e) {
			throw new MatlabRuntimeException("Unable to set up the type");
		}
	}

	// Used when retrieving blocks with find_system
	public SimulinkElement(String path, SimulinkModel model, MatlabEngine engine) throws MatlabRuntimeException {
		super(model, engine);
		setHandle(path);
		try {
			setType();
		} catch (MatlabException e) {
			throw new MatlabRuntimeException("Unable to set up the type");
		}
	}

	// FIXME is this being called?
	public SimulinkElement(SimulinkModel model, MatlabEngine engine) {
		super(model, engine);
	}

	@Override
	public Object getProperty(String property) throws EolRuntimeException {
		try {
			String command = ""
					+ "handle = ?; "
					+ "property = '?';"
					+ "try\n" + 
					"        result = get_param(handle,property);\n" + 
					"    catch ME\n" + 
					"        if contains(ME.message, '(mask)');\n" + 
					"            maskParams = get_param(handle,'MaskPrompts');\n" +
					"            [tf,idx] = ismember(maskParams,property);\n" + 
					"            idx = find(idx==1);\n" + 
					"            maskVal = get_param(handle,'MaskValues');\n" +
					"            result = maskVal{idx};\n" + 
					"        end\n" + 
					"    end";
			engine.eval(command, getHandle(), property);
			return engine.getVariable("result");
		} catch (MatlabException e) {
			throw e.toEolRuntimeException();
		}
	}

	@Override
	public void setProperty(String property, Object value) throws EolRuntimeException {
		try {
			String escaped = "?";
			if (value instanceof ISimulinkModelElement) {
				ISimulinkModelElement element = (ISimulinkModelElement) value;
				value = element.getHandle();
			} else {
				escaped = "'" + escaped + "'";
			}
			String stringValue = String.valueOf(value);
			String command = ""
					+ "handle = ?; "
					+ "property = '?';"
					+ "value = "+escaped+";"
					+ "stringValue = '?';"
					+ "try\n" + 
					"        set_param(handle,property,value);\n" + 
					"    catch ME\n" + 
					"        if contains(ME.message, '(mask)')\n" + 
					"            maskParams = get_param(handle,'MaskPrompts');\n" + 
					"            [tf,idx] = ismember(maskParams,property);\n" + 
					"            idx = find(idx==1);\n" + 
					"            maskVal = get_param(handle,'MaskValues');\n" + 
					"            maskVal{idx} = char(stringValue);\n" + 
					"            set_param(handle,'MaskValues',maskVal);\n" +
					"        end\n" + 
					"    end";
			engine.eval(command, getHandle(), property, value, stringValue);
		} catch (MatlabException ex) {
			throw ex.toEolRuntimeException();
		}
	}

	@Override
	public Object getHandle() {
		return this.handle;
	}
	
	private void setHandle(String path) {
		this.handle = SimulinkUtil.getHandle(path, engine);
	}
	
	private void setType() throws MatlabException {
		if (handle != null) {
			this.superType = (String) engine.evalWithSetupAndResult(HANDLE, GET_SIMULINK_KIND, handle);
			this.superType = StringUtil.firstToUpper(superType);
			String typeCmd = String.format(GET_SIMULINK_TYPE, this.superType);
			this.type = (String) engine.evalWithSetupAndResult(HANDLE, typeCmd, handle);
			try {
				TypeHelper.put(type, superType);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return Identifier (Path)
	 */
	public String getPath() {
		try {
			String path = (String) engine.evalWithResult(GET_FULL_NAME, handle);
			return path.replace("\n", " ");
		} catch (MatlabException e) {
			return null;
		}
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof SimulinkElement
				&& ((SimulinkElement) other).getHandle().equals(this.getHandle());
	}

	@Override
	public Collection<String> getAllTypeNamesOf() {
		return Arrays.asList(Kind.SIMULINK.getKind(), getType(), getSuperType());
	}

	@Override
	public String toString() {
		return getType() + "[ Path=" + getPath() + ", handle=" + getHandle() + "]";
	}

}
