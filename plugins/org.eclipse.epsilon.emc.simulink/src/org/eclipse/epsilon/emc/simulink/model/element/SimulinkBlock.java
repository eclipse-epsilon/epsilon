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
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.lang.model.type.NullType;

import org.eclipse.epsilon.emc.simulink.engine.MatlabEngine;
import org.eclipse.epsilon.emc.simulink.exception.MatlabException;
import org.eclipse.epsilon.emc.simulink.exception.MatlabRuntimeException;
import org.eclipse.epsilon.emc.simulink.model.SimulinkModel;
import org.eclipse.epsilon.emc.simulink.types.Struct;
import org.eclipse.epsilon.emc.simulink.util.SimulinkUtil;
import org.eclipse.epsilon.emc.simulink.util.collection.SimulinkPortCollection;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;

@SuppressWarnings("unused")
public class SimulinkBlock extends SimulinkElement {

	/** CONSTANTS */
	private static final String HANDLE_DELETE_BLOCK_HANDLE = "handle = ?; delete_block(handle);";
	private static final String INSPECT_HANDLE = "handle = ?; inspect(handle);";
	private static final String GET_SIMULINK_BLOCK_HANDLE = "getSimulinkBlockHandle('?');";
	private static final String DELETE = "delete";
	private static final String CREATE = "add";
	private static final String DELETE_BLOCK = "handle = ?; delete_block(handle);";

	/**
	 * CONSTRUCTORS
	 * 
	 * @throws MatlabRuntimeException
	 * 
	 * @throws EolRuntimeException
	 */

	public SimulinkBlock(SimulinkModel model, MatlabEngine engine, Double handle) throws MatlabRuntimeException {
		super(model, engine, handle);
	}

	public SimulinkBlock(String path, SimulinkModel model, MatlabEngine engine) throws MatlabRuntimeException {
		super(path, model, engine);
	}

	public SimulinkBlock(SimulinkModel model, MatlabEngine engine, String type) throws MatlabRuntimeException {
		super(model, engine, type);
	}

	/** PARENT / CHILDREN */

	protected String getParentPath() throws EolRuntimeException { // FIXME could be simplified
		SimulinkBlock parent = getParent();
		return parent == null ? ((SimulinkModel)model).getSimulinkModelName() : parent.getPath();
	}

	public void setParent(SimulinkBlock parent) {
		try {
			String name = (String) getProperty("name");
			String parentPath = parent == null ? ((SimulinkModel)model).getSimulinkModelName() : parent.getPath();
			Double newHandle = (Double) engine.evalWithResult(ADD_BLOCK_MAKE_NAME_UNIQUE_ON, getPath(),
					parentPath + "/" + name);
			engine.eval(HANDLE_DELETE_BLOCK_HANDLE, handle);
			handle = newHandle;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// Returns null for top-level elements and a SimulinkElement for nested elements
	public SimulinkBlock getParent() throws EolRuntimeException { // FIXME could be simplified
		String path = getPath();
		String name = ((String) getProperty("name")).replace("/", "//").replace("\n", " ");
		if (!path.equalsIgnoreCase(name)) {
			String parentPath = path.substring(0, path.length() - name.length() - 1);
			if (parentPath.replace("//", "").indexOf("/") < 0) {
				return null;
			}
			try {
				return new SimulinkBlock(parentPath, ((SimulinkModel)model), engine);
			} catch (MatlabRuntimeException e) {
				throw new MatlabRuntimeException("Unable to retrieve parent");
			}
		}
		return null;
	}

	public Collection<ISimulinkModelElement> getChildren() throws MatlabException {
		return SimulinkUtil.getChildren(((SimulinkModel)model), this);
	}

	public SimulinkModelElement inspect() throws EolRuntimeException {
		try {
			engine.eval(INSPECT_HANDLE, handle);
			engine.flush();
			return this;
		} catch (MatlabException e) {
			throw e.toEolRuntimeException();
		}
	}

	@Override
	public boolean deleteElementInModel() throws EolRuntimeException {
		try {
			engine.eval(DELETE_BLOCK, getHandle());
			engine.flush();
			return true;
		} catch (MatlabException e) {
			throw e.toEolRuntimeException();
		}
	}

	/** TYPE-SPECIFIC METHODS */

	public void setScript(String script) throws EolRuntimeException {
		try {
			engine.eval("sf = sfroot();" + "block = sf.find('Path','?','-isa','Stateflow.EMChart');"
					+ "block.Script = sprintf('?');", getPath(), script);
			engine.flush();
		} catch (MatlabException e) {
			throw e.toEolRuntimeException();
		}
	}

	public String getScript() throws EolRuntimeException {
		try {
			engine.eval("sf = sfroot();" + "block = sf.find('Path','?','-isa','Stateflow.EMChart');"
					+ "script = string(block.Script);", getPath());
			return engine.getVariable("script") + "";
		} catch (MatlabException e) {
			throw e.toEolRuntimeException();
		}
	}

	public void link(SimulinkBlock other) throws EolRuntimeException {
		link(other, 1, 1);
	}

	public void linkTo(SimulinkBlock other, int inPort) throws EolRuntimeException {
		link(other, 1, inPort);
	}

	public void linkFrom(SimulinkBlock other, int outPort) throws EolRuntimeException {
		link(other, outPort, 1);
	}

	public void link(SimulinkBlock other, int outPort, int inPort) throws EolRuntimeException {
		manageLink(other, outPort, inPort, true);
	}

	public void unlink(SimulinkBlock other) throws EolRuntimeException {
		unlink(other, 1, 1);
	}

	public void unlinkTo(SimulinkBlock other, int inPort) throws EolRuntimeException {
		unlink(other, 1, inPort);
	}

	public void unlinkFrom(SimulinkBlock other, int outPort) throws EolRuntimeException {
		unlink(other, outPort, 1);
	}

	public void unlink(SimulinkBlock other, int outPort, int inPort) throws EolRuntimeException {
		manageLink(other, outPort, inPort, false);
	}

	public void manageLink(SimulinkBlock other, int outPort, int inPort, boolean create) throws EolRuntimeException {
		String command = "sourceHandle = ?;" + "targetHandle = ?;"
				+ "OutPortHandles = get_param(sourceHandle,'PortHandles');"
				+ "InPortHandles = get_param(targetHandle,'PortHandles');"
				+ "?_line('?',OutPortHandles.Outport(?),InPortHandles.Inport(?));";
		try {
			engine.eval(command, getHandle(), other.getHandle(), create ? CREATE : DELETE, getParentPath(), outPort,
					inPort);
			engine.flush();
		} catch (MatlabException ex) {
			EolRuntimeException rex = ex.toEolRuntimeException();
			if (rex.getReason().isEmpty()) {
				// When attempting to link unlinkable elements, the Simulink API
				// throws an exception with an empty message and prints the message 
				// (e.g. "Index exceeds the number of array elements. Index must not exceed 0.")
				// to System.err instead. When this happens we need to set the reason explicitly.
				rex.setReason("Cannot " + (create ? "link" : "unlink") + " inport " + inPort + " to outport " + outPort);
			}
			throw rex;
		}
	}
	
	public SimulinkPortCollection getPorts() throws EolRuntimeException {
		try {
			Struct portHandles  = (Struct) engine.evalWithSetupAndResult("handle = ?;",  "get_param(handle, 'PortHandles');",
					this.handle);
			Collection<?> values = portHandles.values();
			values.removeIf(Objects::isNull);
			List<?> list = values.stream().collect(Collectors.toList());
			return new SimulinkPortCollection(list, ((SimulinkModel)model));
		} catch (MatlabException e) {
			throw e.toEolRuntimeException();
		}
	}
	

	public SimulinkPortCollection getOutports() throws EolRuntimeException {
		try {
			Object handles = engine.evalWithSetupAndResult("handle = ?; " + "ph = get_param(handle, 'PortHandles');",
					"ph.Outport;", this.handle);
			return new SimulinkPortCollection(handles, ((SimulinkModel)model));
		} catch (MatlabException e) {
			throw e.toEolRuntimeException();
		}
	}

	public SimulinkPortCollection getInports() throws EolRuntimeException {
		try {
			Object handles = engine.evalWithSetupAndResult("handle = ?; " + "ph = get_param(handle, 'PortHandles');",
					"ph.Inport;", this.handle);
			return new SimulinkPortCollection(handles, ((SimulinkModel)model));
		} catch (MatlabException e) {
			throw e.toEolRuntimeException();
		}
	}

}
