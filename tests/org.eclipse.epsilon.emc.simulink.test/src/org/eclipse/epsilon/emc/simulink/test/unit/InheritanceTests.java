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

public class InheritanceTests extends AbstractSimulinkTest {
	
	// MODEL
	
	@Test
	public void testModelChildren() {
		eol = "var chart = new `sflib/Chart`; "
				+ "var result = M.children; "
				+ "assert(result.includes(chart)); "
				+ "assert(result.size() = 1); ";
	}
	
	@Test
	public void testModelFindBlocks() {
		eol = "var chart = new `sflib/Chart`; "
				+ "var result = M.findBlocks(); "
				+ "assert(result.size() = 1); "
				+ "assert(result.includes(chart)); ";
	}
	
	// CHART PARENT
	
	@Test
	public void testChartStateflowConstParent() {
		eol = "var parentSim = new `sflib/Chart`; "
				+ "var parent = `Stateflow.Chart`.all.first(); "
				+ "var sA = new `Stateflow.State`(parent); "
				+ "assert(sA.parent = parent);";
	}
	
	@Test
	public void testChartStateflowAttrParent() {
		eol = "var parentSim = new `sflib/Chart`; "
				+ "var parent = `Stateflow.Chart`.all.first(); "
				+ "var sA = new `Stateflow.State`; "
				+ "sA.parent = parent; "
				+ "assert(sA.parent = parent);";
	}
	
	@Test
	public void testChartStateflowAddParent() {
		eol = "var parentSim = new `sflib/Chart`; "
				+ "var parent = `Stateflow.Chart`.all.first(); "
				+ "var sA = new `Stateflow.State`; "
				+ "parent.add(sA); "
				+ "assert(sA.parent = parent);";
	}
	
	@Test
	public void testStateflowConstChartChildren() {
		eol = "var parentSim = new `sflib/Chart`; "
				+ "var parent = `Stateflow.Chart`.all.first(); "
				+ "var sA = new `Stateflow.State`(parent); "
				+ "assert(parent.getChildren().size() = 1);"
				+ "assert(parent.children.size() = 1); "
				+ "assert(parent.children.includes(sA); "
				+ "assert(parent.getChildren().includes(sA); ";
	}
	
	@Test
	public void testStateflowAttrChartChildren() {
		eol = "var parentSim = new `sflib/Chart`; "
				+ "var parent = `Stateflow.Chart`.all.first(); "
				+ "var sA = new `Stateflow.State`; "
				+ "sA.parent = parent; "
				+ "assert(parent.getChildren().size() = 1);"
				+ "assert(parent.children.size() = 1); "
				+ "assert(parent.children.includes(sA); "
				+ "assert(parent.getChildren().includes(sA); ";
	}
	
	@Test
	public void testAddStateflowChartChildren() {
		eol = "var parentSim = new `sflib/Chart`; "
				+ "var parent = `Stateflow.Chart`.all.first(); "
				+ "var sA = new `Stateflow.State`; "
				+ "parent.add(sA); "
				+ "assert(parent.getChildren().size() = 1);"
				+ "assert(parent.children.size() = 1); "
				+ "assert(parent.children.includes(sA); "
				+ "assert(parent.getChildren().includes(sA); ";
	}
	
	// STATEFLOW PARENT
	/* 
	
	Temporarily commented out because they all EOL programs below contain 
	a syntax error as they use the reserved "function" keyword as a variable 
	name and just renaming "function" to "fun" doesn't make them pass.
	
	@Test
	public void testSfStateflowConstParent() {
		eol = "var function = new `simulink/User-Defined Functions/MATLAB Function`;"
				+ "var parent = EMChart.all.first(); "
				+ "var sA = new `Stateflow.State`(parent); "
				+ "assert(sA.parent = parent);";
	}
	
	@Test
	public void testSfStateflowAttrParent() {
		eol = "var function = new `simulink/User-Defined Functions/MATLAB Function`;"
				+ "var parent = EMChart.all.first(); "
				+ "var sA = new `Stateflow.State`; "
				+ "sA.parent = parent; "
				+ "assert(sA.parent = parent);";
	}
	
	@Test
	public void testSfStateflowAddParent() { // OK
		eol = "var function = new `simulink/User-Defined Functions/MATLAB Function`;"
				+ "var parent = EMChart.all.first(); "
				+ "var sA = new `Stateflow.State`; "
				+ "parent.add(sA); "
				+ "assert(sA.parent = parent);";
	}
	
	@Test
	public void testStateflowConstSfChildren() {
		eol = "var function = new `simulink/User-Defined Functions/MATLAB Function`;"
				+ "var parent = EMChart.all.first(); "
				+ "var sA = new `Stateflow.State`(parent); "
				+ "assert(parent.getChildren().size() = 1);"
				+ "assert(parent.children.size() = 1); "
				+ "assert(parent.children.includes(sA); "
				+ "assert(parent.getChildren().includes(sA); ";
	}
	
	@Test
	public void testStateflowAttrSfChildren() {
		eol = "var function = new `simulink/User-Defined Functions/MATLAB Function`;"
				+ "var parent = EMChart.all.first(); "
				+ "var sA = new `Stateflow.State`; "
				+ "sA.parent = parent; "
				+ "assert(parent.getChildren().size() = 1);"
				+ "assert(parent.children.size() = 1); "
				+ "assert(parent.children.includes(sA); "
				+ "assert(parent.getChildren().includes(sA); ";
	}
	
	@Test
	public void testAddStateflowSfChildren() {
		eol = "var function = new `simulink/User-Defined Functions/MATLAB Function`;"
				+ "var parent = EMChart.all.first(); "
				+ "var sA = new `Stateflow.State`; "
				+ "parent.add(sA); "
				+ "assert(parent.getChildren().size() = 1);"
				+ "assert(parent.children.size() = 1); "
				+ "assert(parent.children.includes(sA); "
				+ "assert(parent.getChildren().includes(sA); ";
	}
	*/
	
}
