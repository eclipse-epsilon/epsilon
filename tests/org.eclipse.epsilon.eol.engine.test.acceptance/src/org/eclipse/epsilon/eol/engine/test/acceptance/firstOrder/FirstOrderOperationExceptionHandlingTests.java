/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.engine.test.acceptance.firstOrder;

import static org.eclipse.epsilon.eol.engine.test.acceptance.util.EolAcceptanceTestUtil.testExceptionEquivalenceBetweenModules;
import org.junit.Test;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class FirstOrderOperationExceptionHandlingTests {

	final String
		testData = "var testData := Sequence{-9..16};\ntestData.",
		testPredicate = "n | n >= 0 and n.invalidProp.nullPointer";
	
	@Test
	public void testExceptionIsThrown() throws Exception {
		testExceptionEquivalenceBetweenModules(testData+"select("+testPredicate+");");
	}
	
	@Test
	public void testInvalidNMatch() throws Exception {
		testExceptionEquivalenceBetweenModules(testData+"nMatch(n | n > 0);");
	}
}
