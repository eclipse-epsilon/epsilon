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
 * $Id: ParseSuite.java,v 1.3 2008/08/08 14:33:06 louis Exp $
 */
package org.eclipse.epsilon.hutn.test.unit;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

import org.eclipse.epsilon.hutn.parse.spec.HutnPreambleTests;
import org.eclipse.epsilon.hutn.parse.spec.TestHutnSpec;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({TestHutnSpec.class, HutnPreambleTests.class})
public class PreambleSuite {

	public static Test suite() {
		return new JUnit4TestAdapter(PreambleSuite.class);
	}
}
