/*******************************************************************************
 * Copyright (c) 2009-2019 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 *     Sina Madani - Concurrency tests and refactoring
 ******************************************************************************
 *
 * $Id$
 */
package org.eclipse.epsilon.evl.engine.test.acceptance;

import org.eclipse.epsilon.evl.engine.test.acceptance.builtins.EvlCanAccessBuiltinsTests;
import org.eclipse.epsilon.evl.engine.test.acceptance.reuse.EvlModuleReuseTests;
import org.eclipse.epsilon.evl.engine.test.acceptance.unparser.EvlUnparserTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

@RunWith(Suite.class)
@SuiteClasses({
	EvlCanAccessBuiltinsTests.class,
	EvlTests.class,
	EvlModuleReuseTests.class,
	EvlUnparserTests.class,
	EvlParserTests.class
})
public class EvlAcceptanceTestSuite {
	
	public static Test suite() {
		return new JUnit4TestAdapter(EvlAcceptanceTestSuite.class);
	}
}
