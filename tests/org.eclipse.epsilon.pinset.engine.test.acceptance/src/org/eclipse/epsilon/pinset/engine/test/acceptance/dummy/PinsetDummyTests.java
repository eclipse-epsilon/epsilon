/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *********************************************************************/
package org.eclipse.epsilon.pinset.engine.test.acceptance.dummy;

import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.pinset.PinsetModule;
import org.eclipse.epsilon.pinset.engine.test.acceptance.PinsetTests;
import org.junit.BeforeClass;
import org.junit.Test;

public class PinsetDummyTests extends PinsetTests {

	@BeforeClass
	public static void setup() throws Exception {
		registerMetamodel("dummy.ecore", PinsetDummyTests.class);
	}

	@Test
	public void testDummy() throws Exception {
		FileUtil.copyToTemp(getFile("operations.eol"));

		PinsetModule module = new PinsetModule();
		module.persistDatasets(false);
		module.parse(getFile("dummy.pinset"));
		if (!module.getParseProblems().isEmpty()) {
			System.err.println("The following errors were identified");
			for (ParseProblem parseProblem : module.getParseProblems()) {
				System.err.println("- " + parseProblem);
			}
			throw new RuntimeException("Parsing errors detected");
		}

		module.getContext().getModelRepository().addModel(
				loadModel("M", "dummy.model", "pinsetdummy", true, false));
		module.execute();

		assertEquivalent(module);
	}

}
