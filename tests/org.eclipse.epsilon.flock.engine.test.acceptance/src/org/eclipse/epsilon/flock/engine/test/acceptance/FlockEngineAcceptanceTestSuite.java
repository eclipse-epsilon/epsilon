/*******************************************************************************
 * Copyright (c) 2009 The University of York.
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
package org.eclipse.epsilon.flock.engine.test.acceptance;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

import org.eclipse.epsilon.flock.engine.test.acceptance.builtins.FlockCanAccessBuiltinsTests;
import org.eclipse.epsilon.flock.engine.test.acceptance.preandpost.PreAndPostBlocks;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({FlockCanAccessBuiltinsTests.class,
               GuardedConstructSuite.class,
               TypeMappingsSuite.class,
               RulesSuite.class,
               MigrationLogicSuite.class,
               CopyingSuite.class,
               IgnoringSuite.class,
               EquivalencesSuite.class,
               WarningsSuite.class,
               PreAndPostBlocks.class})
public class FlockEngineAcceptanceTestSuite {
	public static Test suite() {
		return new JUnit4TestAdapter(FlockEngineAcceptanceTestSuite.class);
	}
}
