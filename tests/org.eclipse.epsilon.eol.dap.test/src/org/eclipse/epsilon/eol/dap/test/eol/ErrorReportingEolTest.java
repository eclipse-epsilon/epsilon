/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.eol.dap.test.eol;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.dap.EpsilonDebugAdapter;
import org.eclipse.epsilon.eol.dap.test.AbstractEpsilonDebugAdapterTest;
import org.junit.Test;

/**
 * Test class for an EOL script with no imports. It uses direct calls to the
 * {@link EpsilonDebugAdapter}, bypassing the use of streams and JSON-RPC.
 */
public class ErrorReportingEolTest extends AbstractEpsilonDebugAdapterTest {

	@Override
	protected void setupModule() throws Exception {
		this.module = new EolModule();
		module.parse(new File(BASE_RESOURCE_FOLDER, "22-errors.eol"));
	}

	@Test
	public void runProducesErrors() throws Exception {
		// We're done with set up - we can let the module start execution now
		attach();
		assertProgramFailed();
		assertTrue("Standard error stream should contain error",
			getStderr().contains("Undefined variable"));
	}
}
