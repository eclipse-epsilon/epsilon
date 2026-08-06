/**
 * *******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 * ******************************************************************************
 *
 * $Id: HutnUnitTestSuite.java,v 1.3 2008/08/07 14:51:10 louis Exp $
 */
package org.eclipse.epsilon.hutn.test.unit;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ParseSuite.class, ModelSuite.class, PostProcessorSuite.class, TranslatorSuite.class,
               ModelValidatorSuite.class, ConfigFileValidatorSuite.class, ModelGeneratorSuite.class,
               PreambleSuite.class})
public class HutnUnitTestSuite {

	public static Test suite() {
		return new JUnit4TestAdapter(HutnUnitTestSuite.class);
	}
}
