/********************************************************************************
 * Copyright (c) 2025 University of York
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garcia-Dominguez - initial API and implementation
 ********************************************************************************/
package org.eclipse.epsilon.rdf.emf.test;

import java.io.File;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Parameterized tests for change equivalence with RDF collections (bags and sequences).
 */
@RunWith(Parameterized.class)
public class ChangeEquivalenceTest_EReference extends AbstractChangeEquivalenceTest {
	private static final File TEST_FOLDER = new File("resources", "changeEquivalence_EReference");

	@Parameters(name = "{0}")
	public static Object[] data() {
		return AbstractChangeEquivalenceTest.findEOLScriptsWithin(TEST_FOLDER).toArray();
	}

	public ChangeEquivalenceTest_EReference(File eolTestFile) {
		super(eolTestFile, false);
	}
}