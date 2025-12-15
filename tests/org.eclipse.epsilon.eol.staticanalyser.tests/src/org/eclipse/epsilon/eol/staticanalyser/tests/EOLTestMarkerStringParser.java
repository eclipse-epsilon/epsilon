package org.eclipse.epsilon.eol.staticanalyser.tests;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.epsilon.common.module.ModuleMarker;
import org.eclipse.epsilon.common.module.ModuleMarker.Severity;
import org.eclipse.epsilon.common.parse.Region;

public class EOLTestMarkerStringParser {

	private int lineCounter = 0;
	
	public List<ModuleMarker> extractTestMarkers(File testProgram) throws IOException {
		lineCounter = 0;
		
		String content = new String(Files.readAllBytes(testProgram.toPath()));
		String[] lines = content.split(System.lineSeparator());
		List<ModuleMarker> testMarkerList = new ArrayList<ModuleMarker>();
		
		for (String line : lines) {
			ModuleMarker testLineMarker = createTestMarker(line);
			if (null != testLineMarker) {
				testMarkerList.add(testLineMarker);
			}
			lineCounter++;
		}
		return testMarkerList;
	}

	// Format for test files //![startline:startcolumn-endline:endcolumn] message
	private ModuleMarker createTestMarker(String testMarkedProgramLine) {
		if (testMarkedProgramLine == null 
				|| testMarkedProgramLine.isEmpty() 
				|| testMarkedProgramLine.length() < 3) {
            return null;
        }
		// Just in case...
		if (!testMarkedProgramLine.substring(0, 2).equals("//")) {
			return null;
		}

		ModuleMarker testMarker = new ModuleMarker();
		// Extract Severity level
		String severityString = testMarkedProgramLine.substring(0, 3);
		switch (severityString) {
		case "//!": // ERROR
			testMarker.setSeverity(Severity.Error);
			break;
		case "//?": // WARNING
			testMarker.setSeverity(Severity.Warning);
			break;
		case "// ": // Comment
			return null;
		default:
			// Report anything else that turns up
			System.err.println(String.format(" Check test Marker or comment on line %s : %s",
					lineCounter, testMarkedProgramLine));
			return null;
		}

		// Region column and line information
		Region region = extractRegion(testMarkedProgramLine);
		if(null == region) {
			// problem with region string, assume old test style for now.
			System.err.println("Problem with region in test marker: " + testMarkedProgramLine);
			testMarker.setRegion(null);					
			testMarker.setMessage(testMarkedProgramLine.substring(3));
			return testMarker;
		} else {
			testMarker.setRegion(region);
		}
		
		// Message
		testMarker.setMessage(messageStringMatcher(testMarkedProgramLine));
		return testMarker;
	}

	private Region extractRegion(String testMarkedProgramLine) {
		String regionString = regionStringMatcher(testMarkedProgramLine);
		if(regionString.isEmpty()) {
			return null;
		}
		
		String[] regions = regionString.split("-");
		String[] startRegion = regions[0].split(":");
		String[] endRegion = regions[1].split(":");

		return new Region(Integer.parseInt(startRegion[0]), Integer.parseInt(startRegion[1]),
				Integer.parseInt(endRegion[0]), Integer.parseInt(endRegion[1]));
	}

	private String regionStringMatcher(String line) {
		java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\\[(.+?)\\]");
		java.util.regex.Matcher matcher = pattern.matcher(line);

		if (matcher.find()) {
			// Group 1 contains the content inside the parentheses (the captured group)
			return matcher.group(1);
		} else {
			// No match found
			return "";
		}
	}

	private String messageStringMatcher(String line) {
		java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\\[.+?\\](.*)");
        java.util.regex.Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            // Group 1 contains the content that follows the closing bracket
            String rawMessage = matcher.group(1);
            return rawMessage.trim(); 
        } else {
            // No match found
            return "";
        }
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
}
