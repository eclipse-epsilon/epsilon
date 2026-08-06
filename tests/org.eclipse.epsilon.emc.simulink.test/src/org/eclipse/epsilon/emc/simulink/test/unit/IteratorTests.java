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

public class IteratorTests extends AbstractSimulinkTest {

	@Before
	public  void setup() {
		activeCache = false;
		modelFile = FileUtils.getModelFile("feedbackController.slx");
	}
	
	@Test
	public void test() {
		eol = "Block.all().first().name.println();";
	}
	
	@Test
	public void testFor() {
		eol = "var blocks = Block.all(); "
				+ "for (b in blocks){"
				+ "   b.name.println();"
				+ "}";
	}
	
}
