/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.test.acceptance.patch;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

/**
 * 
 * @since 1.6
 */
@RunWith(Suite.class)
@SuiteClasses({PatchTests.class, PatchValidationTests.class, WildcardTests.class})
public class PatchTestSuite {

	public static Test suite() {
		return new JUnit4TestAdapter(PatchTestSuite.class);
	}
}

