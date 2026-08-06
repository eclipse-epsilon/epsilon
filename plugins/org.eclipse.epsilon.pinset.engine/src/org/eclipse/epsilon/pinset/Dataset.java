/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *********************************************************************/
package org.eclipse.epsilon.pinset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Dataset.
 *
 * @author Alfonso de la Vega
 * @since 2.1
 */
public class Dataset {
	private List<String> columnNames;
	private List<List<ValueWrapper>> rowsList;
	private Map<String, List<ValueWrapper>> colValuesByName;

	public Dataset() {
		columnNames = new ArrayList<>();
		rowsList = new ArrayList<>();
		colValuesByName = new HashMap<>();
	}

	public List<String> getColumnNames() {
		return columnNames;
	}

	public List<List<ValueWrapper>> getRows() {
		return rowsList;
	}

	public List<ValueWrapper> getValuesByColumn(String columnName) {
		return colValuesByName.get(columnName);
	}

	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
		for (String colName : columnNames) {
			colValuesByName.put(colName, new ArrayList<ValueWrapper>());
		}
	}

	/**
	 * Add all column values of a row at once.
	 * They MUST be given in the same order as the column names were.
	 */
	public void addColumnValues(List<Object> values) {
		List<ValueWrapper> wrappers = new ArrayList<>();
		for (Object value : values) {
			wrappers.add(new ValueWrapper(value));
		}
		rowsList.add(wrappers);
		Iterator<String> colIt = columnNames.iterator();
		Iterator<ValueWrapper> wrappersIt = wrappers.iterator();
		while (colIt.hasNext() && wrappersIt.hasNext()) {
			String colName = colIt.next();
			ValueWrapper wrapper = wrappersIt.next();
			colValuesByName.get(colName).add(wrapper);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.join(CSVFile.CSV_DELIMITER, getColumnNames())).append("\n");
		for (List<ValueWrapper> wrappers : getRows()) {
			sb.append(wrappers.stream()
					.map(wrapper -> CSVFile.escapeCSV(wrapper.toString()))
					.collect(Collectors.joining(CSVFile.CSV_DELIMITER)))
			.append("\n");
		}
		return sb.toString();
	}
}
