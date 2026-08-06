/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************
 *
 * $Id$
 */
package org.eclipse.epsilon.hutn.xmi.test;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

import org.eclipse.epsilon.hutn.xmi.test.integration.HutnXmiIntegrationTestSuite;
import org.eclipse.epsilon.hutn.xmi.test.unit.HutnXmiUnitTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({HutnXmiUnitTestSuite.class, HutnXmiIntegrationTestSuite.class})
public class HutnXmiTestSuite {
	public static Test suite() {
		return new JUnit4TestAdapter(HutnXmiUnitTestSuite.class);
	}
}
