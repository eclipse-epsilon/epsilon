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

import org.eclipse.epsilon.emc.simulink.common.test.FileUtils;
import org.eclipse.epsilon.emc.simulink.test.util.AbstractSimulinkTest;
import org.junit.Test;

public class OperatorTest extends AbstractSimulinkTest {

	// Missing Type: Port, Line 
	// Add tests of plain "select" with non string values, and different expression forms
	
	@Test
	public void selectSimulinkBlockTestDoubleEquals() throws Exception {
		modelFile = FileUtils.getModelFile("feedbackController.slx");
		eol = "Block.all.select(b|b.name == 'Controller').println();";
	}
	
	@Test
	public void selectSimulinkBlockTestSingleEquals() throws Exception {
		modelFile = FileUtils.getModelFile("feedbackController.slx");
		eol = "Block.all.select(b|b.name = 'Controller').println();";
	}
	
	@Test
	public void existsSimulinkBlockTest() throws Exception {
		modelFile = FileUtils.getModelFile("feedbackController.slx");
		eol = "Block.all.exists(b|b.name == 'Controller').println();";
	}
	
	@Test
	public void findSimulinkBlockTest() throws Exception {
		modelFile = FileUtils.getModelFile("feedbackController.slx");
		eol = "Block.all.find(b|b.name == 'Controller').println();";
	}
	
	@Test
	public void findOneSimulinkBlockTest() throws Exception {
		modelFile = FileUtils.getModelFile("feedbackController.slx");
		eol = "Block.all.findOne(b|b.name == 'Controller').println();";
	}
	
	@Test
	public void rejectSimulinkBlockTest() throws Exception {
		modelFile = FileUtils.getModelFile("feedbackController.slx");
		eol = "Block.all.reject(b|b.name == 'Controller').println();";
	}
	
	@Test
	public void rejectOneSimulinkBlockTest() throws Exception {
		modelFile = FileUtils.getModelFile("feedbackController.slx");
		eol = "Block.all.rejectOne(b|b.name == 'Controller').println();";
	}
	
	@Test
	public void forAllSimulinkBlockTest() throws Exception {
		modelFile = FileUtils.getModelFile("feedbackController.slx");
		eol = "Block.all.forAll(b|b.name == 'Controller').println();";
	}
	
	@Test
	public void selectOneSimulinkBlockTest() throws Exception {
		modelFile = FileUtils.getModelFile("feedbackController.slx");
		eol = "Block.all.selectOne(b|b.name == 'Controller').println();";
	}

	@Test
	public void collectSimulinkBlockTest() throws Exception {
		modelFile = FileUtils.getModelFile("feedbackController.slx");
		eol = "Block.all.collect(b|b.name).println();";
	}
	
	@Test
	public void sortBySimulinkBlockTest() throws Exception {
		modelFile = FileUtils.getModelFile("feedbackController.slx");
		eol = "Block.all.sortBy(b|b.name).println();";
	}
	
	/**
	 * STATEFLOW
	 */
	
	@Test
	public void selectStateflowStateTestDoubleEquals() throws Exception {
		modelFile = FileUtils.getModelFile("sf_traffic_light.slx");
		eol = "`Stateflow.State`.all.select(b|b.Name == 'Controller').println();";
	}
	
	@Test
	public void selectStateflowStateTestSingleEquals() throws Exception {
		modelFile = FileUtils.getModelFile("sf_traffic_light.slx");
		eol = "`Stateflow.State`.all.select(b|b.Name = 'Controller').println();";
	}
	
	@Test
	public void existsStateflowStateTest() throws Exception {
		modelFile = FileUtils.getModelFile("sf_traffic_light.slx");
		eol = "`Stateflow.State`.all.exists(b|b.Name = 'Controller').println();";
	}
	
	@Test
	public void findOneStateflowStateTest() throws Exception {
		modelFile = FileUtils.getModelFile("sf_traffic_light.slx");
		eol = "`Stateflow.State`.all.findOne(b|b.Name = 'Controller').println();";
	}
	
	@Test
	public void findStateflowStateTest() throws Exception {
		modelFile = FileUtils.getModelFile("sf_traffic_light.slx");
		eol = "`Stateflow.State`.all.find(b|b.Name = 'Controller').println();";
	}
	
	@Test
	public void rejectStateflowStateTest() throws Exception {
		modelFile = FileUtils.getModelFile("sf_traffic_light.slx");
		eol = "`Stateflow.State`.all.reject(b|b.Name = 'Controller').println();";
	}
	
	@Test
	public void rejectOneStateflowStateTest() throws Exception {
		modelFile = FileUtils.getModelFile("sf_traffic_light.slx");
		eol = "`Stateflow.State`.all.rejectOne(b|b.Name = 'Controller').println();";
	}
	
	@Test
	public void forAllStateflowStateTest() throws Exception {
		modelFile = FileUtils.getModelFile("sf_traffic_light.slx");
		eol = "`Stateflow.State`.all.forAll(b|b.Name = 'Controller').println();";
	}
	
	@Test
	public void selectOneStateflowStateTest() throws Exception {
		modelFile = FileUtils.getModelFile("sf_traffic_light.slx");
		eol = "`Stateflow.State`.all.selectOne(b|b.Name = 'Controller').println();";
	}
	
	@Test
	public void collectStateflowStateTest() throws Exception {
		modelFile = FileUtils.getModelFile("sf_traffic_light.slx");
		eol = "`Stateflow.State`.all.collect(b|b.Name).println();";
	}
	
	@Test
	public void sortByStateflowStateTest() throws Exception {
		modelFile = FileUtils.getModelFile("sf_traffic_light.slx");
		eol = "`Stateflow.State`.all.sortBy(b|b.Name).println();";
	}
	
	
}