/*******************************************************************************
 * Copyright (c) 2009-2024 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 *     Antonio Garcia-Dominguez - add import caching tests
 ******************************************************************************/
package org.eclipse.epsilon.etl.engine.test.acceptance;

import org.eclipse.epsilon.etl.engine.test.acceptance.builtins.EtlCanAccessBuiltinsTests;
import org.eclipse.epsilon.etl.engine.test.acceptance.importCaching.ImportCachingTests;
import org.eclipse.epsilon.etl.engine.test.acceptance.oo2db.OO2DBTest;
import org.eclipse.epsilon.etl.engine.test.acceptance.tree2graph.Tree2GraphTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

@RunWith(Suite.class)
@SuiteClasses({
	EtlCanAccessBuiltinsTests.class,
	TransformationRuleTests.class,
	OO2DBTest.class,
	Tree2GraphTest.class,
	ImportCachingTests.class
})
public class EtlAcceptanceTestSuite {

	public static Test suite() {
		return new JUnit4TestAdapter(EtlAcceptanceTestSuite.class);
	}
}
