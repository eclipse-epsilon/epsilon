/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.test.unit;

import org.eclipse.epsilon.emc.simulink.test.suite.ConditionalMatlabSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(ConditionalMatlabSuite.class)
@SuiteClasses({ CRUDTests.class, ExampleTests.class, InheritanceTests.class,
		OperationTests.class, /*SimulateTests.class,*/ PropertyTests.class, OperatorTest.class,
		SimulinkPrimitiveTypeAndStatementTest.class, MatlabEngineTests.class, LoadModelTest.class })
public class AllTests {

}
