/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.egx.engine.test.acceptance.parse;

import org.eclipse.epsilon.egl.EgxModule;
import org.eclipse.epsilon.egl.parse.EgxUnparser;
import org.eclipse.epsilon.egx.engine.test.acceptance.EgxAcceptanceTestSuite;
import org.eclipse.epsilon.eol.engine.test.acceptance.unparser.UnparserTests;
import org.junit.Test;

public class EgxUnparserTests extends UnparserTests {

	@Test
	public void testExistingEgxFiles() throws Exception {
		test(EgxAcceptanceTestSuite.class,
			"ecore2dot/ecore.egx",
			"parse/test.egx"
		);
	}
	
	@Override
	public EgxModule createModule() {
		return new EgxModule();
	}

	@Override
	public EgxUnparser createUnparser() {
		return new EgxUnparser();
	}
}
