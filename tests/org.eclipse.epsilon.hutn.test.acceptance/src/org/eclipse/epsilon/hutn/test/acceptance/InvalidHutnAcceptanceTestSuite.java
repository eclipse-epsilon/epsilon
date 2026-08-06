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
 * $Id: InvalidHutnAcceptanceTestSuite.java,v 1.2 2008/08/07 12:44:23 louis Exp $
 */
package org.eclipse.epsilon.hutn.test.acceptance;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

import org.eclipse.epsilon.hutn.test.acceptance.invalid.ConfigFileSpecifiedWithoutNsUri;
import org.eclipse.epsilon.hutn.test.acceptance.invalid.InvalidConfigFile;
import org.eclipse.epsilon.hutn.test.acceptance.invalid.MixedValues;
import org.eclipse.epsilon.hutn.test.acceptance.invalid.NonExistentConfigFile;
import org.eclipse.epsilon.hutn.test.acceptance.invalid.UnrecognisedNsUri;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({UnrecognisedNsUri.class, NonExistentConfigFile.class,
               ConfigFileSpecifiedWithoutNsUri.class, InvalidConfigFile.class,
               MixedValues.class})
public class InvalidHutnAcceptanceTestSuite {

	public static Test suite() {
		return new JUnit4TestAdapter(InvalidHutnAcceptanceTestSuite.class);
	}
}
