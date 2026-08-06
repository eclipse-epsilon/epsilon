/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.test.unit.type;

import org.eclipse.epsilon.emc.simulink.common.test.FileUtils;
import org.eclipse.epsilon.emc.simulink.test.util.AbstractTypeTests;
import org.junit.Ignore;
import org.junit.Test;

public class SimulinkLineTypeTests extends AbstractTypeTests {

	//CRUD
	
	@Test
	@Ignore //FIXME Implement functionality
	public void testCreateLine() {
		eol 	= "var line = new Line;\n"
				+ "assert(line <> null);\n";
	}
	
	@Test
	public void testFindLine() {
		modelFile = FileUtils.getModelFile("sf_msg_traffic_light.slx");
		eol 	= "var lines = Line.all;\n"
				+ "assert(lines.size()>0);\n";
	}
	
	
	@Test
	@Ignore //FIXME Implement functionality
	public void testGetSetNameCreatedLineNotInMatlab() {
		eol 	= "var line = new Line;\n"
				+ "line.Name = \"myLine\";\n"
				+ "assert(line.Name == \"myLine\");\n"
				+ "assert(Line.all.select(l.Name == \"myLine\").size() == 0);\n";
	}
	
	@Test
	@Ignore //FIXME Implement functionality
	public void testGetSetNameCreatedLineInMatlab() {
		eol 	= "var line = new Line;\n"
				+ "line.Name = \"myLine\";\n"
				+ "assert(line.Name == \"myLine\");\n"
				+ "line.source = port;\n"
				+ "line.target = port;\n"
				+ "assert(Line.all.select(l.Name == \"myLine\").size() == 1);\n";
	}
	
	@Test
	public void testGetSetNameFoundLine() {
		modelFile = FileUtils.getModelFile("sf_msg_traffic_light.slx");
		eol 	= "var line = Line.all().first();\n"
				+ "line.Name = \"myLine\";\n"
				+ "assert(line.Name == \"myLine\");\n";
	}
	
	@Test
	public void testDeleteLine() {
		modelFile = FileUtils.getModelFile("sf_msg_traffic_light.slx");
		eol 	= "var lines = Line.all();\n"
				+ "var initialSize= lines.size()"
				+ "var line = lines.first();\n"
				+ "delete line;\n"
				+ "assert(lines.size() == Line.all().size() + 1);\n";
	}

	
	
	// Epsilon Object methods
	
	@Test
	@Ignore //FIXME Write EOL
	public void testGetDestination() {
		modelFile = FileUtils.getModelFile("feedbackController.slx");
		eol 	= "var lines = Line.all().first();\n"
				+ "assert(lines.size()>0);\n";
	}

	@Test
	@Ignore //FIXME Write EOL
	public void testGetDestinationPort() {
		modelFile = FileUtils.getModelFile("feedbackController.slx");
		eol 	= "var lines = Line.all().first();\n"
				+ "assert(lines.size()>0);\n";
	}
	
	@Test
	@Ignore //FIXME Write EOL
	public void testGetSource() {
		modelFile = FileUtils.getModelFile("feedbackController.slx");
		eol 	= "var lines = Line.all().first();\n"
				+ "assert(lines.size()>0);\n";
	}

	@Test
	@Ignore //FIXME Write EOL
	public void testGetSourcePort() {
		modelFile = FileUtils.getModelFile("feedbackController.slx");
		eol 	= "var lines = Line.all().first();\n"
				+ "assert(lines.size()>0);\n";
	}
	
	
}