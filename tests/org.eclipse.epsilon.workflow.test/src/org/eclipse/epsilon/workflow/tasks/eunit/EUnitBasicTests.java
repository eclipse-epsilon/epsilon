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

import static org.junit.Assert.*;
import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.apache.tools.ant.BuildException;
import org.eclipse.epsilon.eunit.EUnitModule;
import org.eclipse.epsilon.eunit.EUnitParseException;
import org.junit.Test;

/**
 * EUnit tests: basic aspects.
 *
 * @author Antonio García-Domínguez
 * @version 1.0
 */
public class EUnitBasicTests extends EUnitTestCase {

	@Test
	public void allPassProducesGoodOutput() throws Exception {
		runTarget(ANT_BUILD_FILE, "allPass");
		checkOutput(new File(BASE_DIR, "TEST-default.all-pass.xml"),
				EUnitModule.DEFAULT_PACKAGE,
				new String[]{"twoElements", "firstElement"},
				new HashSet<String>(), new HashSet<String>());
	}

	@Test
	public void allPassWithContextOperations() throws Exception {
		runTarget(ANT_BUILD_FILE, "allPassContextOperations");
	}

	@Test
	public void allPassProducesGoodOutputWithCustomRelativePathFromBuildDir()
			throws Exception {
				runTarget(ANT_BUILD_FILE, "allPassCustom");
				checkOutput(new File(BASE_DIR.getParentFile(), "TEST-default.all-pass.xml"),
						EUnitModule.DEFAULT_PACKAGE,
						new String[]{"twoElements", "firstElement"},
						new HashSet<String>(), new HashSet<String>());
	}

	@Test
	public void allPassModelRef() throws Exception {
		runTarget(ANT_BUILD_FILE, "allPassModelRef");
	}

	@Test
	public void allPassXMLReportCanBeSuppressed() throws Exception {
		runTarget(ANT_BUILD_FILE, "allPassModelRefReportSuppressed");
		final File xmlReportFile = new File(BASE_DIR, "TEST-default.all-pass.xml");
		assertFalse("The XML report should have been suppressed", xmlReportFile.exists());
	}

	@Test(expected = Exception.class)
	public void buildFailsWithBadImports() throws Exception {
		runTarget(ANT_BUILD_FILE, "badImports");
	}

	@Test
	public void customPackageIsHonored() throws Exception {
		runTarget(ANT_BUILD_FILE, "package");
		checkOutput(new File(BASE_DIR, "TEST-mypackage.all-pass.xml"),
				"mypackage",
				new String[]{"twoElements", "firstElement"},
				new HashSet<String>(), new HashSet<String>());
	}

	@Test
	public void failingTestProducesGoodOutput() throws Exception {
		runTarget(ANT_BUILD_FILE, "failure");
		checkOutput(new File(BASE_DIR, "TEST-default.one-failure.xml"),
				EUnitModule.DEFAULT_PACKAGE,
				new String[]{"twoElements", "firstElement", "iAmAFailure"},
				new HashSet<>(Arrays.asList("iAmAFailure")), new HashSet<String>());
	}

	@Test
	public void failingTestProducesGoodOutputEvenIfAborted()
			throws Exception {
				try {
					runTarget(ANT_BUILD_FILE, "failureFailOnError");
					fail("Should have thrown a BulidException (failed build)");
				} catch (BuildException ex) {
					// OK, build failed
				}
				// But it should *still* have generated a report
				checkOutput(new File(BASE_DIR, "TEST-default.one-failure.xml"),
						EUnitModule.DEFAULT_PACKAGE,
						new String[]{"twoElements", "firstElement", "iAmAFailure"},
						new HashSet<>(Arrays.asList("iAmAFailure")), new HashSet<String>());
			}

	@Test
	public void testWithErrorsProducesGoodOutput() throws Exception {
		runTarget(ANT_BUILD_FILE, "error");
		checkOutput(new File(BASE_DIR, "TEST-default.one-error.xml"),
				EUnitModule.DEFAULT_PACKAGE,
				new String[]{"twoElements", "firstElement", "iAmError"},
				new HashSet<String>(), new HashSet<>(Arrays.asList("iAmError")));
	}

	@Test
	public void parametric1LevelProducesGoodOutput() throws Exception {
		runTarget(ANT_BUILD_FILE, "parametric1level");
		checkOutput(new File(BASE_DIR, "TEST-default.1level.xml"),
				EUnitModule.DEFAULT_PACKAGE,
				new String[]{
					"twoElements[1]", "firstElement[1]",
					"twoElements[2]", "firstElement[2]"},
				new HashSet<String>(), new HashSet<String>());
	}

	@Test
	public void parametric2LevelsProducesGoodOutput() throws Exception {
		assertParametric2LevelWorks("parametric2levels", "2levels");
	}

	@Test
	public void parametric2LevelsWithReusedOpProducesGoodOutput() throws Exception {
		assertParametric2LevelWorks("parametric2levels-reused-op", "2levels-reused-op");
	}

	private void assertParametric2LevelWorks(final String targetName, String eunitBasename) throws Exception {
		runTarget(ANT_BUILD_FILE, targetName);
		checkOutput(new File(BASE_DIR, "TEST-default." + eunitBasename + ".xml"),
			EUnitModule.DEFAULT_PACKAGE,
			new String[]{
				"twoElements[1][1]", "firstElement[1][1]",
				"twoElements[1][2]", "firstElement[1][2]",
				"twoElements[2][1]", "firstElement[2][1]",
				"twoElements[2][2]", "firstElement[2][2]",
			},
			new HashSet<String>(), new HashSet<String>()
		);
	}

	@Test
	public void parametricHandleErrors() throws Exception {
		final String[] expectedTestCases = new String[]{"root"};
	
		runTarget(ANT_BUILD_FILE, "parametricHandleErrors");
		checkOutput(new File(BASE_DIR, "TEST-default.handle-errors.xml"),
				EUnitModule.DEFAULT_PACKAGE,
				expectedTestCases,
				new HashSet<String>(),
				new HashSet<>(Arrays.asList(expectedTestCases)));
	}

	@Test
	public void suiteSetupTeardown() throws Exception {
		runTarget(ANT_BUILD_FILE, "suite-ops");
		final List<String> expected = Arrays.asList("mytest - 0", "mytest - 1", "mytest - 2", "mytest - 3", "end test");
		final List<String> obtained = SharedStringCollector.getList();
		assertEquals(expected, obtained);
	}

	@Test
	public void withCanUseDataVariables() throws Exception {
		runTarget(ANT_BUILD_FILE, "withAnnotationCombineData");
		checkOutput(new File(BASE_DIR, "TEST-default.combine-data.xml"),
				EUnitModule.DEFAULT_PACKAGE,
				new String[]{
					"hasOneA[1]", "hasOneA[2]"
				},
				new HashSet<String>(), new HashSet<String>());
	}

	@Test
	public void withCanUseDataVariablesHandleErrors() throws Exception {
		runTarget(ANT_BUILD_FILE, "withAnnotationCombineDataHandleErrors");
		checkOutput(new File(BASE_DIR, "TEST-default.combine-data-handle-errors.xml"),
				EUnitModule.DEFAULT_PACKAGE,
				new String[]{
					"hasOneA[1]", "hasOneA[2]"
				},
				new HashSet<String>(), new HashSet<>(Arrays.asList("hasOneA[1]", "hasOneA[2]")));
	}

	@Test
	public void withIsHonored() throws Exception {
		runTarget(ANT_BUILD_FILE, "withAnnotation");
		checkOutput(new File(BASE_DIR, "TEST-default.basic-usage.xml"),
				EUnitModule.DEFAULT_PACKAGE,
				new String[]{
					"hasOneA[1]", "hasOneA[2]",
					"hasOneB", "hasTwoB",
					"hasTwoElements", "hasThreeElements"
				},
				new HashSet<String>(), new HashSet<String>());
	}

	@Test
	public void withUsingMissingModelsIsFailedTestCase() throws Exception {
		runTarget(ANT_BUILD_FILE, "withAnnotationMissingModel");
		checkOutput(new File(BASE_DIR, "TEST-default.missing-model.xml"),
				EUnitModule.DEFAULT_PACKAGE,
				new String[]{
					"hasOneA[1]", "hasOneA[2]"
				},
				new HashSet<String>(), new HashSet<>(Arrays.asList("hasOneA[2]")));
	}

	@Test
	public void onlyWithIsHonored() throws Exception {
		runTarget(ANT_BUILD_FILE, "onlyWithAnnotation");
		checkOutput(new File(BASE_DIR, "TEST-default.only-with.xml"),
				EUnitModule.DEFAULT_PACKAGE,
				new String[]{
					"hasOneA", "hasTwoB",
					"hasTwoElements_bIsRemoved", "hasTwoElements_aIsRemoved",
					"hasThreeElements_bIsRemoved", "hasThreeElements_aIsRemoved"
				},
				new HashSet<String>(),
				new HashSet<>(Arrays.asList(
					"hasTwoElements_aIsRemoved", "hasThreeElements_bIsRemoved")));
	}

	@Test
	public void variables() throws Exception {
		runTarget(ANT_BUILD_FILE, "variables");
	}

}
