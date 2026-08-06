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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.eclipse.epsilon.emc.simulink.common.test.MatlabEngineSetupEnum;
import org.eclipse.epsilon.emc.simulink.engine.MatlabEngine;
import org.eclipse.epsilon.emc.simulink.engine.MatlabEnginePool;
import org.eclipse.epsilon.emc.simulink.exception.MatlabException;
import org.junit.BeforeClass;
import org.junit.Test;

public class MatlabEngineTests {

	private static MatlabEngine engine;

	@BeforeClass
	public static void setup() {
		try {
			engine = MatlabEnginePool
					.getInstance(MatlabEngineSetupEnum.ENGINE_JAR.path())
					.getMatlabEngine();
		} catch (Exception e) {
			throw new RuntimeException("could not setup the test class");
		}
	}

	@Test
	public void testEvalNoCommandEnd() {
		try {
			engine.eval("a = 2 + 2");
			engine.flush();
			assertEquals(4.0, engine.getVariable("a"));
		}catch (MatlabException e) {
			fail(e.getMessage());
		} 
	}

	@Test
	public void testEvalSemiColonEnd() {
		try {
			engine.eval("b = 2 + 2;");
			engine.flush();
			assertEquals(4.0, engine.getVariable("b"));
		} catch (MatlabException e) {
			fail(e.getMessage());
		} 
	}

	@Test
	public void testEvalLineBreak() {
		try {
			engine.eval("c = 2 + 2\n");
			engine.flush();
			assertEquals(4.0, engine.getVariable("c"));
		} catch (MatlabException e) {
			fail(e.getMessage());
		} 
	}

	@Test
	public void testRuntimeException() {
		try {
			engine.eval("d  2 + 2");
			engine.flush();
			fail("did not crash");
		} catch (MatlabException e) {
			assertTrue(true);
		} catch (Exception ex) {
			fail("not a matlab Exception");
		}
	}

}
