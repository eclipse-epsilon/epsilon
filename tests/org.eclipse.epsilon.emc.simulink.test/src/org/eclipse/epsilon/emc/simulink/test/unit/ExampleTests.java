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
import org.junit.Before;
import org.junit.Test;

public class ExampleTests extends AbstractSimulinkTest {

	private static final String ROOT = "example_scripts/";

	@Before
	public void reset() {
		activeCache = false;
	}

	@Test
	public void testCreateFeedbackController() {
		eolResourceFile = ROOT + "createFeedbackController.eol";
	}

	@Test
	public void testCreateFunctionWithScript() {
		eolResourceFile = ROOT + "createFunctionWithScript.eol";
	}

	@Test
	public void testCreateSimpleSimulation() {
		eolResourceFile = ROOT + "createSimpleSimulation.eol";
	}
	
	@Test
	public void testCreateBlocksWithPath() {
		eolResourceFile = ROOT + "createBlocksWithPaths.eol";
	}
	
	@Test
	public void testLinkPortsWithStringNames() {
		eolResourceFile = ROOT + "linkStringPorts.eol";
	}

	@Test
	public void testCreateStateflowStateTransitions() {
		eolResourceFile = ROOT + "createStateflowStateTransitions.eol";
	}

	@Test
	public void testRobustTest() {
		eolResourceFile = ROOT + "robustTest.eol";
	}

	@Test
	public void testCachingSFChart() {
		activeCache = true;
		eolResourceFile = ROOT + "caching.eol";
	}

	@Test
	public void testParent() {
		eolResourceFile = ROOT + "parent.eol";
		modelFile = FileUtils.getModelFile("parent.slx");
	}

	@Test
	public void testTypeHierarchy() {
		eolResourceFile = ROOT + "typesTest.eol";
		modelFile = FileUtils.getModelFile("feedbackController.slx");
	}

	@Test
	public void testQueryFeedbackController() {
		eolResourceFile = ROOT + "queryFeedbackController.eol";
		modelFile = FileUtils.getModelFile("feedbackController.slx");
	}
	
	@Test
	public void testMaskProperties() {
		eolResourceFile = ROOT + "mask.eol";
		modelFile = FileUtils.getModelFile("masks.slx");
	}

}
