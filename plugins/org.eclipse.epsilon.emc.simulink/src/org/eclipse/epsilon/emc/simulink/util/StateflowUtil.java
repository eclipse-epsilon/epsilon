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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.eclipse.epsilon.emc.simulink.engine.MatlabEngine;
import org.eclipse.epsilon.emc.simulink.exception.MatlabException;
import org.eclipse.epsilon.emc.simulink.model.SimulinkModel;
import org.eclipse.epsilon.emc.simulink.model.element.ISimulinkModelElement;
import org.eclipse.epsilon.emc.simulink.model.element.StateflowBlock;
import org.eclipse.epsilon.emc.simulink.util.collection.StateflowBlockCollection;

public class StateflowUtil {

	private static final String M = "m";
	private static final String SF_MODEL = "rt = sfroot; "
			+ "? = rt.find('-isa', 'Simulink.BlockDiagram', '-and', 'Name', '?');";

	private static final String FIND_ALL = "list = ?.find('-isa','Stateflow.Object');";
	private static final String FIND_BLOCK_TYPE = "list = ?.find('-isa','?');"; 
	private static final String GET_IDS = "get(list, 'Id');";
	private static final String FIND_BY_ID = "? = ?.find('Id', ?);";
	private static final String FIND_BY_TYPE_PATH = "? = ?.find('-isa', '?', 'Path', '?');";

	/*************/
	/** HANDLES **/
	/*************/

	public static String getBlockHandleFromId(SimulinkModel model, MatlabEngine engine, Double id) throws MatlabException {
		return getBlockHandleFromId(model, engine, id.intValue());
	}
	
	public static String getBlockHandle(StateflowBlock block) throws MatlabException {
		String varName = randomHandleName();
		getBlockHandleAs(block, varName);
		return varName;
	}

	private static void getBlockHandleAs(StateflowBlock block, String varName) throws MatlabException {
		modelHandleAsM(block);
		if (block.getId() != null) {
			block.getEngine().eval(FIND_BY_ID, varName, M, block.getId().intValue());
		} else if (block.getPath() != null) {
			block.getEngine().eval(FIND_BY_TYPE_PATH, varName, M, block.getType(), block.getPath());
		}
	}
	
	private static String randomHandleName() {
		return "e" + UUID.randomUUID().toString().replace("-", "").substring(0, 10);
	}
	
	private static String getBlockHandleFromId(SimulinkModel model, MatlabEngine engine, Integer id) throws MatlabException {
		modelHandleAsM(model);
		String varName = randomHandleName();
		engine.eval(FIND_BY_ID, varName, M, id.intValue());
		return varName;
	}

	public static void modelHandleAsM(ISimulinkModelElement obj) throws MatlabException {
		modelHandleAs(obj, M);
	}

	public static void modelHandleAsM(SimulinkModel model) throws MatlabException {
		modelHandleAs(model, M);
	}

	public static void modelHandleAs(ISimulinkModelElement obj, String as) throws MatlabException {
		modelHandleAs(((SimulinkModel) obj.getOwningModel()), as);
	}

	public static void modelHandleAs(SimulinkModel model, String as) throws MatlabException {
		String modelName = model.getSimulinkModelName();
		model.getEngine().eval(SF_MODEL, as, modelName);
	}
	
	/*******************/
	/** OBJECT METHOD **/
	/*******************/

	public static String handleMethod(StateflowBlock block, String methodName, Object[] parameters) throws MatlabException {
		String h = getBlockHandle(block);
		String cmd = "result = " + h + "." + methodName;
		if (parameters != null && parameters.length > 0) {
			cmd += "(";
			List<Object> list = Arrays.asList(parameters);
			for (Object parameter : list) {
				if (list.indexOf(parameter) != 0) cmd += ", ";
				if (parameter instanceof String) {
					cmd += "'" + String.valueOf(parameter) + "'";
				} else {
					cmd += parameter;
				}
			}
			cmd += ")";
		}
		cmd += ";";
		return cmd;
	}

	/***********/
	/** TYPES **/
	/***********/

	public static Collection<ISimulinkModelElement> getAllStateflowBlocksFromModel(SimulinkModel model) throws MatlabException {
		Object ids = allIds(model);
		return new StateflowBlockCollection(ids, model);
	}

	public static Collection<ISimulinkModelElement> getAllOfStateflowTypeFromModel(SimulinkModel model,	String type) throws MatlabException {
		Object ids = idsOfType(model, type);
		return new StateflowBlockCollection(ids, model);
	}

	private static Object allIds(SimulinkModel model) throws MatlabException {
		StateflowUtil.modelHandleAsM(model);
		return model.getEngine().evalWithSetupAndResult(FIND_ALL, GET_IDS, M);
	}
	
	private static Object idsOfType(SimulinkModel model, String type) throws MatlabException {
		modelHandleAsM(model);
		return model.getEngine().evalWithSetupAndResult(FIND_BLOCK_TYPE, GET_IDS, M, type);
	}
	
}
