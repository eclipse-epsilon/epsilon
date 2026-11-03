/*******************************************************************************
 * Copyright (c) 2026 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 ******************************************************************************/
package org.eclipse.epsilon.coverage;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.epsilon.coverage.model.CoverageElement;
import org.eclipse.epsilon.coverage.model.SourceFile;

/**
 * Computes the coverage status of the lines of a source file. A probe
 * contributes to the line on which it starts; probes that contain no other
 * probes (e.g. multi-line EGL static text sections) contribute to all the
 * lines they span. A line is fully covered if all its probes have been
 * executed, not covered if none of them has, and partially covered otherwise.
 * 
 * @since 2.10
 */
public class CoverageLines {
	
	public enum Status { FULL, PARTIAL, NONE }
	
	protected static class LineCounter {
		int covered, total;
		
		Status getStatus() {
			if (covered == total) return Status.FULL;
			else if (covered == 0) return Status.NONE;
			else return Status.PARTIAL;
		}
	}
	
	protected final Map<Integer, LineCounter> lines = new TreeMap<>();
	
	public CoverageLines(SourceFile file) {
		this(file.getElements());
	}
	
	public CoverageLines(Collection<CoverageElement> elements) {
		for (CoverageElement element : elements) visit(element);
	}
	
	/**
	 * @return Whether the element (or any of its descendants) is a probe
	 */
	protected boolean visit(CoverageElement element) {
		boolean hasProbeDescendants = false;
		for (CoverageElement child : element.getChildren()) {
			hasProbeDescendants |= visit(child);
		}
		if (element.isProbe()) {
			int endLine = element.getStartLine();
			if (!hasProbeDescendants) {
				endLine = element.getEndLine();
				// Regions that end at the start of a line do not cover that line
				if (element.getEndColumn() == 0 && endLine > element.getStartLine()) endLine--;
			}
			for (int line = element.getStartLine(); line <= endLine; line++) {
				LineCounter counter = lines.computeIfAbsent(line, l -> new LineCounter());
				counter.total++;
				if (element.getHits() > 0) counter.covered++;
			}
		}
		return element.isProbe() || hasProbeDescendants;
	}
	
	/**
	 * @return The status of the line (1-based) or null if it contains no probes
	 */
	public Status getStatus(int line) {
		LineCounter counter = lines.get(line);
		return counter == null ? null : counter.getStatus();
	}
	
	/**
	 * @return The status of all lines that contain probes, ordered by line number
	 */
	public Map<Integer, Status> getStatuses() {
		Map<Integer, Status> statuses = new TreeMap<>();
		for (Map.Entry<Integer, LineCounter> entry : lines.entrySet()) {
			statuses.put(entry.getKey(), entry.getValue().getStatus());
		}
		return statuses;
	}
	
	public int getCoveredLines() {
		int count = 0;
		for (LineCounter counter : lines.values()) if (counter.getStatus() == Status.FULL) count++;
		return count;
	}
	
	public int getPartiallyCoveredLines() {
		int count = 0;
		for (LineCounter counter : lines.values()) if (counter.getStatus() == Status.PARTIAL) count++;
		return count;
	}
	
	public int getTotalLines() {
		return lines.size();
	}
}
