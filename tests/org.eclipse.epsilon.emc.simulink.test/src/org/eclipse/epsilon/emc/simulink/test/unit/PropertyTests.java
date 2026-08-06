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

import org.eclipse.epsilon.emc.simulink.test.util.AbstractSimulinkTest;
import org.junit.Test;

public class PropertyTests extends AbstractSimulinkTest {
	
	@Test
	public void testSimulinkScriptProperty() {
		eol = "var multiply = new `simulink/User-Defined Functions/MATLAB Function`; "
				+ "multiply.script = 'function y = fcn(x) \\\\n y = 2 * x';"
				+ "assert(multiply.script <> null); "
				+ "assert(multiply.script <> \"\");";	
	}
	
	@Test 
	public void testStateflowTypeProperty() {
		eol = "var chart = new `sflib/Chart`; "
			+ "var sfChart = `Stateflow.Chart`.all.first; " 
			+ "var state = new `Stateflow.State`(sfChart); "
			+ "assert(state.type = state.getType());";
	}
	
	@Test
	public void testSimulinkGainTypeProperty() {
		eol = "var gain = new `simulink/Math Operations/Gain`; "
			+ "assert(gain.type = 'Gain');";
	}
	
	@Test
	public void testSimulinkChartTypeProperty() {
		eol = "var chart = new `sflib/Chart`; "
			+ "assert(chart.type = 'SubSystem');";
	}
	
	@Test
	public void testStateflowIdProperty() {
		eol = "var chart = new `sflib/Chart`; \n"
				+ "var sfChart = `Stateflow.Chart`.all.first; \n" 
				+ "var state = new `Stateflow.State`(sfChart); \n"
				+ "assert(state.id <> null); \n"
				+ "assert(state.id <> ''); \n"
				+ "assert(state.id = state.getId());\n";
	}
	
	@Test
	public void testSimulinkGainIdProperty() {
		eol = "var gain = new `simulink/Math Operations/Gain`; "
			+ "assert(gain.getHandle() <> null); "
			+ "assert(gain.getHandle() <> ''); ";
	}
		
}
