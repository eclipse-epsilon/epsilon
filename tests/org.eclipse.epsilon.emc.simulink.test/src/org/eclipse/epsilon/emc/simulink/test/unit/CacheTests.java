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

public class CacheTests extends AbstractSimulinkTest {

	@Test
	public void cacheSimulinkBlockTest() {
		activeCache = true;
		eol = 	"//Create Once"
				+ "//Load"; 
	}
	
	@Test
	public void cacheStateflowBlockTest() {
		activeCache = true;
	}
	
	@Test
	public void cacheDualBlockTest() {
		activeCache = true;
	}
	
}
