/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.simulink.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;
import org.eclipse.epsilon.emc.simulink.dictionary.test.SimulinkDictionaryModelTests;
import org.eclipse.epsilon.emc.simulink.test.unit.AllTests;

/**
 * See https://uk.mathworks.com/help/matlab/matlab_external/setup-environment.html
 * for instructions on configuring the MATLAB environment
 */
@RunWith(ConditionalMatlabSuite.class)
@SuiteClasses({ 
	AllTests.class,
	SimulinkDictionaryModelTests.class
	//org.eclipse.epsilon.emc.simulink.test.unit.type.AllTests.class
})
public class SimulinkTestSuite {
}
