/*******************************************************************************
 * Copyright (c) 2009-2020 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *	  Louis Rose - initial API and implementation
 *	  Antonio Garcia-Dominguez - added WorkflowTestSuite
 *	  Sina Madani - advanced tests and refactoring
 ******************************************************************************
 *
 * $Id$
 */
package org.eclipse.epsilon.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

/**
 * Test suite to be run from Eclipse IDE.
 * 
 */
@RunWith(Suite.class)
@SuiteClasses({
	EpsilonSurefireTestSuite.class
})
public class EpsilonEclipseTestSuite {
	public static Test suite() {
		return new JUnit4TestAdapter(EpsilonEclipseTestSuite.class);
	}
}
