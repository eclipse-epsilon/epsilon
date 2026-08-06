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
package org.eclipse.epsilon.flock.test.unit;

import org.eclipse.epsilon.flock.model.checker.IgnoredPropertiesCheckerTests;
import org.eclipse.epsilon.flock.model.checker.TypedConstructCheckerTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

@RunWith(Suite.class)
@SuiteClasses({TypedConstructCheckerTests.class, IgnoredPropertiesCheckerTests.class})
public class CheckerSuite {
	public static Test suite() {
		return new JUnit4TestAdapter(CheckerSuite.class);
	}
}
