/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.eol.dap.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

@RunWith(Suite.class)
@SuiteClasses({
    EpsilonDebugServerTest.class,
    ImportingEolTest.class,
    StandaloneEolTest.class,
    ClasspathEolTest.class,
    HttpEolTest.class
})
public class EpsilonDebugAdapterTestSuite {
	public static Test suite() {
		return new JUnit4TestAdapter(EpsilonDebugAdapterTestSuite.class);
	}
}
