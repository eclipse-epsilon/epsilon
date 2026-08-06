/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.engine.test.acceptance.unparser;

import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.engine.test.acceptance.EolAcceptanceTestSuite;
import org.eclipse.epsilon.eol.parse.EolUnparser;
import org.junit.Test;

public class EolUnparserTests extends UnparserTests {
	
	@Test
	public void testEcore2GMF() throws Exception {
		test("ECore2GMF.eol", EolUnparserTests.class);
	}
	
	@Test
	public void testFormatting() throws Exception {
		test("Formatting.eol", EolUnparserTests.class);
	}
	
	@Test
	public void testElvis() throws Exception {
		test("ElvisTestsNoShortcut.eol", EolUnparserTests.class);
	}
	
	@Test
	public void testExistingEolTests() throws Exception {
		test(EolAcceptanceTestSuite.class,
				"AnnotatedOperationTests.eol",
				"BooleanTests.eol",
				"BuiltInVariablesTests.eol",
				"CllectionPropertyTests.eol",
				"CollectionsTests.eol",
				"ComparisonTests.eol",
				"CreateDeleteTests.eol",
				"EqualityTests.eol",
				"IsDefinedTests.eol",
				"IterableTests.eol",
				"MathTests.eol",
				"ModelElementConstructorTests.eol",
				"ModelElementTypeResolutionTests.eol",
				"OperationOrderTests.eol",
				"PostfixOperatorTests.eol",
				"ReturnTypeTests.eol",
				"SafeNavigationTests.eol",
				"ScopeTests.eol",
				"StringTests.eol",
				"SwitchTests.eol",
				"TransactionTests.eol",
				"TypeConversionTests.eol"
				);
	}

	@Override
	public EolModule createModule() {
		return new EolModule();
	}

	@Override
	public EolUnparser createUnparser() {
		return new EolUnparser();
	}
	
	
}
