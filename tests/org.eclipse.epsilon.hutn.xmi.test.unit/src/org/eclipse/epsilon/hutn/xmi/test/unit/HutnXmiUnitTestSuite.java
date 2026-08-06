 /*******************************************************************************
 * Copyright (c) 2008 The University of York.
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
package org.eclipse.epsilon.hutn.xmi.test.unit;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

import org.eclipse.epsilon.hutn.xmi.coerce.CoerceSuite;
import org.eclipse.epsilon.hutn.xmi.parser.ParserSuite;
import org.eclipse.epsilon.hutn.xmi.postprocessor.fragment.UriFragmentPostProcessorSuite;
import org.eclipse.epsilon.hutn.xmi.postprocessor.identifier.IdentifierPostProcessorSuite;
import org.eclipse.epsilon.hutn.xmi.util.UtilSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({CoerceSuite.class, UtilSuite.class,
               ParserSuite.class,
               IdentifierPostProcessorSuite.class, UriFragmentPostProcessorSuite.class})
public class HutnXmiUnitTestSuite {
	public static Test suite() {
		return new JUnit4TestAdapter(HutnXmiUnitTestSuite.class);
	}
}
