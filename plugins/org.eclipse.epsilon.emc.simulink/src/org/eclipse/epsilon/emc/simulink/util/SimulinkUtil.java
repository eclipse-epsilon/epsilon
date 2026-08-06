/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.eclipse.epsilon.emc.simulink.engine.MatlabEngine;
import org.eclipse.epsilon.emc.simulink.exception.MatlabException;
import org.eclipse.epsilon.emc.simulink.model.SimulinkModel;
import org.eclipse.epsilon.emc.simulink.model.element.ISimulinkModelElement;
import org.eclipse.epsilon.emc.simulink.model.element.SimulinkBlock;
import org.eclipse.epsilon.emc.simulink.util.collection.SimulinkBlockCollection;

public class SimulinkUtil {

	private static final String GET_BLOCK_HANDLE = "getSimulinkBlockHandle('?');";
	
	// FindAll ensures that the response is always handles of kind double, otherwise we get the path  
	public static final String FIND = "find_system('?', 'FindAll', 'On', 'LookUnderMasks', 'On', 'IncludeCommented', 'On'";
	public static final String FIND_FOLLOW = FIND + ", 'FollowLinks', 'on'";
	
	private static final String FIND_BLOCKS_AT_DEPTH = 			FIND 			+ ", 'SearchDepth', ?, 'Type', 'Block');";
	
	public static String getSimpleTypeName(String type) { // OK
		if (type.indexOf("/") > -1) {
			String[] parts = type.split("/");
			return parts[parts.length - 1];
		}
		if (type.indexOf(".") > -1) {
			String[] parts = type.split("\\.");
			return parts[parts.length - 1];
		}
		return type;
	}
	
	public static String getTypePathInModel(SimulinkModel model, String type) { 
		return model.getSimulinkModelName() + "/" + getSimpleTypeName(type);
	}
	
	public static String handleMethod(ISimulinkModelElement obj, String methodName, Object[] parameters) {
		Object handle = obj.getHandle();
		return handleMethod(handle, methodName, parameters);
	}
	
	public static String handleMethod(SimulinkModel obj, String methodName, Object[] parameters) {
		Double handle = obj.getHandle();
		return handleMethod(handle, methodName, parameters);
	}
	
	private static String handleMethod(Object objHandle, String methodName, Object[] parameters) {
		List<Object> list = new ArrayList<>();
		list.add("handle");
		list.addAll(Arrays.asList(parameters));
		return "handle = " + objHandle + "; " + handleMethod(methodName, list.toArray());
	}
	
	public static String handleMethodWithResult(ISimulinkModelElement obj, String methodName, Object[] parameters) {
		Object handle = obj.getHandle();
		return handleMethodWithResult(handle, methodName, parameters);
	}
	
	public static String handleMethodWithResult(SimulinkModel obj, String methodName, Object[] parameters) {
		Double handle = obj.getHandle();
		return handleMethodWithResult(handle, methodName, parameters);
	}
	
	private static String handleMethodWithResult(Object objHandle, String methodName, Object[] parameters) {
		List<Object> list = new ArrayList<>();
		list.add("handle");
		list.addAll(Arrays.asList(parameters));
		return "handle = " + objHandle + "; " + handleMethodWithResult(methodName, list.toArray());
	}

	private static String handleMethodWithResult(String methodName, Object[] parameters) {
		return "result = " + handleMethod(methodName, parameters); 
	}
	
	private static String handleMethod(String methodName, Object[] parameters) {
		String cmd = methodName;
		if (parameters != null && parameters.length > 0) {
			cmd += "(" + parameters[0];
			for (int i = 1; i < parameters.length; i++) {
				cmd += ", " + "'" + String.valueOf(parameters[i]) + "'";
			}
			cmd += ");";
		}
		return cmd;
	}

	public static Double getHandle(String path, MatlabEngine engine) {
		try {
			return (Double) engine.evalWithResult(GET_BLOCK_HANDLE, path);
		} catch (MatlabException e) {
			e.printStackTrace();
		}
		return -1.0;
	}

	/** SPECIFIC COLLECTION */
	
	public static List<ISimulinkModelElement> getChildren(SimulinkModel model, SimulinkBlock block) throws MatlabException {
			return findBlocks(model, block, 1); 
	}
	
	public static List<ISimulinkModelElement> getChildren(SimulinkModel model) throws MatlabException {
		return findBlocks(model, 1);
	}
	
	public static List<ISimulinkModelElement> findBlocks(SimulinkModel model,Integer depth) throws MatlabException {
		MatlabEngine engine = model.getEngine();
		Object handles = engine.evalWithResult(FIND_BLOCKS_AT_DEPTH, model.getSimulinkModelName(), depth);
		return new SimulinkBlockCollection(handles, model);
	}
	
	public static List<ISimulinkModelElement> findBlocks(SimulinkModel model, SimulinkBlock block,Integer depth) throws MatlabException {
		MatlabEngine engine = model.getEngine();
		Object handles = engine.evalWithResult(FIND_BLOCKS_AT_DEPTH, block.getPath(), depth);
		return new SimulinkBlockCollection(handles, model);
	}
	
}
