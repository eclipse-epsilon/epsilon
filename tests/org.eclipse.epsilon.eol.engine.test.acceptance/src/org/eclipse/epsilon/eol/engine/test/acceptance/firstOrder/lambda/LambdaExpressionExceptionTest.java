/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.engine.test.acceptance.firstOrder.lambda;

import static org.eclipse.epsilon.eol.engine.test.acceptance.util.EolAcceptanceTestUtil.testExceptionEquivalenceBetweenModules;
import static org.junit.Assert.assertNull;
import org.eclipse.epsilon.eol.EolModule;
import static org.eclipse.epsilon.eol.engine.test.acceptance.util.EolAcceptanceTestUtil.executeReturnException;
import org.junit.Test;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class LambdaExpressionExceptionTest {

	@Test
	public void testExceptionIsThrownForNonOperation() throws Exception {
		testExceptionEquivalenceBetweenModules(
			"Native('java.util.stream.IntStream').range(0,100).nonExistentOperation(i | foo(i));"
		);
	}
	
	@Test
	public void testExceptionIsThrownForInvalidReturnOperation() throws Exception {
		testExceptionEquivalenceBetweenModules(
			"Native('java.util.stream.IntStream').range(0,100).filter(i | i).count();"
		);
	}
	
	@Test
	public void testLazyExecution() throws Exception {
		assertNull(executeReturnException(
			"Native('java.util.stream.IntStream').range(0,100).filter(i | null.nullPointer);",
			new EolModule())
		);
	}
}
