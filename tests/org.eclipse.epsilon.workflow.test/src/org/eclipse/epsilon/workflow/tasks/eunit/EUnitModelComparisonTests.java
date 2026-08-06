/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.workflow.tasks.eunit;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import org.apache.tools.ant.BuildException;
import org.eclipse.ant.core.AntRunner;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.epsilon.eunit.EUnitModule;
import org.junit.Test;

/**
 * Tests for the EUnit model comparison assertions. Since these depend on the
 * registered model comparators, this test should be run as a JUnit plug-in
 * test.
 *
 * @author Antonio Garcia-Dominguez
 */
public class EUnitModelComparisonTests extends EUnitTestCase {

	@Override
	public void runTarget(File buildFile, String targetName) throws BuildException, IOException {
		AntRunner runner = new AntRunner();
		runner.setBuildFileLocation(buildFile.getCanonicalPath());
		runner.setExecutionTargets(new String[] { targetName });
		try {
			runner.run();
		}
		catch (CoreException e) {
			throw new BuildException(e);
		}
	}

	@Test
	public void compareEMFWithEMF() throws Exception {
		runBasicTests("emf-emf");
	}

	@Test
	public void compareEMFWithEMFWithModelReferences() throws Exception {
		runBasicTests("emf-emf-refs");
	}

	@Test
	public void compareEMFWithEMFUsingGeneratedModels() throws Exception {
		runTarget(ANT_BUILD_FILE, "emf-emf-generated");
	}

	@Test
	public void compareEMFWithEMFCopy() throws Exception {
		runTarget(ANT_BUILD_FILE, "emf-emf-copy");
	}

	@Test
	public void compareEMFInMemoryModels() throws Exception {
		runTarget(ANT_BUILD_FILE, "emf-emf-memory");
	}

	@Test
	public void compareEMFInMemoryModelsWithCustomComparator() throws Exception {
		runTarget(ANT_BUILD_FILE, "emf-emf-memory-customComparator");
	}

	@Test
	public void leftModelIsEmpty() throws Exception {
		try {
			runTarget(ANT_BUILD_FILE, "emf-emf-empty-left");
			fail("Expected a BuildException");
		}
		catch (BuildException ex) {
			assertTrue(ex.getMessage().contains("Expected B to be also empty, but it is not"));
		}
	}

	@Test
	public void rightModelIsEmpty() throws Exception {
		try {
			runTarget(ANT_BUILD_FILE, "emf-emf-empty-right");
			fail("Expected a BuildException");
		}
		catch (BuildException ex) {
			assertTrue(ex.getMessage().contains("but it is empty"));
		}
	}

	@Test
	public void umlAgainstItself() throws Exception {
		runTarget(ANT_BUILD_FILE, "uml-same");
	}

	@Test
	public void bothModelsAreEmpty() throws Exception {
		runTarget(ANT_BUILD_FILE, "emf-emf-empty-both");
	}

	@Test
	public void leftModelIsEmptyNotEquals() throws Exception {
		runTarget(ANT_BUILD_FILE, "emf-emf-empty-left-noteq");
	}

	@Test
	public void rightModelIsEmptyNotEquals() throws Exception {
		runTarget(ANT_BUILD_FILE, "emf-emf-empty-right-noteq");
	}

	@Test
	public void bothModelsAreEmptyNotEquals() throws Exception {
		try {
			runTarget(ANT_BUILD_FILE, "emf-emf-empty-both-noteq");
			fail("Expected a BuildException");
		}
		catch (BuildException ex) {
			assertTrue(ex.getMessage().contains("Expected B not to be empty, but it is empty"));
		}
	}

	@Test
	public void inlineHutnModelsCanBeCompared() throws Exception {
		runTarget(ANT_BUILD_FILE, "inline-hutn-compare");
	}

	private void runBasicTests(String targetName) throws Exception {
		runTarget(ANT_BUILD_FILE, targetName);
		checkOutput(
			new File(BASE_DIR, "TEST-default.emf-emf.xml"),
			EUnitModule.DEFAULT_PACKAGE,
			new String[]{
				"sameModelsAreEqual[1]", "sameModelsAreEqual[2]", "sameModelsAreEqual[3]",
				"modelsFromDifferentMetamodelsAreDifferent[1]",
				"modelsFromDifferentMetamodelsAreDifferent[2]",					
				"modelsWithDifferentContentAreDifferent",
				"modelsWithDifferentContentAreDifferentFailing",
				"transformedIsEqualToExpected",
				"transformedIsEqualToExpectedFailing"
			},
			new HashSet<>(
				Arrays.asList(
					"modelsWithDifferentContentAreDifferentFailing",
					"transformedIsEqualToExpectedFailing"
				)
			),
			new HashSet<String>()
		);
	}

}
