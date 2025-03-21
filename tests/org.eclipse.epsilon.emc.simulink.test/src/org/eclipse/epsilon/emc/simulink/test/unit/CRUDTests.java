/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.test.unit;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.epsilon.emc.simulink.model.SimulinkModel;
import org.eclipse.epsilon.emc.simulink.test.util.AbstractSimulinkTest;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.junit.Test;


public class CRUDTests extends AbstractSimulinkTest {
	
	/** Simulink Gain */
	
	@Test
	public void testCreateSimulinkGain(){
		eol = "var gain = new `simulink/Math Operations/Gain`; "
				+ "assert(Gain.all.size() = 1); "
				+ "var gain2 = new `simulink/Math Operations/Gain`; "
				+ "assert(Gain.all.size() = 2); ";
	}
	
	@Test
	public void testDeleteSimulinkGain(){
		eol = "var gain = new `simulink/Math Operations/Gain`; "
				+ "delete gain; "
				+ "assert(Gain.all.size() = 0);";
	}
	
	/** Simulink Chart */
	
	@Test
	public void testDeleteSimulinkChart(){
		eol = "var chart = new `sflib/Chart`; "
				+ "delete chart; "
				+ "assert(Chart.all.isEmpty());";
	}
	
	/** Stateflow State */
		
	@Test
	public void testCreateStateflowState(){
		eol = "var chart = new `sflib/Chart`; "
				+ "var prevSize = `Stateflow.State`.all.size(); "
				+ "var sfChart = `Stateflow.Chart`.all.first();"
				+ "var state = new `Stateflow.State`(sfChart); "
				+ "assert(`Stateflow.State`.all.size() = (prevSize + 1));";
	}
	
	@Test
	public void testCreateStateflowStateWithEmptyConstructor(){
		eol = "var state = new `Stateflow.State`; "
				+ "var chart = new `sflib/Chart`; "
				+ "var sfChart = `Stateflow.Chart`.all.first();"
				+ "sfChart.add(state); "
				+ "assert(`Stateflow.State`.all.size() = 1);";
	}
	
	@Test
	public void testReadStateflowId(){
		eol = "var chart = new `sflib/Chart`; "	
				+ "var sfChart = `Stateflow.Chart`.all.first();"
				+ "var state = new `Stateflow.State`(sfChart); "
				+ "assert(state.id <> null); "
				+ "assert(state.id.println <> \"\"); ";
	}
	
	@Test
	public void testReadStateflowIdWithEmptyConstructor(){
		eol = "var state = new `Stateflow.State`; "
				+ "var chart = new `sflib/Chart`; "
				+ "var sfChart = `Stateflow.Chart`.all.first;"
				+ "sfChart.add(state); "
				+ "assert(state.id <> null); "
				+ "assert(state.id.println <> \"\"); ";
	}

	@Test
	public void testUpdateStateflowName(){
		eol = "var chart = new `sflib/Chart`; "			
				+ "var sfChart = `Stateflow.Chart`.all.first;"
				+ "var state = new `Stateflow.State`(sfChart); "
				+ "state.name = 'S1'; "
				+ "assert(state.name = 'S1');";
	}
	
	@Test
	public void testUpdateStateflowNameWithEmptyConstructor(){
		eol = "var state = new `Stateflow.State`; "
				+ "state.name = 'S1'; "
				+ "assert(state.name = 'S1'); "
				+ "var chart = new `sflib/Chart`; "
				+ "var sfChart = `Stateflow.Chart`.all.first;"
				+ "sfChart.add(state); "
				+ "state.name = 'S2'; "
				+ "assert(state.name = 'S2'); ";
	}
	

	@Test
	public void testDeleteStateflowState(){
		eol = "var chart = new `sflib/Chart`; "
				+ "var sfChart = `Stateflow.Chart`.all.first;"
				+ "var state = new `Stateflow.State`(sfChart); "
				+ "var prevSize = `Stateflow.State`.all.size();"
				+ "delete state; "
				+ "assert(`Stateflow.State`.all.size() = (prevSize - 1));";
	}
	
	@Test
	public void testDeleteStateflowStateWithEmptyConstructor(){
		eol = "var state = new `Stateflow.State`; "
				+ "var chart = new `sflib/Chart`; "
				+ "var sfChart = `Stateflow.Chart`.all.first;"
				+ "sfChart.add(state); "
				+ "assert(`Stateflow.State`.all.size() = 1);"
				+ "delete state; "
				+ "assert(`Stateflow.State`.all.size() = 0);";
	}
	
	
	@Test
	public void testLinkUnlinkable() throws Exception {
		
		SimulinkModel model = new SimulinkModel();
		model.setFile(new File("testLinkUnlinkable.slx"));
		model.setReadOnLoad(false);
		model.setStoredOnDisposal(false);
		model.load();
		
		EolModule module = new EolModule();
		module.parse("var portIn = new `simulink/Ports & Subsystems/In1`;"
				+ "var portOut = new `simulink/Ports & Subsystems/Out1`;"
				+ "portOut.link(portIn);");
		module.getContext().getModelRepository().addModel(model);
		
		try {
			module.execute();
			throw new RuntimeException("Expected an exception but none was thrown");
		}
		catch (EolRuntimeException ex) {
			ex.printStackTrace();
			assertTrue(ex.getMessage().startsWith("Cannot link inport 1 to outport 1"));
		}
	}
	
}
