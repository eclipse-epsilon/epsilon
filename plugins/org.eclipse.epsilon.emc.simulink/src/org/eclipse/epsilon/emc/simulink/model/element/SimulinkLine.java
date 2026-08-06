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

import org.eclipse.epsilon.emc.simulink.engine.MatlabEngine;
import org.eclipse.epsilon.emc.simulink.exception.MatlabException;
import org.eclipse.epsilon.emc.simulink.exception.MatlabRuntimeException;
import org.eclipse.epsilon.emc.simulink.model.IGenericSimulinkModel;
import org.eclipse.epsilon.emc.simulink.model.SimulinkModel;
import org.eclipse.epsilon.emc.simulink.util.collection.SimulinkBlockCollection;
import org.eclipse.epsilon.emc.simulink.util.collection.SimulinkPortCollection;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;

public class SimulinkLine extends SimulinkElement {

	private static final String DELETE_LINE = "handle = ?; delete_line(handle);";

	private static final String DST_BLOCK_HANDLE = "DstBlockHandle";
	private static final String SRC_BLOCK_HANDLE = "SrcBlockHandle";
	private static final String DST_PORT_HANDLE = "DstPortHandle";
	private static final String SRC_PORT_HANDLE = "SrcPortHandle";
		
	public SimulinkLine(SimulinkModel model, MatlabEngine engine, Double handle) throws MatlabRuntimeException {
		super(model, engine, handle);
	}

	private Object get(String val) throws MatlabException{
		return engine.evalWithSetupAndResult("handle = ?;", "get_param(handle, '?');", this.handle, val);
	}

	public SimulinkBlock getDestination() throws EolRuntimeException {
		try {
			Object list = get(DST_BLOCK_HANDLE);
			return (SimulinkBlock) new SimulinkBlockCollection(list, ((SimulinkModel)model)).get(0);
		} catch (MatlabException e) {
			throw new EolRuntimeException(e.getMessage());
		}
	}

	public SimulinkBlock getSource() throws EolRuntimeException {
		try {
			Object list = get(SRC_BLOCK_HANDLE);
			return (SimulinkBlock) new SimulinkBlockCollection(list, ((SimulinkModel)model)).get(0);
		} catch (MatlabException e) {
			throw new EolRuntimeException(e.getMessage());
		}
	}

	public SimulinkPort getDestinationPort() throws EolRuntimeException {
		try {
			Object list = get(DST_PORT_HANDLE);
			return (SimulinkPort) new SimulinkPortCollection(list, ((SimulinkModel)model)).get(0);
		} catch (MatlabException e) {
			throw new EolRuntimeException(e.getMessage());
		}
	}

	public SimulinkPort getSourcePort() throws EolRuntimeException {
		try {
			Object list = get(SRC_PORT_HANDLE);
			return (SimulinkPort) new SimulinkPortCollection(list, ((SimulinkModel)model)).get(0);
		} catch (MatlabException e) {
			throw new EolRuntimeException(e.getMessage());
		}
	}

	@Override
	public boolean deleteElementInModel() throws EolRuntimeException {
		try {
			engine.eval(DELETE_LINE, getHandle());
			engine.flush();
			return true;
		} catch (MatlabException e) {
			return false;
		} 
	}	
	
}
