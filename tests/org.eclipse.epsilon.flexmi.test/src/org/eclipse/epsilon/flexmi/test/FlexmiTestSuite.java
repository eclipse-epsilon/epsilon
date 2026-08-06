/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.flexmi.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	StandaloneTests.class,
	ImportTests.class,
	IncludeTests.class,
	IncludeImportTests.class,
	TemplateTests.class,
	AllocatorTests.class,
	IdTests.class,
	CompsTests.class,
	PropogenTests.class,
	ActionTests.class,
	ExternalTests.class,
	FlexmiYamlTests.class,
	LocatedSafeConstructorTests.class
})
public class FlexmiTestSuite {

}
