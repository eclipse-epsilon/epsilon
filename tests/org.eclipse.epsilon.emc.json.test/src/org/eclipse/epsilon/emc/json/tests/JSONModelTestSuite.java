/*******************************************************************************
 * Copyright (c) 2023 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.json.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	JSONContainmentTests.class,
	JSONModelCreationTests.class,
	JSONModelHttpTests.class,
	JSONModelOptionsTests.class,
	JSONModelReadTests.class,
	JSONCreateInstanceTests.class,
	JSONSetContentTests.class
})
public class JSONModelTestSuite {

	public static final String RESOURCES_BASE_FOLDER = "../org.eclipse.epsilon.emc.json.test/resources";
	
}
