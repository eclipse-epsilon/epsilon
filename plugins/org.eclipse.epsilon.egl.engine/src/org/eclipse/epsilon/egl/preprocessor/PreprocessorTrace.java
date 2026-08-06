/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.preprocessor;

import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import org.eclipse.epsilon.egl.util.FileUtil;

import com.google.common.collect.TreeBasedTable;

public class PreprocessorTrace {

	private Map<Integer, Integer> lineNumberMapping = new TreeMap<>();

	// Rows are lines. It's important that we use a tree-based table so we get row entries to be sorted by column.
	private TreeBasedTable<Integer, Integer, Integer> columnCorrections = TreeBasedTable.create();

	private int currentEolLine = 1;
	private int maximumEglLineNumber = 1;

	
	public int getEglLineNumberFor(int eolLine) {
		if (lineNumberMapping.containsKey(eolLine))
			return lineNumberMapping.get(eolLine);
		
		return maximumEglLineNumber;
	}
	
	public int getEglColumnNumberFor(int eolLine, int eolCol) {
		if (columnCorrections.containsRow(eolLine)) {
			SortedMap<Integer, Integer> rowUpToColumn = columnCorrections.row(eolLine).headMap(eolCol + 1);
			if (!rowUpToColumn.isEmpty()) {
				int correction = rowUpToColumn.get(rowUpToColumn.lastKey());
				return eolCol + correction;
			}
		}

		return eolCol;
	}

	void setEglLineNumberForCurrentEolLineNumber(int eglLineNumber) {
		currentEolLine++;
		lineNumberMapping.put(currentEolLine, eglLineNumber);
		maximumEglLineNumber = Math.max(maximumEglLineNumber, eglLineNumber);
	}
	
	void incrementColumnCorrectionNumber(int currentEolColumn, int correction) {
		if (columnCorrections.containsRow(currentEolLine)) {
			SortedMap<Integer, Integer> row = columnCorrections.row(currentEolLine);

			Integer lastColumn = row.lastKey();
			if (currentEolColumn < lastColumn) {
				throw new IllegalStateException(String.format(
					"Tried to increment column correction number for EOL column %d, "
					+ "which is before the last EOL column number %d",
					currentEolColumn, lastColumn));
			}

			row.put(currentEolColumn, row.get(lastColumn) + correction);
		} else {
			columnCorrections.row(currentEolLine).put(currentEolColumn, correction);
		}
	}
	
	void reset() {
		lineNumberMapping.clear();
		columnCorrections.clear();
		currentEolLine = 0;
		maximumEglLineNumber = 1;
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		for (int eolLine = 1; eolLine <= currentEolLine; eolLine++) {
			builder.append(eolLine);
			builder.append(" -> ");
			builder.append(getEglLineNumberFor(eolLine));

			boolean bFirst = true;
			builder.append(" [");
			for (Entry<Integer, Integer> lineCorrection : columnCorrections.row(eolLine).entrySet()) {
				if (bFirst) bFirst = false; else builder.append(", ");
				builder.append(lineCorrection.getKey());
				builder.append(" -> ");
				builder.append(lineCorrection.getValue());
			}
			builder.append("]");
			builder.append(FileUtil.NEWLINE);
		}

		return builder.toString();
	}
}
