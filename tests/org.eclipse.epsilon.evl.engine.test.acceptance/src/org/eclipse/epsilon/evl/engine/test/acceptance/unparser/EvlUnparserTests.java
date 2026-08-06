/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.evl.engine.test.acceptance.unparser;

import org.eclipse.epsilon.eol.engine.test.acceptance.unparser.UnparserTests;
import org.eclipse.epsilon.evl.EvlModule;
import org.eclipse.epsilon.evl.engine.test.acceptance.EvlAcceptanceTestSuite;
import org.eclipse.epsilon.evl.parse.EvlUnparser;
import org.junit.Test;

public class EvlUnparserTests extends UnparserTests {

	@Test
	public void testExistingEvlFiles() throws Exception {
		test(EvlAcceptanceTestSuite.class,
				"scripts/cookbook.evl",
				"scripts/java_annotated.evl",
				"scripts/java_findbugs.evl",
				"scripts/java_equals.evl",
				"scripts/java_parallel.evl",
				"scripts/ValidateIntermediateModel.evl");
	}
	
	@Override
	public EvlModule createModule() {
		return new EvlModule();
	}

	@Override
	public EvlUnparser createUnparser() {
		return new EvlUnparser();
	}

}
