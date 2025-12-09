/*******************************************************************************
 * Copyright (c) 2025 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.rdf.emf.test;

import java.io.File;
import java.nio.file.Paths;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	RdfEmfModelTest.class
})
public class RdfEmfModelTestSuite {

	public static final File RESOURCE_FOLDER = Paths.get(
		"..", "org.eclipse.epsilon.emc.rdf.emf.test", "resources"
	).toFile();

}
