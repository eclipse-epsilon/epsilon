/*********************************************************************
 * Copyright (c) 2022 Antonio García-Domínguez.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eunit.junit.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

@RunWith(Suite.class)
@SuiteClasses({
	ImportDataTests.class
})
public class EUnitJUnitTestSuite {

	public static Test suite() {
		return new JUnit4TestAdapter(EUnitJUnitTestSuite.class);
	}

}
