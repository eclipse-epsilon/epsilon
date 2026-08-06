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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Persists a dataset to a CSV file
 * 
 * Adapted from org.apache.commons.text.translate.CsvTranslators
 * https://commons.apache.org/proper/commons-text/jacoco/org.apache.commons.text.translate/CsvTranslators.java.html
 * 
 * @author Alfonso de la Vega
 * @since 2.1
 */
public class CSVFile {

	public static final String CSV_DELIMITER = ",";
	private static final char CSV_QUOTE = '"';
	private static final String CSV_QUOTE_STR = String.valueOf(CSV_QUOTE);
	private static final String CSV_ESCAPED_QUOTE_STR = CSV_QUOTE_STR + CSV_QUOTE_STR;
	private static final char[] CSV_SEARCH_CHARS =
			new char[] { CSV_DELIMITER.charAt(0), CSV_QUOTE, CharUtils.CR, CharUtils.LF };

	public static String escapeCSV(String cellValue) {
		if (StringUtils.containsNone(cellValue, CSV_SEARCH_CHARS)) {
			return cellValue;
		}
		return new StringBuilder()
				.append(CSV_QUOTE)
				.append(StringUtils.replace(cellValue, CSV_QUOTE_STR, CSV_ESCAPED_QUOTE_STR))
				.append(CSV_QUOTE)
				.toString();
	}

	protected String path;
	protected Dataset dataset;
	protected PrintWriter pw;

	public CSVFile(String path, Dataset dataset) {
		this.path = path;
		this.dataset = dataset;
	}

	public void save() throws FileNotFoundException {
		File file = new File(path);
		file.getParentFile().mkdirs();
		pw = new PrintWriter(file);
		headerRecord(dataset.getColumnNames());
		for (List<ValueWrapper> wrappers : dataset.getRows()) {
			rowRecord(wrappers);
		}
		pw.close();
	}

	protected void headerRecord(List<String> columnNames) {
		addRecord(columnNames);
	}

	protected void rowRecord(List<ValueWrapper> wrappers) {
		List<String> records = new ArrayList<>(wrappers.size());
		for (ValueWrapper wrapper : wrappers) {
			records.add(escapeCSV(wrapper.toString()));
		}
		addRecord(records);
	}

	protected void addRecord(List<String> cellValues) {
		pw.println(String.join(CSV_DELIMITER, cellValues));
	}
}
