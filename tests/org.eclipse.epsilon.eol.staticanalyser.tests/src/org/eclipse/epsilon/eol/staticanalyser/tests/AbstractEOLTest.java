package org.eclipse.epsilon.eol.staticanalyser.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.module.ModuleMarker;
import org.eclipse.epsilon.common.module.ModuleMarker.Severity;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.staticanalyser.EolStaticAnalyser;
import org.eclipse.epsilon.eol.staticanalyser.types.EolType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AbstractEOLTest extends AbstractBaseTest {

	private EOLTestMarkerStringParser testMarkerParser = new EOLTestMarkerStringParser();
	protected EolModule module;
	protected EolStaticAnalyser staticAnalyser;
	protected List<ModuleMarker> staticAnalyserMarkers = null;

	public AbstractEOLTest(String testTag, File epsilonTestFile, boolean enableconsoleoutput) {
		super(testTag, epsilonTestFile, enableconsoleoutput);
	}

	@Before
	public void setUp() throws Exception {
		module = new EolModule();
		staticAnalyser = new EolStaticAnalyser(new StaticModelFactory());
		module.parse(programFile);
		staticAnalyserMarkers = staticAnalyser.validate(module);
	}

	@After
	public void cleanUp() {
		module = null;
		staticAnalyser = null;
		staticAnalyserMarkers.clear();
	}

	@Test
	public void originalTestApproach() throws Exception {
		List<ModuleMarker> testMarkers = testMarkerParser.extractTestMarkers(programFile, false);
		int regionCount = countMarkersWithRegions(testMarkers);
		assumeTrue("Test has markers but none with regions",!testMarkers.isEmpty() && regionCount == 0);
		parseFile(programFile);
	}

	@Test
	public void staticAnalysisModuleMarkerTest() throws Exception {
		if (isConsoleOutputActive) {
			System.out.println("\nTesting program: " + testTag);
		}
		List<ModuleMarker> testMarkers = testMarkerParser.extractTestMarkers(programFile, false);
		int regionCount = countMarkersWithRegions(testMarkers);
		assumeTrue("Test either has no markers or has markers with no regions", testMarkers.isEmpty() || regionCount > 0);

		// Check how many markers are there, and how many have regions
		if(testMarkers.size() != regionCount) {
			// Rerun the marker parser and fail test on the first region error detected
			testMarkerParser.extractTestMarkers(programFile, true);
		}
		
		// No test markers in program, therefore the program should be clean with no
		// Static Analyser Markers
		if (testMarkers.isEmpty()) {
			if (isConsoleOutputActive) {
				System.out.println(" [!] Clean program test (no test markers)");
			}
			assertEquals(
					"Static Analyser reporting Markers for a Clean program test (no test markers set or expected)\n"
							+ testMarkerParser.asBulletListString(staticAnalyserMarkers),
					0, staticAnalyserMarkers.size());
			return;
		}

		checkForExpectedStaticAnalyserMarkers(testMarkers, staticAnalyserMarkers);
	}
	
	@Test
	public void checkResolvedTypes() {
		visit(module.getChildren());
	}

	private int countMarkersWithRegions(List<ModuleMarker> testMarkers) {
		int regionCount = 0;
		for (ModuleMarker testMarker : testMarkers) {
			if (null != testMarker.getRegion()) {
				regionCount++;
			}
		}
		return regionCount;
	}

	private void checkForExpectedStaticAnalyserMarkers(List<ModuleMarker> testMarkers,
			List<ModuleMarker> staticAnalysisMarkers) {

		List<ModuleMarker> unmatchedSAM = new ArrayList<ModuleMarker>();
		unmatchedSAM.addAll(staticAnalysisMarkers);

		List<ModuleMarker> matchedTestMarkers = new ArrayList<ModuleMarker>();

		int testMarkerIndex = 0;
		for (ModuleMarker testMarker : testMarkers) {
			unmatchedSAM.remove(
					matchedTestMarkerToStaticAnalysisMarker(testMarker, staticAnalysisMarkers, testMarkerIndex));
			// If we get here then we matched a test marker to a static analysis marker

			matchedTestMarkers.add(isNotDuplicateTestMarker(testMarker, matchedTestMarkers, testMarkerIndex));
			// If we get here when we haven't tried to remove the same test marker more than
			// once
			testMarkerIndex++;
		}

		// If we get here then we matched every test marker to a static analysis marker,
		// there should be no unmatched static analysis markers.
		assertTrue(
				"\nStatic Analysis Markers found that are not Test Markers: \n"
						+ testMarkerParser.asBulletListString(unmatchedSAM),
				unmatchedSAM.isEmpty());
	}
		
	private ModuleMarker isNotDuplicateTestMarker(ModuleMarker aMarker, List<ModuleMarker> listOfMarkers,
			int testMarkerIndexPosition) {
		if (listOfMarkers.isEmpty()) {
			return aMarker;
		}
		int listOfMarkersIndex = 0;

		for (ModuleMarker listMarker : listOfMarkers) {
			assertFalse("\nTest marker " + testMarkerIndexPosition + " " + "is a duplicate of Test marker "
					+ listOfMarkersIndex + "\n - " + aMarker, aMarker.equals(listMarker));
			listOfMarkersIndex++;
		}
		return aMarker;
	}

	private ModuleMarker matchedTestMarkerToStaticAnalysisMarker(ModuleMarker testMarker,
			List<ModuleMarker> staticAnalysisMarkers, int testMarkerIndex) {
		
		List<ModuleMarker> candidatesByMessage = new ArrayList<ModuleMarker>();
		for (ModuleMarker staticAnalysisMarker : staticAnalysisMarkers) {
			if (staticAnalysisMarker.getMessage().equals(testMarker.getMessage())) {
				candidatesByMessage.add(staticAnalysisMarker);
			}
		}
		assertTrue("\nStatic Analysis Marker with MESSAGE not found for Test Marker " + testMarkerIndex + " :\n"
				+ testMarker.toString(), candidatesByMessage.size() > 0);

		List<ModuleMarker> candidatesByRegion = new ArrayList<ModuleMarker>();
		for (ModuleMarker staticAnalysisMarker : candidatesByMessage) {
			if (staticAnalysisMarker.getRegion().equals(testMarker.getRegion())) {
				candidatesByRegion.add(staticAnalysisMarker);
			}
		}
		assertTrue(
				"\nStatic Analysis Marker with REGION not found for Test Marker " + testMarkerIndex + " :\n"
						+ testMarker.toString() + "\n found similar Markers:\n"
						+ testMarkerParser.asBulletListString(candidatesByMessage),
				candidatesByRegion.size() > 0);

		List<ModuleMarker> candidatesBySeverity = new ArrayList<ModuleMarker>();
		for (ModuleMarker staticAnalysisMarker : candidatesByRegion) {
			if (staticAnalysisMarker.getSeverity().equals(testMarker.getSeverity())) {
				candidatesBySeverity.add(staticAnalysisMarker);
			}
		}
		assertTrue(
				"\nStatic Analysis Marker with SEVERITY not found for Test Marker " + testMarkerIndex + " :\n"
						+ testMarker.toString() + "\n found similar Markers:\n"
						+ testMarkerParser.asBulletListString(candidatesByRegion),
				candidatesBySeverity.size() > 0);

		assertEquals(
				"\nMultiple Static Analysis Markers reporting the same information:\n"
						+ testMarkerParser.asBulletListString(candidatesBySeverity),
				1, candidatesBySeverity.size());
		return candidatesBySeverity.get(0);
	}

	/*
	 * Methods below are from the original test, they take a program file, parse and
	 * test
	 */

	// Original test method
	protected void parseFile(File file) throws Exception {
		String content = new String(Files.readAllBytes(file.toPath()));
		String[] lines = content.split(System.lineSeparator());
		List<String> errorMessages = new ArrayList<String>();
		List<String> warningMessages = new ArrayList<String>();
		for (String line : lines) {
			if (!line.substring(0, 2).equals("//")) {
				break;
			}

			if (line.substring(0, 3).equals("//!")) {
				errorMessages.add(line.substring(3));
			} else if (line.substring(0, 3).equals("//?")) {
				warningMessages.add(line.substring(3));
			}
		}
		assertValid(file, errorMessages, warningMessages);
	}

	// Original test method
	public void assertValid(File file, List<String> expectedErrorMessages, List<String> expectedWarningMessages)
			throws Exception {
		List<ModuleMarker> errors = staticAnalyserMarkers.stream().filter(m -> m.getSeverity() == Severity.Error)
				.collect(Collectors.toList());
		List<ModuleMarker> warnings = staticAnalyserMarkers.stream().filter(m -> m.getSeverity() == Severity.Warning)
				.collect(Collectors.toList());

		String errorMessages = errors.stream()
				.map((e) -> e.getMessage() + " line: " + e.getRegion().getStart().getLine())
				.collect(Collectors.joining("\n"));
		assertEquals("Unexpected number of errors\n" + errorMessages + "\n", expectedErrorMessages.size(),
				errors.size());

		Map<String, Integer> errorMessagesMap = new HashMap<String, Integer>();
		for (String errorMessage : errors.stream().map(ModuleMarker::getMessage).collect(Collectors.toList())) {
			if (errorMessagesMap.containsKey(errorMessage)) {
				errorMessagesMap.put(errorMessage, errorMessagesMap.get(errorMessage) + 1);
			} else {
				errorMessagesMap.put(errorMessage, 1);
			}
		}
		for (String errorMessage : expectedErrorMessages) {
			assertTrue("An expected error was not found in the list of thrown errors",
					errorMessagesMap.containsKey(errorMessage));
			errorMessagesMap.put(errorMessage, errorMessagesMap.get(errorMessage) - 1);
		}
		for (Integer i : errorMessagesMap.values()) {
			assertFalse("An error message was not matched enough times", i > 0);
			assertFalse("An error message was matched too many times", i < 0);
		}

		String warningMessages = warnings.stream()
				.map((e) -> e.getMessage() + " line: " + e.getRegion().getStart().getLine())
				.collect(Collectors.joining("\n"));
		assertEquals("Unexpected number of warnings\n" + warningMessages + "\n", expectedWarningMessages.size(),
				warnings.size());

		Map<String, Integer> warningMessagesMap = new HashMap<String, Integer>();
		for (String warningMessage : warnings.stream().map(ModuleMarker::getMessage).collect(Collectors.toList())) {
			if (warningMessagesMap.containsKey(warningMessage)) {
				warningMessagesMap.put(warningMessage, warningMessagesMap.get(warningMessage) + 1);
			} else {
				warningMessagesMap.put(warningMessage, 1);
			}
		}
		for (String warningMessage : expectedWarningMessages) {
			assertTrue("An expected warning was not found in the list of thrown warnings",
					warningMessagesMap.containsKey(warningMessage));
			warningMessagesMap.put(warningMessage, warningMessagesMap.get(warningMessage) - 1);
		}
		for (Integer i : warningMessagesMap.values()) {
			assertFalse("A warning message was not matched enough times", i > 0);
			assertFalse("A warning message was matched too many times", i < 0);
		}

		// Abstract syntax tree type test is now a stand alone test 
	}

	// Original test method -- Checks the Abstract syntax tree types against a
	// multiline comment /* */ for each type
	protected void visit(List<ModuleElement> elements) {
		for (ModuleElement element : elements) {
			// Multiline comments (/* */") are used to capture the expected type of
			// expressions
			if (!element.getComments().isEmpty() && element.getComments().get(0).isMultiline()) {
				assertEquals(element.getComments().get(0).toString(), getResolvedType(element).toString());
			}
			visit(element.getChildren());
		}
	}

	// Original test method
	protected EolType getResolvedType(ModuleElement element) {
		return (EolType) element.getData().get("resolvedType");
	}
	
}
 