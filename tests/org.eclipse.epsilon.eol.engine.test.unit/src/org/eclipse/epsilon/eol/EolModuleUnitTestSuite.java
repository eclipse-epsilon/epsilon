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
package org.eclipse.epsilon.eol;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

import org.eclipse.epsilon.eol.execute.context.FrameStackUnitTestSuite;
import org.eclipse.epsilon.eol.util.ReflectionUtilTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ReflectionUtilTests.class, FrameStackUnitTestSuite.class})
public class EolModuleUnitTestSuite {

	public static Test suite() {
		return new JUnit4TestAdapter(EolModuleUnitTestSuite.class);
	}
}
