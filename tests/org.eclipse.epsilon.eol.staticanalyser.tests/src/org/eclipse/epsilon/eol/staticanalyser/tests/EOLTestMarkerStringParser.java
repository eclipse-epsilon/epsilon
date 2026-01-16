package org.eclipse.epsilon.eol.staticanalyser.tests;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.eclipse.epsilon.common.module.ModuleMarker;
import org.eclipse.epsilon.common.module.ModuleMarker.Severity;
import org.eclipse.epsilon.common.parse.Region;

public class EOLTestMarkerStringParser {

	private int lineCounter = 0;
	
	private static final String GRP_SEVERITY = "severity",
		GRP_START_LINE = "lineStart",
		GRP_START_COLUMN = "columnStart",
		GRP_END_LINE = "lineEnd",
		GRP_END_COLUMN = "columnEnd",
		GRP_MESSAGE = "message";

	private static final String
		RE_SEVERITY = String.format("//(?<%s>[?!])?", GRP_SEVERITY),
		RE_LOCATION_TEMPLATE = "(?<%s>[0-9]+):(?<%s>[0-9]+)",
		RE_START = String.format(RE_LOCATION_TEMPLATE, GRP_START_LINE, GRP_START_COLUMN),
		RE_END = String.format(RE_LOCATION_TEMPLATE, GRP_END_LINE, GRP_END_COLUMN),
		RE_REGION = String.format("(?:\\[%s-%s\\])?", RE_START, RE_END),
		RE_MESSAGE = String.format("(?<%s>.+)", GRP_MESSAGE),
		RE_LINE = String.format("%s *%s *%s", RE_SEVERITY, RE_REGION, RE_MESSAGE);

	private static final Pattern PATTERN_LINE = Pattern.compile(RE_LINE);

	public List<ModuleMarker> extractTestMarkers(File testProgram, boolean failOnRegionError) throws IOException {
		lineCounter = 0;
		
		String content = new String(Files.readAllBytes(testProgram.toPath()));
		String[] lines = content.split(System.lineSeparator());
		List<ModuleMarker> testMarkerList = new ArrayList<ModuleMarker>();
		
		for (String line : lines) {
			ModuleMarker testLineMarker = createTestMarker(line, failOnRegionError);
			if (null != testLineMarker) {
				testMarkerList.add(testLineMarker);
			}
			lineCounter++;
		}
		return testMarkerList;
	}

	// Format for test files //![startline:startcolumn-endline:endcolumn] message
	private ModuleMarker createTestMarker(String testMarkedProgramLine, boolean failOnRegionError) {
		Matcher regexMarker = PATTERN_LINE.matcher(testMarkedProgramLine);
		if(!regexMarker.matches()) {
			// no matches
			return null;
		}

		// Parse comment into a ModuleMarker
		ModuleMarker testMarker = new ModuleMarker();

		// Extract Severity level
		String severityString = regexMarker.group(GRP_SEVERITY);
		if (null == severityString) {
			// Comments land in here
			return null;
		} else {
			switch (severityString) {
			case "!": // ERROR
				testMarker.setSeverity(Severity.Error);
				break;
			case "?": // WARNING
				testMarker.setSeverity(Severity.Warning);
				break;
			default:
				fail(getTestMarkerParsingError("SEVERITY (unknown)", lineCounter, testMarkedProgramLine));
			}
		}
		
		// Message
		String message = regexMarker.group(GRP_MESSAGE); 
		if(null == message) {
			fail(getTestMarkerParsingError("MESSAGE", lineCounter, testMarkedProgramLine));
		} else {
			testMarker.setMessage(message);				
		}

		// Region column and line information -- never fail on region here
		Region region = extractRegion(regexMarker);
		if(null == region && failOnRegionError) {
			fail(getTestMarkerParsingError("REGION", lineCounter, testMarkedProgramLine));
		} else {
			testMarker.setRegion(region);
		}

		return testMarker;
	}
	
	private String getTestMarkerParsingError(String part, int linenumber, String testMarkedProgramLine) {
		return String.format("Check test Marker, problem with %s on line %s > %s", part, linenumber,
				testMarkedProgramLine);
	}

	private Region extractRegion(Matcher regexMarker) {
		if(null == regexMarker.group(GRP_START_LINE)) {
			// If anything is wrong in the region, or there is no region we end up here...
			return null;
		}
		if(null == regexMarker.group(GRP_START_COLUMN)) {
			// This should never happen
			return null;
		}
		if (null == regexMarker.group(GRP_END_LINE)) {
			// This should never happen
			return null;
		}
		if (null == regexMarker.group(GRP_END_COLUMN)) {
			// This should never happen
			return null;
		}

		// Eclipse IDE columns report +1 over the internal column range
		return new Region(
				Integer.parseInt(regexMarker.group(GRP_START_LINE)),
				Integer.parseInt(regexMarker.group(GRP_START_COLUMN)),
				Integer.parseInt(regexMarker.group(GRP_END_LINE)),
				Integer.parseInt(regexMarker.group(GRP_END_COLUMN))
		);
	}

	public List<String> getErrorMessageStrings(List<ModuleMarker> testMarkers, Severity severity) {
		List<String> messageStrings = new ArrayList<String>();
		List<ModuleMarker> filteredMarkers = testMarkers.stream().filter(m -> m.getSeverity() == severity)
				.collect(Collectors.toList());
		for (ModuleMarker testMarker : filteredMarkers) {
			messageStrings.add(testMarker.getMessage());
		}
		return messageStrings;
	}

	public String asBulletListString (List<ModuleMarker> listOfModuleMarkers) {
		String staticAnalyserMarkersString = "";
		for (ModuleMarker staticAnalyserMarker : listOfModuleMarkers) {
			staticAnalyserMarkersString = staticAnalyserMarkersString.concat(" - " + staticAnalyserMarker.toString() + "\n");
		}
		return staticAnalyserMarkersString;
	}
}
