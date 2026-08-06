/*********************************************************************
 * Copyright (c) 2008 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.flexmi.test;

import org.junit.Test;

public class IntegrationTests extends FlexmiTests {
	
	@Test
	public void testSpeedMonitor() throws Exception {
		assertNoWarnings("integration/speed-monitor/speed-monitor.flexmi", "integration/speed-monitor/comps.ecore");
	}

	@Test
	public void testClassDesc() throws Exception {
		assertNoWarnings("integration/ecore-desc/example.flexmi", "integration/ecore-desc/ecoredesc.ecore");
	}

}
