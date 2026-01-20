/*******************************************************************************
 * Copyright (c) 2011-2023 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 *     Antonio Garcia-Dominguez - add MatchTraceTest
 ******************************************************************************/
package org.eclipse.epsilon.ecl.engine.test.acceptance;

import org.eclipse.epsilon.ecl.engine.test.acceptance.builtins.EclCanAccessBuiltinsTests;
import org.eclipse.epsilon.ecl.engine.test.acceptance.domain.DomainTests;
import org.eclipse.epsilon.ecl.engine.test.acceptance.equivalence.EclModuleEquivalenceTests;
import org.eclipse.epsilon.ecl.engine.test.acceptance.matches.MatchesOperationTest;
import org.eclipse.epsilon.ecl.engine.test.acceptance.trace.MatchTraceTest;
import org.eclipse.epsilon.ecl.engine.test.acceptance.trees.TestXmlTreeComparison;
import org.eclipse.epsilon.ecl.engine.test.acceptance.unparser.EclUnparserTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	EclCanAccessBuiltinsTests.class, TestXmlTreeComparison.class,
	MatchesOperationTest.class, DomainTests.class,
	EclUnparserTests.class, MatchTraceTest.class,
	EclModuleEquivalenceTests.class
})
public class EclAcceptanceTestSuite {

}
