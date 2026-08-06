/*********************************************************************
 * Copyright (c) 2022 Antonio García-Domínguez.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eunit.junit.test;

import static org.junit.Assert.assertEquals;

import org.eclipse.epsilon.eunit.junit.EUnitTestRunner;
import org.eclipse.epsilon.eunit.junit.test.suites.ImportEUnitSuite;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runners.model.InitializationError;

public class ImportDataTests {

	@Test
	public void getChildrenCanUseImportedOperations() throws InitializationError {
		EUnitTestRunner runner = new EUnitTestRunner(ImportEUnitSuite.class);
		final Description description = runner.getDescription();
		final Description root = description.getChildren().get(0);
		
		assertEquals("The @data operation should run correctly and result in two tests under the test root",
			2, root.getChildren().size());
	}

}
