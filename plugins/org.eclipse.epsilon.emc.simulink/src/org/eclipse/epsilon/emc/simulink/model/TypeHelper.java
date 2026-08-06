/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.collection.CompositeCollection;
import org.eclipse.epsilon.emc.simulink.exception.MatlabException;
import org.eclipse.epsilon.emc.simulink.model.element.ISimulinkModelElement;
import org.eclipse.epsilon.emc.simulink.util.StateflowUtil;
import org.eclipse.epsilon.emc.simulink.util.collection.SimulinkBlockCollection;
import org.eclipse.epsilon.emc.simulink.util.collection.SimulinkElementCollection;
import org.eclipse.epsilon.emc.simulink.util.collection.SimulinkLineCollection;
import org.eclipse.epsilon.emc.simulink.util.collection.SimulinkPortCollection;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;

public class TypeHelper {
	
	private static Map<String, String> map = new HashMap<>();

	public static Kind getKind(String type) {
		if (type.toLowerCase().startsWith(Kind.STATEFLOW.name().toLowerCase())) {
			return Kind.STATEFLOW;
		} else {
			String kind = map.get(type);
			if (kind != null) {				
				try {
					return Kind.get(kind);
				} catch (Exception e) {}
			}
			return null;
		} 
	}
	
	public static Kind getKind(SimulinkModel model, Object handle){
		if (handle != null) {
			try{
				String type = (String) model.getEngine().evalWithSetupAndResult("handle = ?;", "get_param(handle, 'Type');", handle);
				return Kind.get(type);
			} catch (Exception e) {
				return Kind.STATEFLOW;
			}
		}
		return null;
	}

	public static Collection<ISimulinkModelElement> getAllOfType(SimulinkModel model, String type) throws EolModelElementTypeNotFoundException {
		Kind kind = TypeHelper.getKind(type);
		if (kind == null) {
			// Can't be stateflow
			Kind[] kinds = new Kind[0];
			if (type.charAt(0) == type.toLowerCase().charAt(0)){  //First is lowercase
				// it is more likely that we are dealing with a line or a port but we dont rule out a block
				kinds = new Kind[]{Kind.PORT, Kind.LINE, Kind.BLOCK};
			} else {
				// it is more likely that we are dealing with a block but we dont rule out line or port
				kinds = new Kind[]{Kind.BLOCK, Kind.PORT, Kind.LINE};
			}
			for (Kind k : kinds) {
				try {
					return k.getAllSimulinkTypeFromModel(model, type);
				} catch (Exception e) {
					// this will retry with another kind 
				}	
			}
		} else {
			try {
				switch (kind) {
				case STATEFLOW:
					return StateflowUtil.getAllOfStateflowTypeFromModel(model, type);
				default:
					return kind.getAllSimulinkTypeFromModel(model, type);
				}
			} catch (Exception e) {
				throw new EolModelElementTypeNotFoundException(model.getName(), type);
			}
		}
		return new SimulinkElementCollection(model);
	}

	public static Collection<ISimulinkModelElement> getAll(SimulinkModel model) {
		CompositeCollection compositeCollection = new CompositeCollection();
		compositeCollection.addComposited(Kind.SIMULINK.getAll(model));
		compositeCollection.addComposited(Kind.STATEFLOW.getAll(model));
		return compositeCollection;
	}
	
	public static void put(String type, String supertype) {
		if (!map.containsKey(type)) {				
			try {
				map.put(type, supertype);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("unable to add to types");
			}
		}
	}

	public enum Kind {
		BLOCK("Block"), LINE("Line"), PORT("Port"), SIMULINK("Simulink"), STATEFLOW("Stateflow");

		private String kind;

		Kind(String kind) {
			this.kind = kind;
		}

		public String getKind() {
			return kind;
		}
		
		public boolean is(String v) {
			return name().equalsIgnoreCase(v);
		}

		public boolean isSimulink() {
			return !STATEFLOW.equals(this);
		}

		public  Collection<ISimulinkModelElement> getAll(SimulinkModel model) {
			switch (this) {
			case BLOCK:
			case LINE:
			case PORT:
				try {
					return getAllSimulinkKindFromModel(model);
				} catch (Exception e) {					
					break;
				}
			case SIMULINK:
				CompositeCollection compositeCollection = new CompositeCollection();
				compositeCollection.addComposited(BLOCK.getAll(model));
				compositeCollection.addComposited(LINE.getAll(model));
				compositeCollection.addComposited(PORT.getAll(model));
				return compositeCollection;
			case STATEFLOW:
				try {
					return StateflowUtil.getAllStateflowBlocksFromModel(model);
				} catch (MatlabException e) {					
					break;
				}
			}
			return Collections.emptyList();
		}

		private Collection<ISimulinkModelElement> getAllSimulinkKindFromModel(SimulinkModel model) throws MatlabException, IllegalStateException {
			try {
				String findKindCmd = "find_system('?','FindAll','on',%s,'Type','%s');";
				String cmd = String.format(findKindCmd, model.getSearchPreferences().searchStatement(), getKind());
				Object blocks = model.getEngine().evalWithResult(cmd, model.getSimulinkModelName());
				switch (this) {
				case BLOCK:
					return new SimulinkBlockCollection(blocks, model);
				case LINE:
					return new SimulinkLineCollection(blocks, model);
				case PORT:
					return new SimulinkPortCollection(blocks, model);
				default:
					throw new IllegalStateException("Invalid Kind");
				}
			} catch (MatlabException e) {
				return Collections.emptyList();
			}
		}
		
		private Collection<ISimulinkModelElement> getAllSimulinkTypeFromModel(SimulinkModel model, String type) throws Exception {
			Object blocks = null;
			if (isSimulink()) {
				String findTypeCmd = "find_system('?','FindAll','on',%s,'%sType','?');";
				String cmd = String.format(findTypeCmd, model.getSearchPreferences().searchStatement(), this.getKind());
				blocks = model.getEngine().evalWithResult(cmd, model.getSimulinkModelName(), type);
				switch (this) {
				case BLOCK:
					return new SimulinkBlockCollection(blocks, model);
				case LINE:
					return new SimulinkLineCollection(blocks, model);
				case PORT:
					return new SimulinkPortCollection(blocks, model);
				default:
					throw new Exception("Only specific Simulink types (block, port, line) are allowed");
				}
			} 
			throw new EolModelElementTypeNotFoundException(model.getName(), type);
		}

		public static Kind get(String kind) throws Exception {
			try {
				 return valueOf(kind.toUpperCase());
			} catch (IllegalArgumentException e) {
				throw new Exception("Is not kind");
			}
		}
		
	}

}
