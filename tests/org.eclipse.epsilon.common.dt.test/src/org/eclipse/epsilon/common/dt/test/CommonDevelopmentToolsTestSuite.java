/*******************************************************************************
 * Copyright (c) 2009 The University of York.
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
package org.eclipse.epsilon.common.dt.test;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

import org.eclipse.epsilon.common.dt.nature.BuilderConfiguringNatureTests;
import org.eclipse.epsilon.common.dt.nature.ToggleNatureActionTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ToggleNatureActionTests.class, BuilderConfiguringNatureTests.class})
public class CommonDevelopmentToolsTestSuite {

	public static Test suite() {
		return new JUnit4TestAdapter(CommonDevelopmentToolsTestSuite.class);
	}
}
